package brainight.jutils.args;

import brainight.jutils.args.annotations.O;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import brainight.jutils.args.annotations.A;
import brainight.jutils.args.annotations.O;
import brainight.jutils.args.annotations.wrappers.Argument;
import brainight.jutils.args.annotations.wrappers.Option;
import brainight.jutils.args.handlers.ArgHandler;
import brainight.jutils.args.handlers.CoffeeArgHandler;
import brainight.jutils.refl.ReflectionException;
import brainight.jutils.refl.ReflectionHandler;
import java.util.LinkedList;
import java.util.List;

/**
 * Github: https://github.com/Brainight
 *
 * @author Brainight
 */
public class CmdParser {

    private final ReflectionHandler refl = ReflectionHandler.getHandler("CmdParser");

    protected Map<Class<?>, CmdArgsDef<?>> cmdArgsDefRegistry;
    protected ArgHandlerRegistry argsHandlerRegistry;

    /**
     * Construct an instance of CmdParser with all ootb ArgHandler classes
     * registered in registry.
     */
    public CmdParser() {
        this.cmdArgsDefRegistry = new HashMap<>();
        this.argsHandlerRegistry = ArgHandlerRegistry.loadDefault();
        refl.useGodMode(true);
    }

    public CmdParser(ArgHandlerRegistry registry) {
        this.cmdArgsDefRegistry = new HashMap<>();
        this.argsHandlerRegistry = registry;
        refl.useGodMode(true);
    }

    public ArgHandlerRegistry getArgsHandlerRegistry() {
        return argsHandlerRegistry;
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
        this.parse(bean, args, 0);
    }

    public <T> void parse(T bean, String[] args, int ipos) throws ArgsException {
        CmdArgsHolder cah = new CmdArgsHolder(args, ipos);
        CmdArgsDef def = this.cmdArgsDefRegistry.get(bean.getClass());
        if (def == null) {
            def = this.getArgsDef(bean.getClass());
        }

        Set<O> requiredOs = def.getRequiredOs();
        Set<A> requiredAs = def.getRequiredAs();
        List<O> processedOs = new LinkedList<>();
        boolean onlyAs = false;
        int apos = 0;

        while (cah.hasNext()) {
            String arg = cah.getNext();

            if (!onlyAs) { // Options
                O o = this.getOption(arg, def);
                Option op = def.getOption(o);
                if (o == null) {
                    if (requiredOs.size() > 0) {
                        throw new ArgsException("Missing required options: " + this.getOptionsAsStringList(requiredOs));
                    }
                    onlyAs = true;
                } else {
                    // Get handler and asign
                    this.checkDeniedOps(o, processedOs);
                    if (o.required()) {
                        requiredOs.remove(o);
                    }

                    processOption(op, cah, bean);
                    processedOs.add(o);
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
            Argument ar = def.getArgument(a);
            processArgument(ar, cah, bean);
        }

        if (requiredAs.size() > 0) {
            throw new ArgsException("Missing required arguments at positions: " + this.getArgumentsAsStringList(requiredAs));
        }
    }

    private void checkDeniedOps(O o, List<O> processedOs) throws ArgsException {
        for (O op : processedOs) {
            for (String sname : op.deniesOs()) {
                if (sname.equals(o.sname())) {
                    throw new ArgsException("Option '" + o.sname() + "' is denied by previous option '" + sname + "'");
                }
            }
        }
    }

    private <T> void processOption(Option o, CmdArgsHolder cah, T bean) throws ArgsException {
        try {
            ArgHandler ah = o.getHandler();
            Object value = ah.parseOption(cah, o.getO());
            refl.getHandler().setValue(o.getField(), value, bean);

        } catch (ReflectionException e) {
            throw new ArgsException(e.getMessage(), e);
        }
    }

    private <T> void processArgument(Argument ar, CmdArgsHolder cah, T bean) throws ArgsException {
        ArgHandler ah = ar.getHandler();
        try {
            Object value = ah.parseArgument(cah, ar.getA());
            refl.getHandler().setValue(ar.getField(), value, bean);

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
        int pos = 0;
        for (A a : s) {
            pos = a.position();
            str += "(" + pos + ") " + a.placeHolder() + ", ";
        }
        return str.substring(0, str.length() - 2);
    }

    private String getArgumentsPositionsAsStringList(Set<A> s) {
        String str = "[";
        for (A a : s) {
            str += a.position() + ", ";
        }
        return str.substring(0, str.length() - 2) + "]";
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
        Map<O, Option> oMap = new HashMap<>();
        Map<A, Argument> aMap = new HashMap<>();
        CmdArgsDef cad = new CmdArgsDef(clazz, oMap, aMap);
        boolean isO = false;
        for (Class c = clazz; c != null; c = c.getSuperclass()) {
            for (Field f : c.getDeclaredFields()) {
                isO = false;
                O o = f.getAnnotation(O.class);
                A a = f.getAnnotation(A.class);

                if (o != null) {
                    isO = true;
                    this.checkUniq(o, oMap.keySet());
                    ArgHandler ah = resolveHandler(o.handler(), f);
                    Option op = new Option(o, f, ah);
                    oMap.put(o, op);
                }

                if (a != null) {
                    if (isO) {
                        throw new ArgsException("Field cannot be both option and positional parameter.");
                    }
                    this.checkUniq(a, aMap.keySet());
                    ArgHandler ah = resolveHandler(a.handler(), f);
                    Argument arg = new Argument(a, f, ah);
                    aMap.put(a, arg);
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

    private ArgHandler resolveHandler(Class<? extends ArgHandler> ahClazz, Field tf) throws ArgsException {
        Class<?> tClazz = tf.getType();
        ArgHandler ah = null;
        if (ahClazz == CoffeeArgHandler.class) {
            ah = this.argsHandlerRegistry.getArgHandlerForClass(tClazz);
        } else {
            ah = this.argsHandlerRegistry.getArgHandlerOfType(ahClazz);
            if (ah.getTargetClass() != tClazz) {
                throw new ArgsException("ArgHandler for field " + tf.getName() + " in "
                        + tf.getDeclaringClass().toString() + " cannot handle field's type values " + tClazz.toString());
            }
        }
        return ah;
    }
}
