package UI;

import ConstField.SharedInfo;
import DataStruct.Department;
import sqlMapper.DepartmentMapper;
import sqlMapper.UserMapper;

import javax.annotation.Resource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DisplayDepartmentItemUI extends JPanel {
	private static final long serialVersionUID = 1L;

	private int pageNumber = 0;
	private int currentPage = 1;
	private List<DepartmentItem> showDepartmentItemList;
	private int maxMailNumberPerPage = SharedInfo.MAX_ITEM_PER_PAGE;
	private int foreMailIndex = -1;
	private JLabel pageLabel;
	private JLabel departmentNumber;
	private JLabel forePage;
	private JLabel nextPage;
	private JCheckBox selectAll;

	protected JLabel name;

	private JPanel departmentItemDisplayPanel;

	JLabel titileNameLabel = new JLabel();

	@Resource
	private AddDepartmentUI addDepartmentUI;

	int armyId = 0;

	private static ImageIcon[] imageIcons = new ImageIcon[] {
		new ImageIcon(NavyInfoPanel.class.getResource("/images/forePage.png")),  //0
		new ImageIcon(NavyInfoPanel.class.getResource("/images/forePage1.png")), //1
		new ImageIcon(NavyInfoPanel.class.getResource("/images/nextPage.png")),  //2
		new ImageIcon(NavyInfoPanel.class.getResource("/images/nextPage1.png")), //3
	};

	void init(List<DepartmentItem> showDepartmentItemList, int armyId, String name) {
		this.showDepartmentItemList = showDepartmentItemList;
		this.armyId = armyId;
		titileNameLabel.setText(name);
		launchFrame();
	}

	private void launchFrame() {
		int frameWidth = SharedInfo.WIDTH;
		int frameHeight = SharedInfo.HEIGHT;

		this.setBounds(0, 0, frameWidth - 128, frameHeight * 8 / 9);
		this.setLayout(null);

		//标题面板
		Font font = new Font("微软雅黑", Font.BOLD, 16);
		JPanel titlePanel = new MyPanel(MainUI.images[1], frameWidth - 127, frameHeight / 10);
		titlePanel.setBounds(-1, -1, frameWidth - 127, frameHeight / 10);
		titlePanel.setLayout(null);
		titlePanel.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100),  1));
		//标题名
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
		name = new JLabel("部门名称");
		name.setFont(font);
		name.setForeground(Color.DARK_GRAY);
		name.setBounds((int)(frameWidth / 4.9), frameHeight / 20, frameWidth / 10, frameHeight / 22);
		//主题
		JLabel Id = new JLabel("ID");
		Id.setFont(font);
		Id.setForeground(Color.DARK_GRAY);
		Id.setBounds((int)(frameWidth / 2.2), frameHeight / 20, frameWidth / 10, frameHeight / 22);
		//备注
		JLabel remark = new JLabel("备注");
		remark.setFont(font);
		remark.setForeground(Color.DARK_GRAY);
		remark.setBounds((int)(frameWidth / 1.5), frameHeight / 20, frameWidth / 10, frameHeight / 22);
		//*************************************************************************

		//**************************邮件显示部分内容********************************
		departmentItemDisplayPanel = new JPanel(null);
		departmentItemDisplayPanel.setBounds(0, frameHeight / 10 , frameWidth - 127, frameHeight * 7 / 9 - frameHeight / 10);

		//******************************尾部面板************************************
		JPanel tailPanel = new JPanel(null);
		tailPanel.setBounds(0, frameHeight * 7 / 9 + frameHeight / 80, frameWidth - 127, frameHeight / 9);
		tailPanel.setBackground(new Color(214, 216, 220));

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
		titlePanel.add(selectAll);
		titlePanel.add(name);
		titlePanel.add(Id);
		titlePanel.add(remark);
		tailPanel.add(deleteDepartment);
		tailPanel.add(addDepartment);
		tailPanel.add(pageLabel);
		tailPanel.add(forePage);
		tailPanel.add(nextPage);
		this.add(titlePanel);
		this.add(departmentItemDisplayPanel);
		this.add(tailPanel);
		this.setVisible(true);

		new Thread(new ScannAddDepartment()).start();

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

				//				int startIndex = (currentPage -1) * maxMailNumberPerPage;
				//				int endIndex = currentPage * maxMailNumberPerPage;

				if(selectAll.isSelected()) {
					forePage.setEnabled(false);
					nextPage.setEnabled(false);
				} else {
					forePage.setEnabled(true);
					nextPage.setEnabled(true);
				}

				for(DepartmentItem department : showDepartmentItemList) {
					department.setSelected(selectAll.isSelected());
				}
			}
		});

		forePage.addMouseListener(new MyListener());
		nextPage.addMouseListener(new MyListener());

		deleteDepartment.addActionListener(e -> {
            ArrayList<Integer> deleteId = new ArrayList<>();

            Iterator<DepartmentItem> iter = showDepartmentItemList.iterator();
            while(iter.hasNext()) {
                DepartmentItem departmentItem = iter.next();
                if(departmentItem.isSelected()) {
                    deleteId.add(departmentItem.getId());
                    iter.remove();
                    deleteMailUpdate(departmentItem);
                    deleteUsr(departmentItem.getId());
                }
            }

            if(!deleteId.isEmpty()) {
            	DepartmentMapper departmentMapper = (DepartmentMapper) SharedInfo.AC.getBean("departmentMapper");

            	departmentMapper.deleteDepartmentById(deleteId);
//
            }
        });

		addDepartment.addActionListener(e -> {
            addDepartmentUI.clear();
            addDepartmentUI.setArmy_id(armyId);
            addDepartmentUI.pack();
            addDepartmentUI.setVisible(true);
        });
	}

	private void deleteUsr(int department_id) {
//		if(mySQLService.executeUpdate("delete from usr where department = ?", department_id) < 1)
//			System.err.println("delete department from mysql error!");

		UserMapper userMapper = (UserMapper) SharedInfo.AC.getBean("userMapper");

		userMapper.deleteUserByDepartment(department_id);
	}

	private void addMailItem(Object object, int mailIndex) {
		if(foreMailIndex == maxMailNumberPerPage - 1)
			mailIndex = 0;
		else
			mailIndex = foreMailIndex + 1;
		foreMailIndex = mailIndex;
		((DepartmentItem)object).updateLocation(mailIndex);
		departmentItemDisplayPanel.add((DepartmentItem)object);
	}

	//�����ʼ�item��ʾ����
	private void updateMailDisplayItemUI(int startIndex, int endIndex) {
		departmentItemDisplayPanel.removeAll();

		for(int i = startIndex; i < showDepartmentItemList.size() && i < endIndex; ++ i) {
			int mailIndex = 0;
			addMailItem(showDepartmentItemList.get(i), mailIndex);
		}

		setPageNumber();
		departmentItemDisplayPanel.updateUI();
	}

	//�����ʼ��������½��棬�������ʼ�ʱʹ�ã��÷�����Ҫ��д
	private void addDepartmentUpdate(DepartmentItem departmentItem) {
		showDepartmentItemList.add(departmentItem);

		if(showDepartmentItemList.size() % maxMailNumberPerPage == 1) {
			++ pageNumber;
			pageLabel.setText(currentPage + "/" + pageNumber);
		}

		departmentNumber.setText("(共" + showDepartmentItemList.size() + "个)");

		foreMailIndex = -1;
		updateMailDisplayItemUI((currentPage - 1) * maxMailNumberPerPage, currentPage * maxMailNumberPerPage);
	}

	//ɾ���ʼ��������½���
	private void deleteMailUpdate(DepartmentItem mailItem) {
		//		showDepartmentItemList.remove(mailItem);

		if(showDepartmentItemList.size() % maxMailNumberPerPage == 0) {

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

	public DisplayDepartmentItemUI getDisplayMailItemUI() {
		return this;
	}

	//����ҳ��
	private void setPageNumber() {
		if(showDepartmentItemList.size() == 0) {
			pageLabel.setText("1/1");
			pageNumber = 0;
		} else {
			pageNumber = showDepartmentItemList.size() / maxMailNumberPerPage;
			if(showDepartmentItemList.size() % maxMailNumberPerPage != 0)
				++ pageNumber;
			pageLabel.setText(currentPage + "/" + pageNumber);
		}
	}

	public void updateItem() {
		updateMailDisplayItemUI(0, showDepartmentItemList.size());
	}

	public AddDepartmentUI getAddDepartmentUI() {
		return addDepartmentUI;
	}

	public void setAddDepartmentUI(AddDepartmentUI addDepartmentUI) {
		this.addDepartmentUI = addDepartmentUI;
	}

	class ScannAddDepartment implements Runnable {

		@Override
		public void run() {
			while(true) {
				if(SharedInfo.ADD_DEPARTMENT && SharedInfo.PARENT_ID == armyId) {
//					List<Index> res = mySQLService.queryIndex("select max(id) from department");

					DepartmentMapper departmentMapper = (DepartmentMapper) SharedInfo.AC.getBean("departmentMapper");

					int index = departmentMapper.getMaxId();

					addDepartmentUpdate(new DepartmentItem(new Department(SharedInfo.DEPARTMENT_NAME, index, SharedInfo.DEPARTMENT_REMARK)));
					SharedInfo.ADD_DEPARTMENT = false;
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
