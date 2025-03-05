package brainight.jutils.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author MK - github.com/Brainight
 */
public class Tester {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(100);
        ICacheService<String, String> srv = new DummyCacheService() {

            @Override
            public void onFinishProcessing(KeyValue<String, String> kv) {
                super.onFinishProcessing(kv);
                cdl.countDown();
                System.out.println("Remaining: " + cdl.getCount());
            }
        };
        List<String> identifiers = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            identifiers.add("ID_" + i);
        }

        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                for (String id : identifiers) {
                    String value = srv.get(id);
                    System.out.println(Thread.currentThread().getName() + " - ID: " + id + " - RESULT: " + value);
                }
            }).start();
        }
        cdl.await();
        srv.dump(System.out);
    }
}
