package brainight.jutils.args.annotations.wrappers;

import brainight.jutils.args.annotations.A;
import brainight.jutils.args.handlers.ArgHandler;
import java.lang.reflect.Field;

/**
 * Github: https://github.com/Brainight
 *
 * @author Brainight
 */
public class Argument {

    protected final A a;
    protected final ArgHandler handler;
    protected final Field field;

    public Argument(A a, Field f, ArgHandler handler) {
        this.a = a;
        this.field = f;
        this.handler = handler;
    }

    public ArgHandler getHandler() {
        return handler;
    }


    public A getA() {
        return a;
    }

    public Field getField() {
        return field;
    }
}
