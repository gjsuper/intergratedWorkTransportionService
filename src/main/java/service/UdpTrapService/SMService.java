package service.UdpTrapService;

import ConstField.JsonString;
import DataStruct.Message;
import DataStruct.User;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import service.dataFetchService.CacheService;
import service.transportationService.UdpInterface;
import sqlMapper.MessageMapper;

import javax.annotation.Resource;

@Service
public class SMService {

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Resource
	private MessageMapper messageMapper;

	@Resource
	private UdpInterface udpInterface;

	@Resource
	private CacheService cacheService;

	public void process(JSONObject jsonObject, String ip, int port) {
		int messageIndex = jsonObject.getInt(JsonString.MESSAGE_INDEX);
		int src_id = jsonObject.getInt(JsonString.SRC_ID);
		int dst_id = jsonObject.getInt(JsonString.DST_ID);
		String data = jsonObject.getString(JsonString.MESSAGE);



		System.out.println("recv a message:src_id:" + src_id + ",dst_id:" + dst_id + ",message:" + data);

		Message message = new Message();
		message.setSrc_id(src_id);
		message.setDst_id(dst_id);
		message.setData(data);

		messageMapper.addMessage(message);

		User user;
		if((user = getUserIfOnLine(dst_id)) != null) {
			jsonObject.put(JsonString.MESSAGE_INDEX, message.getId());

			udpInterface.sendData(jsonObject.toString().getBytes(), user.getIp(), user.getPort());
		}

		//���ض���Ϣȷ��
		JSONObject jObject = new JSONObject();
		jObject.put(JsonString.PACKET_TYPE, JsonString.MESSAGE_DATA_ACK);
		jObject.put(JsonString.MESSAGE_INDEX, messageIndex);
		udpInterface.sendData(jObject.toString().getBytes(), ip, port);
		

	}


	/**
	 * ����Է����߾ͷ�������ip,���򷵻�null
	 * @param dst_id Ŀ��id
	 * @return Ŀ��ip
	 */
	private User getUserIfOnLine(int dst_id) {
		User usr = cacheService.getUserById(dst_id);
		if(usr != null && usr.getStatus() == 1) {
			return usr;
		}
		
		return null;
	}
}
