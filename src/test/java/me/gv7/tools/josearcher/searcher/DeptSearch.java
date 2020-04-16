package me.gv7.tools.josearcher.searcher;

import java.util.*;

public class DeptSearch {
    public static void main(String args[]){
        //构造需要点对象
        NodeT a=new NodeT("a");
        NodeT b=new NodeT("b");
        NodeT c=new NodeT("c");
        NodeT d=new NodeT("d");
        NodeT e=new NodeT("e");
        NodeT f=new NodeT("f");
        NodeT g=new NodeT("g");
        NodeT h=new NodeT("h");
        ArcT ab=new ArcT(a,b);
        ArcT ac=new ArcT(a,c);
        ArcT ad=new ArcT(a,d);
        ArcT ah=new ArcT(a,h);
        ArcT bc=new ArcT(b,c);
        ArcT de=new ArcT(d,e);
        ArcT ef=new ArcT(e,f);
        ArcT eg=new ArcT(e,g);
        ArcT hg=new ArcT(h,g);

        //建立它们的关系
        a.outgoing.add(ab);
        a.outgoing.add(ac);
        a.outgoing.add(ad);
        a.outgoing.add(ah);
        b.outgoing.add(bc);
        d.outgoing.add(de);
        e.outgoing.add(ef);
        e.outgoing.add(eg);
        h.outgoing.add(hg);

        //构造本对象
        DeptSearch search=new DeptSearch();

        //广度遍历
        System.out.println("广度遍历如下：");
        search.widthSearch(a);

        //深度遍历
        System.out.println("深度遍历如下：");
        List<NodeT> visited=new ArrayList<NodeT>();
        search.deptFisrtSearch(a,visited);

    }

    /*
     * 深度排序的方法
     * 这个方法的方式：按一个节点，一直深入的找下去，直到它没有节点为止
     * cur  当前的元素
     * visited 访问过的元素的集合
     */
    void deptFisrtSearch(NodeT cur,List<NodeT> visited){
        //被访问过了，就不访问，防止死循环
        if(visited.contains(cur)) return;
        visited.add(cur);
        System.out.println("这个遍历的是："+cur.word);
        for(int i=0;i<cur.outgoing.size();i++){
            //访问本点的结束点
            deptFisrtSearch(cur.outgoing.get(i).end,visited);
        }
    }

    /**
     * 广度排序的方法
     * 这个方法的方式：按层次对图进行访问，先第一层，再第二层，依次类推
     * @param start 从哪个开始广度排序
     */
    void widthSearch(NodeT start){
        //记录所有访问过的元素
        Set<NodeT> visited=new HashSet<NodeT>();
        //用队列存放所有依次要访问元素
        Queue<NodeT> q=new LinkedList<NodeT>();
        //把当前的元素加入到队列尾
        q.offer(start);

        while(!q.isEmpty()){
            NodeT cur=q.poll();
            //被访问过了，就不访问，防止死循环
            if(!visited.contains(cur)){
                visited.add(cur);
                System.out.println("查找的节点是："+cur.word);
                for(int i=0;i<cur.outgoing.size();i++){
                    //把它的下一层，加入到队列中
                    q.offer(cur.outgoing.get(i).end);
                }
            }
        }
    }

}

/**
 * 图的点
 */
class NodeT
{
    /* 点的所有关系的集合 */
    List<ArcT> outgoing;
    //点的字母
    String word;
    public NodeT(String word){
        this.word=word;
        outgoing=new ArrayList<ArcT>();
    }
}

/**
 * 单个图点的关系
 */
class ArcT
{
    NodeT start,end;/* 开始点，结束点 */
    public ArcT(NodeT start,NodeT end){
        this.start=start;
        this.end=end;
    }
}
