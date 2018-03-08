package DataStruct;

import java.sql.Timestamp;

public class Message {

	private int src_id;
	private int id;
	private int dst_id;
	private String data;
	private Timestamp timestamp;

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public int getSrc_id() {
		return src_id;
	}
	public void setSrc_id(int src_id) {
		this.src_id = src_id;
	}
	public int getDst_id() {
		return dst_id;
	}
	public void setDst_id(int dst_id) {
		this.dst_id = dst_id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
