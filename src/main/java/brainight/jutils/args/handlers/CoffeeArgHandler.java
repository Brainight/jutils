

package brainight.jutils.args.handlers;

import brainight.jutils.args.ArgsException;
import brainight.jutils.args.CmdArgsHolder;
import brainight.jutils.args.annotations.A;
import brainight.jutils.args.annotations.O;

/**
 * Dummy useless handler
 * Github: https://github.com/Brainight
 * @author Brainight
 */
public class CoffeeArgHandler implements ArgHandler{

    @Override
    public Object parseArgument(CmdArgsHolder args, A a) throws ArgsException {
        return null;
    }

    @Override
    public Object parseOption(CmdArgsHolder args, O o) throws ArgsException {
       return null;
    }

}
