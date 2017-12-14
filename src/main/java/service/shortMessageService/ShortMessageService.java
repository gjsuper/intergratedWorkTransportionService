package service.shortMessageService;

import DataBuilder.DataBuilder;
import domin.ConstantField;
import domin.KafkaDataStruct;
import domin.ShortMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import service.transportationService.UdpInterface;
import sqlMapper.ShortMessageMapper;

import javax.annotation.Resource;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

@Service
public class ShortMessageService {

    @Value("#{properties['serverIp']}")
    private String ip;

    @Value("#{properties['serverPort']}")
    private int port;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    private ShortMessageMapper shortMessageMapper;

    @Resource
    private UdpInterface udpInterface;

    public void dealShortMessage(KafkaDataStruct kafkaDataStruct) {
        byte[] bytes = kafkaDataStruct.getData();

        if(bytes[2] == ConstantField.IP_TYPE) {
            dealIpSM(kafkaDataStruct);
        }  else if(bytes[2] == ConstantField.USER_TYPE) {
            dealUserSM(kafkaDataStruct);
        } else if(bytes[2] == ConstantField.SM_QUERY_USER || bytes[2] == ConstantField.SM_QUERY_IP) {
            queryShortMessage(kafkaDataStruct);
        }
    }

    private void dealIpSM(KafkaDataStruct kafkaDataStruct) {
        int index = 3;

        byte[] data = kafkaDataStruct.getData();

        //dstip
        String dstIp = Arrays.toString(Arrays.copyOfRange(data, index, index + 4));

        index += 4;
        //dstport
        short dstPort = 0;
        dstPort = (short) (data[index ++] & 0xFF);
        dstPort += (short) (data[index ++] & 0xFF);

        //srcip
        String srcIp = Arrays.toString(Arrays.copyOfRange(data, index, index + 4));

        index += 4;

        //srcport
        short srcPort = 0;
        srcPort = (short) (data[index ++] & 0xFF);
        srcPort += (short) (data[index ++] & 0xFF);

        //数据长度
        int len = ByteBuffer.wrap(data, index, 4).getInt();

        index += 4;

        //数据内容
        String message = Arrays.toString(Arrays.copyOfRange(data, index, index + len));

        index += len;
        //其他信息

        String extend = Arrays.toString(Arrays.copyOfRange(data, index, kafkaDataStruct.getLen()));

        ShortMessage shortMessage = new ShortMessage();
        shortMessage.setReceiver(dstIp);
        shortMessage.setSender(srcIp);
        shortMessage.setMessage(message);
        shortMessage.setStatus(ConstantField.UNREADED);
        shortMessage.setTransType(ConstantField.IP_TYPE);
        shortMessage.setExtendInfo(extend);
        shortMessage.setDstPort(dstPort);
        shortMessage.setSrcPort(srcPort);

        shortMessageMapper.addShortMessage(shortMessage);

        udpInterface.send(ip, port ,kafkaDataStruct.getData());
    }

    private void dealUserSM(KafkaDataStruct kafkaDataStruct) {
        int index = 3;

        byte[] data = kafkaDataStruct.getData();

        //目的用户名长度
        int dstUsernameLen = data[index ++] & 0xFF;

        String dstUser = Arrays.toString(Arrays.copyOfRange(data, index, index + dstUsernameLen));

        index += dstUsernameLen;

        //srcip
        int srcUsernameLen = data[index ++] & 0xFF;

        String srcUser = Arrays.toString(Arrays.copyOfRange(data, index, index + srcUsernameLen));

        index += srcUsernameLen;


        //数据长度
        int len = ByteBuffer.wrap(data, index, 4).getInt();

        index += 4;

        //数据内容
        String message = Arrays.toString(Arrays.copyOfRange(data, index, index + len));

        index += len;
        //其他信息

        String extend = Arrays.toString(Arrays.copyOfRange(data, index, kafkaDataStruct.getLen()));

        ShortMessage shortMessage = new ShortMessage();
        shortMessage.setReceiver(dstUser);
        shortMessage.setSender(srcUser);
        shortMessage.setMessage(message);
        shortMessage.setStatus(ConstantField.UNREADED);
        shortMessage.setTransType(ConstantField.IP_TYPE);
        shortMessage.setExtendInfo(extend);

        shortMessageMapper.addShortMessage(shortMessage);

        udpInterface.send(ip, port ,kafkaDataStruct.getData());
    }

    private void queryShortMessage(KafkaDataStruct kafkaDataStruct) {
        int index = 3;

        byte[] data = kafkaDataStruct.getData();

        byte status = data[index ++];
        String dst = null;
        String src = null;

        if(data[2] == ConstantField.SM_QUERY_IP) {
            dst = Arrays.toString(Arrays.copyOfRange(data, index, index + 4));
            index += 4;
            src = Arrays.toString(Arrays.copyOfRange(data, index, index + 4));
        } else if(data[2] == ConstantField.SM_QUERY_USER) {
            int dstUsernameLen = data[index ++] & 0xFF;

            //dst user
            dst = Arrays.toString(Arrays.copyOfRange(data, index, index + dstUsernameLen));

            index += dstUsernameLen;

            //src user

            int srcUsernameLen = data[index ++] & 0xFF;

            src = Arrays.toString(Arrays.copyOfRange(data, index, index + srcUsernameLen));
        }

        List<String> sms = null;

        if(status == ConstantField.UNREADED || status == ConstantField.READED) {
            sms = shortMessageMapper.getMessgesByStatus(src, dst, status);
        } else {
            sms = shortMessageMapper.getMessges(src, dst);
        }

        byte[] sendBuf = DataBuilder.buildQuerySMResponse(sms);

        udpInterface.send(kafkaDataStruct.getIp(), kafkaDataStruct.getPort(), sendBuf);
    }
}
