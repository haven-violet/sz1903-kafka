package com.baidu.day21;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author liaojincheng
 * @Date 2020/6/2 11:47
 * @Version 1.0
 * @Description
 */
public class _01MyProducerTest {
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        prop.load(_01MyProducerTest.class.getClassLoader().getResourceAsStream("consumer.properties"));
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(prop);
        String topic = "hadoop";
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, "1234");
        for(int i=0; i<10; i++){
            producer.send(record);
        }
        producer.close();
    }
}
