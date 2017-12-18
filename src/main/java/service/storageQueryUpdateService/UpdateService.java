package service.storageQueryUpdateService;

import DataBuilder.DataBuilder;
import domin.ConstantField;
import domin.KafkaDataStruct;
import org.springframework.stereotype.Service;
import service.redisService.RedisClusterUtils;
import service.transportationService.UdpInterface;
import sqlMapper.StorageMapper;

import javax.annotation.Resource;
import java.util.Arrays;

@Service
public class UpdateService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    private StorageMapper storageMqpper;

    @Resource
    private UdpInterface udpInterface;

    @Resource
    private RedisClusterUtils redisClusterUtils;

    void dealUpdateSignal(KafkaDataStruct kafkaDataStruct) {


        byte[] data = kafkaDataStruct.getData();

        //key
        int index = 2;
        int len = data[index++] & 0xFF;
        String key = Arrays.toString(Arrays.copyOfRange(data, index, index + len));

        index += len;

        //value
        len = data[index ++] & 0xFF;
        len += (data[index ++] & 0xFF) << 8;

        String value = Arrays.toString(Arrays.copyOfRange(data, index, index + len));

        byte[] sendBuf;

        try {
            storageMqpper.update(key, value);
        } catch (Exception e) {
            System.out.println(UpdateService.class + ".dealUpdateSignal:key not exists");
            sendBuf = DataBuilder.buildUpdateResponse(ConstantField.UPDATE_KEY_NOT_EXISTS);

            udpInterface.send(kafkaDataStruct.getIp(), kafkaDataStruct.getPort(), sendBuf);
        }

        sendBuf = DataBuilder.buildUpdateResponse(ConstantField.UPDATE_SUCCESS);

        udpInterface.send(kafkaDataStruct.getIp(), kafkaDataStruct.getPort(), sendBuf);


        //如果redis里有数据，则更新缓存
        if(redisClusterUtils.get(key) != null) {

            redisClusterUtils.set(key, value);
        }
    }
}
