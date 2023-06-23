/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package brainight.jutils.args.handlers;

import brainight.jutils.args.ArgsException;
import brainight.jutils.args.CmdArgsHolder;
import brainight.jutils.args.annotations.A;
import brainight.jutils.args.annotations.O;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Github: https://github.com/Brainight
 *
 * @author Brainight
 */
public interface ArgHandler<T> {

    T parseArgument(CmdArgsHolder args, A a) throws ArgsException;

    T parseOption(CmdArgsHolder args, O o) throws ArgsException;

    default Class<T> getTargetClass() throws ArgsException {
        ParameterizedType ptype = null;
        try {
            for (Type t : this.getClass().getGenericInterfaces()) {
                if(t instanceof ParameterizedType && ((ParameterizedType)t).getRawType() == ArgHandler.class){
                    ptype = ((ParameterizedType)t);
                }
            }
            Class<T> clazz = (Class<T>) ptype.getActualTypeArguments()[0];
            return clazz;
        } catch (ClassCastException cce) {
            throw new ArgsException(cce);
        }

    }

}
