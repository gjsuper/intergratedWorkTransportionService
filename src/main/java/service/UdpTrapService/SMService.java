package service.UdpTrapService;

import ConstField.JsonString;
import ConstField.SharedInfo;
import DataStruct.Message;
import DataStruct.User;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;
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

	public void process(JSONObject jsonObject, String ip, int port) {
		int messageIndex = jsonObject.getInt(JsonString.MESSAGE_INDEX);
		int src_id = jsonObject.getInt(JsonString.SRC_ID);
		int dst_id = jsonObject.getInt(JsonString.DST_ID);
		String data = jsonObject.getString(JsonString.MESSAGE);

		String dst_ip;

		Message message = new Message();
		message.setSrc_id(src_id);
		message.setDst_id(dst_id);
		message.setData(data);

		messageMapper.addMessage(message);

		if((dst_ip = getIpIfOnLine(dst_id)) != null) {
			jsonObject.put(JsonString.MESSAGE_INDEX, message.getId());

			udpInterface.sendData(jsonObject.toString().getBytes(), dst_ip, port);
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
	private String getIpIfOnLine(int dst_id) {
		String ip = null;
		
		User usr = SharedInfo.map.get(dst_id);
		if(usr != null && usr.getStatus() == 1) {
			ip = SharedInfo.map.get(dst_id).getIp();
		}
		
		return ip;
	}
}
