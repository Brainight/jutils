package brainight.jutils.args;

import brainight.jutils.args.annotations.O;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import brainight.jutils.args.annotations.A;
import brainight.jutils.args.handlers.ArgHandler;
import brainight.jutils.refl.ReflectionException;
import brainight.jutils.refl.ReflectionHandler;

/**
 * Github: https://github.com/Brainight
 *
 * @author Brainight
 */
public class CmdParser {

    private final ReflectionHandler REFL = ReflectionHandler.getHandler("CmdParser");
    
    Map<Class<?>, CmdArgsDef<?>> cmdArgsDefRegistry;
    Map<Class<? extends ArgHandler>, ArgHandler> argHandlers;

    public CmdParser() {
        this.cmdArgsDefRegistry = new HashMap<>();
        this.argHandlers = new HashMap<>();
        REFL.useGodMode(true);
    }

    public <T> void registerArgsDefinitionForClass(Class<T> clazz) throws ArgsException {
        CmdArgsDef<T> def = this.load(clazz);
        this.cmdArgsDefRegistry.put(clazz, def);
    }

    private <T> CmdArgsDef<T> getArgsDef(Class<T> clazz) throws ArgsException {
        CmdArgsDef<T> def = this.load(clazz);
        return def;
    }

    public <T> void registerArgsDefinition(CmdArgsDef<T> def) throws ArgsException {
        this.cmdArgsDefRegistry.put(def.getBeanType(), def);
    }

    public <T> void parse(T bean, String[] args) throws ArgsException {
        CmdArgsHolder cah = new CmdArgsHolder(args);
        CmdArgsDef def = this.cmdArgsDefRegistry.get(bean.getClass());
        if (def == null) {
            def = this.getArgsDef(bean.getClass());
        }

        Set<O> requiredOs = def.getRequiredOs();
        Set<A> requiredAs = def.getRequiredAs();
        boolean onlyAs = false;
        int apos = 0;

        while (cah.hasNext()) {
            String arg = cah.getNext();

            if (!onlyAs) { // Options
                O o = this.getOption(arg, def);
                if (o == null) {
                    if (requiredOs.size() > 0) {
                        throw new ArgsException("Missing required options: " + this.getOptionsAsStringList(requiredOs));
                    }
                    onlyAs = true;
                } else {
                    // Get handler and asign
                    if (o.required()) {
                        requiredOs.remove(o);
                    }
                    processOption(o, cah, def, bean);
                    continue;
                }
            }

            A a = this.getArgument(++apos, def);
            if (a == null) {
                throw new ArgsException("Unkown argument at position '" + apos + "'.");
            }

            // Get handler and asign
            if (a.required()) {
                requiredAs.remove(a);
            }

            processArgument(a, cah, def, bean);
        }

        if (requiredAs.size() > 0) {
            throw new ArgsException("Missing required arguments at positions: " + this.getArgumentsAsStringList(requiredAs));
        }
    }

    private <T> void processOption(O o, CmdArgsHolder cah, CmdArgsDef def, T bean) throws ArgsException {

        // Get Handler and asign value
        Class<? extends ArgHandler> clazz = o.handler();
        try {
            ArgHandler ah = this.argHandlers.get(clazz);
            if (ah == null) {

                ah = REFL.getHandler().constructDefault(clazz);
                Object value = ah.parseOption(cah, o);
                Field f = def.getOptionField(o);
                REFL.getHandler().setValue(f, value, bean);
            }
        } catch (ReflectionException e) {
            throw new ArgsException(e.getMessage(), e);
        }
    }

    private <T> void processArgument(A a, CmdArgsHolder cah, CmdArgsDef def, T bean) throws ArgsException {
        Class<? extends ArgHandler> clazz = a.handler();
        try {
            ArgHandler ah = this.argHandlers.get(clazz);
            if (ah == null) {

                ah = REFL.getHandler().constructDefault(clazz);
                Object value = ah.parseArgument(cah, a);
                Field f = def.getArgumentField(a);
                REFL.getHandler().setValue(f, value, bean);
            }
        } catch (ReflectionException e) {
            throw new ArgsException(e.getMessage(), e);
        }
    }

    private String getOptionsAsStringList(Set<O> s) {
        String str = "";
        for (O o : s) {
            str += o.sname() + ", ";
        }
        return str.substring(0, str.length() - 2);
    }

    private String getArgumentsAsStringList(Set<A> s) {
        String str = "";
        for (A a : s) {
            str += a.placeHolder() + ", ";
        }
        return str.substring(0, str.length() - 2);
    }

    private <T> O getOption(String arg, CmdArgsDef<T> def) {
        O op = def.getOptions().keySet().stream().filter(o -> o.sname().equals(arg) || o.lname().equals(arg)).findFirst().orElse(null);
        return op;
    }

    private <T> A getArgument(int pos, CmdArgsDef<T> def) {
        A a = def.getArguments().keySet().stream().filter(p -> p.position() == pos).findFirst().orElse(null);
        return a;
    }

    private <T> CmdArgsDef<T> load(Class<T> clazz) throws ArgsException {
        Map<O, Field> oMap = new HashMap<>();
        Map<A, Field> aMap = new HashMap<>();
        CmdArgsDef cad = new CmdArgsDef(clazz, oMap, aMap);
        boolean isO = false;
        for (Class c = clazz; c != null; c = c.getSuperclass()) {
            for (Field f : c.getDeclaredFields()) {
                isO = false;
                O o = f.getAnnotation(O.class);
                A p = f.getAnnotation(A.class);

                if (o != null) {
                    isO = true;
                    this.checkUniq(o, oMap.keySet());
                    oMap.put(o, f);
                }

                if (p != null) {
                    if (isO) {
                        throw new ArgsException("Field cannot be both option and positional parameter.");
                    }
                    this.checkUniq(p, aMap.keySet());
                    aMap.put(p, f);
                }
            }
        }
        return cad;
    }

    private <T> T load(String clazz) throws ClassNotFoundException {
        Class<T> c = (Class<T>) Class.forName(clazz);
        return this.load(clazz);
    }

    private void checkUniq(O newO, Set<O> existing) throws ArgsException {
        for (O o : existing) {
            if (o.sname().equals(newO.sname()) || !newO.lname().equals("") && o.lname().equals(newO.lname())) {
                throw new ArgsException("Found two options with same short name or long name");
            }
        }

    }

    private void checkUniq(A newA, Set<A> existing) throws ArgsException {
        for (A a : existing) {
            if (a.position() == newA.position()) {
                throw new ArgsException("Found two positional parameters with same position index.");
            }
        }

    }
}
