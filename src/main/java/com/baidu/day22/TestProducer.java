package com.baidu.day22;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author liaojincheng
 * @Date 2020/6/14 12:10
 * @Version 1.0
 * @Description
 * 测试
 */
public class TestProducer {
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        prop.load(TestProducer.class.getClassLoader().getResourceAsStream("producer.properties"));
        KafkaProducer<String, String> producer = new KafkaProducer<>(prop);
        String topic = "test1903";
        BufferedReader br = new BufferedReader(new FileReader("data\\access.txt"));
        String line = null;
        int count = 0;
        while((line = br.readLine()) != null){
            System.out.println(line);
            producer.send(new ProducerRecord<String, String>(topic, count++ + "", line));
        }
        //关闭释放资源
        producer.close();
        br.close();
    }
}
