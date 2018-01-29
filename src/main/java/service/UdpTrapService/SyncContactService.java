package service.UdpTrapService;

import ConstField.JsonString;
import DataStruct.User;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import service.transportationService.UdpInterface;
import sqlMapper.UserMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SyncContactService {

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Resource
	private UserMapper userMapper;

	@Resource
	private UdpInterface udpInterface;

	public void process(JSONObject jsonObject, String ip, int port) {
		int id = jsonObject.getInt(JsonString.USR_ID);
//		List<User> usrs = mySQLService.queryUsr("select * from usr where army in (select army from usr where id = ?)", id);
		List<User> users = userMapper.getUsersByArmy(id);

		List<JSONObject> list = new ArrayList<>();

		for(User usr : users) {
			JSONObject contact = new JSONObject();
			contact.put(JsonString.USR_NAME, usr.getName());
			contact.put(JsonString.USR_ID, usr.getId());
			list.add(contact);
		}

		JSONObject result = new JSONObject();
		result.put(JsonString.PACKET_TYPE, JsonString.SYNC_CONTACT_RESPONSE);
		result.put("result", list);

		udpInterface.sendData(result.toString().getBytes(), ip, port);
	}
}
