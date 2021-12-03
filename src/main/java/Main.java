import akka.NotUsed;
import akka.actor.*;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;
import akka.pattern.PatternsCS;
import akka.routing.*;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.util.Timeout;
import scala.util.parsing.json.JSON;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;

public class Main {
    static final Duration TIMEOUT = Duration.ofSeconds(5);

    public class MainHttp {
        public Route createRoute(ActorSystem system) {
            route(get())
            return route(
                    get(
                            () -> parameter("packageId", (parameter) -> {
                                int packageId = Integer.parseInt(parameter);
                                ActorSelection storeActor = system.actorSelection("/user/storeActor");
                                CompletionStage<Object> result = PatternsCS.ask(storeActor, new StoreActor.GetMessage(packageId), TIMEOUT);
                                return completeOKWithFuture(
                                        result,
                                        Jackson.marshaller()
                                );
                            })
                    ),
                    post(() -> entity(Jackson.unmarshaller(PackageTests.class)), (message) -> )

            );
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("routes");
        ActorRef storeActor = system.actorOf(Props.create(StoreActor.class), "storeActor");
        final SupervisorStrategy strategy =
                new OneForOneStrategy(
                        5,
                        Duration.ofMinutes(1),
                        Collections.<Class<? extends Throwable>>singletonList(Exception.class));
        ActorRef router = system.actorOf(new RoundRobinPool(5).withSupervisorStrategy(strategy).props(Props.create(TestExecutor.class)), "router");
        Route route;
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        MainHttp instance = new MainHttp(system);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =
                instance.createRoute(system).flow(system, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost("localhost", 8080),
                materializer
        );
        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());


    }
}
