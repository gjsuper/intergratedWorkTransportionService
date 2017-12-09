package service.redisService;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class RedisService implements InitializingBean {

    @Value("#{properties['redis.server']}")
    private String redisServer;

    private Jedis jedis;

    @Override
    public void afterPropertiesSet() throws Exception {
        jedis = new Jedis(redisServer);
    }

    public String get(String key) {
        return jedis.get(key);
    }

    public void set(String key, String value) {
        jedis.set(key, value);
    }
}
