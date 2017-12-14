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

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    private UserMapper userMapper;

    //先从缓存去数据，如果没取到再从数据库取数据，然后在把数据放到缓存
    public User getUser(String username) {
        byte[] value = redisClusterUtils.get(username.getBytes());
        User user;

        //如果从redis取到数据了
        if (value != null) {
            Object o = SerializeUtil.deserialize(value);

            if (o instanceof User) {
                return (User) o;
            }
        } else {//如果没有取到，则从数据库取数据
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

//        user.setUsername("jie3");
//        user.setPassword("123456");
//        user.setExtendInfo("extend info");
////h
////        redisClusterUtils.set("jie", user);
//
//        userMapper.addUser(user);
        System.out.println(user.getId());
        System.out.println(getUser("jie3"));

//        redisClusterUtils.set("kk");
    }
}
