package UI;

import javax.swing.*;
import java.awt.*;

/**
 * �����࣬ �̳���JPanel������ֱ�Ӱ����˱����ʹ�С��ֻ�贫��������ɣ�����������
 * @author Administrator
 *
 */
public class MyPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private int width, height;
	Image image;
	

	public MyPanel(Image image, int width, int height) {
		this.width = width;
		this.height = height;
		this.image = image;
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, width, height, null);
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
}
