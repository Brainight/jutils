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
public class StringArgHandler implements ArgHandler<String> {

    @Override
    public String parseArgument(CmdArgsHolder args, A a) throws ArgsException {
        return args.getCurrent();
    }

    @Override
    public String parseOption(CmdArgsHolder args, O o) throws ArgsException {
        return args.getNext();
    }

}
