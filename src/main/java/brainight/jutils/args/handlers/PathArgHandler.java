

package brainight.jutils.args.handlers;

import brainight.jutils.args.ArgsException;
import brainight.jutils.args.CmdArgsHolder;
import brainight.jutils.args.annotations.A;
import brainight.jutils.args.annotations.O;
import java.nio.file.Path;

/**
 * Github: https://github.com/Brainight
 * @author Brainight
 */
public class PathArgHandler implements ArgHandler<Path>{

    @Override
    public Path parseArgument(CmdArgsHolder args, A a) throws ArgsException {
        return Path.of(args.getCurrent());
    }

    @Override
    public Path parseOption(CmdArgsHolder args, O o) throws ArgsException {
        return Path.of(args.getNext());
    }

}
