package me.gv7.tools.josearcher.test;

import java.util.HashMap;
import java.util.Map;

public class TestBaseClass {
    public java.lang.Class test = TestBaseClass.class;
    public EntityTest public_entity = new EntityTest();
    protected EntityTest protected_entity = new EntityTest();
    private EntityTest private_entity = new EntityTest();
    public Map<Object,Object> map1 = new HashMap<>();
    protected Map<Object,Object> map2 = new HashMap<>();
    public TestBaseClass(){
        map1.put("aaa",new Object[0]);
        map1.put("bbb",new Object[0]);
        map1.put("ccc",new Object[0]);
        map1.put("ddd",new Object[0]);
    }
}
