package com.itstaredu.flowsort;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/18
 * @description:
 * @version: 1.0
 * 直接完成排序
 */
public class FlowBean implements WritableComparable<FlowBean> {
    private long upFlow;
    private long dfFlow;
    private long flowSum;

    public FlowBean() {
    }

    public long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    public long getDfFlow() {
        return dfFlow;
    }

    public void setDfFlow(long dfFlow) {
        this.dfFlow = dfFlow;
    }

    public long getFlowSum() {
        return flowSum;
    }

    public void setFlowSum(long flowSum) {
        this.flowSum = flowSum;
    }

    public FlowBean(long upFlow, long dfFlow){
        this.upFlow = upFlow;
        this.dfFlow = dfFlow;
        this.flowSum = upFlow + dfFlow;
    }

    /**
     * 序列化
     * @param dataOutput
     * @throws IOException
     */
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(upFlow);
        dataOutput.writeLong(dfFlow);
        dataOutput.writeLong(flowSum);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        upFlow = dataInput.readLong();
        dfFlow = dataInput.readLong();
        flowSum = dataInput.readLong();

    }

    @Override
    public int compareTo(FlowBean o) {
        //倒叙排序
        return this.flowSum > o.getFlowSum() ? -1 : 1;
    }

    @Override
    public String toString() {
        return upFlow + "\t" + dfFlow + "\t" + flowSum;
    }
}
