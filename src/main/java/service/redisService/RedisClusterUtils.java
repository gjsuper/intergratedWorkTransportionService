package service.redisService;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;
import service.serializeService.SerializeUtil;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RedisClusterUtils implements InitializingBean {

    @Resource
    private JedisCluster jedisCluster;

    /**
     * 得到指定key值的value
     * @param key
     */
    public Object getBytes(String key){
        return jedisCluster.get(key);
    }

    public int getInt(String key) {
        String o = jedisCluster.get(key);

        if(o == null) {
            return -1;
        }

        return Integer.parseInt(o);
    }

    /**
     * 保存指定key值的value
     * @param key
     * @param value
     */
    public void setObject(String key, String value){
        jedisCluster.set(key, value);
    }

    /**
     * 保存指定key值的value
     * @param key
     * @param list
     */
    public void setObject(String key, List<String> list){
        jedisCluster.rpush(key, (String[]) list.toArray());
    }


    /**
     * 将序列化的对象存在redis
     * @param key
     * @param object
     */
    public void setObject(String key, Object object) {
        setObject(key, SerializeUtil.serialize(object));
    }

    public void setInt(String key, int value) {
        jedisCluster.set(key, "" + value);
    }

    public void setObject(String key, byte[] bytes){
        jedisCluster.set(key.getBytes(), bytes);
    }

    /**
     * 将序列化的对象取出来
     *
     * @param bytes
     * @return byte[]
     */
    public byte[] getBytes(byte[] bytes) {
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
        System.out.println("redis getBytes:" + jedisCluster.get("f") + "," + jedisCluster.getClusterNodes());
    }
}
