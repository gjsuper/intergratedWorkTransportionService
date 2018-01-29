package service.UdpTrapService;

import ConstField.JsonString;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;
import service.transportationService.UdpInterface;
import sqlMapper.UserMapper;

import javax.annotation.Resource;

@Service
public class CloseAccountService {

	@Resource
	private UdpInterface udpInterface;

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Resource
	private UserMapper userMapper;

	public void process(JSONObject jsonObject, String ip, int port) {
		String result = JsonString.SUCCESS;
//		if(mySQLService.executeUpdate("delete from usr where id = ?", jsonObject.getInt(JsonString.USR_ID)) < 1) {
		if(userMapper.deleteUserById(jsonObject.getInt(JsonString.USR_ID)) < 1) {
			System.err.println("delete usr error!");
			result = JsonString.FAIL;
		}
			
		JSONObject jObject = new JSONObject();
		jObject.put(JsonString.PACKET_TYPE, JsonString.CLOSE_ACCOUNT_RESPONSE);
		jObject.put(JsonString.RESULT, result);
		
		udpInterface.sendData(jObject.toString().getBytes(), ip, port);
	}

}
