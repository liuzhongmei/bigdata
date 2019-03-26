package com.itstaredu;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author: lzm
 * @date: 2019/3/6
 * @description:
 * @version: 1.0
 */
public class Map {
    private static TreeMap<Integer,String> m = new TreeMap<>();
    private static TreeMap<String,String> m2 = new TreeMap<>();

    private static HashMap<String,String> hm = new HashMap<>();
    public static void main(String[] args) {
        m.put(4,"");
        m.put(3,"");
        m.put(1,"");
        m.put(2,"");
        m.put(5,"");
        m.remove(m.firstKey());
        Set<Integer> integers = m.keySet();
        for(Integer i:integers){
            System.out.println(i);
        }

        m2.put("hello","hello");
        m2.put("hadoop","hadoop");
        m2.put("aaa","aaa");
        m2.put("aab","aab");
        Set<String> strings = m2.keySet();
        for(String i:strings){
            System.out.println(i);
        }
    }
}
