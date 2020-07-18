package me.gv7.tools.josearcher.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

public class LogUtil {
    public static String readThrowableInfo(Throwable throwable){
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    public static synchronized void saveThrowableInfo(Throwable throwable,String fileName){
        try {
            String throwableInfo = readThrowableInfo(throwable);
            FileWriter fileWriter = new FileWriter(new File(fileName),true);
            fileWriter.write(throwableInfo);
            fileWriter.write("\n");
            fileWriter.flush();
            fileWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
