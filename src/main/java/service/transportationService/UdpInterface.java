package service.transportationService;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.*;

@Service
public class UdpInterface implements InitializingBean {

    @Value("#{properties['port']}")
    private int port;

    private DatagramSocket sock;

    private byte[] recvBuf = new byte[1024 * 10];
    private DatagramPacket dp = new DatagramPacket(recvBuf, recvBuf.length);

    public void send(String ip, int port, byte[] data) {
        try {
            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(ip), port);
            sock.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    DatagramPacket recv() {
        try {
            sock.receive(dp);
            return dp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        sock = new DatagramSocket(port);
    }
}
