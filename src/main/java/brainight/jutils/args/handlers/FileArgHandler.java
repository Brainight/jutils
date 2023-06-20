

package brainight.jutils.args.handlers;

import brainight.jutils.args.ArgsException;
import brainight.jutils.args.CmdArgsHolder;
import brainight.jutils.args.annotations.A;
import brainight.jutils.args.annotations.O;
import java.io.File;

/**
 * Github: https://github.com/Brainight
 * @author Brainight
 */
public class FileArgHandler implements ArgHandler<File>{

    @Override
    public File parseArgument(CmdArgsHolder args, A a) throws ArgsException {
        return new File(args.getCurrent());
    }

    @Override
    public File parseOption(CmdArgsHolder args, O o) throws ArgsException {
        return new File(args.getNext());
    }

}
