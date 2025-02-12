package brainight.jutils.misc;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 *
 * @author MK - github.com/Brainight
 */
public abstract class AbstractCacheService<K, V> implements ICacheService<K, V> {

    protected class KeyValue<K, V> {

        private final V value;
        private final K key;

        KeyValue(K k, V v) {
            this.key = k;
            this.value = v;
        }
    }

    private ConcurrentHashMap<K, V> _data = new ConcurrentHashMap<>();
    private ReentrantLock lock = new ReentrantLock();
    private Set<K> _processing = new HashSet<>();
    private Consumer<KeyValue<K, V>> onFinish = (kv) -> this.onFinishProcessing(kv);
    private TimeoutExecutor<KeyValue<K, V>> executor = new TimeoutExecutor(onFinish);
    private long timeoutMs = 200L;
    private long waitingProcessSleep = timeoutMs > 40 ? 20 : 2;

    protected AbstractCacheService() {

    }

    public void dump(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        System.out.println("###### DUMP ######");
        for (K k : _data.keySet()) {
            pw.printf("%s : %s\n", k, _data.get(k));
        }
        pw.flush();
    }

    protected abstract V resolve(K key) throws Exception;

    protected KeyValue<K, V> _resolve(K key) throws Exception {
        V v = this.resolve(key);
        return new KeyValue(key, v);
    }

    public V get(K key) {
        V val = this._data.get(key);
        if (val != null) {
            return val;
        }
        try {
            if (!process(key)) {
                System.out.println(Thread.currentThread().toString() + " Waiting for previously submitted task for: " + key.toString());
                return waitSubmited(key);
            } else {
                System.out.println(Thread.currentThread().toString() + " Submitting task for: " + key.toString());
                return submit(key);
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        return val;
    }

    private V submit(K key) throws ExecutionException, InterruptedException {
        KeyValue<K, V> kv = executor.submit(() -> _resolve(key), timeoutMs);
        if (kv == null) {
            System.out.println(Thread.currentThread().toString() + " Execution timed out.");
            return null;
        }
        this.onFinishProcessing(kv);
        return kv.value;
    }

    private V waitSubmited(K key) throws InterruptedException, ExecutionException {
        Future<V> future = executor.submit(() -> {
            while (this.isProcessing(key)) {
                try {
                    Thread.sleep(waitingProcessSleep);
                } catch (InterruptedException ie) {
                    break;
                }
            }
            return this._data.get(key);
        });
        V v = null;
        try {
            v = future.get(timeoutMs, TimeUnit.MILLISECONDS);
        } catch (TimeoutException ex) {
            System.out.println(Thread.currentThread().toString() + " Waiting submitted task " + key.toString() + " timeoud.");
            v = this._data.get(key);
        } finally {
            future.cancel(true);
        }
        return v;
    }

    protected void onFinishProcessing(KeyValue<K, V> kv) {
        this.lock.lock();
        try {
            this._processing.remove(kv.key);
            System.out.println(Thread.currentThread().toString() + " Removed processing " + kv.key);
            this._data.put(kv.key, kv.value);
            System.out.println(Thread.currentThread().toString() + " Add to cache " + kv.key);
        } finally {
            this.lock.unlock();
        }
    }

    protected boolean isProcessing(K key) {
        this.lock.lock();
        try {
            return this._processing.contains(key);
        } finally {
            this.lock.unlock();
        }
    }

    protected boolean process(K key) {
        this.lock.lock();
        try {
            boolean inprocess = this._processing.contains(key);
            if (!inprocess) {
                System.out.println(Thread.currentThread().toString() + " Set processing " + key);
                this._processing.add(key);
                inprocess = true;
                return true;
            }
            return false;
        } finally {
            this.lock.unlock();
        }
    }
}
