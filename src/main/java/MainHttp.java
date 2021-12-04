import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.server.Route;
import akka.pattern.PatternsCS;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;

public class MainHttp {

    static final Duration TIMEOUT = Duration.ofSeconds(5);
    public static final String TEST_EXECUTION_STARTED_MESSAGE = "test execution started";

    ActorSystem system;
    ActorRef storeActor;
    ActorRef routerActor;

    MainHttp(ActorSystem system, ActorRef storeActor, ActorRef routerActor){
        this.system = system;
        this.storeActor = storeActor;
        this.routerActor = routerActor;
    }

    public Route createRoute() {
        return route(
                get(
                        () -> parameter(Constants.PACKAGE_ID, (parameter) -> {
                            int packageId = Integer.parseInt(parameter);
                            CompletionStage<Object> result = PatternsCS.ask(storeActor, new StoreActor.GetMessage(packageId), TIMEOUT);
                            return completeOKWithFuture(
                                    result,
                                    Jackson.marshaller()
                            );
                        })
                ),
                post(
                        () -> entity(Jackson.unmarshaller(PackageTests.class), (message) -> {
                            for(int i = 0; i < message.getTests().size(); ++i) {
                                PackageTests.Test test = message.getTests().get(i);
                                routerActor.tell(
                                        new TestExecutor.Message(message.getPackageId(), message.getFunctionName(),
                                                message.getJsScript(), test.getParams(), test.getExpectedResult()), ActorRef.noSender()
                                );
                            }
                            return complete(TEST_EXECUTION_STARTED_MESSAGE);
                        })
                )
        );
    }
}