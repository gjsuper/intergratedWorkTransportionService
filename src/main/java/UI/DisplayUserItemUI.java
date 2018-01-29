package UI;

import ConstField.SharedInfo;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DisplayUserItemUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private int mailIndex = 0;
	private int pageNumber = 0;
	private int currentPage = 1;
	private List<UserItem> showUsrItemList;
	private int maxMailNumberPerPage = SharedInfo.MAX_ITEM_PER_PAGE;
	private int foreMailIndex = -1;
	private JLabel pageLabel;
	private JLabel titileNameLabel, departmentNumber, forePage, nextPage;
	private JCheckBox selectAll;

	protected JLabel name;

	private JPanel departmentItemDisplayPanel;

	private int oldX;

	private int oldY;

	private static ImageIcon[] imageIcons = new ImageIcon[] {
		new ImageIcon(NavyInfoPanel.class.getResource("/images/forePage.png")),  //0
		new ImageIcon(NavyInfoPanel.class.getResource("/images/forePage1.png")), //1
		new ImageIcon(NavyInfoPanel.class.getResource("/images/nextPage.png")),  //2
		new ImageIcon(NavyInfoPanel.class.getResource("/images/nextPage1.png")), //3
	};
	public DisplayUserItemUI() {
		showUsrItemList = new ArrayList<>();
		launchFrame();
	}

	private void launchFrame() {
		int frameWidth = SharedInfo.WIDTH;
		int frameHeight = SharedInfo.HEIGHT;

		this.setBounds(0, 0, frameWidth - 128, frameHeight * 8 / 9);
		this.setPreferredSize(new Dimension(frameWidth - 128, frameHeight * 8 / 9));
		this.setLayout(null);
		this.setResizable(false);
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);

		//标题面板
		Font font = new Font("微软雅黑", Font.BOLD, 16);
		JPanel titlePanel = new MyPanel(MainUI.images[1], frameWidth - 127, frameHeight / 10);
		titlePanel.setBounds(0, 1, frameWidth - 130, frameHeight / 10 - 1);
		titlePanel.setLayout(null);
		titlePanel.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120),  1));
		//标题名
		titileNameLabel = new JLabel();
		titileNameLabel.setBounds(frameWidth / 50, 1, frameWidth / 3, frameHeight / 25);
		titileNameLabel.setFont(font);
		titileNameLabel.setForeground(Color.DARK_GRAY);
		//邮件数量
		font = new Font("微软雅黑", Font.PLAIN, 14);
		departmentNumber = new JLabel();
		departmentNumber.setFont(font);
		departmentNumber.setBounds(frameWidth / 7, 1, frameWidth / 10, frameHeight / 25);

		//全选框
		selectAll = new JCheckBox();
		selectAll.setOpaque(false);
		selectAll.setBounds(frameWidth / 35, frameHeight / 20, frameWidth / 50, frameHeight / 22);
		//部门名称
		name = new JLabel("用户名");
		name.setFont(font);
		name.setForeground(Color.DARK_GRAY);
		name.setBounds((int)(frameWidth / 4.9), frameHeight / 20, frameWidth / 10, frameHeight / 22);
		//主题
		JLabel Id = new JLabel("ID");
		Id.setFont(font);
		Id.setForeground(Color.DARK_GRAY);
		Id.setBounds((int)(frameWidth / 2.2), frameHeight / 20, frameWidth / 10, frameHeight / 22);
		//备注
		JLabel remark = new JLabel("军种");
		remark.setFont(font);
		remark.setForeground(Color.DARK_GRAY);
		remark.setBounds((int)(frameWidth / 1.5), frameHeight / 20, frameWidth / 10, frameHeight / 22);
		//*************************************************************************

		//**************************邮件显示部分内容********************************
		departmentItemDisplayPanel = new JPanel(null);
		departmentItemDisplayPanel.setBounds(0, frameHeight / 10 , frameWidth - 130, frameHeight * 7 / 9 - frameHeight / 10);
		departmentItemDisplayPanel.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120),  1));

		//******************************尾部面板************************************
		JPanel tailPanel = new JPanel(null);
		tailPanel.setBounds(0, frameHeight * 7 / 9 + frameHeight / 80 - 10, frameWidth - 130, frameHeight / 9 + 2);
		tailPanel.setBackground(new Color(214, 216, 220));
		tailPanel.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120),  1));

		//删除按钮
		JButton deleteDepartment = new JButton("删除");
		deleteDepartment.setFocusPainted(false);
		deleteDepartment.setBackground(Color.GREEN);
		deleteDepartment.setOpaque(false);
		deleteDepartment.setFont(font);
		deleteDepartment.setBounds(frameWidth / 30, frameHeight / 30, frameWidth / 11, frameHeight / 24);

		//增加按钮
		JButton addDepartment = new JButton("添加");
		addDepartment.setFocusPainted(false);
		addDepartment.setBackground(Color.GREEN);
		addDepartment.setOpaque(false);
		addDepartment.setFont(font);
		addDepartment.setBounds(frameWidth / 5, frameHeight / 30, frameWidth / 11, frameHeight / 24);

		//删除按钮
		JButton quit = new JButton("退出");
		quit.setFocusPainted(false);
		quit.setBackground(Color.GREEN);
		quit.setOpaque(false);
		quit.setFont(font);
		quit.setBounds(frameWidth / 30, frameHeight / 30, frameWidth / 11, frameHeight / 24);

		//页码label
		pageLabel = new JLabel();
		pageLabel.setBounds((int)(frameWidth / 1.53), frameHeight / 30, frameWidth / 20, frameHeight / 24);
		pageLabel.setFont(font);
		//上一页label
		forePage = new JLabel(imageIcons[0]);
		forePage.setBounds((int)(frameWidth / 1.45), frameHeight / 30, frameWidth / 35, frameHeight / 24);
		forePage.setToolTipText("上一页");
		//下一页label
		nextPage = new JLabel(imageIcons[2]);
		nextPage.setBounds((int)(frameWidth / 1.4), frameHeight / 30, frameWidth / 35, frameHeight / 24);
		nextPage.setToolTipText("下一页");

		titlePanel.add(titileNameLabel);
		titlePanel.add(departmentNumber);
		//		titlePanel.add(selectAll);
		titlePanel.add(name);
		titlePanel.add(Id);
		titlePanel.add(remark);
		//		tailPanel.add(deleteDepartment);
		//		tailPanel.add(addDepartment);
		tailPanel.add(quit);
		tailPanel.add(pageLabel);
		tailPanel.add(forePage);
		tailPanel.add(nextPage);
		this.add(titlePanel);
		this.add(departmentItemDisplayPanel);
		this.add(tailPanel);
