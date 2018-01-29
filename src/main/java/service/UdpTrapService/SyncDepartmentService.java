package service.UdpTrapService;

import ConstField.JsonString;
import DataStruct.Department;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import service.transportationService.UdpInterface;
import sqlMapper.DepartmentMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SyncDepartmentService {

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Resource
	private DepartmentMapper departmentMapper;

	@Resource
	private UdpInterface udpInterface;

	public void process(JSONObject jsonObject, String ip, int port) {
		JSONObject rt = new JSONObject();

		rt.put(JsonString.PACKET_TYPE, JsonString.SYNC_DEPARTMENT_RESPONSE);

		List<JSONObject> departments= getDepartment();

		rt.put(JsonString.RESULT, departments);
		udpInterface.sendData(rt.toString().getBytes(), ip, port);
	}

	private List<JSONObject> getDepartment() {

//		List<Department> departments = mySQLService.queryDepartment("select * from department");

		List<Department> departments = departmentMapper.getAllDepartments();

		List<JSONObject> list = new ArrayList<>();

		for(Department department : departments) {
			JSONObject dep = new JSONObject();
			dep.put(JsonString.DEPARTMENT_ID, department.getId());
			dep.put(JsonString.DEPARTMENT_NAME, department.getName());
			dep.put(JsonString.PARENT_ID, department.getParent_id());
			list.add(dep);
		}

		return list;
	}
}
