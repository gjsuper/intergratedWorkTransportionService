package main;

import domin.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.dataFetchService.CacheService;
import service.redisService.RedisClusterUtils;

public class Main {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("applicationContext.xml");


//        CacheService cacheService = (CacheService) ac.getBean("cacheService");
//
//        RedisClusterUtils redisClusterUtils = (RedisClusterUtils) ac.getBean("redisClusterUtils");
//
//        User user = new User();
//
//        user.setId(10);
//        user.setUserName("jie");
//        user.setPassword("123456");
//        user.setExtendInfo("extend info");

//        redisClusterUtils.set("jie", user);


    }
}
