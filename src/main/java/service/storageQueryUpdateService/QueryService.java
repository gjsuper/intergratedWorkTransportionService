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
public class QueryService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    private StorageMapper storageMapper;

    @Resource
    private RedisClusterUtils redisClusterUtils;

    @Resource
    private UdpInterface udpInterface;


    void dealQuerySignal(KafkaDataStruct kafkaDataStruct) {

        byte[] data = kafkaDataStruct.getData();

        int index = 2;
        int len = data[index ++] & 0xFF;

        //key
        String key = Arrays.toString(Arrays.copyOfRange(data, index, index + len));

        String value = get(key);

        byte result = value == null ? ConstantField.QUERY_KEY_NOT_EXISTS : ConstantField.QUERY_SUCCESS;
        value = value == null ? "" : value;

        byte[] sendBuf = DataBuilder.buildQueryResponse(result, key, value);

        udpInterface.send(kafkaDataStruct.getIp(), kafkaDataStruct.getPort(), sendBuf);
    }

    private String get(String key) {
        Object value = redisClusterUtils.get(key);

        if (value != null) {

            if (value instanceof String) {
                return (String) value;
            }

            return null;
        } else {
            String v = storageMapper.getBykey(key);

            if(v != null) {
                redisClusterUtils.set(key, v);
            }

            return v;
        }
    }
}
