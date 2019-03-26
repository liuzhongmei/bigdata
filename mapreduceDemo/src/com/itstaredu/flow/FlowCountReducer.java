package com.itstaredu.flow;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/16
 * @description:
 * @version: 1.0
 */
public class FlowCountReducer extends Reducer<Text, FlowBean, Text, FlowBean>  {
    /**
     *
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        //1.相同手机号的流量使用再次汇总  相同key在一个迭代器中
        long upFlowSum = 0;
        long dfFlowSum = 0;

        //2.累加
        for (FlowBean flowBean:values) {
            upFlowSum += flowBean.getUpFlow();
            dfFlowSum += flowBean.getDfFlow();
        }
        //3.输出
        context.write(key,new FlowBean(upFlowSum,dfFlowSum));
    }
}
