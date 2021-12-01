import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("test");
        ArrayList<Routees> routees;
        routees.add()
        Router router = new Router(new RoundRobinPool(), routees);
        ActorRef router = system.actorOf((Props.empty().withRouter(new RoundRobinPool(5))));


    }
}
