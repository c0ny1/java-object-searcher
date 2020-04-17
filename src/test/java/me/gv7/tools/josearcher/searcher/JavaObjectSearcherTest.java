package me.gv7.tools.josearcher.searcher;


import me.gv7.tools.josearcher.test.TestClass;

import java.lang.reflect.Field;

class JavaObjectSearcherTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        TestClass target =  new TestClass();

        String[] keys = new String[]{"Request","ServletRequst", "Entity"};

        JavaObjectSearcher grab = new JavaObjectSearcher(target,keys,"JavaObjectSearcherTest",1000,true);
        grab.searchObject();
    }
}