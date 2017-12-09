package service.redisMysqlService;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import service.redisService.RedisService;
import service.MysqlService.MysqlService;

import javax.annotation.Resource;

@Service
public class CacheService {

    @Resource
    private RedisService redisService;

    @Resource
    private MysqlService sqlService;

    public String getData(String userName) {
        //先从缓存去数据，如果没取到再从数据库取数据，然后在把数据放到缓存
        String value = redisService.get(userName);

        if(!StringUtils.isEmpty(value)) {
            return value;
        }


        return null;

    }

}
