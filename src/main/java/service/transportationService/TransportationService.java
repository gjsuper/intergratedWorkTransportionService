package service.transportationService;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.DatagramPacket;

@Service
public class TransportationService implements InitializingBean {

    @Value("#{netProp['ip']}")
    private String ip;

    @Value("#{netProp['port']}")
    private int port;

    @Resource
    private UdpInterface udpInterface;

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
        String str = "hello";
        System.out.println("send:" + str + ", ip:" + ip + ", port:" + port);
        udpInterface.send(ip, port, str.getBytes());

        while (true) {

            DatagramPacket dp = udpInterface.recv();

            assert dp != null;
            byte[] recvBuf = dp.getData();

            for (int i = 0; i < dp.getLength(); ++i) {
                System.out.print(Integer.toHexString(recvBuf[i] & 0xFF) + " ");
            }

            Thread.sleep(10);
        }
    }
}
