package service.dataService;

import domin.ConstantField;
import domin.KafkaDataStruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import service.transportationService.UdpInterface;

import javax.annotation.Resource;

@Service
public class RealTimeDataService {

    @Value("#{properties['serverIp']}")
    private String ip;

    @Value("#{properties['serverPort']}")
    private int port;

    @Resource
    private UdpInterface udpInterface;

    void dealRealTimeData(KafkaDataStruct kafkaDataStruct) {
        byte[] bytes = kafkaDataStruct.getData();

        if(bytes[2] == ConstantField.IP_TYPE) {
            dealIpData(kafkaDataStruct);
        }  else if(bytes[2] == ConstantField.USER_TYPE) {
            dealUserData(kafkaDataStruct);
        }
    }

    private void dealIpData(KafkaDataStruct kafkaDataStruct) {
        udpInterface.send(ip, port ,kafkaDataStruct.getData());
    }

    private void dealUserData(KafkaDataStruct kafkaDataStruct) {
        udpInterface.send(ip, port ,kafkaDataStruct.getData());
    }
}
