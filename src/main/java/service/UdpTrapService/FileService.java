package service.UdpTrapService;

import ConstField.JsonString;
import ConstField.SharedInfo;
import DataStruct.User;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import service.transportationService.UdpInterface;

import javax.annotation.Resource;

@Service
public class FileService {

	@Resource
	private UdpInterface udpInterface;

	public void process(JSONObject jsonObject, String ip, int port) {
		int dst_id = jsonObject.getInt(JsonString.DST_ID);
		User usr = SharedInfo.map.get(dst_id);
		String dst_ip = usr.getIp();
		udpInterface.sendData(jsonObject.toString().getBytes(), dst_ip, port);
	}
}
