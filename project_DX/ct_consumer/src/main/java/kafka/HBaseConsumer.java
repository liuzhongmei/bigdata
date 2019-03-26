package kafka;

import hbase.HBaseDAO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import util.PropertiesUtil;

import java.util.Arrays;

/**
 * @author: lzm
 * @date: 2019/3/18
 * @description:
 * @version: 1.0
 */
public class HBaseConsumer {
    public static void main(String[] args) {
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(PropertiesUtil.properties);
        //订阅消息主题
        kafkaConsumer.subscribe(Arrays.asList(PropertiesUtil.getProperties("kafka.topics")));
        HBaseDAO hd = new HBaseDAO();

        while (true) {
            //拉取数据
            ConsumerRecords<String, String> records = kafkaConsumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                String value = record.value();
//                System.out.println(value);
                hd.put(value);
            }
        }
    }
}
