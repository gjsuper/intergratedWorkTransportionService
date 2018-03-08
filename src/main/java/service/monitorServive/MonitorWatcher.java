package service.monitorServive;

import ConstField.JsonString;
import DataStruct.User;
import net.sf.json.JSONObject;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import service.transportationService.UdpInterface;
import sqlMapper.UserMapper;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeSet;

@Service
public class MonitorWatcher implements Watcher, InitializingBean {

    @Value("#{properties['zookeeper.servers']}")
    private String connectString;

    @Value("#{properties['sessionTimeout']}")
    private int sessionTimeout;

    @Value("#{properties['connectionTimeOut']}")
    private int connectionTimeout;

    @Value("#{properties['curator.timeout']}")
    private int curatorTimeout;

    @Value("#{properties['curator.retry']}")
    private int curatorRetry;

    @Value("#{properties['server.ips']}")
    private String ips;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    private UserMapper userMapper;

    @Resource
    private UdpInterface udpInterface;

    private CuratorFramework client;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private TreeSet<String> childrenSet;

    private TreeSet<String> newChildrenSet;

    @Override
    public void process(WatchedEvent watchedEvent) {

        try {
            System.out.println("窝被调用了");
            List<String> newChildrenList = client.getChildren().usingWatcher(this).forPath("/monitor");

//            boolean miss = false;
//
//            for (String ip : newChildrenList) {
//                if (!childrenSet.contains(ip)) {
//                    tempSet.remove(ip);
//                    miss = true;
//                }
//            }

            String ip = getIp();

            newChildrenSet.clear();
            newChildrenSet.addAll(newChildrenList);

            if (changed()) {

                if (ip.equals(newChildrenSet.first())) {//让ip最小的来通知客户端
                    List<User> users = userMapper.getOnlineUsers();

                    JSONObject json = new JSONObject();
                    json.put(JsonString.ONLINE_SERVER, ip);

                    for (User user : users) {
                        udpInterface.sendData(json.toString().getBytes(), user.getIp(), user.getPort());

                        Thread.sleep(100);
                    }
                }
            }

            childrenSet.clear();
            childrenSet.addAll(newChildrenList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean changed() {

        if (newChildrenSet.size() < childrenSet.size()) {
            return true;
        } else if (newChildrenSet.size() == childrenSet.size()) {

            for (String child : childrenSet) {
                if (!newChildrenSet.contains(child)) {
                    return true;
                }
            }

        }

        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(curatorTimeout, curatorRetry);

        //创建一个zk连接
        client = CuratorFrameworkFactory.newClient(connectString, sessionTimeout
                , connectionTimeout, retryPolicy);
        client.start();

        childrenSet = new TreeSet<>(String::compareTo);
        newChildrenSet = new TreeSet<>(String::compareTo);

        String[] ip = ips.split(",");

        childrenSet.addAll(Arrays.asList(ip));

        List<String> newChildrenList = client.getChildren().usingWatcher(this).forPath("/monitor");
//
//        for(String s : newChildrenList) {
//            System.out.println(s);
//        }
    }

    protected String getIp() {
        String ip = "";
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            try {
                String hostName = InetAddress.getLocalHost().getHostName();

                if (hostName.length() > 0) {
                    InetAddress[] addrs = InetAddress.getAllByName(hostName);

                    if (addrs.length > 0) {

                        for (InetAddress addr : addrs) {
                            if (addr.getHostAddress().contains("162."))
                                ip = addr.getHostAddress();
                        }
                    }
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        } else {
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                    NetworkInterface intf = en.nextElement();
                    String name = intf.getName();
                    if (!name.contains("docker") && !name.contains("lo")) {
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress()) {
                                String ipaddress = inetAddress.getHostAddress();
                                if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
                                    ip = ipaddress;
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (SocketException ex) {
                ip = "127.0.0.1";
                ex.printStackTrace();
            }
        }

        return ip;
    }
}
