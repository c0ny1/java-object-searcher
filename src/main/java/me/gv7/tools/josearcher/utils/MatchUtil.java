package me.gv7.tools.josearcher.utils;

import me.gv7.tools.josearcher.entity.Blacklist;
import me.gv7.tools.josearcher.entity.Keyword;
import java.lang.reflect.Field;
import java.util.List;

public class MatchUtil {
    public static boolean matchClassType(String clsType,String[] keys){
        clsType = clsType.toLowerCase();
        for(String key:keys){
            key = key.toLowerCase();
            if(clsType.contains(key)){
                return true;
            }
        }
        return false;
    }

    /**
     * 比较对象
     * @param field_name
     * @param field_value
     * @param keyword_list
     * @return
     */
    public static boolean matchObject(String field_name, Object field_value, List<Keyword> keyword_list){
        String field_type = field_value.getClass().getName();
        boolean isInFieldName = false;
        boolean isInFieldValue = false;
        boolean isInFieldType = false;
        for(Keyword keyword:keyword_list){

            //属性名
            if(keyword.getField_name() != null){
                if(isIn(field_name,keyword.getField_name(),false)){
                    isInFieldName = true;
                }
            }else{
                isInFieldName = true;
            }

            //属性值
            if(keyword.getField_value() != null){
                if(isIn(field_value.toString(),keyword.getField_value(),false)){
                    isInFieldValue = true;
                }
            }else{
                isInFieldValue = true;
            }
            //属性类型
            if(keyword.getField_type() != null){
                if(isIn(field_type,keyword.getField_type(),false)){
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

    public static boolean isIn(String target,String keyword,boolean match_case){


        if(target == null){
            if(keyword == null){
                return true;
            }else{
                return false;
            }
        }else{
            //不区分大小写
            if(match_case == false){
                target = target.toLowerCase();
                keyword = keyword.toLowerCase();
            }

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
                if(isIn(field_name,blacklist.getField_name(),false)){
                    isInFieldName = true;
                }
            }else{
                isInFieldName = true;
            }

            //属性值
            if(blacklist.getField_value() != null){
                if(isIn(field_value.toString(),blacklist.getField_value(),false)){
                    isInFieldValue = true;
                }
            }else{
                isInFieldValue = true;
            }
            //属性类型
            if(blacklist.getField_type() != null){
                if(isIn(field_type,blacklist.getField_type(),false)){
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
        if ("java.utils.Locale".equals(objType) ||
                "java.utils.logging.Level".equals(objType) ||
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
        if ("class java.utils.Locale".equals(objType) ||
                "class java.utils.logging.Level".equals(objType) ||
                objType.startsWith("class java.lang.reflect.") ||
                objType.startsWith("class sun.misc.")){
            return true;
        }
        return false;
    }
}
