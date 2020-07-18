/**
 * author: c0ny1
 * date: 2020-04-12 23:17
 * description: 本类用于搜索对象中所有的属性
 * ps: avicii 在写完《waiting for love》是否也是一样的心情。
 */
package me.gv7.tools.josearcher.searcher;

import me.gv7.tools.josearcher.entity.Blacklist;
import me.gv7.tools.josearcher.entity.Keyword;
import me.gv7.tools.josearcher.utils.CheckUtil;
import me.gv7.tools.josearcher.utils.LogUtil;
import me.gv7.tools.josearcher.utils.MatchUtil;
import java.lang.reflect.Field;
import java.util.*;
import static me.gv7.tools.josearcher.utils.CommonUtil.*;
import static me.gv7.tools.josearcher.utils.CheckUtil.*;

/**
 * 本类用于搜索Java对象中是否存在request相关的属性，比如可以在反序列化需要回显的场景，用于辅助挖掘request对象。
 */
public class SearchRequstByDFS {
    //private Logger logger = Logger.getLogger(SearchRequstByDFS.class);
    private String model_name = SearchRequstByDFS.class.getSimpleName();
    private Object target;
    private List<Keyword> keys;
    private List<Blacklist> blacklists = new ArrayList<>();
    private int max_search_depth = Integer.MAX_VALUE;/* 递归搜索深度 */
    private boolean is_debug = false;
    private String report_save_path = null;
    private String result_file;
    private String all_chain_file;
    private String err_log_file;
    private Set<Object> visited = new HashSet<Object>();


    public SearchRequstByDFS(Object target, List<Keyword> keys){
        this.target = target;
        this.keys = keys;
        this.result_file = String.format("%s_result_%s.txt",model_name,getCurrentDate());
        this.all_chain_file = String.format("%s_log_%s.txt",model_name,getCurrentDate());
    }

    public void initSavePath(){
        if(report_save_path == null){
            this.result_file = String.format("%s_result_%s.txt",model_name,getCurrentDate());
            this.all_chain_file = String.format("%s_log_%s.txt",model_name,getCurrentDate());
            this.err_log_file = String.format("%s_error_%s.txt",model_name,getCurrentDate());
        }else{
            this.result_file = String.format("%s/%s_result_%s.txt",report_save_path,model_name,getCurrentDate());
            this.all_chain_file = String.format("%s/%s_log_%s.txt",report_save_path,model_name,getCurrentDate());
            this.err_log_file = String.format("%s/%s_error_%s.txt",report_save_path,model_name,getCurrentDate());
        }
    }

    public void setBlacklists(List<Blacklist> blacklists) {
        this.blacklists = blacklists;
    }

    public void setMax_search_depth(int max_search_depth) {
        this.max_search_depth = max_search_depth;
    }

    public void setReport_save_path(String report_save_path) {
        this.report_save_path = report_save_path;
    }

    public void setIs_debug(boolean is_debug) {
        this.is_debug = is_debug;
    }

    public void setErrLogFile(String err_log_file) {
        this.err_log_file = err_log_file;
    }

    public void searchObject(){
        this.initSavePath();
        searchObject(null,target,"",0);
    }


