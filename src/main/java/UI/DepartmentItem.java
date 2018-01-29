package UI;


import ConstField.SharedInfo;
import DataStruct.Department;
import DataStruct.User;
import sqlMapper.DepartmentMapper;
import sqlMapper.UserMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DepartmentItem extends JPanel {
	private static final long serialVersionUID = 1L;

	private int frameWidth;
	private Color gray = SharedInfo.GRAY;
	private Color light_gray = SharedInfo.LIGHT_GRAY;
	private Color mainColor;
	private JCheckBox select;
	private int tempValue;
	private Department department;
	private JLabel person;
	private int departmentId = 0;

	 public DepartmentItem(final Department department) {
		this.frameWidth = SharedInfo.WIDTH;
		int frameHeight = SharedInfo.HEIGHT;
		this.department = department;
		this.departmentId = department.getId();

		tempValue = (frameHeight * 8 / 9 - frameHeight / 10) / 9;
		this.setLayout(null);

		 //选择框
		 Font font = new Font("微软雅黑", Font.PLAIN, 15);
		 select = new JCheckBox();
		 select.setBounds(frameWidth / 36, frameHeight / 50, frameWidth / 50, frameHeight / 35);
		 select.setOpaque(false);
		 select.setFont(font);
		 //部门名称
		 JLabel name = new JLabel(department.getName(), JLabel.CENTER);
		 name.setBounds(frameWidth / 9, frameHeight / 50, frameWidth / 4, frameHeight / 30);
		 name.setFont(font);
		 //				theme.setBorder(BorderFactory.createLineBorder(new Color(1), 1));
		 //				mailIcon.setBorder(BorderFactory.createLineBorder(new Color(1), 1));
		 //id
		 person = new JLabel(department.getId() + "", JLabel.CENTER);
		 person.setBounds(frameWidth / 6 + frameWidth / 5, frameHeight / 50, frameWidth / 5, frameHeight / 30);
		 person.setFont(font);
		 //				sender.setBorder(BorderFactory.createLineBorder(new Color(1), 1));
		 //备注
		 JLabel remark = new JLabel(department.getRemark(), JLabel.CENTER);
		 remark.setBounds(frameWidth / 12 + frameWidth / 2, frameHeight / 50, frameWidth / 5, frameHeight / 30);
		 remark.setFont(font);

		 //添加组件
		 this.add(select);
		 this.add(name);
		 this.add(person);
		 this.add(remark);

		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				DisplayUserItemUI displayUserItemUI = (DisplayUserItemUI) SharedInfo.AC.getBean("displayUserItemUI");
				String armyName;

				SharedInfo.USR.clear();

//				List<Department> departments = mySQLService.queryDepartment("select * from department where id in(select parent_id from department where id = ?)", departmentId);

				DepartmentMapper departmentMapper = (DepartmentMapper) SharedInfo.AC.getBean("departmentMapper");

				Department department1 = departmentMapper.getDepartmentByChildId(departmentId);

				if(department1 != null)
					armyName = department1.getName();
				else {
					armyName = "";
					System.err.println("query department name error!");
				}

//				List<Usr> usrs = mySQLService.queryUsr("select * from usr where department = ? ", departmentId);

				UserMapper userMapper = (UserMapper) SharedInfo.AC.getBean("userMapper");

				List<User> users = userMapper.getUsersByDepartment(departmentId);


				displayUserItemUI.getShowUsrItemList().clear();
				for(User user : users) {
					user.setArmyName(armyName);
					displayUserItemUI.getShowUsrItemList().add(new UserItem(user));
				}


				displayUserItemUI.setTitle(department.getName() + "人员信息");
				displayUserItemUI.updateItem();
				displayUserItemUI.pack();
				displayUserItemUI.setVisible(true);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				getMailItem().setBackground(gray);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				getMailItem().setBackground(mainColor);
			}
		});
	}

	private DepartmentItem getMailItem() {
		return this;
	}

	boolean isSelected() {
		return select.isSelected();
	}

	void setSelected(boolean selected) {
		select.setSelected(selected);
	}

	void updateLocation(int mailIndex) {
		if(mailIndex % 2 != 0) {
			mainColor = light_gray;
		} else {
			mainColor = Color.WHITE;
		}

		this.setBackground(mainColor);
		this.setBounds(0, tempValue * mailIndex , frameWidth - 127, tempValue);
	}

	public String getName() {
		return department.getName();
	}

	public int getId() {
		return department.getId();
	}

	public String setRemark() {
		return department.getRemark();
	}

	public Department getDepartment() {
		return department;
	}
}
