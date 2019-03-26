package runner;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: lzm
 * @date: 2019/3/26
 * @description:
 * @version: 1.0
 */
public class CollectionDemo {
    public static void main(String[] args) {
        Hashtable<String,String> hashtable = new Hashtable<>();
        HashMap<String,String> hashMap = new HashMap<>();
        ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap<>();
        hashMap.put(null,null);
        hashMap.containsKey("hello");
        hashtable.put("hello","hello");
        concurrentHashMap.put("","");
    }
}
