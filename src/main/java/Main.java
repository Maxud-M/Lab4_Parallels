import akka.actor.ActorSystem;
import akka.routing.RouterActor;

public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("test");
        RouterActor router = system.actorOf()

    }
}
