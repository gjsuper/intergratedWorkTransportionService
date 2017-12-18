package service.storageQueryUpdateService;

import DataBuilder.DataBuilder;
import domin.ConstantField;
import domin.KafkaDataStruct;
import org.springframework.stereotype.Service;
import service.transportationService.UdpInterface;
import sqlMapper.StorageMapper;

import javax.annotation.Resource;
import java.util.Arrays;

@Service
public class StorageService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    private StorageMapper storageMqpper;

    @Resource
    private UdpInterface udpInterface;

    void dealStorageSignal(KafkaDataStruct kafkaDataStruct) {


        byte[] data = kafkaDataStruct.getData();

        //key
        int index = 2;
        int len = data[index++] & 0xFF;
        String key = Arrays.toString(Arrays.copyOfRange(data, index, index + len));

        index += len;

        //value
        len = data[index++] & 0xFF;
        len += (data[index++] & 0xFF) << 8;

        String value = Arrays.toString(Arrays.copyOfRange(data, index, index + len));

        byte[] sendBuf;

        try {
            storageMqpper.add(key, value);
        } catch (Exception e) {
            sendBuf = DataBuilder.buildStorageResponse(ConstantField.STORE_KEY_EXISTS);
            udpInterface.send(kafkaDataStruct.getIp(), kafkaDataStruct.getPort(), sendBuf);

            System.out.println(StorageService.class + ".dealStorageSignal:key already exists");
        }

        sendBuf = DataBuilder.buildStorageResponse(ConstantField.STORE_SUCCESS);

        udpInterface.send(kafkaDataStruct.getIp(), kafkaDataStruct.getPort(), sendBuf);
    }
}
