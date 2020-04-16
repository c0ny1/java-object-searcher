package me.gv7.tools.josearcher.searcher;

import java.util.List;
import java.util.ArrayList;
import me.gv7.tools.josearcher.entity.Keyword;

class RequstSearchByRecursiveTest {
    public static void main(String[] args)  {
        Thread thread = Thread.currentThread();
        //Field f = thread.getClass().getDeclaredField("threadLocals");
        //f.setAccessible(true);
        //Object target = f.get(thread);

        //TestClass target =  new TestClass();
        List<Keyword> keys = new ArrayList<>();
        keys.add(new Keyword.Builder().setField_type("java.util.concurrent.ConcurrentHashMap").build());

        //List<Blacklist> blacklists = new ArrayList<>();
        //blacklists.add(new Blacklist.Builder().setField_name("parallelLockMap").build());

        RequstSearchByRecursive searcher = new RequstSearchByRecursive(thread,keys,10000,true);
        searcher.searchObject();
    }
}