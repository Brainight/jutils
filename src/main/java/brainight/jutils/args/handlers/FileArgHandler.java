package brainight.jutils.args.handlers;

import brainight.jutils.args.ArgsException;
import brainight.jutils.args.CmdArgsHolder;
import brainight.jutils.args.annotations.wrappers.Argument;
import brainight.jutils.args.annotations.wrappers.Option;
import java.io.File;

/**
 * Github: https://github.com/Brainight
 *
 * @author Brainight
 */
public class FileArgHandler implements ArgHandler<File> {

    @Override
    public File parseArgument(CmdArgsHolder args, Argument a, Object target) throws ArgsException {
        return new File(args.getCurrent());
    }

    @Override
    public File parseOption(CmdArgsHolder args, Option o, Object target) throws ArgsException {
        return new File(args.getNext());
    }

}
