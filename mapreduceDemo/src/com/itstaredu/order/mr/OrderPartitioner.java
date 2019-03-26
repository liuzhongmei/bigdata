package com.itstaredu.order.mr;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author: lzm
 * @date: 2019/1/18
 * @description:
 * @version: 1.0
 */
public class OrderPartitioner extends Partitioner<OrderBean, NullWritable> {
    @Override
    public int getPartition(OrderBean orderBean, NullWritable nullWritable, int numPartitions) {
        return (orderBean.getOrderId() & Integer.MAX_VALUE) % numPartitions;
    }
}
