package service.kafkaService;

import org.apache.kafka.common.serialization.Serializer;
import service.serializeService.SerializeUtil;

import java.util.Map;

public class EncodeKafka implements Serializer {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Object data) {
        return SerializeUtil.serialize(data);
    }

    @Override
    public void close() {

    }
}
