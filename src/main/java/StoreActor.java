import akka.actor.AbstractActor;
import akka.actor.dsl.Inbox;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoreActor extends AbstractActor {
    private Map<Integer, ArrayList<Integer>> store = new HashMap<>();

    public static class StoreMessage{
        private int packageId;
        private int result;

        StoreMessage(int packageId, int result) {
            this.packageId = packageId;
            this.result = result;
        }

        public int getResult() {
            return result;
        }

        public int getPackageId() {
            return packageId;
        }
    }

    public static class GetMessage{
        private int packageId;

        GetMessage(int packageId) {
            this.packageId = packageId;
        }

        public int getPackageId() {
            return packageId;
        }
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(StoreMessage.class, m -> {
                    ArrayList<Integer> results = store.get(m.getPackageId());
                    if(results == null) {
                        results = new ArrayList<>(0);
                    }
                    results.add(m.getResult());
                    store.put(m.getPackageId(), results);
                    System.out.println("recieve test");
        })
                .match(GetMessage.class, req -> sender().tell(store.get(req.getPackageId()), self())).build();
    }
}
