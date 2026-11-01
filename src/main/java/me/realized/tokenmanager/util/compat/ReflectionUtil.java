package me.realized.tokenmanager.util.compat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;

public final class ReflectionUtil {
    private static final String PACKAGE_VERSION;
    private static final int MAJOR_VERSION;

    static {
        final String packageName = Bukkit.getServer().getClass().getPackage().getName();
        final String lastPart = packageName.substring(packageName.lastIndexOf('.') + 1);
        PACKAGE_VERSION = lastPart.startsWith("v") ? lastPart : "";
        MAJOR_VERSION = (int) CompatUtil.getSubVersion();
    }

    public static int getMajorVersion() {
        return MAJOR_VERSION;
    }

    public static Class<?> getClassUnsafe(final String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    public static Method getMethodUnsafe(final Class<?> clazz, final String name, final Class<?>... parameters) {
        if (clazz == null) {
            return null;
        }

        try {
            return clazz.getMethod(name, parameters);
        } catch (NoSuchMethodException ex) {
            return null;
        }
    }

    public static Class<?> getNMSClass(final String name, final boolean logError) {
        try {
            if (getMajorVersion() < 17 && !PACKAGE_VERSION.isEmpty()) {
                return Class.forName("net.minecraft.server." + PACKAGE_VERSION + "." + name);
            }

            return Class.forName("net.minecraft." + name);
        } catch (ClassNotFoundException ex) {
            if (logError) {
                ex.printStackTrace();
            }

            return null;
        }
    }

    public static Class<?> getNMSClass(final String name) {
        return getNMSClass(name, false);
    }

    public static Class<?> getCBClass(final String path, final boolean logError) {
        final String className = PACKAGE_VERSION.isEmpty()
            ? "org.bukkit.craftbukkit." + path
            : "org.bukkit.craftbukkit." + PACKAGE_VERSION + "." + path;

        try {
            return Class.forName(className);
        } catch (ClassNotFoundException ex) {
            if (!PACKAGE_VERSION.isEmpty()) {
                try {
                    return Class.forName("org.bukkit.craftbukkit." + path);
                } catch (ClassNotFoundException ignored) {}
            }

            if (logError) {
                ex.printStackTrace();
            }

            return null;
        }
    }

    public static Class<?> getCBClass(final String path) {
        return getCBClass(path, false);
    }

    public static Class<?> getALClass(final String name) {
        try {
            return Class.forName("com.mojang.authlib." + name);
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    public static Method getMethod(final Class<?> clazz, final String name, final Class<?>... parameters) {
        if (clazz == null) {
            return null;
        }

        try {
            return clazz.getMethod(name, parameters);
        } catch (NoSuchMethodException ex) {
            return null;
        }
    }

    private static Method findDeclaredMethod(final Class<?> clazz, final String name, final Class<?>... parameters) throws NoSuchMethodException {
        final Method method = clazz.getDeclaredMethod(name, parameters);
        method.setAccessible(true);
        return method;
    }

    public static Method getDeclaredMethod(final Class<?> clazz, final String name, final Class<?>... parameters) {
        if (clazz == null) {
            return null;
        }

        try {
            return findDeclaredMethod(clazz, name, parameters);
        } catch (NoSuchMethodException ex) {
            return null;
        }
    }

    public static Method getDeclaredMethodUnsafe(final Class<?> clazz, final String name, final Class<?>... parameters) {
        if (clazz == null) {
            return null;
        }

        try {
            return findDeclaredMethod(clazz, name, parameters);
        } catch (NoSuchMethodException ex) {
            return null;
        }
    }

    public static Field getField(final Class<?> clazz, final String name) {
        if (clazz == null) {
            return null;
        }

        try {
            return clazz.getField(name);
        } catch (NoSuchFieldException ex) {
            return null;
        }
    }

    public static Field getDeclaredField(final Class<?> clazz, final String name) {
        if (clazz == null) {
            return null;
        }

        try {
            final Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException ex) {
            return null;
        }
    }

    public static Constructor<?> getConstructor(final Class<?> clazz, final Class<?>... parameters) {
        if (clazz == null) {
            return null;
        }

        try {
            return clazz.getConstructor(parameters);
        } catch (NoSuchMethodException ex) {
            return null;
        }
    }

    private ReflectionUtil() {}
}
