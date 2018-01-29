package service.UdpTrapService;

import ConstField.JsonString;
import DataStruct.Message;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import service.transportationService.UdpInterface;
import sqlMapper.MessageMapper;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service
public class SyncSMService {

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Resource
	private MessageMapper messageMapper;

	@Resource
	private UdpInterface udpInterface;

	public void process(JSONObject jsonObject, String ip, int port) {
		int id = jsonObject.getInt(JsonString.USR_ID);

		List<Message> messages;

		if (jsonObject.containsKey(JsonString.MESSAGE_INDEX)) {
			int index = jsonObject.getInt(JsonString.MESSAGE_INDEX);
//			messages = mySQLService.queryMessage("select * from message where dst_id = ? and id > ?", id, index);
			messages = messageMapper.getMessagesById(id, index);

		} else {
			String time = jsonObject.getString(JsonString.TIME);
			Timestamp timestamp = null;
			if (time.equals(JsonString.WEEK_AGO))
				timestamp = new Timestamp(System.currentTimeMillis() - 604800000);
			else {
				long t = 2_592_000_000L;
				timestamp = new Timestamp(System.currentTimeMillis() - t);
			}
//			messages = mySQLService.queryMessage("select *, data from message where dst_id = ? and time > ?", id, timestamp);
			messages = messageMapper.getMessagesByTime(id, timestamp);
		}

		for (Message message : messages) {
			JSONObject data = new JSONObject();
			data.put(JsonString.PACKET_TYPE, JsonString.MESSAGE_DATA);
			data.put(JsonString.SRC_ID, message.getSrc_id());
			data.put(JsonString.DST_ID, message.getDst_id());
			data.put(JsonString.MESSAGE, message.getData());


			udpInterface.sendData(data.toString().getBytes(), ip, port);

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
