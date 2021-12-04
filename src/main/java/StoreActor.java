import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoreActor extends AbstractActor {
    public static final String RECIEVE_TEST_MESSAGE = "recieve test";

    private Map<Integer, ArrayList<String>> store = new HashMap<>();

    public static class StoreMessage{
        private int packageId;
        private String result;

        StoreMessage(int packageId, String result) {
            this.packageId = packageId;
            this.result = result;
        }

        public String getResult() {
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
                    ArrayList<String> results = store.get(m.getPackageId());
                    if(results == null) {
                        results = new ArrayList<>(0);
                    }
                    results.add(m.getResult());
                    System.out.println(m.getResult());
                    store.put(m.getPackageId(), results);
                    System.out.println(RECIEVE_TEST_MESSAGE);
        })
                .match(GetMessage.class, req -> sender().tell(store.get(req.getPackageId()), self())).build();
    }
}
