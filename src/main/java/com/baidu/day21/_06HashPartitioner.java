package com.baidu.day21;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @Author liaojincheng
 * @Date 2020/6/2 12:35
 * @Version 1.0
 * @Description
 * 自定义分区之hash分区
 * key的hashCode值 % partitionNum
 */
public class _06HashPartitioner implements Partitioner {
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        //获取topic的分区
        Integer partitionNum = cluster.partitionCountForTopic(topic);
        if(keyBytes != null){
            return Math.abs(key.hashCode()) % partitionNum;
        }
        return 0;//如果key不存在,那么直接返回0
    }

    public void close() {

    }

    public void configure(Map<String, ?> configs) {

    }
}
