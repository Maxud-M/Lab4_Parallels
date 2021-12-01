import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoreActor extends AbstractActor {
    private Map<Integer, ArrayList<Integer>> store = new HashMap<>();

    public static class StoreTestResult{
        private int packageId;
        private int result;

        public int getResult() {
            return result;
        }

        public int getPackageId() {
            return packageId;
        }
    }

    public static class GetMessage{
        int packageId;
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(StoreTestResult.class, m -> {
                    ArrayList<Integer> results = store.get(m.getPackageId());
                    if(results == null) {
                        results = new ArrayList<>(0);
                    }
                    results.add(m.getResult());
                    store.put(m.getPackageId(), results);
                    System.out.println("recieve test");
        })
                .match(GetMessage.class, req -> sender().tell(new StoreTestResult(req.getKey(), store.get(req.getKey())), self())).build();
    }
}
