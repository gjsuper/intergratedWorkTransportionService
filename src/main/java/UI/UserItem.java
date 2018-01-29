package UI;

/**
 * ÿһ���ʼ������ĺ���
 */

import ConstField.SharedInfo;
import DataStruct.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class UserItem extends JPanel {
	private static final long serialVersionUID = 1L;

	private int frameWidth, frameHeight;
	private Color gray = SharedInfo.GRAY;
	private Color light_gray = SharedInfo.LIGHT_GRAY;
	private Color mainColor;
	private JCheckBox select;
	private int tempValue;
	private User usr;
	private JLabel person;

	UserItem(User e) {
		this.frameWidth = SharedInfo.WIDTH;
		this.frameHeight = SharedInfo.HEIGHT;
		this.usr = e;

		tempValue = (frameHeight * 8 / 9 - frameHeight / 10) / 9;
		this.setLayout(null);
		
		//ѡ���
		Font font = new Font("΢���ź�", Font.PLAIN, 15);
		select = new JCheckBox();
		select.setBounds(frameWidth / 36, frameHeight / 50, frameWidth / 50, frameHeight / 35);
		select.setOpaque(false);
		select.setFont(font);
		//��������
		JLabel name = new JLabel(e.getName(), JLabel.CENTER);
		name.setBounds(frameWidth / 9, frameHeight / 50, frameWidth / 4, frameHeight / 30);
		name.setFont(font);
		//				theme.setBorder(BorderFactory.createLineBorder(new Color(1), 1));
		//				mailIcon.setBorder(BorderFactory.createLineBorder(new Color(1), 1));
		//id
		person = new JLabel(e.getId() + "", JLabel.CENTER);
		person.setBounds(frameWidth / 6 + frameWidth / 5, frameHeight / 50, frameWidth / 5, frameHeight / 30);
		person.setFont(font);
		//				sender.setBorder(BorderFactory.createLineBorder(new Color(1), 1));
		//��ע
		JLabel remark = new JLabel(e.getArmyName(), JLabel.CENTER);
		remark.setBounds(frameWidth / 12 + frameWidth / 2, frameHeight / 50, frameWidth / 5, frameHeight / 30);
		remark.setFont(font);

		//������
//		this.add(select);
		this.add(name);
		this.add(person);
		this.add(remark);

		this.addMouseListener(new MouseAdapter() {

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

	private UserItem getMailItem() {
		return this;
	}

	public boolean isSelected() {
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
		this.setBounds(3, tempValue * mailIndex , frameWidth - 134, tempValue);
	}

	public String getName() {
		return usr.getName();
	}

	public int getId() {
		return usr.getId();
	}
	
	public String setArmyName() {
		return usr.getArmyName();
	}
}
