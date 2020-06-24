package com.baidu.day21;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 * @Author liaojincheng
 * @Date 2020/6/1 20:52
 * @Version 1.0
 * @Description Kafka消费者
 */
public class _01MyConsumerTest {
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        prop.load(_01MyConsumerTest.class
            .getClassLoader().getResourceAsStream("consumer.properties"));

        //构建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(prop);
        //消费对应的topic数据
        consumer.subscribe(Arrays.asList("hadoop"));
        //打印所有相关的数据信息
        System.out.println("topic\t partition\t offset\t key\t value");
        while(true) {
            /*
            消费数据 timeout: 从consumer的缓冲区Buffer中获取可用数据的等待时间
            如果设置为0, 则会理解返回该缓冲区内的所有数据,如果不设置为0,返回空,并且不能写负数
             */
            ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
            for(ConsumerRecord<String, String> cr : consumerRecords){
                String topic = cr.topic();
                int partition = cr.partition();
                String key = cr.key();
                String value = cr.value();
                long offset = cr.offset();
                System.out.printf(
                        "topic:%s\t partition:%d\t offset:%d\t key:%s\t value:%s\r\n",
                        topic,partition,offset,key,value);
            }

        }
    }
}
