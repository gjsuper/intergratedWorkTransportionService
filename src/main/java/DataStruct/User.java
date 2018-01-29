package DataStruct;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

public class User implements Serializable{
	private int id = 0;
	private String name = null;
	private String password = null;
	private int army = 0;
	private int department = 0;
	private String ip = null;
	private int status = 0;
	private String armyName;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	private int port;
	
	public User() {
		
	}
	
	public User(String name, int id, String armyName) {
		this.name = name;
		this.id = id;
		this.armyName = armyName;
	}
	
	public User(int id, String name, String password, int army, int department,
                String ip, int status) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.army = army;
		this.department = department;
		this.ip = ip;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getArmy() {
		return army;
	}
	public void setArmy(int army) {
		this.army = army;
	}
	public int getDepartment() {
		return department;
	}
	public void setDepartment(int department) {
		this.department = department;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public String getArmyName() {
		return armyName;
	}

	public void setArmyName(String armyName) {
		this.armyName = armyName;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object usr) {
		if(usr == null)
			return false;
		if(usr == this)
			return true;
		if(usr instanceof User) {
			User u = (User)usr;
			return name.equals(u.getName());
		}
		return false;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("id", id).
				add("name", name).add("password", password).
				add("army", army).add("department", department).
				add("ip", ip).add("status",status).add("port", port).toString();
	}
}
