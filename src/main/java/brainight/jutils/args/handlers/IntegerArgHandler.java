package brainight.jutils.args.handlers;

import brainight.jutils.args.ArgsException;
import brainight.jutils.args.CmdArgsHolder;
import brainight.jutils.args.annotations.wrappers.Argument;
import brainight.jutils.args.annotations.wrappers.Option;

/**
 * Github: https://github.com/Brainight
 *
 * @author Brainight
 */
/**
 * Github: https://github.com/Brainight
 *
 * @author Brainight
 */
public class IntegerArgHandler implements ArgHandler<Integer> {

    @Override
    public Integer parseArgument(CmdArgsHolder args, Argument a, Object target) throws ArgsException {
        return this.parse(args.getCurrent());
    }

    @Override
    public Integer parseOption(CmdArgsHolder args, Option o, Object target) throws ArgsException {
        return this.parse(args.getNext());
    }

    protected Integer parse(String arg) throws ArgsException {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException nfe) {
            throw new ArgsException("Cant convert argument '" + arg + "' to int");
        }
    }

}
