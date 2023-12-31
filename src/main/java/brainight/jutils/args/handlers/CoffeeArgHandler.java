package brainight.jutils.args.handlers;

import brainight.jutils.args.ArgsException;
import brainight.jutils.args.CmdArgsHolder;
import brainight.jutils.args.annotations.wrappers.Argument;
import brainight.jutils.args.annotations.wrappers.Option;

/**
 * Dummy useless handler Github: https://github.com/Brainight
 *
 * @author Brainight
 */
public class CoffeeArgHandler implements ArgHandler {

    @Override
    public Object parseArgument(CmdArgsHolder args, Argument a, Object target) throws ArgsException {
        return null;
    }

    @Override
    public Object parseOption(CmdArgsHolder args, Option o, Object target) throws ArgsException {
        return null;
    }

}
