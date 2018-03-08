package service.UdpTrapService;

import ConstField.JsonString;
import DataStruct.Department;
import DataStruct.User;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import service.redisService.RedisClusterUtils;
import service.transportationService.UdpInterface;
import sqlMapper.DepartmentMapper;
import sqlMapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LoginService {

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Resource
	private UserMapper userMapper;

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Resource
	private DepartmentMapper departmentMapper;

	@Resource
	private UdpInterface udpInterface;

	@Resource
	private RedisClusterUtils redisClusterUtils;

	public void process(JSONObject jsonObj, String dst_ip, int port) {
		String name = jsonObj.getString(JsonString.USR_NAME);
		String password = jsonObj.getString(JsonString.PASSWORD);

//		List<User> usrs = mySQLService.queryUsr("select * from usr where name = ?", name);

		User user = userMapper.getUserByUsername(name);

		String result = JsonString.USR_NOT_EXIST;
		int id = 0;
		String army_name = "";
		String department_name = "";

		if(user != null) {
			if(user.getPassword().equals(password)) {
//				if(mySQLService.executeUpdate("update usr setObject status = 1,ip = ? where name = ?", dst_ip, name) < 1)
//					System.err.println("update usr status, ip error!");

				if(userMapper.setLoginInfo(name, dst_ip, port) < 1) {
					System.err.println("update usr status, ip error!");
				}

				result = JsonString.SUCCESS;
				id = user.getId();

				user.setStatus(1);
				user.setIp(dst_ip);
				user.setPort(port);

//				SharedInfo.map.get(id).setStatus(1);
//				SharedInfo.map.get(id).setIp(dst_ip);

				redisClusterUtils.setObject(user.getName(), user);
				redisClusterUtils.setObject("" + user.getId(), user);

			} else {
				result = JsonString.PASSWORD_WRONG;
			}
//			List<Department> departments = mySQLService.queryDepartment("select * from department where id in (select department from usr where name = ?) "
//					+ "or id int (select army from usr where name = ?) order by id", name, name);

			List<Department> departments = departmentMapper.getArmyAndDepByUsername(name);

			if(!departments.isEmpty() && departments.size() == 2) {
				army_name = departments.get(0).getName();
				department_name = departments.get(1).getName();
			} else {
				System.err.println("query department error!");
			}
		}

		JSONObject json = new JSONObject();
		json.put(JsonString.PACKET_TYPE, JsonString.LOGIN_REPONSE);
		json.put(JsonString.USR_ID, id);
		json.put(JsonString.RESULT, result);
		json.put(JsonString.ARMY_NAME, army_name);
		json.put(JsonString.DEPARTMENT_NAME, department_name);
		udpInterface.sendData(json.toString().getBytes(), dst_ip, port);
	}
}