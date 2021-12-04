import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.server.Route;
import akka.pattern.PatternsCS;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;

public class MainHttp {

    static final Duration TIMEOUT = Duration.ofSeconds(5);
    public static final String STORE_ACTOR_PATH = "/user/storeActor";
    public static final String ROUTER_ACTOR_PATH = "/user/router";
    public static final String TEST_EXECUTION_STARTED_MESSAGE = "test execution started";

    ActorSystem system;
    MainHttp(ActorSystem system){
        this.system = system;
    }

    public Route createRoute(ActorSystem system) {
        return route(
                get(
                        () -> parameter(Constants.PACKAGE_ID, (parameter) -> {
                            int packageId = Integer.parseInt(parameter);
                            ActorSelection storeActor = system.actorSelection(STORE_ACTOR_PATH);
                            CompletionStage<Object> result = PatternsCS.ask(storeActor, new StoreActor.GetMessage(packageId), TIMEOUT);
                            return completeOKWithFuture(
                                    result,
                                    Jackson.marshaller()
                            );
                        })
                ),
                post(
                        () -> entity(Jackson.unmarshaller(PackageTests.class), (message) -> {
                            ActorSelection router = system.actorSelection(ROUTER_ACTOR_PATH);
                            for(int i = 0; i < message.getTests().size(); ++i) {
                                PackageTests.Test test = message.getTests().get(i);
                                router.tell(
                                        new TestExecutor.Message(message.getPackageId(), message.getFunctionName(),
                                                message.getJsScript(), test.getParams(), test.getExpectedResult()), ActorRef.noSender());
                            }
                            return complete(TEST_EXECUTION_STARTED_MESSAGE);
                        })
                )
        );
    }
}