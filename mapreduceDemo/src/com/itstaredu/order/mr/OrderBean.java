package com.itstaredu.order.mr;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/18
 * @description:
 * @version: 1.0
 */
public class OrderBean implements WritableComparable<OrderBean> {
    //订单id
    private int orderId;
    //价格
    private double price;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderBean(){

    }

    public OrderBean(int orderId, double price) {
        this.orderId = orderId;
        this.price = price;
    }

    /**
     * 排序 id正序，price倒序
     * @param o
     * @return
     */
    @Override
    public int compareTo(OrderBean o) {
        int rs = 0;
        if(this.orderId > o.getOrderId()){
            rs = 1;
        }else if (this.orderId < o.getOrderId()){
            rs = -1;
        }else{
            rs = this.price > o.getPrice() ? -1 : 1;
        }
        return rs;
    }

    /**
     * 序列化
     * @param dataOutput
     * @throws IOException
     */
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(orderId);
        dataOutput.writeDouble(price);
    }

    /**
     * 反序列化
     * @param dataInput
     * @throws IOException
     */
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        orderId = dataInput.readInt();
        price = dataInput.readDouble();
    }

    @Override
    public String toString() {
        return orderId + "\t" + price;
    }
}
