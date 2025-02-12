package brainight.jutils.misc;


import java.io.OutputStream;
import java.io.PrintStream;

/**
 *
 * @author MK - github.com/Brainight
 */
public interface ICacheService<K, V> {

    V get(K key);

    void dump(OutputStream os);
}
