package com.baidu.day21;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 * @Author liaojincheng
 * @Date 2020/6/1 22:34
 * @Version 1.0
 * @Description
 * 指定offset位置进行消费数据
 * 之前咱们消费的方式是从头开始
 * partition 0 --> offset[0, 22]
 * partition 1 --> offset[0, 20]
 * partition 2 --> offset[0, 21]
 * 现在要去从指定offset位置消费
 * partition 0 --> offset 10
 * partition 1 --> offset 10
 * partition 2 --> offset 10
 * 注意: 这里从指定的offset位置开始消费,那么我们需要使用assign API来完成
 * 说白了就是指定具体的所有信息即可
 */
public class _02MyConsumerSeekOffsetTest {
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        prop.load(
                _01MyConsumerTest.class.getClassLoader().getResourceAsStream("consumer.properties"));
        //构建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(prop);
        //消费对应的Topic数据,要将所有的分区指定好,少了就报错No current assignment for partition
        consumer.assign(Arrays.asList(
                new TopicPartition("hadoop", 0),
                new TopicPartition("hadoop", 1),
                new TopicPartition("hadoop", 2)
        ));
        //指定消费的偏移量位置
        consumer.seek(new TopicPartition("hadoop", 0), 10);//定位
        consumer.seek(new TopicPartition("hadoop", 1), 10);//定位
        consumer.seek(new TopicPartition("hadoop", 2), 10);//定位
        System.out.println("topic\tpartition\toffset\tkey\tvalue");
        while(true) {
            /**
             * 消费数据 timeout:从consumer的缓冲区Buffer中获取可用数据的等待时间,
             * 如果设置为0，则会理解成返回该缓冲区内的所有数据,如果不设置为0，返回为空
             */
            ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
            for(ConsumerRecord<String,String> cr : consumerRecords) {
                String topic = cr.topic();
                int partition = cr.partition();
                String key = cr.key();
                String value = cr.value();
                long offset = cr.offset();
                System.out.printf("topic%s\t partition%d\t offset%d\t key%s\t value%s\r\n",
                        topic,partition,offset,key,value);
            }
        }
    }
}
