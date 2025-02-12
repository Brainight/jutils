package brainight.jutils.misc;


/**
 *
 * @author MK - github.com/Brainight
 */
public class DummyCacheService extends AbstractCacheService<String, String> {

    @Override
    protected String resolve(String key) {
        try {
            long sleep = (long) (Math.random() * 1000);
            Thread.sleep(sleep);
            return sleep + "";
        } catch (InterruptedException ex) {
            return null;
        }
    }
}
