package service.UdpTrapService;

import ConstField.JsonString;
import DataStruct.User;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import service.dataFetchService.CacheService;
import service.transportationService.UdpInterface;

import javax.annotation.Resource;

@Service
public class FileService {

	@Resource
	private UdpInterface udpInterface;

	@Resource
	private CacheService cacheService;

	public void process(JSONObject jsonObject, String ip, int port) {
		int dst_id = jsonObject.getInt(JsonString.DST_ID);
//		User usr = SharedInfo.map.get(dst_id);

		User user = cacheService.getUserById(dst_id);

		String dst_ip = user.getIp();
		udpInterface.sendData(jsonObject.toString().getBytes(), dst_ip, port);
	}
}
