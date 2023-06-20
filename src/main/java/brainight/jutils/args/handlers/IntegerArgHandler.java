package brainight.jutils.args.handlers;

import brainight.jutils.args.ArgsException;
import brainight.jutils.args.CmdArgsHolder;
import brainight.jutils.args.annotations.A;
import brainight.jutils.args.annotations.O;

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
    public Integer parseArgument(CmdArgsHolder args, A a) throws ArgsException {
        return this.parse(args.getCurrent());
    }

    @Override
    public Integer parseOption(CmdArgsHolder args, O o) throws ArgsException {
        return this.parse(args.getNext());
    }

    protected Integer parse(String arg) throws ArgsException{
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException nfe) {
            throw new ArgsException("Cant convert argument '" + arg + "' to int");
        }
    }

}
