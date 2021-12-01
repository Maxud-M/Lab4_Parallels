import akka.actor.AbstractActor;

public class ExampleActor extends AbstractActor {
    public static enum Msg {
        GREET, DONE, ERROR;
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals(Msg.GREET, m -> {
                    System.out.println("Hello World!");
                    sender().tell(Msg.DONE, self());
                })
                .build();
    }
}