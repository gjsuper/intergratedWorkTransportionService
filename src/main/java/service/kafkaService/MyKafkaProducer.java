package service.kafkaService;

import domin.KafkaDataStruct;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.Random;

@Service
public class MyKafkaProducer implements InitializingBean {

    @Value("#{properties['bootstrap.servers']}")
    private String bootstrapServers;

    @Value("#{properties['retries']}")
    private int retries;

    @Value("#{properties['linger.ms']}")
    private int lingerMs;

    @Value("#{properties['key.serializer']}")
    private String keyDeserializer;

    @Value("#{properties['value.serializer']}")
    private String valueDeserializer;

    @Value("#{properties['topic']}")
    private String topic;

    private KafkaProducer<String, KafkaDataStruct> producer;

    private Random random = new Random();

    private KafkaProducer<String, KafkaDataStruct> createProducer() {
        Properties properties = new Properties();

        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("retries", retries);
        properties.put("linger.ms", lingerMs);
        properties.put("key.serializer",
                keyDeserializer);
        properties.put("value.serializer",
                valueDeserializer);

        return new KafkaProducer<>(properties);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        producer = createProducer();
    }

    private String generateKey() {

        return "" + random.nextInt(Integer.MAX_VALUE);
    }

    public void sendMessage(KafkaDataStruct dataStruct) {
        ProducerRecord<String, KafkaDataStruct> record = new ProducerRecord<>(topic, generateKey(), dataStruct);

        if(producer == null) {
            producer = createProducer();
        }

        producer.send(record);

        System.out.println("send to kafka finsh.....");
    }
}
