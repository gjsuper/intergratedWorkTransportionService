package service.monitorServive;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MonitorRegister implements InitializingBean {

    @Value("#{properties['curator.timeout']}")
    private int timeout;

    @Value("#{properties['curator.retry']}")
    private int retry;

    @Value("#{properties['zookeeper.servers']}")
    private String connectStrings;

    @Value("#{properties['sessionTimeout']}")
    private int sessionTimeout;

    @Value("#{properties['connectionTimeOut']}")
    private int connectionTimeout;

    @Resource
    private MonitorWatcher monitorWatcher;

    @Override
    public void afterPropertiesSet() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(timeout, retry);

        CuratorFramework client = CuratorFrameworkFactory.newClient(connectStrings, sessionTimeout, connectionTimeout, retryPolicy);

        client.start();

        String ip = monitorWatcher.getIp();
        System.out.println("my ip:" + ip);

        client.create().creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)//指定节点类型
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)//指定设置节点权限信息
                .forPath("/monitor/" + ip);//指定节点名称


        System.out.println("register");
    }
}
