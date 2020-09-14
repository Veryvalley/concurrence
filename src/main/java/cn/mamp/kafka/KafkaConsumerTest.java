package cn.mamp.kafka;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author mamp
 * @data 2020/9/1
 */
public class KafkaConsumerTest {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerTest.class);

    private static int partition_num = 1;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(partition_num);

    public static void main(String[] args) {

        try {
            consumer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void consumer() throws InterruptedException {
        Properties properties = new Properties();


        properties.setProperty("bootstrap.servers", "192.168.92.130:9092");
        properties.setProperty("zookeeper.connection.timeout.ms", "1000000");
        properties.setProperty("auto.commit.enable", "true");
        properties.setProperty("group.id", "mcd-cgf");
        // 注意： 这里是 deserializer 与 生产者不同
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        properties.setProperty("auto.commit.interval.ms", "60000");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        List<String> topic = new ArrayList<>();
        topic.add("cgf_topic_888");
        consumer.subscribe(topic);
        ConsumerRecords<String, String> recordsData = null;
        List<ConsumerRecord<String, String>> records = null;
        while (true) {
            // 每隔一秒pull一次
            recordsData = consumer.poll(1000);
            Set<TopicPartition> partitions = recordsData.partitions();
            for (TopicPartition partition : partitions) {
                records = recordsData.records(partition);
                // 可以每个分区开一个线程去消费
                threadPool.submit(new CounsumerRunner(records));
            }
        }
    }
}

class CounsumerRunner implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(CounsumerRunner.class);
    private List<ConsumerRecord<String, String>> records;

    public CounsumerRunner(List<ConsumerRecord<String, String>> records) {
        this.records = records;
    }

    @Override
    public void run() {
        System.out.println("thread " + Thread.currentThread().getName() + " started...");
        for (ConsumerRecord record : records) {
            log.error("topic:{},key:{},value:{}", record.topic(), record.key(), record.value());
//            System.out.println(record.value());
        }
    }
}


