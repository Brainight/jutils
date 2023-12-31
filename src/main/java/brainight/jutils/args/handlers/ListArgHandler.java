package brainight.jutils.args.handlers;

import brainight.jutils.args.ArgsException;
import brainight.jutils.args.CmdArgsHolder;
import brainight.jutils.args.annotations.wrappers.Argument;
import brainight.jutils.args.annotations.wrappers.Option;
import brainight.jutils.refl.ReflectionException;
import brainight.jutils.refl.ReflectionHandler;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 * Github: https://github.com/Brainight
 *
 * @author Brainight
 */
public class ListArgHandler implements ArgHandler<List> {

    @Override
    public List<?> parseArgument(CmdArgsHolder args, Argument a, Object target) throws ArgsException {
        try {
            Class entryType = ReflectionHandler.getHandler().getComponentTypeForList(a.getField());
            String sArgs = args.getCurrent();
            String[] values = sArgs.split(a.getA().multiValueSeparator());
            List result = ReflectionHandler.getHandler().getListImplementation(a.getField(), target);
            this.addValues(values, entryType, result);
            return result;
        } catch (ReflectionException ex) {
            throw new ArgsException(ex);
        }
    }

    @Override
    public List<?> parseOption(CmdArgsHolder args, Option o, Object target) throws ArgsException {
        try {
            Class entryType = ReflectionHandler.getHandler().getComponentTypeForList(o.getField());
            String sArgs = args.getNext();
            String[] values = sArgs.split(o.getO().multiValueSeparator());
            List result = ReflectionHandler.getHandler().getListImplementation(o.getField(), target);
            this.addValues(values, entryType, result);
            return result;
        } catch (ReflectionException ex) {
            throw new ArgsException(ex);
        }
    }

    private void addValues(String[] values, Class castTo, List result) throws ArgsException {
        if (castTo.isAssignableFrom(String.class)) {
            addStringValues(values, result);
        } else if (castTo.isAssignableFrom(Integer.class)) {
            addIntegerValues(values, result);
        } else if (castTo.isAssignableFrom(File.class)) {
            addFileValues(values, result);
        } else if (castTo.isAssignableFrom(Path.class)) {
            addPathValues(values, result);
        }
    }

    private void addStringValues(String[] values, List result) throws ArgsException {
        for (String s : values) {
            result.add(s);
        }
    }

    private void addIntegerValues(String[] values, List result) throws ArgsException {
        try {
            for (String s : values) {
                Integer i = Integer.parseInt(s);
                result.add(i);
            }
        } catch (NumberFormatException nfe) {
            throw new ArgsException(nfe);
        }

    }

    private void addFileValues(String[] values, List result) throws ArgsException {
        for (String s : values) {
            result.add(new File(s));
        }
    }

    private void addPathValues(String[] values, List result) throws ArgsException {
        for (String s : values) {
            result.add(Path.of(s));
        }
    }

}
