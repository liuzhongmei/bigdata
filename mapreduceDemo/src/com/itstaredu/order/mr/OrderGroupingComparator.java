package com.itstaredu.order.mr;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author: lzm
 * @date: 2019/1/18
 * @description:
 * @version: 1.0
 */
public class OrderGroupingComparator extends WritableComparator {
    protected OrderGroupingComparator(){
        super(OrderBean.class,true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        OrderBean aBean = (OrderBean) a;
        OrderBean bBean = (OrderBean) b;
        int rs;
        //id不同不是同一个对象
        if(aBean.getOrderId() > bBean.getOrderId()){
            rs = 1;
        }else if (aBean.getOrderId() < bBean.getOrderId()){
            rs = -1;
        }else{
            rs = 0;
        }
        return rs;
    }
}
