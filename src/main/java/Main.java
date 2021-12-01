import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.RouterActor;

public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("test");
        Router router = new Router()
        ActorRef router = system.actorOf((Props.empty().withRouter(new RoundRobinPool(5))));


    }
}
