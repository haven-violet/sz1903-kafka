package com.baidu.day21;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 * @Author liaojincheng
 * @Date 2020/6/2 10:04
 * @Version 1.0
 * @Description
 * kafka自动提交偏移量操作
 * 这种自动提交offset是会周期性的进行offset提交,如果该周期设置的时间比较大,就有可能造成数据的读取重复,
 * 所以我们如果使用这种提交方式的话,那么应该尽可能把时间设置短一点
 * 这种方式有一点问题,因为自动管理是周期性提交方式,那么如果在这种周期提交的时候,正好挂了,此时这个周期
 * 的offset就没交上去了,那么就丢失了,造成数据不准,这种问题如何解决?
 * 就需要我们自己手动管理offset了
 *
 * kafka手动维护offset
 * 注释: 手动管理offset会在SparkStreaming中重点讲解,这里简单介绍如何管理即可,
 * 然后将这个offset存入一个系统或者数据库都行,就将它保存起来,当我们数据处理完成后，在
 * 往系统或者数据库提交此offset，这样offset在我们自己的系统或者数据库中，我们就不用担心周期性提交失败问题
 */
public class _03AutoOffsetCommitConsumerOps {
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        prop.load(_03AutoOffsetCommitConsumerOps.class.getClassLoader().getResourceAsStream("consumer.properties"));
        //构建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(prop);
        //消费对应的topic数据
        consumer.subscribe(Arrays.asList("hadoop"));
        //打印所有相关的数据信息
        System.out.println("topic\t partition\t offset\t key\t value");
        while(true){
            /*
            消费数据timeout:从consumer的缓冲区buffer中获取可用数据的等待超时时间
            如果设置为0,则会理解返回该缓冲区内的所有数据,如果不设置为0,返回为空,并且不能写负数
            */
            ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
            for(ConsumerRecord<String, String> cr : consumerRecords) {
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