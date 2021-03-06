package service.kafkaService;

import ConstField.SharedInfo;
import domin.KafkaDataStruct;
import net.sf.json.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import service.UdpTrapService.UdpTrapService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Properties;

@Service
public class MyKafkaConsumer implements InitializingBean {

    @Value("#{properties['bootstrap.servers']}")
    private String bootstrapServers;

    @Value("#{properties['topic']}")
    private String topic;

    @Value("#{properties['pollTimeout']}")
    private int pollTimeout;

    @Value("#{properties['groupId']}")
    private String groupId;

    @Value("#{properties['enable.auto.commit']}")
    private String autoComit;

    @Value("#{properties['auto.offset.reset']}")
    private String offset;

    @Value("#{properties['session.timeout.ms']}")
    private String sessionTimeout;

    @Value("#{properties['key.deserializer']}")
    private String keyDeserializer;

    @Value("#{properties['value.deserializer']}")
    private String valueDeserializer;

    @Value("#{properties['minBatchSize']}")
    private int minBatchSize;

    @Resource
    private UdpTrapService udpTrapService;

    @Override
    public void afterPropertiesSet() {
        new Thread(new MyConsumer()).start();
    }

    class MyConsumer implements Runnable {

        @Override
        public void run() {
            KafkaConsumer<String, Object> consumer = createConsumer();

            consumer.subscribe(Collections.singletonList(topic));

            while (true) {
                ConsumerRecords<String, Object> records = consumer.poll(pollTimeout);

                for (ConsumerRecord<String, Object> record : records) {

                    Object o = record.value();
                    if(o instanceof  KafkaDataStruct) {
                        KafkaDataStruct kafkaDataStruct = (KafkaDataStruct) o;
                        byte[] data = kafkaDataStruct.getData();
//                        int len = kafkaDataStruct.getLen();
//
//                        for (int i = 0; i < len; ++i) {
//                            System.out.print(Integer.toHexString(data[i] & 0xFF) + " ");
//                        }

                        String str = new String(data, 0, kafkaDataStruct.getLen()).trim();

                        if (SharedInfo.PRINT) {
                            System.out.println("recv udp : " + str);
                        }
                        JSONObject jsonObj = JSONObject.fromObject(str);

                        udpTrapService.processTrap(jsonObj, kafkaDataStruct.getIp(), kafkaDataStruct.getPort());

                        System.out.println("now commit offset");
                        consumer.commitSync();
                    }

                    System.out.println("consumer message values is " + record.value() + " and the offset is " + record.offset());
                }
            }
        }
    }

    private KafkaConsumer<String, Object> createConsumer() {

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", groupId);
        props.put("enable.auto.commit", autoComit);
        props.put("auto.offset.reset", offset);
        props.put("session.timeout.ms", sessionTimeout);
        props.put("key.deserializer", keyDeserializer);
        props.put("value.deserializer", valueDeserializer);

        return new KafkaConsumer<>(props);
    }
}
