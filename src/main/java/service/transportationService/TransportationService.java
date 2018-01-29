package service.transportationService;

import domin.KafkaDataStruct;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import service.kafkaService.MyKafkaProducer;

import javax.annotation.Resource;
import java.net.DatagramPacket;

@Service
public class TransportationService implements InitializingBean {

    @Value("#{properties['ip']}")
    private String ip;

    @Value("#{properties['port']}")
    private int port;

    @Resource
    private UdpInterface udpInterface;

    @Resource
    private MyKafkaProducer myKafkaProducer;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        String str = "hello111";
//        System.out.println("sendData:" + str + ", ip:" + ip + ", port:" + port);
//        udpInterface.sendData(str.getBytes(), ip, port);

        new Thread(() -> {
            while (true) {

                DatagramPacket dp = udpInterface.recv();

                if(dp == null) {
                    System.err.println("received datagram Packet == null");
                    continue;
                }

                myKafkaProducer.sendMessage(new KafkaDataStruct(dp.getData(), dp.getLength(), dp.getAddress().getHostAddress(), dp.getPort()));

//            byte[] recvBuf = dp.getData();
//
//            for (int i = 0; i < dp.getLength(); ++i) {
//                System.out.print(Integer.toHexString(recvBuf[i] & 0xFF) + " ");
//            }
            }
        });

    }
}
