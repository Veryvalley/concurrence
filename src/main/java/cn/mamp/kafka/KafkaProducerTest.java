package cn.mamp.kafka;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author mamp
 * @data 2020/9/1
 */
public class KafkaProducerTest {
    public static void main(String[] args) {
        try {
            produce();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void produce() throws InterruptedException {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.92.130:9092");
        properties.setProperty("producer.type", "sync");
        properties.setProperty("serializer.class", "kafka.serializer.StringEncoder");
        properties.setProperty("ompression.codec", "0");
        properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<String, String>(properties);
        String topicName = "cgf_topic_888";
//        String message = "a,13666666666,1,1";
        String message = "{\"BUSINESS_ID\":\"500000020103\",\"CHANNEL_ID\":\"1\",\"TERMINAL_TYPE\":\"\",\"EVENT_CODE\":\"CIC_001301\",\"EXT_1\":\"\",\"OFFER_ID\":\"212086802878\",\"OPERATE_TYPE\":\"CREATE\",\"ORDER_STATE\":\"1\",\"CHANNEL_CODE\":\"29\",\"OFFER_TYPE\":\"OFFER_VAS_OTHER\",\"BUSI_TYPE\":\"0\",\"CUST_CODE\":\"18884910425\",\"SO_DONE_DATE\":\"2018-10-31 23:54:25\"}";
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, message);

        while (true) {
            producer.send(producerRecord);
            System.out.println("--");
            Thread.sleep(5000);
        }
    }
}


