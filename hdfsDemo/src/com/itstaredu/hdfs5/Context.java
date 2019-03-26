package com.itstaredu.hdfs5;

import java.util.HashMap;

/**
 * @author: lzm
 * @date: 2019/1/13
 * @description:
 * @version: 1.0
 *
 * 数据传输的类
 * 封装数据
 * 集合
 */
public class Context {
    // 数据封装
    private HashMap<Object,Object> contextMap =  new HashMap<>();

    /**
     * 写数据
     * @param key
     * @param value
     */
    public void write(Object key,Object value){
        //放数据到map中
        contextMap.put(key,value);
    }

    /**
     * 根据key获取value
     * @param key
     * @return
     */
    public Object get(Object key){
        return contextMap.get(key);
    }

    /**
     * 获取map
     * @return
     */
    public HashMap<Object,Object> getContextMap (){
        return contextMap;
    }
}
