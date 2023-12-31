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
public class StringArgHandler implements ArgHandler<String> {

    @Override
    public String parseArgument(CmdArgsHolder args, Argument a, Object target) throws ArgsException {
        return args.getCurrent();
    }

    @Override
    public String parseOption(CmdArgsHolder args, Option o, Object target) throws ArgsException {
        return args.getNext();
    }

}
