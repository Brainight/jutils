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
        ICacheService<String, String> srv = new DummyCacheService();
        List<String> identifiers = new ArrayList<>();
        CountDownLatch cdl = new CountDownLatch(400);
        for (int i = 0; i < 100; i++) {
            identifiers.add("ID_" + i);
        }

        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                for (String id : identifiers) {
                    String value = srv.get(id);
                    cdl.countDown();
                    System.out.println(Thread.currentThread().getName() + " - ID: " + id + " - RESULT: " + value);
                }
            }).start();
        }
        cdl.await();
        srv.dump(System.out);
    }
}
