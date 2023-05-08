package org.tomoyo.springframework.utils;

public class ClassUtils {
    public static ClassLoader getDefaultClassLoader(){
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (Throwable ex){

        }
        if(cl == null){
            cl = ClassUtils.class.getClassLoader();
        }
        return cl;
    }

    public static boolean isCglibProxyClass(Class<?> clazz){
        return (clazz != null && isCglibProxyClasssName(clazz.getName()));
    }

    public static boolean isCglibProxyClasssName(String className){
        return (className != null && className.contains("$$"));
    }
}
