package brainight.jutils.args;

import brainight.jutils.args.annotations.O;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import brainight.jutils.args.annotations.A;
import brainight.jutils.args.annotations.wrappers.Argument;
import brainight.jutils.args.annotations.wrappers.Option;
import java.util.HashSet;

/**
 * Github: https://github.com/Brainight
 *
 * @author Brainight
 */
public class CmdArgsDef<T> {

    private final Class<T> beanType;
    private final Map<O, Option> options;
    private final Map<A, Argument> arguments;

    public CmdArgsDef(Class<T> clazz, Map<O, Option> options, Map<A, Argument> arguments) {
        this.beanType = clazz;
        this.options = options;
        this.arguments = arguments;
    }

    public Class<T> getBeanType() {
        return beanType;
    }

    public Map<O, Option> getOptions() {
        return options;
    }

    public Map<A, Argument> getArguments() {
        return arguments;
    }

    public Option getOption(O o) {
        return this.options.get(o);
    }

    public Argument getArgument(A a) {
        return this.arguments.get(a);
    }

    public Set<O> getOptionsSet() {
        return options.keySet();
    }

    public Set<A> getArgumentsSet() {
        return arguments.keySet();
    }

    public Field getOptionField(O o) {
        return this.options.get(o).getField();
    }

    public Field getArgumentField(A p) {
        return this.arguments.get(p).getField();
    }

    public Set<O> getRequiredOs() {
        return this.options.keySet().stream().filter(o -> o.required()).collect(Collectors.toCollection(HashSet::new));
    }

    public Set<A> getRequiredAs() {
        return this.arguments.keySet().stream().filter(o -> o.required()).collect(Collectors.toCollection(HashSet::new));
    }

}
