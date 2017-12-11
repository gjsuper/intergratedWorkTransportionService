package service.dataFetchService;

import domin.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import service.redisService.RedisClusterUtils;
import service.serializeService.SerializeUtil;
import sqlMapper.UserMapper;

import javax.annotation.Resource;

@Service
public class CacheService implements InitializingBean {

    @Resource
    private RedisClusterUtils redisClusterUtils;

    @Resource
    private UserMapper userMapper;

    public User getUser(String username) {
//        //先从缓存去数据，如果没取到再从数据库取数据，然后在把数据放到缓存
        byte[] value = redisClusterUtils.get(username.getBytes());
        User user;

        if (value != null) {
            Object o = SerializeUtil.deserialize(value);

            if (o instanceof User) {
                return (User) o;
            }
        } else {
            user = userMapper.getUserByUsername(username);
            if (user != null) {
                redisClusterUtils.set(username, user);
                return user;
            }
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        User user = new User();

        user.setUsername("jie3");
        user.setPassword("123456");
        user.setExtendInfo("extend info");
//
//        redisClusterUtils.set("jie", user);

        userMapper.addUser(user);
        System.out.println(user.getId());
        System.out.println(getUser("jie3"));

//        redisClusterUtils.set("kk");
    }
}
