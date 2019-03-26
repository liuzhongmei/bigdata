package com.itstaredu.hdfs5;

/**
 * @author lzm
 * @date 2019/1/13
 * @description
 *
 */
public interface Mapper {
    /**
     * 通用方法
     * @param line
     * @param context
     */
    public void map(String line,Context context);
}
