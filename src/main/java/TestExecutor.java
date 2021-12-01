import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.japi.pf.ReceiveBuilder;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;

public class TestExecutor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create().match(Message.class, m -> {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
            engine.eval(m.getJsScript());
            Invocable invocable = (Invocable) engine;
            String result = invocable.invokeFunction(m.getFunctionName(), m.getParams()).toString();
            sender().tell(new StoreActor.StoreMessage(m.getPackageId(), result), self());
        }).build();
    }

    public class Message{
        private int packageId;
        private String functionName;
        private String jsScript;
        private ArrayList<String> params;
        private String expectedResult;

        public int getPackageId() {
            return packageId;
        }
        public String getFunctionName() {
            return functionName;
        }

        public String getJsScript() {
            return jsScript;
        }

        public String getExpectedResult() {
            return expectedResult;
        }

        public ArrayList<String> getParams() {
            return params;
        }
    }



    public class
}
