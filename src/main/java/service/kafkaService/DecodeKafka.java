package service.kafkaService;

import org.apache.kafka.common.serialization.Deserializer;

import java.io.*;
import java.util.Map;

public class DecodeKafka implements Deserializer<Object>{

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public Object deserialize(String topic, byte[] data) {
        Object readObject = null;
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream inputStream;

        try {
            inputStream = new ObjectInputStream(in);
            readObject = inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return readObject;
    }

    @Override
    public void close() {

    }
}
