package service.UdpTrapService;


import ConstField.JsonString;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UdpTrapService {
	  
	@Resource
	private
	UdpTrapServiceFactory udpTrapServiceFactory;
	
	public UdpTrapService() {}

	public void processTrap(JSONObject jsonObj, String ip, int port) {

		if(jsonObj.getString(JsonString.PACKET_TYPE).equals(JsonString.REGISTER_REQUEST)) {
			udpTrapServiceFactory.getRegisterService().process(jsonObj, ip, port);
		} else if(jsonObj.getString(JsonString.PACKET_TYPE).equals(JsonString.LOGIN_REQUEST)) {
			udpTrapServiceFactory.getLoginService().process(jsonObj, ip, port);
		} else if(jsonObj.getString(JsonString.PACKET_TYPE).equals(JsonString.MESSAGE_DATA)) {
			udpTrapServiceFactory.getProcessSM().process(jsonObj, ip, port);
		} else if(jsonObj.getString(JsonString.PACKET_TYPE).equals(JsonString.SYNC_CONTACT_REQUEST)) {
			udpTrapServiceFactory.getSyncContactService().process(jsonObj, ip, port);
		} else if(jsonObj.getString(JsonString.PACKET_TYPE).equals(JsonString.SYNC_DEPARTMENT_REQUEST)) {
			udpTrapServiceFactory.getSyncDepartmentService().process(jsonObj, ip, port);
		} else if(jsonObj.getString(JsonString.PACKET_TYPE).equals(JsonString.LOGOUT_REQUEST)) {
			udpTrapServiceFactory.getLogoutService().process(jsonObj, ip, port);
		} else if(jsonObj.getString(JsonString.PACKET_TYPE).equals(JsonString.CHANGE_PASSWORD_REQUEST)) {
			udpTrapServiceFactory.getChangePasswordService().process(jsonObj, ip, port);
		} else if(jsonObj.getString(JsonString.PACKET_TYPE).equals(JsonString.SYNC_MESSAGE_REQUEST)) {
			udpTrapServiceFactory.getSyncSMService().process(jsonObj, ip, port);
		} else if(jsonObj.getString(JsonString.PACKET_TYPE).equals(JsonString.FILE_REQUEST) || 
				jsonObj.getString(JsonString.PACKET_TYPE).equals(JsonString.FILE_DATA) ||
				jsonObj.getString(JsonString.PACKET_TYPE).equals(JsonString.SEND_FILE_FINISH) ||
				jsonObj.getString(JsonString.PACKET_TYPE).equals(JsonString.FILE_RESEND_REQUEST)) {
			udpTrapServiceFactory.getSyncSMService().process(jsonObj, ip, port);
		} else if(jsonObj.getString(JsonString.PACKET_TYPE).equals(JsonString.CLOSE_ACCOUNT_REQUEST)) {
			udpTrapServiceFactory.getCloseAccountService().process(jsonObj, ip, port);
		}
	}
	
	public void print() {
		//    	System.out.println(mySQLService.executeQuery("select * from test2 where id = ?", 1234));
		System.out.println("UdpTrapService ");

//		ResultSet setObject = mySQLService.executeQuery("select * from test2 where id = ?", "1234");
//		PreparedStatement ps = null;
//		try {
//			ps = (PreparedStatement) setObject.getStatement();
//		} catch (SQLException e2) {
//			e2.printStackTrace();
//		}
//		try {
//			ps = (PreparedStatement) setObject.getStatement();
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//
//		try {
//			while(setObject.next()) {
//				System.out.println(setObject.getInt(1) + " " + setObject.getString(2) +  " " + setObject.getInt(3) + " " + setObject.getDate(4));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				setObject.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//
//			try {
//				ps.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		} 
	}

	public UdpTrapServiceFactory getUdpTrapServiceFactory() {
		return udpTrapServiceFactory;
	}

	public void setUdpTrapServiceFactory(UdpTrapServiceFactory udpTrapServiceFactory) {
		this.udpTrapServiceFactory = udpTrapServiceFactory;
	}
}
