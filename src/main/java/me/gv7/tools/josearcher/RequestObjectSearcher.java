package me.gv7.tools.josearcher;

/**
 * 本类用于搜索Java对象中是否存在request相关的属性，比如可以在反序列化需要回显的场景，用于辅助挖掘request对象。
 */
public class RequestObjectSearcher {
    private String[] keys; /* name:xxx,value:xxx,type:request|context */
    private String[] backlist; /*  */
}
