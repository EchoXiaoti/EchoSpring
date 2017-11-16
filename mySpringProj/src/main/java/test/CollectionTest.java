package test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * 集合类测试
 *
 * @author Echo-cxt
 * @create 2017-11-14 1:41 PM
 **/
public class CollectionTest {

    public static void main(String[] args) {
        for(int i=0;i<10000;i++){

        }
        long start = System.currentTimeMillis();

        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        for(int i=0;i<100000;i++){
            linkedList.add(0,i);
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for(int i=0;i<100000;i++){
            arrayList.add(0,i);
        }

        System.out.println(System.currentTimeMillis() - end);

        Random random = new Random();

        for(int i=0;i<10000;i++){

        }
        LinkedList<Integer> linkedList2 = new LinkedList<Integer>();
        for(int i=0;i<100000;i++){
            linkedList2.add(i);
        }

        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        for(int i=0;i<100000;i++){
            arrayList2.add(i);
        }

        long start2 = System.currentTimeMillis();


        for(int i=0;i<100000;i++){
            int j = random.nextInt(i+1);
            int k = linkedList2.get(j);
        }

        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start2);

        for(int i=0;i<100000;i++){
            int j = random.nextInt(i+1);
            int k = arrayList2.get(j);
        }

        System.out.println(System.currentTimeMillis() - end2);
    }
}
