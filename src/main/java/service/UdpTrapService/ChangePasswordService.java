package service.UdpTrapService;

import ConstField.JsonString;
import DataStruct.User;
import org.springframework.stereotype.Service;


import net.sf.json.JSONObject;
import service.transportationService.UdpInterface;
import sqlMapper.UserMapper;

import javax.annotation.Resource;

@Service
public class ChangePasswordService {

	@Resource
	private UdpInterface udpInterface;

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Resource
	private UserMapper userMapper;

	public void process(JSONObject jsonObject, String ip, int port) {
		String id = jsonObject.getString(JsonString.USR_ID);
		String ori_password = jsonObject.getString(JsonString.ORIGINAL_PASSWORD);
		String new_password = jsonObject.getString(JsonString.NEW_PASSWORD);

//		List<User> usr = mySQLService.queryUsr("select * from usr where id = ? and password = ?", id, ori_password);
		User user = userMapper.getUserByIdAndPassword(id, ori_password);
		String result = JsonString.SUCCESS;

		if(user == null) {
			result = JsonString.ORIGINAL_PASSWORD_WRONG;
		} else {
//			if(mySQLService.executeUpdate("update usr setObject password = ? where id = ?", new_password, id) < 1)
//				System.err.println("update usr status error!");
			userMapper.setPassword(new_password, id);
		}

		JSONObject jObject = new JSONObject();
		jObject.put(JsonString.PACKET_TYPE, JsonString.CHANGE_PASSWORD_RESPONSE);
		jObject.put(JsonString.RESULT, result);

		udpInterface.sendData(jObject.toString().getBytes(), ip, port);

	}
}
