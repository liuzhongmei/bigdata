package com.itstaredu.flowsort;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/18
 * @description:
 * @version: 1.0
 */
public class FlowSortReducer extends Reducer<FlowBean,Text,Text,FlowBean> {
    @Override
    protected void reduce(FlowBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        context.write(values.iterator().next(),key);
    }
}
