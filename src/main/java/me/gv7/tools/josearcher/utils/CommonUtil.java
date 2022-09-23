package me.gv7.tools.josearcher.utils;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {
    public static String getBanner() {
        String banner = "#############################################################\n" +
                "   Java Object Searcher v0.01\n" +
                "   author: c0ny1<root@gv7.me>\n" +
                "   github: http://github.com/c0ny1/java-object-searcher\n" +
                "#############################################################\n\n\n";
        return banner;
    }

    public static String genExpress(String log) {
        String[] logs = log.split("--->");

        String idea_express = null;
        for (int i = 0; i < logs.length; i++) {
            if (i == 0) {
                idea_express = new Express(logs[i]).getField();
                continue;
            }
            String express;
            Express currentExpress = new Express(logs[i]);
            Express previousExpress = new Express(logs[i - 1]);
            express = String.format("%s.%s", idea_express, currentExpress.getField());
            if (previousExpress.getClassName().contains("[Ljava")) {
                express = String.format("%s%s", idea_express, currentExpress.getField());
            }
            if (previousExpress.getClassName().contains("Map")) {
                express = String.format("%s.get(\"%s\")", idea_express, currentExpress.getField().replace("[", "").replace("]", ""));
            }
            idea_express = express;
        }
        return idea_express;
    }


    public static void write2log(String filename, String content) {
        try {
            File file = new File(filename);
            String new_content;
            if (!file.exists()) {
                file.createNewFile();
                new_content = getBanner() + content;
            } else {
                new_content = content;
            }

            //使用true，即进行append file
            FileWriter fileWritter = new FileWriter(file, true);
            fileWritter.write(new_content);
            fileWritter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getBlank(int n) {
        String strTab = "";
        for (int i = 0; i < n; i++) {
            strTab += " ";
        }
        return strTab;
    }

    public static String getCurrentDate() {
        Date date = new Date();
        String str = "yyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        return sdf.format(date);
    }
}
