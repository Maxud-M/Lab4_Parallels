import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import java.util.ArrayList;

public class TestExecutor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create().match(Message.class, m -> {
            
        }).build();
    }

    public class Message{
        private String functionName;
        private String jsScript;
        private ArrayList<String> params;
        private String expectedResult;
    }



    public class
}