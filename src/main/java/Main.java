import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("test");
        RoundRobinPool router = new RoundRobinPool(5);
        Router router1 = router.createRouter(system);
        

    }
}
    x