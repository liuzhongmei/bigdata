package com.itstaredu.flowpartition;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author: lzm
 * @date: 2019/1/17
 * @description:
 * @version: 1.0
 */
public class PhoneNumPartitioner extends Partitioner<Text, FlowBean> {
    /**
     * 根据手机号前三位进行分区
     * @param text
     * @param flowBean
     * @param numPartitions
     * @return
     */
    @Override
    public int getPartition(Text text, FlowBean flowBean, int numPartitions) {
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
