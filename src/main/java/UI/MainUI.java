package UI;

import ConstField.SharedInfo;
import DataStruct.Department;
import DataStruct.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import service.redisService.RedisClusterUtils;
import sqlMapper.DepartmentMapper;
import sqlMapper.UserMapper;

import javax.annotation.Resource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

/**
 * ��0.1�����϶����ݿ���ʷ�ʽ���޸�,��jdbcΪJdbcTemplate
 * @author Administrator
 *
 */

@Service
public class MainUI extends JFrame implements InitializingBean{
	private static final long serialVersionUID = 1L;

	private int oldX;
	private int oldY;
	static Image[] images;
	private static Icon[] imageIcons;
	private JLabel navy;
	private JLabel army;
	private JLabel setting;
	private JLabel airForce;
	private boolean isNavyClicked = true;
	private boolean isArmyClicked;
	private boolean isSettingClicked;
	private boolean isAirForceClicked;
	private JPanel centerPanel;

	@Resource
	private NavyInfoPanel navyInfoPanel;
	@Resource
	private ArmyInfoPanel armyInfoPanel;
	@Resource
	private AirForceInfoPanel airForceInfoPanel;

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Resource
	private UserMapper userMapper;

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Resource
	private DepartmentMapper departmentMapper;

	@Resource
	private RedisClusterUtils redisClusterUtils;

	private CardLayout card = new CardLayout();

