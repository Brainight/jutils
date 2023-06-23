package brainight.jutils.args;

import brainight.jutils.args.handlers.ArgHandler;
import brainight.jutils.refl.ReflectionException;
import brainight.jutils.refl.ReflectionHandler;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Github: https://github.com/Brainight
 *
 * @author Brainight
 */
public class ArgHandlerRegistry {

    protected Map<Class<?>, ArgHandler<?>> defaultHandlerRegistry;
    protected LinkedList<ArgHandler<?>> secondaryHandlerRegistry;

    public ArgHandlerRegistry() {
        this.defaultHandlerRegistry = new HashMap<>();
        this.secondaryHandlerRegistry = new LinkedList<>();
    }

    /**
     * Sets this handler as the default handler to be assigned when no handler
     * is specified. If the previous handler, if existing, is added to the
     * secondary handler registry.
     *
     * @param <T>
     * @param clazz
     * @param handler
     */
    public <T> void setDefaultHandler(Class<T> clazz, ArgHandler<T> handler) {
        this.defaultHandlerRegistry.replace(clazz, handler);
        this.defaultHandlerRegistry.put(clazz, handler);
    }

    /**
     * Adds a Handler to the registry. The handler will become default handler
     * if no default handler for @param clazz exists. Otherwise an instance of
     * this handler will be added to the secondary registry if it doesn't exist
     * yet.
     *
     * @param <T>
     * @param clazz
     * @param handler
     */
    public <T> void addHandler(Class<T> clazz, ArgHandler<T> handler) {
        if (this.defaultHandlerRegistry.containsKey(clazz)) {
            if (!this.secondaryHandlerRegistry.contains(handler)) {
                this.secondaryHandlerRegistry.add(handler);
            }
            this.secondaryHandlerRegistry.add(handler);
        } else {
            this.defaultHandlerRegistry.put(clazz, handler);
        }
    }

    /**
     * Tries to find a instance for this ArgHandler class. If no instance is
     * available in the registry, it creates a new instance and adds it to the
     * registry.
     *
     * @param <T>
     * @param argHandlerClazz
     * @return
     */
    public <T extends ArgHandler> ArgHandler getArgHandlerOfType(Class<T> argHandlerClazz) throws ArgsException {
        ArgHandler<?> ah = this.defaultHandlerRegistry.values().stream().filter(a -> a.getClass() == argHandlerClazz).findAny().orElse(null);
        if (ah == null) {
            ah = this.secondaryHandlerRegistry.stream().filter(a -> a.getClass() == argHandlerClazz).findAny().orElse(null);
            if (ah == null) {
                try {
                    ah = ReflectionHandler.getHandler().constructDefault(argHandlerClazz);
                    this.defaultHandlerRegistry.put(ah.getTargetClass(), ah);
                } catch (ReflectionException ex) {
                    throw new ArgsException("Cannot get a handler for class: " + argHandlerClazz.toString(), ex);
                }
            }
        }
        return ah;
    }

    public <T> ArgHandler<T> getArgHandlerForClass(Class<T> clazz) throws ArgsException {
        ArgHandler handler = this.defaultHandlerRegistry.get(clazz);
        if (handler == null) {
            handler = this.secondaryHandlerRegistry.stream().filter(h -> {
                try {
                    return h.getTargetClass().equals(clazz);
                } catch (ArgsException e) {
                    return false;
                }
            }
            ).findFirst().orElse(null);
            if (handler == null) {
                throw new ArgsException("Cannot find a handler for class of type: " + clazz.toString());
            }
        }
        return handler;
    }

    protected void initDefaultRegistry() {
        try {
            Enumeration<URL> urls = ClassLoader.getSystemClassLoader().getResources("brainight.jutils.args.handlers");
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();

                InputStream is = url.openStream();

            }
        } catch (IOException ex) {
            Logger.getLogger(ArgHandlerRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
