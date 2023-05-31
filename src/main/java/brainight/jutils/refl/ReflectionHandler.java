package com.brainache.utils.refl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Brainight
 */
public class ReflectionHandler {

    private static ReflectionHandler instance;

    private boolean useGodMode = false;

    public static ReflectionHandler getHandler() {
        if(instance == null){
            instance = new ReflectionHandler();
        }
        return instance;
    }

    public void useGodMode(boolean bool) throws ReflectionException {
        if (instance == null) {
            throw new ReflectionException("Current thread has no defined ReflectionHandler");
        }

        instance.useGodMode = bool;
    }

    /**
     * Return null if object does not have getter method. Note that the null
     * return does not mean getter method is not defined, as method invoke could
     * return null too.
     *
     * @param <T>
     * @param field
     * @param target
     * @return
     */
    public <T> T getValueByGetter(Field field, Object target) {
        Method m;
        T res = null;
        try {
            m = getGetterMethodForField(field);
            if (m != null) {
                if (m.canAccess(target)) {
                    res = (T) m.invoke(target);
                } else if (this.useGodMode) {
                    m.setAccessible(true);
                    res = (T) m.invoke(target);
                    m.setAccessible(false);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return res;
    }

    /**
     * Get the value for this field and the given target Object. It will try to
     * do it by the getter method. If no getter method is present it will try to
     * access object directly.
     *
     * @param <T>
     * @param field
     * @param target
     * @return
     */
    public <T> T getValue(Field field, Object target) {
        T res = getValueByGetter(field, target);
        try {
            if (res == null) {
                if (this.useGodMode || field.canAccess(target)) {
                    res = (T) field.get(target);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public Method getGetterMethodForField(Field field, Class<?>... args) throws ReflectionException {
        Method m;
        try {
            m = field.getDeclaringClass().getMethod(getGetterMethodName(field), args);
        } catch (Exception ex) {
            try {
                transformArgsToPrimitives(args);
                m = field.getDeclaringClass().getMethod(getGetterMethodName(field), args);
            } catch (NoSuchMethodException ex1) {
                throw new ReflectionException(ex);
            }
        }
        return m;
    }

    public Method getSetterMethodForField(Field field, Class<?>... args) throws ReflectionException {
        Method m;
        try {
            m = field.getDeclaringClass().getMethod(getSetterMethodName(field), args);
        } catch (Exception ex) {
            try {
                transformArgsToPrimitives(args);
                m = field.getDeclaringClass().getMethod(getSetterMethodName(field), args);
            } catch (NoSuchMethodException ex1) {
                throw new ReflectionException("Caused by:", ex);
            }
        }
        return m;
    }

    public static String getGetterMethodName(Field field) {
        String prefix = "get";
        if (field.getType().isAssignableFrom(Boolean.class
        ) || field.getType().isAssignableFrom(boolean.class
        )) {
            prefix = "is";
        }
        String name = prefix + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        return name;

    }

    public static String getSetterMethodName(Field field) {
        String name = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        return name;
    }

    public <T> boolean setValueBySetter(Field field, T value, Object target) {
        boolean success = false;
        // Try with class and superclasses
        for (Class<?> clazz = value.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            if (trySetValueBySetter(field, value, clazz, target)) {
                success = true;
                break;
            }
        }

        // Try with interfaces
        for (Class<?> clazz : value.getClass().getInterfaces()) {
            if (trySetValueBySetter(field, value, clazz, target)) {
                success = true;
                break;
            }
        }

        return success;
    }

    private <T> boolean trySetValueBySetter(Field field, T value, Class<?> tryClazz, Object target) {
        Method m;
        try {
            m = getSetterMethodForField(field, tryClazz);
            if (m != null) {
                if (m.canAccess(target)) {
                    m.invoke(target, value);
                    return true;
                } else if (this.useGodMode) {
                    m.setAccessible(true);
                    m.invoke(target, value);
                    m.setAccessible(false);
                    return true;
                }
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return false;
    }

    public <T> boolean setValue(Field field, T value, Object target) {
        boolean success = setValueBySetter(field, value, target);
        try {
            if (!success) {
                if (this.useGodMode || field.canAccess(target)) {
                    field.setAccessible(true);
                    field.set(target, value);
                    field.setAccessible(false);
                    success = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    /**
     * Return the primitive type class for a Wrapper class. If the class
     * provided is not a wrapper of a Java Primitive Type then the provided.
     * class is returned.
     *
     * @param wrapperClazz
     * @return
     */
    public Class<?> getPrimitiveClassForWrapper(Class<?> wrapperClazz) {
        Class<?> primitiveClazz = wrapperClazz;
        if (wrapperClazz == null) {
            return null;
        }

        if (wrapperClazz.isAssignableFrom(Boolean.class
        )) {
            primitiveClazz
                    = boolean.class;
        } else if (wrapperClazz.isAssignableFrom(Byte.class
        )) {
            primitiveClazz
                    = byte.class;
        } else if (wrapperClazz.isAssignableFrom(Character.class
        )) {
            primitiveClazz
                    = char.class;
        } else if (wrapperClazz.isAssignableFrom(Short.class
        )) {
            primitiveClazz
                    = short.class;
        } else if (wrapperClazz.isAssignableFrom(Integer.class
        )) {
            primitiveClazz
                    = int.class;
        } else if (wrapperClazz.isAssignableFrom(Long.class
        )) {
            primitiveClazz
                    = long.class;
        } else if (wrapperClazz.isAssignableFrom(Float.class
        )) {
            primitiveClazz
                    = float.class;
        } else if (wrapperClazz.isAssignableFrom(Double.class
        )) {
            primitiveClazz
                    = double.class;
        }

        return primitiveClazz;
    }

    public void transformArgsToPrimitives(Class<?>... args) {
        for (int i = 0; i < args.length; i++) {
            args[i] = this.getPrimitiveClassForWrapper(args[i]);
        }
    }

    public Class<?> getComponentTypeForList(Field f) {
        if (f.getType().isAssignableFrom(List.class)) {
            ParameterizedType type = (ParameterizedType) f.getGenericType();
            Class<?> clazz = (Class) type.getActualTypeArguments()[0];
            return clazz;
        }
        return null;
    }

    public <T, R> List<T> getListImplementation(Field field, R targetInstance) throws ReflectionException {

        if (targetInstance == null) {
            return new ArrayList<T>();
        }

        try {
            Object target = getHandler().getValue(field, targetInstance);
            Class<?> impl = target == null ? ArrayList.class : target.getClass();
            if (impl.equals(ArrayList.class)) {
                return new ArrayList<T>();
            } else if (impl.equals(LinkedList.class)) {
                return new LinkedList<T>();
            } else if (impl.equals(Stack.class)) {
                return new Stack<T>();
            }
        } catch (Exception e) {
            throw new ReflectionException("Caused by:", e);
        }
        return new ArrayList<>();
    }

    public <T> List<T> getListImplementation(Field field, Class<?> targetClazz) throws ReflectionException {
        Object target = constructDefault(targetClazz);
        return this.getListImplementation(field, target);
    }

    public <T> T constructDefault(Class<T> clazz) throws ReflectionException {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception ex) {
            throw new ReflectionException("Cannot instanciate class '" + clazz.getName() + "'. Caused by:", ex);
        }
    }

    /**
     * Returns null if the constructor cannot be found. Otherwise a consutructor
     * for the specified class with given parameter types is returned.
     *
     * @param <T>
     * @param clazz
     * @param parameters
     * @return
     */
    public <T> Constructor<T> findConstructor(Class<T> clazz, Class<?>... parameters) {
        try {
            return clazz.getConstructor(parameters);
        } catch (Exception ex) {
            return null;
        }
    }
}
