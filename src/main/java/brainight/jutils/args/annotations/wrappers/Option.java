package brainight.jutils.args.annotations.wrappers;

import brainight.jutils.args.annotations.O;
import brainight.jutils.args.handlers.ArgHandler;
import java.lang.reflect.Field;

/**
 * Github: https://github.com/Brainight
 *
 * @author Brainight
 */
public class Option {

    protected final O o;
    protected final ArgHandler handler;
    protected final Field field;

    public Option(O o, Field f, ArgHandler handler) {
        this.o = o;
        this.field = f;
        this.handler = handler;
    }

    public ArgHandler getHandler() {
        return handler;
    }

    public O getO() {
        return o;
    }

    public Field getField() {
        return field;
    }
}
