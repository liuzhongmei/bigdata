package com.itstaredu.hdfs5;

/**
 * @author: lzm
 * @date: 2019/1/13
 * @description:
 * @version: 1.0
 *
 * 思路：
 *   添加一个map方法，单词切分，相同key的value++
 */
public class WordCountMapper implements Mapper {
    @Override
    public void map(String line, Context context) {
        String[] words = line.split(" ");
        for (String word:words) {
            Object value = context.get(word);
            if(null != value){
                int v = (int)value;
                context.write(word,v+1);
            }else{
                context.write(word,1);
            }
        }
    }
}
