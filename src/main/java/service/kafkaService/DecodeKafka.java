package service.kafkaService;

import org.apache.kafka.common.serialization.Deserializer;
import service.serializeService.SerializeUtil;

import java.io.*;
import java.util.Map;

public class DecodeKafka implements Deserializer<Object>{

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public Object deserialize(String topic, byte[] data) {

        return SerializeUtil.deserialize(data);
    }

    @Override
    public void close() {

    }
}
