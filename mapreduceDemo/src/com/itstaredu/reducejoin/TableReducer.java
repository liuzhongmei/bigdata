package com.itstaredu.reducejoin;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author: lzm
 * @date: 2019/1/19
 * @description:
 * @version: 1.0
 */
public class TableReducer extends Reducer<Text,TableBean,TableBean, NullWritable> {
    @Override
    protected void reduce(Text key, Iterable<TableBean> values, Context context) throws IOException, InterruptedException {
        //创建集合 存放订单数据
        ArrayList<TableBean> arrayList = new ArrayList<>();

        //商品存储
        HashMap<String,String> map = new HashMap<>();

        for (TableBean v:values){
            if("0".equals(v.getFlag())){//订单表
                //1.创建一个临时变量拷贝数据
                TableBean tableBean = new TableBean();
                //2.拷贝
                tableBean.setOrderId(v.getOrderId());
                tableBean.setPid(v.getPid());
                tableBean.setAmount(v.getAmount());
                tableBean.setPname(v.getPname());
                tableBean.setFlag(v.getFlag());
                arrayList.add(tableBean);
            }else{
                map.put(v.getPid(),v.getPname());
            }
        }
        //拼接表
        for (TableBean tableBean:arrayList){
            //加入商品名
            tableBean.setPname(map.get(tableBean.getPid()));
            context.write(tableBean,NullWritable.get());
        }
    }
}
