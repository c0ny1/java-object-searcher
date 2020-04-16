package me.gv7.tools.josearcher.utils;

import java.lang.reflect.Field;

public class CheckUtil {

    public static boolean isSysType(Object object){
        String type = object.getClass().toString();
        return isSysType(type);
    }

    public static boolean isSysType(Field field){
        String type = field.getGenericType().toString();
        return isSysType(type);
    }

    public static boolean isSysType(String strType){
        // 基本类型
        if ("byte".equals(strType) ||
            "short".equals(strType) ||
            "int".equals(strType)||
            "long".equals(strType)||
            "double".equals(strType) ||
            "float".equals(strType) ||
            "boolean".equals(strType)){
            return true;
        // 包装类
        }else if ("class java.lang.Byte".equals(strType) ||
                "class java.lang.Short".equals(strType) ||
                "class java.lang.Integer".equals(strType) ||
                "class java.lang.Long".equals(strType) ||
                "class java.lang.Double".equals(strType) ||
                "class java.lang.Float".equals(strType) ||
                "class java.lang.Boolean".equals(strType) ||
                "class java.lang.String".equals(strType) ||
                "class java.lang.Class".equals(strType) ||
                "class java.lang.Character".equals(strType)){
            return true;
        }else {
            return  false;
        }
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
