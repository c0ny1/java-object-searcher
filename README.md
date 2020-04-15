# Java Object Searcher | java内存对象搜索

## 0x01 工具简介

```
#############################################################
   Java Object Searcher v0.1
   author: c0ny1<root@gv7.me>
   github: http://github.com/c0ny1/java-object-searcher
#############################################################
```

配合IDEA在Java应用运行时，对内存中的对象进行搜索。比如可以可以用挖掘request对象用于回显等场景。

## 0x02 使用步骤

搜索语法


keywords

```
FiledName:
FiledValue:
FiledType:
```

backlist

```$xslt
field-backlist
object-backlist
```


**1. 将项目的java引入到目标应用的classpath中**

**2. 在IDEA调试时,下好断点并在计算器中输入如下代码即可搜索**

```
Thread target = Thread.current(); # 搜索的目标对象
String[] keys = new String[]{"Servlet","request"}; # 搜索的关键字
String[] backlist = new String[]{"logger"}; # 黑名单，表示不会对改类型属性进行搜索
int dig_depth = 100; # 递归搜索的深度
RequestSearcher searcher = new RequstSearcher(target,keys,"SearchRequst",dig_depth);
searcher.searchObject();
```

**3. 结果会保存在目标应用的运行目录下**