package com.baidu.day21;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author liaojincheng
 * @Date 2020/6/2 12:41
 * @Version 1.0
 * @Description 自定义分区之轮询分区
 */
public class _07RoundRobinPartitioner implements Partitioner {
    /*
    轮询操作
     */
    private AtomicInteger counter = new AtomicInteger();
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        Integer partitionNum = cluster.partitionCountForTopic(topic);
        return counter.getAndIncrement() % partitionNum;
    }

    public void close() {

    }

    public void configure(Map<String, ?> configs) {

    }
}
