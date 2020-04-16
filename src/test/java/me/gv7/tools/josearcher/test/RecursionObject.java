package me.gv7.tools.josearcher.test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
// https://blog.csdn.net/dhklsl/article/details/83992950

 利用递归遍历获取复杂对象中所有目标属性的值（三）
 https://blog.csdn.net/dhklsl/article/details/88245460

 通过java反射机制，获取对象的属性和值（包括所有继承的父类）
 https://blog.csdn.net/sinat_28530913/article/details/72301506

 log4j日志输出到文件的配置
 https://www.cnblogs.com/coder-wdq/p/8097889.html
 */
public class RecursionObject {

    public static void main(String[] args){


        //利用方法一
        Map<String,Object> resultMap = recursionLoopThroughObj(Thread.currentThread(),"addrId");
        System.err.println("result:" + resultMap.get("proValue"));

        //利用方法二
//        String businessNoFromArg = getBusinessNoFromArg(userBO, "addrId");
//        System.err.println("businessNoFromArg=" + businessNoFromArg);

    }

    /**
     * 方法一：利用递归遍历
     * 用途：从复杂对象中递归遍历，获取string类型的目标属性名的值
     * 适用条件：该复杂对象中如果存在多个目标属性targetProName，遍历到第一个atargetProName则退出遍历
     *           targetProName属性必须是string
     *           targetProName可以存在自定义对象中、list、map、数组中
     *           如果复杂对象不包含目标属性则返回空字符串
     *           复杂对象可以是复杂嵌套的BO/List<BO>/Map<Object,BO>,目标属性存在于BO中
     *           对于复杂对象是list或map嵌套的不做支持。比如List<List<BO></BO>> /List<Map<object,BO>> / Map<object,List<BO>>
     * @param object 复杂对象
     * @param targetProName  目标属性名
     * @return
     */
    public static  Map<String,Object> recursionLoopThroughObj(Object object,String targetProName){
        Map<String,Object> resultMap = new HashMap<>();
        Class clazz = null;
        String proValue = "";
        boolean loopFlag = true;
        resultMap.put("loopFlag",loopFlag);
        resultMap.put("proValue",proValue);
        try {
            if (object==null || checkObjectIsSysType(object)){
                //如果object是null/基本数据类型/包装类/日期类型，则不需要在递归调用
                resultMap.put("loopFlag",false);
                resultMap.put("proValue","");
                return resultMap;
            }
            if (object instanceof Map){
                Map map = (Map)object;
                Map<String,Object> objMap = new HashMap<>();
                if (map!=null && map.size()>0){
                    Iterator iterator = map.values().iterator();
                    while (iterator.hasNext()){
                        objMap = recursionLoopThroughObj(iterator.next(),targetProName);
                        if (!(boolean)objMap.get("loopFlag")){
                            return objMap;
                        }
                    }
                }
            }

            clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String proType = field.getGenericType().toString();
                String proName = field.getName();
                System.err.println("proName:" + proName + ",proType:" + proType );
                if ("class java.lang.String".equals(proType) && targetProName.equals(proName)){
                    field.setAccessible(true);
                    proValue = (String)field.get(object);
                    resultMap.put("loopFlag",false);
                    resultMap.put("proValue",proValue);
                    return resultMap;
                }else if ("byte".equals(proType) || "short".equals(proType) || "int".equals(proType)|| "long".equals(proType)|| "double".equals(proType) || "float".equals(proType) || "boolean".equals(proType) ){
                    //属性是基本类型跳过
                    continue;
                }else if ("class java.lang.Byte".equals(proType) || "class java.lang.Short".equals(proType) || "class java.lang.Integer".equals(proType) || "class java.lang.Long".equals(proType) || "class java.lang.Double".equals(proType) || "class java.lang.Float".equals(proType) || "class java.lang.Boolean".equals(proType) || ("class java.lang.String".equals(proType) && !targetProName.equals(proName))){
                    //属性是包装类跳过
                    continue;
                }else if (proType.startsWith("java.utils")){
                    //属性是集合类型则遍历
                    if (proType.startsWith("java.utils.List")){
                        //对List类型的属性遍历
                        PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), clazz);
                        Method method = descriptor.getReadMethod();
                        List list = (List)method.invoke(object);
                        Map<String,Object> objMap = new HashMap<>();
                        if (list!=null && list.size()>0){
                            int len = list.size();
                            for (int i= 0;i<len;i++){
                                objMap = recursionLoopThroughObj(list.get(i),targetProName);
                                if (!(boolean)objMap.get("loopFlag")){
                                    return objMap;
                                }
                            }
                        }

                    }else if (proType.startsWith("java.utils.Map")){
                        //对Map类型的属性遍历
                        PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), clazz);
                        Method method = descriptor.getReadMethod();
                        Map map = (Map)method.invoke(object);
                        Map<String,Object> objMap = new HashMap<>();
                        if (map!=null && map.size()>0){
                            for (Object obj : map.values()){
                                objMap = recursionLoopThroughObj(obj,targetProName);
                                if (!(boolean)objMap.get("loopFlag")){
                                    return objMap;
                                }
                            }
                        }

                    }

                }else if(field.getType().isArray()){
                    //属性是数组类型则遍历
                    field.setAccessible(true);
                    Object[] objArr = (Object[]) field.get(object);
                    Map<String,Object> objMap = new HashMap<>();
                    if (objArr!=null && objArr.length>0){
                        for (Object arr : objArr){
                            objMap = recursionLoopThroughObj(arr,targetProName);
                            if (!(boolean)objMap.get("loopFlag")){
                                return objMap;
                            }
                        }
                    }

                }else  {
                    //class类型的遍历
                    PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), clazz);
                    Method method = descriptor.getReadMethod();
                    Object obj = method.invoke(object);
                    Map<String,Object> objMap = new HashMap<>();
                    if (obj!= null){
                        objMap = recursionLoopThroughObj(obj,targetProName);
                        if (!(boolean)objMap.get("loopFlag")){
                            return objMap;
                        }
                    }else {
                        continue;
                    }

                }
            }
        } catch (Exception e) {
            System.err.println("err:" + e);
        }
        return resultMap;
    }

    /**
     * 检查object是否为java的基本数据类型/包装类/java.utils.Date/java.sql.Date
     * @param object
     * @return
     */
    public static boolean checkObjectIsSysType(Object object){
        String objType = object.getClass().toString();
        if ("byte".equals(objType) || "short".equals(objType) || "int".equals(objType)|| "long".equals(objType)|| "double".equals(objType) || "float".equals(objType) || "boolean".equals(objType)){
            return true;
        }else if ("class java.lang.Byte".equals(objType) || "class java.lang.Short".equals(objType) || "class java.lang.Integer".equals(objType) || "class java.lang.Long".equals(objType) || "class java.lang.Double".equals(objType) || "class java.lang.Float".equals(objType) || "class java.lang.Boolean".equals(objType) || "class java.lang.String".equals(objType)){
            return true;
        }else {
            return  false;
        }

    }
}