//		this.setVisible(true);
		
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

				getDisplayUsrItemUI().setLocation(dx, dy);
			}
		});

		class MyListener extends MouseAdapter {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getSource() == forePage && currentPage > 1 && forePage.isEnabled()) {
					foreMailIndex = -1;
					-- currentPage;
					updateMailDisplayItemUI((currentPage - 1) * maxMailNumberPerPage, currentPage * maxMailNumberPerPage);
					pageLabel.setText(currentPage + "/" + pageNumber);
				} else if(e.getSource() == nextPage && currentPage < pageNumber && nextPage.isEnabled()) {
					foreMailIndex = -1;
					updateMailDisplayItemUI(currentPage * maxMailNumberPerPage, (currentPage + 1) * maxMailNumberPerPage);
					++ currentPage;
					pageLabel.setText(currentPage + "/" + pageNumber);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if(e.getSource() == forePage && forePage.isEnabled())
					forePage.setIcon(imageIcons[1]);
				else if(e.getSource() == nextPage && nextPage.isEnabled())
					nextPage.setIcon(imageIcons[3]);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if(e.getSource() == forePage)
					forePage.setIcon(imageIcons[0]);
				else if(e.getSource() == nextPage) 
					nextPage.setIcon(imageIcons[2]);
			}
		}

		selectAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(selectAll.isSelected()) {
					forePage.setEnabled(false);
					nextPage.setEnabled(false);
				} else {
					forePage.setEnabled(true);
					nextPage.setEnabled(true);
				}

				for(UserItem department : getShowUsrItemList()) {
					department.setSelected(selectAll.isSelected());
				}
			}
		});

		forePage.addMouseListener(new MyListener());
		nextPage.addMouseListener(new MyListener());

//		deleteDepartment.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				ArrayList<Integer> deleteId = new ArrayList<>();
//
//				Iterator<UserItem> iter = showUsrItemList.iterator();
//				while(iter.hasNext()) {
//					UserItem departmentItem = iter.next();
//					if(departmentItem.isSelected()) {
//						deleteId.add(departmentItem.getId());
//						iter.remove();
//						deleteMailUpdate(departmentItem);
//						deleteUsr(departmentItem.getId());
//					}
//				}
//
//				if(!deleteId.isEmpty()) {
//					StringBuilder sb = new StringBuilder("delete from department where id in(");
//
//					for(int i = 0; i < deleteId.size() - 1; ++ i) {
//						sb.append(deleteId.getBytes(i)).append(",");
//					}
//
//					sb.append(deleteId.getBytes(deleteId.size() - 1)).append(")");
//
//					PreparedStatement ps = null;
//					try {
//						ps = mySQLService.executeUpdate(sb.toString());
//						System.out.println("delete");
//					} finally {
//						if(ps != null) {
//							try {
//								ps.close();
//							} catch (SQLException e1) {
//								e1.printStackTrace();
//							}
//						}
//					}
//				}
//			}
//		});
//
//		addDepartment.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				addDepartmentUI.clear();
//				addDepartmentUI.setArmy_id(armyId);
//				addDepartmentUI.pack();
//				addDepartmentUI.setVisible(true);
//			}
//		});
		
		quit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getDisplayUsrItemUI().dispose();
			}
		});
	}

