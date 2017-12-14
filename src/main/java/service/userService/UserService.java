package service.userService;

import DataBuilder.DataBuilder;
import domin.ConstantField;
import domin.KafkaDataStruct;
import domin.User;
import org.springframework.stereotype.Service;
import service.transportationService.UdpInterface;
import sqlMapper.UserMapper;

import javax.annotation.Resource;
import java.util.Arrays;

@Service
public class UserService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    private UserMapper userMapper;

    @Resource
    private UdpInterface udpInterface;

    public void dealUserData(KafkaDataStruct kafkaDataStruct) {
        byte[] data = kafkaDataStruct.getData();

        if(data[1] == ConstantField.USER_REGISTER_REQUEST) {//注册
            dealUserRegisterRequest(kafkaDataStruct);
        } else if(data[1] == ConstantField.USER_LOGIN_REQUEST) {//登陆
            dealLoginRequest(kafkaDataStruct);
        } else if(data[1] == ConstantField.USER_LOGOUT_REQUEST) {//退出
            dealLogoutResponse(kafkaDataStruct);
        }
    }

    private void dealUserRegisterRequest(KafkaDataStruct kafkaDataStruct) {
        int index = 2;
        byte[] data = kafkaDataStruct.getData();

        //用户名
        int usernameLen = data[index ++] & 0xFF;
        String username = Arrays.toString(Arrays.copyOfRange(data, index, index + usernameLen));

        index += usernameLen;

        //密码
        int passwordLen = data[index ++] & 0xFF;
        String password = Arrays.toString(Arrays.copyOfRange(data, index, index + passwordLen));

        index += passwordLen;

        //扩展信息
        int totalLen = kafkaDataStruct.getLen();
        String extendInfo = Arrays.toString(Arrays.copyOfRange(data, index, totalLen));

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setExtendInfo(extendInfo);
        user.setIp(kafkaDataStruct.getIp());
        user.setPort(kafkaDataStruct.getPort());


        byte[] sendBuf;
        try {
            userMapper.addUser(user);
        } catch (Exception e) {
            //用户名重复
            System.err.println("用户名重复!");
            sendBuf = DataBuilder.buildRegisterResponse(username, ConstantField.USER_ALREADY_EXISTS, 0);
            udpInterface.send(kafkaDataStruct.getIp(), kafkaDataStruct.getPort(), sendBuf);
        }

        sendBuf = DataBuilder.buildRegisterResponse(username, ConstantField.USER_REGISTER_SUCCESS, user.getId());

        udpInterface.send(kafkaDataStruct.getIp(), kafkaDataStruct.getPort(), sendBuf);
    }

    private void dealLoginRequest(KafkaDataStruct kafkaDataStruct) {
        int index = 2;
        byte[] data = kafkaDataStruct.getData();

        //用户名
        int usernameLen = data[index ++] & 0xFF;
        String username = Arrays.toString(Arrays.copyOfRange(data, index, index + usernameLen));

        index += usernameLen;

        //密码
        int passwordLen = data[index ++] & 0xFF;
        String password = Arrays.toString(Arrays.copyOfRange(data, index, index + passwordLen));

        User user = userMapper.getUserByUsernameAndPassword(username, password);

        byte[] sendBuf;

        //如果用户名或密码错误
        if(user == null) {
            sendBuf = DataBuilder.buildLoginResponse(username, ConstantField.USERNAME_OR_PASSWORD_WRONG, 0);
        } else {
            sendBuf = DataBuilder.buildLoginResponse(username, ConstantField.USER_LOGIN_SUCCESS, user.getId());
            userMapper.setUserOnline(username, ConstantField.ONLINE, kafkaDataStruct.getIp(), kafkaDataStruct.getPort());
        }

        udpInterface.send(kafkaDataStruct.getIp(), kafkaDataStruct.getPort(), sendBuf);
    }

    private void dealLogoutResponse(KafkaDataStruct kafkaDataStruct) {
        int index = 2;
        byte[] data = kafkaDataStruct.getData();

        //用户名
        int usernameLen = data[index ++] & 0xFF;
        String username = Arrays.toString(Arrays.copyOfRange(data, index, index + usernameLen));


        byte[] sendBuf = DataBuilder.buildLogoutResponse(username, ConstantField.USER_LOGOUT_SUCCESS);
        udpInterface.send(kafkaDataStruct.getIp(), kafkaDataStruct.getPort(), sendBuf);

        userMapper.setUserStatus(username, ConstantField.OFFLINE);
    }
}
