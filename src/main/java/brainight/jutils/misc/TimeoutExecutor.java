package brainight.jutils.misc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MK - github.com/Brainight
 */
public class TimeoutExecutor<V> extends ThreadPoolExecutor {

    private final LinkedBlockingQueue<Future> futuresQueue = new LinkedBlockingQueue();
    private final Thread ufjThread;

    private class FutureChecker implements Runnable {

        private Consumer<V> onFinish;

        public FutureChecker(Consumer onFinish) {
            this.onFinish = onFinish;
        }

        public void run() {
            V result;
            for (;;) {
                result = null;
                try {
                    Future<V> f = futuresQueue.take();
                    if (f != null) {
                        if (f.isCancelled()) {
                            continue;
                        }
                        if (!f.isDone()) {
                            futuresQueue.add(f);
                            continue;
                        }
                        try {
                            result = f.get();
                            this.onFinish.accept(result);
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InterruptedException ie) {
                    System.out.println("Shutdown call for " + Thread.currentThread() + " ...");
                    break;
                }

            }
            System.out.println("Shutdown " + Thread.currentThread() + "");
        }

    }

    public TimeoutExecutor(Consumer onFinish) {
        super(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        this.ufjThread = new Thread(this.new FutureChecker(onFinish), "Timeout-Unfinished-Jobs-Thread");
        this.ufjThread.start();
    }

    @Override
    public void shutdown() {
        try {
            this.ufjThread.interrupt();
            this.ufjThread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            super.shutdown();
        }
    }

    public <V> V submit(Callable<V> r, long timeout) throws InterruptedException, ExecutionException {
        Future<V> task = this.submit(r);
        try {
            V value = task.get(timeout, TimeUnit.MILLISECONDS);
            return value;
        } catch (TimeoutException ex) {
            futuresQueue.add(task);
            return null;
        }
    }
}
