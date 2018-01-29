package DataStruct;

public class Department {
	private String name;
	public void setName(String name) {
		this.name = name;
	}

	private int id;
	private String remark;
	private int parent_id;

	public Department(){};

	public Department(String name, int id, String remark) {
		this.name = name;
		this.setId(id);
		this.setRemark(remark);
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

}
