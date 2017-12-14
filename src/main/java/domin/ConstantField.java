package domin;

public class ConstantField {

    //信令类型
    public static byte USER_SIGNAL = 0x00;
    public static byte DATA_SIGNAL = 0x01;
    public static byte STORE_SIGNAL = 0x02;
    public static byte QUERY_SIGNAL = 0x03;

    //用户信令
    public static byte USER_REGISTER_REQUEST = 0x00;
    public static byte USER_REGISTER_RESPONSE = 0x01;
    public static byte USER_LOGIN_REQUEST = 0x02;
    public static byte USER_LOGIN_RESPONSE = 0x03;
    public static byte USER_LOGOUT_REQUEST = 0x04;
    public static byte USER_LOGOUT_RESPONSE = 0x05;

    public static byte USER_REGISTER_SUCCESS = 0x00;
    public static byte USER_ALREADY_EXISTS = 0x01;
    public static byte USER_LOGIN_SUCCESS = 0x00;
    public static byte USER_LOGOUT_SUCCESS = 0x00;
    public static byte USERNAME_OR_PASSWORD_WRONG = 0x01;

    //用户离线、在线
    public static int OFFLINE = 0;
    public static int ONLINE = 1;

    //数据类型
    public static byte FILE = 0x00;
    public static byte VIDEO = 0x01;
    public static byte SHORT_MESSAGE = 0x02;
    public static byte REAL_TIME_DATA = 0x03;

    //传输类型:采用IP.port传输/采用用户名传输
    public static byte IP_TYPE = 0x00;
    public static byte USER_TYPE = 0x01;

    //短消息
    public static byte UNREADED = 0x00;
    public static byte READED = 0x01;
    public static byte ALL = 0x02;

    //短消息查询
    public static byte SM_QUERY_IP = 0x02;
    public static byte SM_QUERY_USER = 0x03;
    public static byte SM_QUERY_RESPONSE = 0x04;


}
