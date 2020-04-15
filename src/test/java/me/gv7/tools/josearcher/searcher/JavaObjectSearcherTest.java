package me.gv7.tools.josearcher.searcher;


import java.lang.reflect.Field;

class JavaObjectSearcherTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Thread thread = Thread.currentThread();
        Field f = thread.getClass().getDeclaredField("threadLocals");
        f.setAccessible(true);
        Object target = f.get(thread);
        //TestClass target =  new TestClass();

        String[] keys = new String[]{"Request","ServletRequst", "Entity"};

        JavaObjectSearcher grab = new JavaObjectSearcher(target,keys,"JavaObjectSearcherTest",50,true);
        grab.searchObject();
    }
}