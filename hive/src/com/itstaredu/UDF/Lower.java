package com.itstaredu.UDF;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author: lzm
 * @date: 2019/2/18
 * @description:
 * @version: 1.0
 */
public class Lower extends UDF {
    /**
     * 大写转化为小写
     * @param s
     * @return
     */
    public String evaluate(final String s){
        if(s == null){
            return null;
        }
        return s.toLowerCase();
    }
}
