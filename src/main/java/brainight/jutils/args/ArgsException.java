

package brainight.jutils.args;

/**
 * Github: https://github.com/Brainight
 * @author Brainight
 */
public class ArgsException extends Exception{

    public ArgsException() {
    }

    public ArgsException(String message) {
        super(message);
    }

    public ArgsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgsException(Throwable cause) {
        super(cause);
    }

    public ArgsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    
}
