package me.gv7.tools.josearcher.util;

import java.lang.reflect.Field;

public class TypeUtils {
    public static boolean checkObjectIsSysType(Object object){
        String objType = object.getClass().toString();
        if ("byte".equals(objType) ||
            "short".equals(objType) ||
            "int".equals(objType)||
            "long".equals(objType)||
            "double".equals(objType) ||
            "float".equals(objType) ||
            "boolean".equals(objType)){
            return true;
        }else if ("class java.lang.Byte".equals(objType) ||
                "class java.lang.Short".equals(objType) ||
                "class java.lang.Integer".equals(objType) ||
                "class java.lang.Long".equals(objType) ||
                "class java.lang.Double".equals(objType) ||
                "class java.lang.Float".equals(objType) ||
                "class java.lang.Boolean".equals(objType) ||
                "class java.lang.String".equals(objType)){
            return true;
        }else {
            return  false;
        }

    }

    public static boolean checkObjectIsBacklist(Object object){
        String objType = object.getClass().getName();
        if ("java.util.Locale".equals(objType) ||
                "java.util.logging.Level".equals(objType) ||
                "java.lang.Class".equals(objType) ||
                "java.lang.Character".equals(objType) ||
                "java.io.File".equals(objType) ||
                objType.toLowerCase().startsWith("java.lang.reflect.") ||
                objType.toLowerCase().startsWith("sun.misc.") ||
                objType.toLowerCase().contains("logging") ||
                objType.toLowerCase().contains("log4j") ||
                objType.toLowerCase().contains("logger")){
            return true;
        }
        return false;
    }


    /**
     * 是否是基本类型
     * @param field
     * @return
     */
    public static boolean isBasicType(Field field){
        String proType = field.getGenericType().toString();
        if ("byte".equals(proType) ||
                "short".equals(proType) ||
                "int".equals(proType) ||
                "long".equals(proType) ||
                "double".equals(proType) ||
                "float".equals(proType) ||
                "boolean".equals(proType)) {
            return true;
        }
        return false;
    }

    public static boolean isWrapperClass(Field field){
        String proType = field.getGenericType().toString();
        if ("class java.lang.Byte".equals(proType) ||
                "class java.lang.Short".equals(proType) ||
                "class java.lang.Integer".equals(proType) ||
                "class java.lang.Long".equals(proType) ||
                "class java.lang.Double".equals(proType) ||
                "class java.lang.Float".equals(proType) ||
                "class java.lang.Boolean".equals(proType) ||
                "class java.lang.String".equals(proType) ||
                "class java.lang.Class".equals(proType) ||
                "class java.lang.Character".equals(proType)){
            return true;
        }
        return false;
    }


    public static boolean isBacklistType(Field field){
        String objType = field.getGenericType().toString();
        if ("class java.util.Locale".equals(objType) ||
                "class java.util.logging.Level".equals(objType) ||
                objType.startsWith("class java.lang.reflect.") ||
                objType.startsWith("class sun.misc.")){
            return true;
        }
        return false;
    }


    /**
     * 判断是否是Map或者HashMap
     * @param field
     * @return
     */
    public static boolean isMap(Field field){
        boolean flag = false;
        String simpleName = field.getType().getSimpleName();
        if ("Map".equals(simpleName) || "HashMap".equals(simpleName)){
            flag = true;
        }
        return flag;
    }

    /**
     * 判断是否是List或者ArrayList
     * @param field
     * @return
     */
    public static boolean isList(Field field){
        boolean flag = false;
        String simpleName = field.getType().getSimpleName();
        if ("List".equals(simpleName) || "ArrayList".equals(simpleName)){
            flag = true;
        }
        return flag;
    }
}
