package service.dataFetchService;

import DataStruct.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import service.redisService.RedisClusterUtils;
import service.serializeService.SerializeUtil;
import sqlMapper.DepartmentMapper;
import sqlMapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CacheService implements InitializingBean {

    @Resource
    private RedisClusterUtils redisClusterUtils;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    private UserMapper userMapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    private DepartmentMapper departmentMapper;

    //先从缓存去数据，如果没取到再从数据库取数据，然后在把数据放到缓存
    public User getUserByName(String username) {
        byte[] value = redisClusterUtils.getBytes(username.getBytes());
        User user;

        //如果从redis取到数据了
        if (value != null) {

            Object o = SerializeUtil.deserialize(value);

            if (o instanceof User) {
                System.out.println("get user by name from redis");
                return (User) o;
            }
        } else {//如果没有取到，则从数据库取数据
            user = userMapper.getUserByUsername(username);

            System.out.println("get user by name from db");

            if (user != null) {
               setUserByName(username, user);
                return user;
            }
        }
        return null;
    }

    public User getUserById(int id) {
        byte[] value = redisClusterUtils.getBytes(("" + id).getBytes());
        User user;

        //如果从redis取到数据了
        if (value != null) {

            Object o = SerializeUtil.deserialize(value);

            if (o instanceof User) {
                System.out.println("get user by id from redis");
                return (User) o;
            }
        } else {//如果没有取到，则从数据库取数据
            user = userMapper.getUserById(id);

            System.out.println("get user by id from db");

            if (user != null) {
                setUserById(id, user);
                return user;
            }
        }
        return null;
    }

    public int getArmyId(String armyName) {
        int id = redisClusterUtils.getInt(armyName);

        if(id == -1) {
            id = departmentMapper.getIdByName(armyName);
            redisClusterUtils.setInt(armyName, id);
            System.out.println("getBytes army id by name from db");
        } else {
            System.out.println("get armyId by armyName from redis");
        }
        return id;
    }

    public void setUserById(int id, User user) {
        redisClusterUtils.setObject("" + id, user);
    }

    public void setUserByName(String name, User user) {
        redisClusterUtils.setObject(name, user);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        User user = new User();
//
////        user.setUsername("jie3");
////        user.setPassword("123456");
////        user.setExtendInfo("extend info");
//////h
//////      redisClusterUtils.setObject("jie", user);
////
////        userMapper.addUser(user);
////        System.out.println(user.getId());

//        System.out.println("get user by name:" + getUserByName("jie"));
//        System.out.println("get user by id:" + getUserById(1));

//        redisClusterUtils.setObject("kk");


        System.out.println("start init redis************");
        List<User> users = userMapper.getAllUsers();

        for(User user : users) {
//			SharedInfo.map.put(user.getId(), user);
            redisClusterUtils.setObject("" + user.getId(), user);
            redisClusterUtils.setObject(user.getName(), user);
        }
    }
}
