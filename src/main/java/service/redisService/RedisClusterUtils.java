package service.redisService;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;
import service.serializeService.SerializeUtil;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component
public class RedisClusterUtils implements InitializingBean {

    @Resource
    private JedisCluster jedisCluster;

    /**
     * 得到指定key值的value
     * @param key
     */
    public Object get(String key){
        return jedisCluster.get(key);
    }

    /**
     * 保存指定key值的value
     * @param key
     * @param value
     */
    public void set(String key, String value){
        jedisCluster.set(key, value);
    }

    /**
     * 保存指定key值的value
     * @param key
     * @param list
     */
    public void set(String key, List<String> list){
        jedisCluster.rpush(key, (String[]) list.toArray());
    }


    /**
     * 将序列化的对象存在redis
     * @param key
     * @param object
     */
    public void set(String key, Object object) {
        set(key, SerializeUtil.serialize(object));
    }

    public void set(String key, byte[] bytes){
        jedisCluster.set(key.getBytes(), bytes);
    }

    /**
     * 将序列化的对象取出来
     * @param bytes
     * @return byte[]
     */
    public byte[] get(byte[] bytes) {
        return jedisCluster.get(bytes);
    }

    /**
     * 删除指定key的value
     * @param key
     */
    public void del(String key){
        jedisCluster.del(key);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("redis get:" + jedisCluster.get("f") + "," + jedisCluster.getClusterNodes());
    }
}
