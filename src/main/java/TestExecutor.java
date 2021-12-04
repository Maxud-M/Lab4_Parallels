import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class TestExecutor extends AbstractActor {
    ActorRef storeActor;

    TestExecutor(ActorRef storeActor) {
        this.storeActor = storeActor;
    }

    String execute(Message m) throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(m.getJsScript());
        Invocable invocable = (Invocable) engine;
        String result = invocable.invokeFunction(m.getFunctionName(), m.getParams()).toString();

        return result;
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create().match(Message.class, m -> {
            String result = execute(m);
            storeActor.tell(new StoreActor.StoreMessage(m.getPackageId(), result), ActorRef.noSender());
        }).build();
    }

    public static class Message{
        private int packageId;

        private String functionName;

        private String jsScript;
        private Object[] params;
        private String expectedResult;

        Message(int packageId, String functionName, String jsScript, Object[] params, String expectedResult) {
            this.packageId = packageId;
            this.functionName = functionName;
            this.jsScript = jsScript;
            this.params = params;
            this.expectedResult = expectedResult;
        }

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

        public Object[] getParams() {
            return params;
        }
    }
}
