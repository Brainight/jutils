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
public class BooleanArgHandler implements ArgHandler<Boolean> {

    @Override
    public Boolean parseArgument(CmdArgsHolder args, Argument a, Object target) throws ArgsException {
        return Boolean.valueOf(args.getCurrent());
    }

    @Override
    public Boolean parseOption(CmdArgsHolder args, Option o, Object target) throws ArgsException {
        return Boolean.valueOf(args.getNext());
    }

}
