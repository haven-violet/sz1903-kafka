package com.baidu.day20;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author liaojincheng
 * @Date 2020/5/31 23:33
 * @Version 1.0
 * @Description
 */
public class _01MyProducerHome {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Properties prop = new Properties();
        prop.load(_01MyProducerHome
                .class.getClassLoader().getResourceAsStream("producer.properties"));
        //1.创建一个生产者
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(prop);
        //2.创建ProducerRecord
        String topic = "kafka-test1";
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, "520");
        //3.send发送
        Future<RecordMetadata> future = producer.send(record);
        //4.获取结果
        RecordMetadata recordMetadata = future.get();
        boolean b = recordMetadata.hasOffset();
        long offset = recordMetadata.offset();
        boolean b1 = recordMetadata.hasTimestamp();
        long timestamp = recordMetadata.timestamp();
        int partition = recordMetadata.partition();
        String topic1 = recordMetadata.topic();
        if(b){
            System.out.println("offset = " + offset);
        }
        if(b1){
            System.out.println("timestamp = " + timestamp);
        }
        System.out.println("partition = " + partition);
        System.out.println("topic1 = " + topic1);
        //5.关闭producer
        producer.close();

    }
}
