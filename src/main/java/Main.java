import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("test");
        ActorRef router = system.actorOf(new RoundRobinPool(5)
                .props(Props.create(StoreActor.class))


    }
}
