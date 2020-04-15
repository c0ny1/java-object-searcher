package me.gv7.tools.josearcher.util;

import java.lang.reflect.Field;
import java.util.List;
import me.gv7.tools.josearcher.entity.Blacklist;


public class BlacklistUtil {

    public static boolean isInBlacklist1(String field_name,Object field_value, List<Blacklist> blacklists){
        String field_type = field_value.getClass().getSimpleName();
        for(Blacklist blacklist:blacklists){
            //属性名
            if(blacklist.getField_name() != null
                    && !blacklist.getField_name().equals("")
                    && field_name != null
                    && !field_name.toLowerCase().contains(blacklist.getField_name().toLowerCase())){
                continue;
            }
            //属性值
            if(blacklist.getField_value() != null
                    && !blacklist.getField_value().equals("")
                    && !field_value.toString().toLowerCase().contains(blacklist.getField_value().toLowerCase())){
                continue;
            }
            //属性类型
            if(blacklist.getField_type() != null
                    && !blacklist.getField_type().equals("")
                    && !field_type.toLowerCase().contains(blacklist.getField_type().toLowerCase())){
                continue;
            }
            return true;
        }
        return false;
    }

    public static boolean isIn(String target,String keyword){
        if(target == null){
            if(keyword == null){
                return true;
            }else{
                return false;
            }
        }else{
            if(target.contains(keyword)){
                return true;
            }else{
                return false;
            }
        }
    }


    public static boolean isInBlacklist(String field_name,Object field_value, List<Blacklist> blacklists){
        String field_type = field_value.getClass().getName();
        boolean isInFieldName = false;
        boolean isInFieldValue = false;
        boolean isInFieldType = false;
        for(Blacklist blacklist:blacklists){
            //属性名
            if(blacklist.getField_name() != null){
                if(isIn(field_name,blacklist.getField_name())){
                    isInFieldName = true;
                }
            }else{
                isInFieldName = true;
            }

            //属性值
            if(blacklist.getField_value() != null){
                if(isIn(field_value.toString(),blacklist.getField_value())){
                    isInFieldValue = true;
                }
            }else{
                isInFieldValue = true;
            }
            //属性类型
            if(blacklist.getField_type() != null){
                if(isIn(field_type,blacklist.getField_type())){
                    isInFieldType = true;
                }
            }else{
                isInFieldType = true;
            }

            if(isInFieldName && isInFieldValue && isInFieldType){
                return true;
            }
        }
        return false;
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
}
