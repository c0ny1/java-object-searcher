package me.gv7.tools.josearcher.util;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        String str = "yyyMMdd";
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
}
