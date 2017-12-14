package DataBuilder;

import domin.ConstantField;
import java.util.List;

import java.nio.ByteBuffer;
import java.util.stream.Stream;

public class DataBuilder {

    public static byte[] buildRegisterResponse(String username, byte registerResult, int userId) {

        byte[] b_username = username.getBytes();
        byte usernameLen = (byte) b_username.length;

        int len = 1 + 1 + 1 + 1 + usernameLen + 4;//信令类型(1) + 包类型(1) + 注册结果(1) + 用户名长度(1) + 用户名 + 用户ID(4)
        ByteBuffer byteBuffer = ByteBuffer.allocate(len);

        //信令类型
        byteBuffer.put(ConstantField.USER_SIGNAL);

        //包类型
        byteBuffer.put(ConstantField.USER_REGISTER_RESPONSE);

        //注册结果
        byteBuffer.put(registerResult);

        //用户名长度
        byteBuffer.put(usernameLen);

        //用户名
        byteBuffer.put(b_username);

        //用户ID
        byteBuffer.putInt(userId);

        return byteBuffer.array();
    }

    public static byte[] buildLoginResponse(String username, byte result, int id) {
        byte[] b_username = username.getBytes();

        byte usernameLen = (byte) b_username.length;

        int len = 1 + 1 + 1 + 1 + usernameLen + 4; //信令类型(1) + 包类型(1) + 登陆结果(1) + 用户名长度(1) + 用户名 + 用户ID(4)

        ByteBuffer byteBuffer = ByteBuffer.allocate(len);

        //信令类型
        byteBuffer.put(ConstantField.USER_SIGNAL);

        //包类型
        byteBuffer.put(ConstantField.USER_LOGIN_RESPONSE);

        //登陆结果
        byteBuffer.put(result);

        //用户名长度
        byteBuffer.put(usernameLen);

        //用户名
        byteBuffer.put(b_username);

        //用户ID
        byteBuffer.putInt(id);

        return byteBuffer.array();
    }

    public static byte[] buildLogoutResponse(String username, byte result) {
        byte[] b_username = username.getBytes();

        byte usernameLen = (byte) b_username.length;

        int len = 1 + 1 + 1 + 1 + usernameLen; //信令类型(1) + 包类型(1) + 登陆结果(1) + 用户名长度(1) + 用户名

        ByteBuffer byteBuffer = ByteBuffer.allocate(len);

        //信令类型
        byteBuffer.put(ConstantField.USER_SIGNAL);

        //包类型
        byteBuffer.put(ConstantField.USER_LOGOUT_RESPONSE);

        //退出结果
        byteBuffer.put(result);

        //用户名长度
        byteBuffer.put(usernameLen);

        //用户名
        byteBuffer.put(b_username);

        return byteBuffer.array();
    }

    public static byte[] buildQuerySMResponse(List<String> messages) {

        int len = messages.stream().map(String::getBytes).map(x -> x.length).reduce(0, Integer::sum);

        int size = messages.size();

        int totalLen = 1 + 1 + 1 + 4 + size + len;//信令类型 + 包类型 + 查询返回 + 短消息数量 + ...

        ByteBuffer byteBuffer = ByteBuffer.allocate(totalLen);

        byteBuffer.put(ConstantField.DATA_SIGNAL);

        byteBuffer.put(ConstantField.SHORT_MESSAGE);

        byteBuffer.put(ConstantField.SM_QUERY_RESPONSE);

        byteBuffer.putInt(size);

        for(String message : messages) {
            byteBuffer.putInt(message.getBytes().length);
            byteBuffer.put(message.getBytes());
        }

        return byteBuffer.array();
    }
}
