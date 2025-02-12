package brainight.jutils.args.handlers;

import brainight.jutils.args.ArgsException;
import brainight.jutils.args.CmdArgsHolder;
import brainight.jutils.args.annotations.wrappers.Argument;
import brainight.jutils.args.annotations.wrappers.Option;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Github: https://github.com/Brainight
 *
 * @author Brainight
 */
public class PathArgHandler implements ArgHandler<Path> {

    @Override
    public Path parseArgument(CmdArgsHolder args, Argument a, Object target) throws ArgsException {
        return Paths.get(args.getCurrent());
    }

    @Override
    public Path parseOption(CmdArgsHolder args, Option o, Object target) throws ArgsException {
        return Paths.get(args.getNext());
    }

}
