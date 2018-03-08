package service.UdpTrapService;


import ConstField.JsonString;
import DataStruct.User;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import service.dataFetchService.CacheService;
import service.transportationService.UdpInterface;
import sqlMapper.UserMapper;

import javax.annotation.Resource;

@Service
public class LogoutService {

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Resource
	private UserMapper userMapper;

	@Resource
	UdpInterface udpInterface;

	@Resource
	private CacheService cacheService;

	public void process(JSONObject jsonObject, String ip, int port) {
		String id = jsonObject.getString(JsonString.USR_ID);

//		if(mySQLService.executeUpdate("update usr setObject status = 0 where id = ?", id) < 1)
//			System.err.println("update usr status error!");

		if(userMapper.setLogoutInfo(id) < 1) {
			System.err.println("update usr status error!");
		}
		
		JSONObject response = new JSONObject();
		response.put(JsonString.PACKET_TYPE, JsonString.LOGOUT_REPONSE);
		response.put(JsonString.RESULT, JsonString.SUCCESS);

		udpInterface.sendData(response.toString().getBytes(), ip, port);
		
//		SharedInfo.map.get(id).setStatus(0);

		User user = cacheService.getUserById(Integer.valueOf(id));

		cacheService.setUserById(Integer.valueOf(id), user);
	}
}
