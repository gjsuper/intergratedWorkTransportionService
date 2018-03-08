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
public class RegisterService {

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Resource
	private UserMapper userMapper;

	@Resource
	private UdpInterface udpInterface;

	@Resource
	private CacheService cacheService;

	public void process(JSONObject jsonObj, String dst_ip, int port) {
		String name = jsonObj.getString(JsonString.USR_NAME);
		String password = jsonObj.getString(JsonString.PASSWORD);
		int army = jsonObj.getInt(JsonString.ARMY);
		int department = jsonObj.getInt(JsonString.DEPARTMENT);
		String ip = jsonObj.getString(JsonString.CLIENT_IP);

//		List<User> usrs = mySQLService.queryUsr("select * from usr where name = ?", name);

		User user = userMapper.getUserByUsername(name);

		String result = JsonString.USR_EXIST;
		int id = -1;
		if(user != null) {
			result = JsonString.SUCCESS;
//			if( mySQLService.executeUpdate("insert into usr(name, password, army, department, ip) values(?, ?, ?, ?, ?)",
//					name, password, army, department, ip) < 1)
//				System.err.println("insert usr to mysql error!");

			User u = new User();
			u.setArmy(army);
			u.setName(name);
			u.setPassword(password);
			u.setDepartment(department);
			u.setIp(ip);
			u.setPort(port);
			u.setStatus(0);

			userMapper.addUser(u);

			id = u.getId();
			cacheService.setUserById(id, u);
			cacheService.setUserByName(name, u);

		} 

		JSONObject json = new JSONObject();
		json.put(JsonString.PACKET_TYPE, JsonString.REGISTER_REPONSE);
		json.put(JsonString.USR_ID, id);
		json.put(JsonString.RESULT, result);

		udpInterface.sendData(json.toString().getBytes(), dst_ip, port);
	}

}
