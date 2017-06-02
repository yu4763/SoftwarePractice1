package front;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class FrontPage extends JFrame{
	
	ImageIcon daram;
	
	FrontPage(){
		
		setTitle("�ٶ��� ����� ���α׷�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		daram = new ImageIcon("./resources/darami.jpg");
		
		Container plate = getContentPane();
		plate.setLayout(new BorderLayout(1700,1000));
	
		ImageIcon oldIcon = new ImageIcon("./resources/darami.jpg");
		Image oldImage = oldIcon.getImage();
		Image newImage = oldImage.getScaledInstance(1700,1000,java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newImage);
		JLabel l = new JLabel(newIcon);
		l.setSize(1700,1000);
		l.setLocation(0,0);
		
		Font font1 = new Font("MD��Ʈü", Font.BOLD, 100);
		Font font2 = new Font("MD��Ʈü", Font.PLAIN, 60);
		
		JLabel title = new JLabel("�ٶ��� �����");
		title.setFont(font1);
		title.setSize(800, 300);
		title.setLocation(520, 180);
		
		JButton b1 = new JButton("����� �ۼ�");
		JButton b2 = new JButton("����� �м�");
		
		b1.setSize(400, 80);
		b1.setLocation(280, 580);
		b2.setSize(400, 80);
		b2.setLocation(280, 680);
		
		b1.setBorderPainted(false);
		b2.setBorderPainted(false);
		b1.setBackground(new Color(255,233,157));
		b2.setBackground(new Color(255,233,157));
		b1.setFocusPainted(false);
		b2.setFocusPainted(false);
		
		b1.setFont(font2);
		b2.setFont(font2);
		
		l.add(title);
		l.add(b1);
		l.add(b2);
		plate.add(l);
		
		setVisible(true);
		setSize(1700,1000);
		
		
		
	}
	
	

}