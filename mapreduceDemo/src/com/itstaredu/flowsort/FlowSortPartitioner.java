package com.itstaredu.flowsort;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author: lzm
 * @date: 2019/1/18
 * @description:
 * @version: 1.0
 * 手机号前3位分区
 * 流量额倒叙排序
 */
public class FlowSortPartitioner extends Partitioner<FlowBean,Text> {
    @Override
    public int getPartition(FlowBean flowBean,Text text, int numPartitions) {
        //1.获取手机号前三位
        String phoneNum = text.toString().substring(0, 3);

        //2.分区
        if("135".equals(phoneNum)){
            return 0;
        }else if("137".equals(phoneNum)){
            return 1;
        }else if("138".equals(phoneNum)){
            return 2;
        }else if ("139".equals(phoneNum)){
            return 3;
        }
        return 4;
    }
}
