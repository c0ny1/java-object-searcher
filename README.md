# Java Object Searcher | java内存对象搜索

## 简介

可以用挖掘request对象用于回显，但不仅仅限制

## 使用

搜索语法

```
FiledName:
FiledValue:
FiledType:
```

```
Thread target = Thread.current();
String[] keys = new String[]{"Servlet","request"};
String[] backlist = new String[]{"logger"};
int dig_depth = 100;
RequestSearcher searcher = new RequstSearcher(target,keys,"SearchRequst",dig_depth);
searcher.searchObject();
```