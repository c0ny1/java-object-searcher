package me.gv7.tools.josearcher.util;

import me.gv7.tools.josearcher.entity.Keyword;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static me.gv7.tools.josearcher.util.BlacklistUtil.isIn;

public class Common {
    public static String getBanner(){
        String banner = "#############################################################\n" +
                        "   Java Object Searcher v0.1\n" +
                        "   author: c0ny1<root@gv7.me>\n" +
                        "   github: http://github.com/c0ny1/java-object-searcher\n" +
                        "#############################################################\n\n\n";
        return banner;
    }

    public static void write2log(String filename,String content){
        try {
            File file = new File(filename);
            String new_content;
            if (!file.exists()) {
                file.createNewFile();
                new_content = getBanner() + content;
            }else{
                new_content = content;
            }

            //使用true，即进行append file
            FileWriter fileWritter = new FileWriter(file.getName(), true);
            fileWritter.write(new_content);
            fileWritter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static String getBlank(int n){
        String strTab = "";
        for(int i=0;i<n;i++){
            strTab += " ";
        }
        return strTab;
    }

    public static String getCurrentDate(){
        Date date = new Date();
        String str = "yyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        return sdf.format(date);
    }


    public static String getRandomNum(int length){
        String str_num = "";
        int max=9,min=0;
        for(int i=0;i<length;i++){
            int num = (int) (Math.random()*(max-min)+min);
            str_num += String.valueOf(num);
        }
        return str_num;
    }

    public static boolean matchClassType(String clsType,String[] keys){
        for(String key:keys){
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
         String field_type = field_value.getClass().getSimpleName();
        boolean isInFieldName = false;
        boolean isInFieldValue = false;
        boolean isInFieldType = false;
         for(Keyword keyword:keyword_list){

             //属性名
             if(keyword.getField_name() != null){
                 if(isIn(field_name,keyword.getField_name())){
                     isInFieldName = true;
                 }
             }else{
                 isInFieldName = true;
             }

             //属性值
             if(keyword.getField_value() != null){
                 if(isIn(field_value.toString(),keyword.getField_value())){
                     isInFieldValue = true;
                 }
             }else{
                 isInFieldValue = true;
             }
             //属性类型
             if(keyword.getField_type() != null){
                 if(isIn(field_type,keyword.getField_type())){
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
}