    private void searchObject(String filed_name,Object filed_object,String log_chain,int father_depth) {
        int current_depth = father_depth;

        //最多挖多深
        if(current_depth > max_search_depth){
            return;
        }

        if (filed_object == null || CheckUtil.isSysType(filed_object) || MatchUtil.isInBlacklist(filed_name,filed_object,this.blacklists)){
            //如果object是null/基本数据类型/包装类/日期类型，则不需要在递归调用
            return;
        }

        try {
            // 如果已经搜索过这个对象就返回不再继续搜索
            // 注意：Set.contains 可能存在空指针异常
            if (!visited.contains(filed_object)) {
                visited.add(filed_object);
            } else {
                return;
            }
        }catch (Throwable e){
            LogUtil.saveThrowableInfo(e,this.err_log_file);
        }

        String new_log_chain = "";
        Class clazz = filed_object.getClass();

        if(log_chain != null && log_chain != ""){
            current_depth++;
            new_log_chain = String.format("%s \n%s ---> %s = {%s}",log_chain,getBlank(current_depth),filed_name,clazz.getName());
        }else{
            new_log_chain = String.format("%s = {%s}","TargetObject",filed_object.getClass().getName());
        }




        if(filed_object instanceof List){

        }


        if (filed_object instanceof Map){
            Map map = (Map)filed_object;
            if (map!=null && map.size()>0){
                Iterator iterator = map.values().iterator();
                while (iterator.hasNext()){
                    searchObject(filed_name,iterator.next(),new_log_chain,current_depth);
                }
            }
        }

        if(clazz.isArray()){
            try {
                //Object[] 没有属性
                Object[] obj_arr = (Object[]) filed_object;
                if (obj_arr != null && obj_arr.length > 0) {
                    for(int i=0; i < obj_arr.length; i++){
                        if(obj_arr[i] == null){
                            continue;
                        }
                        String arr_type = obj_arr[i].getClass().getName();
                        String arr_name = String.format("[%d] = {%s}",i,arr_type);
                        searchObject(arr_name,obj_arr[i], new_log_chain,current_depth);
                    }
                }
            }catch (Throwable e){
                //logger.error(String.format("%s - %s",this.model_name,"clazz.isArray"),e);
                LogUtil.saveThrowableInfo(e,this.err_log_file);
            }
        }


        /**
         * 问题一：getDeclaredFields无法获取从父类获取的属性，需要向上遍历父类
         */
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {//向上循环 遍历父类
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.isAccessible();
                field.setAccessible(true);
                String proType = field.getGenericType().toString();
                String proName = field.getName();
                Object subObj = null;
                try {
                    subObj = field.get(filed_object);
                } catch (Throwable e) {
                    //logger.error(String.format("%s - %s",this.model_name,"class"),e);
                    LogUtil.saveThrowableInfo(e,this.err_log_file);
                }

                if(subObj == null){
                    continue;
                }else if (CheckUtil.isSysType(field)) {
                    //属性是系统类型跳过
                    continue;
                } else if(MatchUtil.isInBlacklist(proName,subObj,this.blacklists)){
                    continue;
                }else if (isList(field)) {
                    //对List,ArrayList类型的属性遍历
                    try {
                        List list = (List) field.get(filed_object);
                        if (list != null && list.size() > 0) {
                            current_depth++;
                            String tmp_log_chain = String.format("%s \n%s ---> %s = {%s}", new_log_chain, getBlank(current_depth), proName, proType);
                            int len = list.size();
                            for (int i = 0; i < len; i++) {
                                if (list.get(i) == null) {
                                    continue;
                                }
                                String list_name = String.format("[%d]", i);
                                searchObject(list_name, list.get(i), tmp_log_chain, current_depth);
                            }
                        }
                    } catch (Throwable e) {
                        //logger.error(String.format("%s - %s",this.model_name,"isList"),e);
                        LogUtil.saveThrowableInfo(e,this.err_log_file);
                    }
                } else if (isMap(field)) {
                    try {
                        //对Map,HashMap类型的属性遍历
                        Map map = (Map) field.get(filed_object);
                        if (map != null && map.size() > 0) {
                            current_depth++;
                            String tmp_log_chain = String.format("%s \n%s ---> %s = {%s}", new_log_chain, getBlank(current_depth), proName, proType);

                            Iterator<String> iter = map.keySet().iterator();
                            while (iter.hasNext()) {
                                String key = iter.next();
                                Object value = map.get(key);
                                String map_name = String.format("[%s]", key);
                                searchObject(map_name, value, tmp_log_chain, current_depth);
                            }
                        }

                    } catch (Throwable e) {
                        //logger.error(String.format("%s - %s",this.model_name,"isMap"),e);
                        LogUtil.saveThrowableInfo(e,this.err_log_file);
                    }
                } else if (field.getType().isArray()) {
                    try {
                        //属性是数组类型则遍历
                        Object obj = field.get(filed_object);
                        if(obj == null){
                            continue;
                        }

                        Object[] objArr = (Object[]) obj;
                        if (objArr != null && objArr.length > 0) {
                            current_depth++;
                            for (int i = 0; i < objArr.length; i++) {
                                if (objArr[i] == null) {
                                    continue;
                                }

                                String tmp_log_chain = String.format("%s \n%s ---> %s = {%s}", new_log_chain, getBlank(current_depth), proName, proType);

                                String arrType = objArr[i].getClass().getName();
                                String arr_name = String.format("[%d]", i);
                                searchObject(arr_name, objArr[i], tmp_log_chain, current_depth);

                            }
                        }
                    } catch (Throwable e) {
                        //logger.error(String.format("%s - %s",this.model_name,"isArray"),e);
                        LogUtil.saveThrowableInfo(e,this.err_log_file);
                    }
                } else {
                    try {
                        //class类型的遍历
                        searchObject(proName, subObj, new_log_chain, current_depth);
                    } catch (Throwable e) {
                        //logger.error(String.format("%s - %s",this.model_name,"class"),e);
                        LogUtil.saveThrowableInfo(e,this.err_log_file);
                    }
                }
            }
        }

        if(MatchUtil.matchObject(filed_name,filed_object,keys)){
            write2log(result_file,new_log_chain + "\n\n\n");
        }

        if(is_debug) {
            write2log(all_chain_file, new_log_chain + "\n\n\n");
        }

        System.out.println(new_log_chain);
        System.out.println("\n\n\n");
    }
}
