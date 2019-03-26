package com.itstaredu.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author: lzm
 * @date: 2019/2/23
 * @description: kafka生产者API
 * @version: 1.0
 */
public class Producer1 {
    public static void main(String[] args) {
        //1.配置生产者属性（指定多个参数）
        Properties properties = new Properties();
        //参数配置
        //kafka节点的地址
        properties.put("bootstrap.servers","192.168.230.201:9092");
        //发送消息是否等待应答
        properties.put("acks","all");
        //配置发送消息失败重试
        properties.put("retries","0");
        //配置批量处理消息大小
        properties.put("batch.size","10241");
        //配置批量处理消息的延迟
        properties.put("liners.mr","5");
        //配置内存缓冲
        properties.put("buffer.memory","123412345");

        //消息在发送前必须序列化
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        //2.实例化producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        //3.发送消息
        for(int i = 0;i<99;i++) {
            producer.send(new ProducerRecord<String, String>("shengdan", "hunterhenshuai" + i) );
        }

        //4.释放资源
        producer.close();
    }
}
