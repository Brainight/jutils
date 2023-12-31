package brainight.jutils.args.handlers;

import brainight.jutils.args.ArgsException;
import brainight.jutils.args.CmdArgsHolder;
import brainight.jutils.args.annotations.wrappers.Argument;
import brainight.jutils.args.annotations.wrappers.Option;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Github: https://github.com/Brainight
 *
 * @author Brainight
 */
public interface ArgHandler<T> {

    T parseArgument(CmdArgsHolder args, Argument a, Object target) throws ArgsException;

    T parseOption(CmdArgsHolder args, Option o, Object target) throws ArgsException;

    default Class<T> getTargetClass() throws ArgsException {
        ParameterizedType ptype = null;
        try {
            for (Type t : this.getClass().getGenericInterfaces()) {
                if (t instanceof ParameterizedType && ((ParameterizedType) t).getRawType() == ArgHandler.class) {
                    ptype = ((ParameterizedType) t);
                }
            }
            Class<T> clazz = (Class<T>) ptype.getActualTypeArguments()[0];
            return clazz;
        } catch (ClassCastException cce) {
            throw new ArgsException(cce);
        }

    }

}
