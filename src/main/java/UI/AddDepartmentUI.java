package UI;

import ConstField.SharedInfo;
import org.springframework.stereotype.Service;
import sqlMapper.DepartmentMapper;

import javax.annotation.Resource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@Service
public class AddDepartmentUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField departmentText, remarkText;

	private JLabel titileNameLabel;

	private int armyId = 0;

	private int oldX, oldY;

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Resource
	private DepartmentMapper departmentMapper;

	public AddDepartmentUI() {
		int frameWidth = SharedInfo.WIDTH;
		int frameHeight = SharedInfo.HEIGHT;

		this.setBounds(0, 0, frameWidth - 128, frameHeight * 8 / 9);
		this.setPreferredSize(new Dimension(frameWidth - 128, frameHeight * 8 / 9));
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setUndecorated(true);

		//***************************标题面板设置*******************************
		//标题面板
		Font font = new Font("微软雅黑", Font.BOLD, 16);
		JPanel titlePanel = new MyPanel(MainUI.images[1], frameWidth - 130, frameHeight / 10 - 1);
		titlePanel.setBounds(1, 1, frameWidth - 130, frameHeight / 10);
		titlePanel.setLayout(null);
		titlePanel.setBorder(BorderFactory.createLineBorder(new Color(130,130,130), 1));
		//标题名
		titileNameLabel = new JLabel("添加部门");
		titileNameLabel.setBounds(frameWidth / 50, frameHeight / 50, frameWidth / 10, frameHeight / 22);
		titileNameLabel.setFont(font);
		titileNameLabel.setForeground(Color.DARK_GRAY);
		//**********************************************************************

		//*************************添加好友主面板********************************
		JPanel mainPanel = new JPanel(null);
		mainPanel.setBounds(1, frameHeight / 10 , frameWidth - 130, frameHeight * 7 / 9 + 6);
		mainPanel.setBorder(BorderFactory.createLineBorder(new Color(120,120,120), 1));
		//用户名label
		font = new Font("微软雅黑", Font.PLAIN, 14);
		JLabel usrLabel = new JLabel("部  门 :");
		usrLabel.setFont(font);
		usrLabel.setBounds(frameWidth / 6, frameHeight / 6, frameWidth / 16, frameHeight / 24);
		//用户名text
		departmentText = new JTextField();
		departmentText.setBounds(frameWidth / 4, frameHeight / 6, frameWidth / 4, frameHeight / 24);
		//备注label
		JLabel remarkLabel = new JLabel("备   注 :");
		remarkLabel.setFont(font);
		remarkLabel.setBounds(frameWidth / 6, frameHeight / 4, frameWidth / 18, frameHeight / 24);
		//备注text
		remarkText = new JTextField();
		remarkText.setBounds(frameWidth / 4, frameHeight / 4, frameWidth / 4, frameHeight / 24);
		//添加按钮
		JButton add = new JButton("添加");
		add.setBounds((int)(frameWidth / 3.7), (int)(frameHeight / 2.9), frameWidth / 12, frameHeight / 24);
		add.setFocusPainted(false);
		add.setBackground(SharedInfo.GREEN);
		add.setFont(font);
		//取消按钮
		JButton quit = new JButton("取消");
		quit.setBounds((int)(frameWidth / 2.6), (int)(frameHeight / 2.9), frameWidth / 12, frameHeight / 24);
		quit.setFocusPainted(false);
		quit.setFont(font);
		quit.setBackground(Color.GREEN);
		quit.setOpaque(false);

		//添加组件
		titlePanel.add(titileNameLabel);
		mainPanel.add(usrLabel);
		mainPanel.add(departmentText);
		mainPanel.add(remarkLabel);
		mainPanel.add(remarkText);
		mainPanel.add(add);
		mainPanel.add(quit);
		this.add(titlePanel);
		this.add(mainPanel);

		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				oldX = e.getX();
				oldY = e.getY();
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				int absX = e.getXOnScreen();
				int absY = e.getYOnScreen();
				int dx = absX - oldX;
				int dy = absY - oldY;

				getAddDepartmentUI().setLocation(dx, dy);
			}
		});

		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if(departmentText.getText().compareTo("") == 0)
					JOptionPane.showMessageDialog(getRootPane(), "部门名称不能为空！",
							"系统信息", JOptionPane.WARNING_MESSAGE);
				else {
					String usr = departmentText.getText();
					String remark = remarkText.getText();

					SharedInfo.ADD_DEPARTMENT = true;
					SharedInfo.DEPARTMENT_NAME = usr;
					SharedInfo.DEPARTMENT_REMARK = remark;
					SharedInfo.PARENT_ID = armyId;
//					if (mySQLService.executeUpdate("insert into department(name, parent_id, remark) values(?, ?, ?)", usr, armyId, remark) < 1)
//						System.err.println("insert usr into mysql error!");

					System.out.println("parent_id:" + armyId);
					departmentMapper.addDepartment(usr, armyId, remark);

					getAddDepartmentUI().dispose();
				}
			}
		});

		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getAddDepartmentUI().dispose();
			}
		});
	}

	private AddDepartmentUI getAddDepartmentUI() {
		return this;
	}

	public void setTitle(String str) {
		titileNameLabel.setText(str);
	}

	void setArmy_id(int armyId) {
		this.armyId = armyId;
	}

	void clear() {
		departmentText.setText("");
		remarkText.setText("");
	}
}