//	private void deleteUsr(int department_id) {
//		PreparedStatement ps = null;
//		if(mySQLService == null) {
//			System.out.println("mySQLService is null");
//			return;
//		}
//		try {
//			ps = mySQLService.executeUpdate("delete from usr where department = ?", department_id);
//		} finally {
//			if(ps != null) {
//				try {
//					ps.close();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//		}
//	}

	private void addMailItem(Object object, int mailIndex) {
		if(foreMailIndex == maxMailNumberPerPage - 1)
			mailIndex = 0;
		else
			mailIndex = foreMailIndex + 1;
		foreMailIndex = mailIndex;
		((UserItem)object).updateLocation(mailIndex);
		departmentItemDisplayPanel.add((UserItem)object);
	}

	//�����ʼ�item��ʾ����
	private void updateMailDisplayItemUI(int startIndex, int endIndex) {
		departmentItemDisplayPanel.removeAll();

		for(int i = startIndex; i < getShowUsrItemList().size() && i < endIndex; ++ i) {
			addMailItem(getShowUsrItemList().get(i), mailIndex);
		}

		setPageNumber();
		departmentItemDisplayPanel.updateUI();
	}

	//�����ʼ��������½��棬�������ʼ�ʱʹ�ã��÷�����Ҫ��д
	public void addUsrUpdate(UserItem usrItem) {
		getShowUsrItemList().add(usrItem);

		if(getShowUsrItemList().size() % maxMailNumberPerPage == 1) {
			++ pageNumber;
			pageLabel.setText(currentPage + "/" + pageNumber);
		}

		departmentNumber.setText("(共" + getShowUsrItemList().size() + "个)");

		foreMailIndex = -1;
		updateMailDisplayItemUI((currentPage - 1) * maxMailNumberPerPage, currentPage * maxMailNumberPerPage);
	}

	//ɾ���ʼ��������½���
	public void deleteMailUpdate(UserItem mailItem) {
		//		showDepartmentItemList.remove(mailItem);

		if(getShowUsrItemList().size() % maxMailNumberPerPage == 0) {

			if(currentPage > 1)
				-- currentPage;
			if(pageNumber > 1)
				-- pageNumber;
			pageLabel.setText(currentPage + "/" + pageNumber);
		}

		foreMailIndex = -1;
		updateMailDisplayItemUI((currentPage - 1) * maxMailNumberPerPage, currentPage * maxMailNumberPerPage);
	}

	//		showMailItemVector.remove(mailItem);
	//
	//		if(showMailItemVector.size() % maxMailNumberPerPage == 0) {
	//
	//			if(currentPage > 1)
	//				-- currentPage;
	//			if(pageNumber > 1)
	//				-- pageNumber;
	//			pageLabel.setText(currentPage + "/" + pageNumber);
	//		}
	//
	//		foreMailIndex = -1;
	//		updateMailDisplayItemUI((currentPage - 1) * maxMailNumberPerPage, currentPage * maxMailNumberPerPage);
	//	}

	public DisplayUserItemUI getDisplayMailItemUI() {
		return this;
	}

	//����ҳ��
	private void setPageNumber() {
		if(getShowUsrItemList().size() == 0) {
			pageLabel.setText("1/1");
			pageNumber = 0;
		} else {
			pageNumber = getShowUsrItemList().size() / maxMailNumberPerPage;
			if(getShowUsrItemList().size() % maxMailNumberPerPage != 0)
				++ pageNumber;
			pageLabel.setText(currentPage + "/" + pageNumber);
		}
	}

	void updateItem() {
		if(getShowUsrItemList() == null) {
			System.out.println("usr list null");
			return;
		}
		foreMailIndex = -1;
		mailIndex = 0;
		updateMailDisplayItemUI(0, getShowUsrItemList().size());
	}

	private DisplayUserItemUI getDisplayUsrItemUI() {
		return this;
	}

	List<UserItem> getShowUsrItemList() {
		return showUsrItemList;
	}

	public void setShowUsrItemList(List<UserItem> showUsrItemList) {
		this.showUsrItemList = showUsrItemList;
	}
	
	public void setTitle(String title) {
		titileNameLabel.setText(title);
	} 
}
