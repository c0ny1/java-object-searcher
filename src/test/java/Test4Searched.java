import me.gv7.tools.josearcher.test.EntityTest;

import java.util.ArrayList;
import java.util.List;

public class Test4Searched {
    public static void main(String[] args) {
        EntityTest entity1 = new EntityTest();
        EntityTest entity2 = new EntityTest();
        EntityTest entity3 = entity1;
        if(entity1 == entity2){
            System.out.println("true");
        }else{
            System.out.println("false");
        }

        if(entity1 == entity3){
            System.out.println("true");
        }else{
            System.out.println("false");
        }


        List<Object> list = new ArrayList<>();
        list.add(entity2);
        list.add(entity3);
        for(Object obj:list){
            if(obj == entity1){
                System.out.println("true");
            }else{
                System.out.println("false");
            }
        }

        List<Object> searched = new ArrayList<>();
        for(Object obj:searched){
            if(obj == entity1){
                return;
            }
            searched.add(entity1);
        }
    }
}
