package me.gv7.tools.josearcher.searcher;

import me.gv7.tools.josearcher.entity.Blacklist;
import me.gv7.tools.josearcher.entity.Keyword;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class RequstObjectSearcherTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Thread thread = Thread.currentThread();
        //Field f = thread.getClass().getDeclaredField("threadLocals");
        //f.setAccessible(true);
        //Object target = f.get(thread);

        //TestClass target =  new TestClass();
        List<Keyword> keys = new ArrayList<>();
        keys.add(new Keyword.Builder().setField_type("java.util.concurrent.ConcurrentHashMap").build());

        //List<Blacklist> blacklists = new ArrayList<>();
        //blacklists.add(new Blacklist.Builder().setField_name("parallelLockMap").build());

        RequstObjectSearcher searcher = new RequstObjectSearcher(thread,keys,"RequestObjectSearcherTest",10000,true);
        searcher.searchObject();
    }
}