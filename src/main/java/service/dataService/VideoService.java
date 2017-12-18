package service.dataService;

import domin.ConstantField;
import domin.KafkaDataStruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import service.transportationService.UdpInterface;

import javax.annotation.Resource;

@Service
public class VideoService {

    @Value("#{properties['serverIp']}")
    private String ip;

    @Value("#{properties['serverPort']}")
    private int port;

    @Resource
    private UdpInterface udpInterface;

    void dealVideoData(KafkaDataStruct kafkaDataStruct) {
        byte[] bytes = kafkaDataStruct.getData();

        if(bytes[2] == ConstantField.IP_TYPE) {
            dealIpVideo(kafkaDataStruct);
        }  else if(bytes[2] == ConstantField.USER_TYPE) {
            dealUserVideo(kafkaDataStruct);
        }
    }

    private void dealIpVideo(KafkaDataStruct kafkaDataStruct) {
        udpInterface.send(ip, port ,kafkaDataStruct.getData());
    }

    private void dealUserVideo(KafkaDataStruct kafkaDataStruct) {
        udpInterface.send(ip, port ,kafkaDataStruct.getData());
    }
}