	static {
		images = new Image[] {
				new ImageIcon(MainUI.class.getResource("/images/background.png")).getImage(), //0
				new ImageIcon(MainUI.class.getResource("/images/file_menu.png")).getImage(),//1
		};

		imageIcons = new ImageIcon[] {
				new ImageIcon(MainUI.class.getResource("/images/min.png")),//0
				new ImageIcon(MainUI.class.getResource("/images/min1.png")),//1
				new ImageIcon(MainUI.class.getResource("/images/close.png")),//2
				new ImageIcon(MainUI.class.getResource("/images/close1.png")),//3

				new ImageIcon(MainUI.class.getResource("/images/haijun.png")),//4
				new ImageIcon(MainUI.class.getResource("/images/haijun1.png")),//5
				new ImageIcon(MainUI.class.getResource("/images/haijun2.png")),//6

				new ImageIcon(MainUI.class.getResource("/images/army.png")),//7
				new ImageIcon(MainUI.class.getResource("/images/army1.png")),//8
				new ImageIcon(MainUI.class.getResource("/images/army2.png")),//9

				new ImageIcon(MainUI.class.getResource("/images/airforce.png")),//10
				new ImageIcon(MainUI.class.getResource("/images/airforce1.png")),//11
				new ImageIcon(MainUI.class.getResource("/images/airforce2.png")),//12

				new ImageIcon(MainUI.class.getResource("/images/setting.png")),//13
				new ImageIcon(MainUI.class.getResource("/images/setting1.png")),//14
				new ImageIcon(MainUI.class.getResource("/images/setting2.png")),//15
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//		main.udpInterface.print();
		//		SyncContactService sync = (SyncContactService) ac.getBean("syncContactService");
		//		sync.process(1);
		init();

		System.out.println("start init");

		//		main.startService();
		EventQueue.invokeLater(this::launchFrame);
	}

	private void launchFrame() {

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

				getMain().setLocation(dx, dy);
			}
		});

		int frameWidth = SharedInfo.WIDTH;
		int frameHeight = SharedInfo.HEIGHT;
		this.setSize(frameWidth, frameHeight);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setUndecorated(true);

		//�����
		JPanel mainPanel = new MyPanel(images[0], frameWidth, frameHeight);
		mainPanel.setLayout(null);

		//��С����ť
		final JLabel min = new JLabel(imageIcons[0]);
		min.setBounds(frameWidth * 18 / 20, frameHeight / 40, frameWidth / 50, frameHeight / 40);
		min.setToolTipText("最小化");

		//�رհ�ť
		final JLabel close = new JLabel(imageIcons[2]);
		close.setBounds(frameWidth * 19 / 20, frameHeight / 40, frameWidth / 50, frameHeight / 40);
		close.setToolTipText("关闭");

		//����
		navy = new JLabel(imageIcons[6]);
		navy.setBounds(0, frameHeight / 25 + 55, 128, 44);

		//½��
		army = new JLabel(imageIcons[7]);
		army.setBounds(0, frameHeight / 25 + 55 * 2, 128, 44);

		//�վ�
		airForce = new JLabel(imageIcons[10]);
		airForce.setBounds(0, frameHeight / 25 + 55 * 3, 128, 44);

		//����
		setting = new JLabel(imageIcons[13]);
		setting.setBounds(0, frameHeight / 25 + 55 * 4, 128, 44);

		//*******************************�������****************************
		card = new CardLayout();
		centerPanel = new JPanel(card);
		centerPanel.setBounds(128,  frameHeight / 9, frameWidth - 129, frameHeight * 8 / 9);
		//����
		centerPanel.add("navyInfoPanel", navyInfoPanel);
		navyInfoPanel.updateItem();
		card.show(centerPanel, "navyInfoPanel");
		//½��
		centerPanel.add("armyInfoPanel", armyInfoPanel);
		armyInfoPanel.updateItem();

		//�վ�
		centerPanel.add("airForceInfoPanel", airForceInfoPanel);
		airForceInfoPanel.updateItem();
		//******************************����������*************************

		mainPanel.add(min);
		mainPanel.add(close);

		mainPanel.add(navy);
		mainPanel.add(army);
		mainPanel.add(airForce);
		mainPanel.add(setting);
		mainPanel.add(centerPanel);

		this.getContentPane().add(mainPanel);
		this.setVisible(true);

		class MyListener extends MouseAdapter {


			@Override
			public void mouseClicked(MouseEvent e) {

				if(e.getSource() == navy || e.getSource() == army || e.getSource() == airForce) {
					navy.setIcon(imageIcons[4]);
					army.setIcon(imageIcons[7]);
					airForce.setIcon(imageIcons[10]);
					setting.setIcon(imageIcons[13]);

					isNavyClicked = false;
					isArmyClicked = false;
					isAirForceClicked = false;
					isSettingClicked = false;

					if(e.getSource() == navy && !isNavyClicked) {
						navy.setIcon(imageIcons[6]);
						//						recved MailBoxUI.showMailItemUI();
						card.show(centerPanel, "navyInfoPanel");
						isNavyClicked = true;
					} else if(e.getSource() == army && !isArmyClicked) {
						army.setIcon(imageIcons[9]);
						//						writeMailUI.setTheme("");
						//						writeMailUI.setMailContent("");
						card.show(centerPanel, "armyInfoPanel");
						isArmyClicked = true;
					} else if(e.getSource() == airForce && !isAirForceClicked){
						airForce.setIcon(imageIcons[12]);
						//						friendUI.showDisplayFriendUI();
						card.show(centerPanel, "airForceInfoPanel");
						isAirForceClicked = true;
					} else if(e.getSource() == setting && !isSettingClicked) {
						setting.setIcon(imageIcons[15]);
						//						card.show(centerPanel, "settingUI");
						isSettingClicked = true;
					}
				} else if(e.getSource() == close) {
					System.exit(0);
				} else if(e.getSource() == min) {
					setExtendedState(JFrame.ICONIFIED);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if(e.getSource() == min) {
					min.setIcon(imageIcons[1]);
				} else if(e.getSource() == close) {
					close.setIcon(imageIcons[3]);
				} else if(e.getSource() == navy && !isNavyClicked) {
					navy.setIcon(imageIcons[5]);
				} else if(e.getSource() == army && !isArmyClicked) {
					army.setIcon(imageIcons[8]);
				} else if(e.getSource() == airForce && !isAirForceClicked){
					airForce.setIcon(imageIcons[11]);
				} else if(e.getSource() == setting && !isSettingClicked) {
					setting.setIcon(imageIcons[14]);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if(e.getSource() == min) {
					min.setIcon(imageIcons[0]);
				} else if(e.getSource() == close) {
					close.setIcon(imageIcons[2]);
				} else if(e.getSource() == navy && !isNavyClicked) {
					navy.setIcon(imageIcons[4]);
				} else if(e.getSource() == army && !isArmyClicked) {
					army.setIcon(imageIcons[7]);
				} else if(e.getSource() == airForce && !isAirForceClicked){
					airForce.setIcon(imageIcons[10]);
				} else if(e.getSource() == setting && !isSettingClicked) {
					setting.setIcon(imageIcons[13]);
				}
			}
		}

		close.addMouseListener(new MyListener());
		min.addMouseListener(new MyListener());
		navy.addMouseListener(new MyListener());
		army.addMouseListener(new MyListener());
		airForce.addMouseListener(new MyListener());
		setting.addMouseListener(new MyListener());

	}

	private void init() {
//		List<User> usrs = mySQLService.queryUsr("select * from user");

		List<User> users = userMapper.getAllUsers();

		for(User user : users) {
//			SharedInfo.map.put(user.getId(), user);
			redisClusterUtils.setObject("" + user.getId(), user);
			redisClusterUtils.setObject(user.getName(), user);
		}

		loadDepartment("海军");
		loadDepartment("陆军");
		loadDepartment("空军");
	}

	private void loadDepartment(String department) {
//		List<Department> departments = mySQLService.queryDepartment("select * from department where parent_id in (select id from department where name = ?)", department);
		List<Department> departments = departmentMapper.getDepartmentByFatherName(department);

		List<DepartmentItem> list = null;
		if(department.equals("海军"))
			list = SharedInfo.NAVY_DEPARTMENT;
		else if(department.equals("陆军"))
			list = SharedInfo.ARMY_DEPARTMENT;
		else
			list = SharedInfo.AIR_FORCE_DEPARTMENT;
		for(Department dep : departments) {
			list.add(new DepartmentItem(dep));
		}
	}

	public MainUI getMain() {
		return this;
	}

	public NavyInfoPanel getNavyInfoPanel() {
		return navyInfoPanel;
	}

	public void setNavyInfoPanel(NavyInfoPanel navyInfoPanel) {
		this.navyInfoPanel = navyInfoPanel;
	}

	public ArmyInfoPanel getArmyInfoPanel() {
		return armyInfoPanel;
	}

	public void setArmyInfoPanel(ArmyInfoPanel armyInfoPanel) {
		this.armyInfoPanel = armyInfoPanel;
	}

	public AirForceInfoPanel getAirForceInfoPanel() {
		return airForceInfoPanel;
	}

	public void setAirForceInfoPanel(AirForceInfoPanel airForceInfoPanel) {
		this.airForceInfoPanel = airForceInfoPanel;
	}

}
