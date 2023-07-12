

package brainight.jutils.args.handlers;

import brainight.jutils.args.ArgsException;
import brainight.jutils.args.CmdArgsHolder;
import brainight.jutils.args.annotations.A;
import brainight.jutils.args.annotations.O;

/**
 * Github: https://github.com/Brainight
 * @author Brainight
 */
public class BooleanOptionHandler implements ArgHandler<Boolean>{

    @Override
    public Boolean parseArgument(CmdArgsHolder args, A a) throws ArgsException {
        return true; // ... :|
    }

    @Override
    public Boolean parseOption(CmdArgsHolder args, O o) throws ArgsException {
        return true;// ... :)
    }

    
}
