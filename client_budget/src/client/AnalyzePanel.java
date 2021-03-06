package client;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 * 카테고리별로 수입, 지출 패턴을 분석해주는 class
 * @author team 6
 * 
 */
public class AnalyzePanel extends JPanel implements ActionListener{
	
	private JLabel l;
	
	private ChartPanel graph = new ChartPanel(); //지출
	private ChartPanel2 graph2 = new ChartPanel2(); //수입

	private Calendar cal;
	private Calendar today;
	private JPanel pmonth;
	private JPanel color;
	private JPanel color2;
	private JButton before;
	private JButton after;
	private JTextField TcurrentYear;
	private JTextField TcurrentMonth;
	private int currentYear;
	private int currentMonth;

	private JTextArea T1,T2,T3,T4,T5,Texpense;
	private JTextArea T12,T22,Tearning;
	
	Font titlef = new Font("서울남산체 B", Font.PLAIN, 30);
	
	private String[][] dataCSV = new String[100][9];
	private int cnt;
	private int[] spentMoney = new int[5]; //카테고리별 지출 돈
	private int[] earnedMoney = new int[2];
	private int[] arcAngle = new int[5];
	private int[] arcAngle2 = new int[2];
	
	Color[] colors = {new Color(185,24,35),new Color(231,146,20),new Color(138,186,43),new Color(50,175,219),new Color(135,17,126)};

	private String[] cate = {"식비","교통비","문화생활비","학비","저축"};
	private String[] cate2 = {"용돈","월급"};
	private int sum;
	private int sum2;
	
	/**
	 * 수입, 지출 원형 그래프가 올라갈 기본 바탕과 홈으로 가는 버튼, 뒤로 가는 버튼, 지금의 연도와 달이 써있는 텍스트필드와 달을 앞 뒤로 이동하는 버튼, 수입, 지출 원형 그래프 생성
	 * @param data 서버의 csv파일에서 유저의 가계부 기록 정보 저장
	 * @param cnt 유저의 가계부 기록의 인덱스 개수 저장
	 */
	AnalyzePanel(String[][] data, int cnt){
		this.dataCSV = data;
		this.cnt = cnt;
		setLayout(null);
		
		ImageIcon oldIcon = new ImageIcon("./resources/darami.jpg");
		Image oldImage = oldIcon.getImage();
		Image newImage = oldImage.getScaledInstance(1700,1000,java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newImage);		
		l = new JLabel(newIcon);
		l.setSize(1700,1000);
		l.setLocation(0,0);
		
		
		/* 홈 버튼 */
		JButton home = new JButton("홈");
		home.setSize(80,80);
		home.setLocation(100,80);
		home.setBackground(new Color(236,230,204));
		home.setFont(titlef);
		l.add(home);
		
		home.addActionListener(new ChangePanel());
		/* 홈버튼 완료 */
		
		
		/* 뒤로 버튼 */
		JButton back = new JButton("뒤로");
		back.setSize(130,80);
		back.setLocation(250,80);
		back.setBackground(new Color(236,230,204));
		back.setFont(titlef);
		l.add(back);
		
		back.addActionListener(new ChangePanel());
		/* 뒤로 버튼 완료 */
		
		
		
		/* 원형 그래프 연,달 표시 */
		pmonth = new JPanel();
		pmonth.setLocation(500,180);
		pmonth.setSize(650,100);
		pmonth.setOpaque(false);

		today = Calendar.getInstance();
		cal = new GregorianCalendar();
		currentYear = today.get(Calendar.YEAR);
		currentMonth = today.get(Calendar.MONTH)+1;
		
		before = new JButton("before");
		after = new JButton("after");
		TcurrentYear = new JTextField(currentYear+"년");
		TcurrentMonth = new JTextField(currentMonth+"월");
		before.setFont(titlef);
		after.setFont(titlef);
		TcurrentYear.setFont(titlef);
		TcurrentMonth.setFont(titlef);
		before.setBackground(new Color(255,255,255));
		before.setOpaque(false);
		before.setBorderPainted(false);
		after.setBackground(new Color(255,255,255));
		after.setOpaque(false);
		after.setBorderPainted(false);
		
		pmonth.add(before);
		pmonth.add(TcurrentYear);
		pmonth.add(TcurrentMonth);
		pmonth.add(after);
		TcurrentYear.setEnabled(false);
		TcurrentMonth.setEnabled(false);
		
		l.add(pmonth);
		
		/* 연, 달 완료 */
		


		/* 원형 그래프 그림, 글*/
		T1 = new JTextArea();
		T2 = new JTextArea();
		T3 = new JTextArea();
		T4 = new JTextArea();
		T5 = new JTextArea();
		Texpense = new JTextArea();
		
		T12 = new JTextArea();
		T22 = new JTextArea();
		Tearning = new JTextArea();
				
		before.addActionListener(this);
		after.addActionListener(this);
		
		graph.setSize(700,700);
		graph.setLocation(20,330);
		graph.setOpaque(false);
		
		graph2.setSize(700,700);
		graph2.setLocation(850,330);
		graph2.setOpaque(false);
		
		buttonSet();
		drawChart();
		buttonSet2();
		drawChart2();
		
		l.add(graph);
		l.add(graph2);
		
		/* 원형 그래프 완료 그림, 글*/
		
		add(l);
		setSize(1700,1000);
	}
	
	/**
	 * 각 카테고리별 지출 금액을 다 더해 그 값이 0이 아니면 각 카테고리의 지출 금액 별로 원형 그래프서 차지할 각도를 구한다.
	 */
	void drawChart(){
		sum = 0;
		for(int i=0;i<5;i++) sum+=spentMoney[i];
		if(sum!=0){
			for(int i=0;i<5;i++) arcAngle[i] = (int)Math.round((double)spentMoney[i]/(double)sum*360);
		}
		repaint();
	}
	
	/**
	 * 각 카테고리별 수입 금액을 다 더해 그 값이 0이 아니면 각 카테고리의 수입 금액 별로 원형 그래프서 차지할 각도를 구한다.
	 */
	void drawChart2(){
		sum2 = 0;
		for(int i=0;i<2;i++) sum2+=earnedMoney[i];
		if(sum2!=0){
			for(int i=0;i<2;i++) arcAngle2[i] = (int)Math.round((double)earnedMoney[i]/(double)sum2*360);
		}
		repaint();
	}
	
	/**
	 * 각 카테고리별 지출 금액을 퍼센트로 환산해서 보여주고 지출 원형 그래프를 그리고 색칠한다.
	 * @author team 6
	 *
	 */
	class ChartPanel extends JPanel{
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			if(sum!=0){
				int angle=0;
				for(int i=0;i<5;i++){
					g.setColor(colors[i]);
					g.drawString(cate[i]+" "+Math.round(arcAngle[i]*100/360)+"%", 50+i*100,20);
				}
				for(int i=0;i<5;i++){
					g.setColor(colors[i]);
					g.fillArc(50,50,500,500,angle,arcAngle[i]);
					angle += arcAngle[i];
				}
			}
			else{
				for(int i=0;i<5;i++){
					g.setColor(colors[i]);
					g.drawString(cate[i]+" 0%", 50+i*100,20);
				}
				g.drawOval(50,50,500,500);
			}
		}
	}
	
	/**
	 * 각 카테고리별 수입 금액을 퍼센트로 환산해서 보여주고 수입 원형 그래프를 그리고 색칠한다.
	 * @author team 6
	 *
	 */
	class ChartPanel2 extends JPanel{
		public void paintComponent(Graphics g2){
			super.paintComponent(g2);
			if(sum2!=0){
				int angle = 0;
				for(int i=0;i<2;i++){
					g2.setColor(colors[i]);
					g2.drawString(cate2[i]+" "+Math.round(arcAngle2[i]*100/360)+"%",50+i*100,20);
				}
				for(int i=0;i<2;i++){
					g2.setColor(colors[i]);
					g2.fillArc(50, 50, 500, 500, angle, arcAngle2[i]);
					angle += arcAngle2[i];
				}
			}
			else{
				for(int i=0;i<2;i++){
					g2.setColor(colors[i]);
					g2.drawString(cate2[i]+" 0%",50+i*100,20);
				}
				g2.drawOval(50,50,500,500);
			}
		}
	}
	
	/**
	 * 지금의 달에 1을 더하거나 빼서 달을 이동하고 1보다 작아지면 연도를 빼고 12보다 커지면 연도를 더한다.
	 * @param d 달에 더할 값
	 */
	private void calInput(int d){
		currentMonth += d;
		if(currentMonth<=0){
			currentMonth = 12;
			currentYear -= 1;
		}
		else if(currentMonth>12){
			currentMonth = 1;
			currentYear += 1;
		}
	}
	
	/**
	 * 지출 원형 그래프를 그릴때 지금의 달의 각 카테고리별 지출 금액과 총 지출액을 구하고 그 내역을 보여준다. 
	 */
	private void buttonSet(){
		
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		cal.set(Calendar.YEAR,currentYear);
		cal.set(Calendar.MONTH,currentMonth-1);
		
		color = new JPanel();
		color.setLocation(600,370);
		color.setSize(350,600);
		color.setOpaque(false);
		
		for(int i=0;i<5;i++) spentMoney[i] = 0;

		int dataCSVIntCost,dataCSVIntYear,dataCSVIntMonth;
		for(int i=0;i<cnt;i++){
			dataCSVIntYear = Integer.parseInt(dataCSV[i][1]);
			dataCSVIntMonth = Integer.parseInt(dataCSV[i][2]);
			if(dataCSVIntYear==currentYear && dataCSVIntMonth==currentMonth){
				dataCSVIntCost = Integer.parseInt(dataCSV[i][8]);
				
				if(dataCSV[i][5].equals("지출")){
					if(dataCSV[i][4].equals("식비")){
						spentMoney[0] += dataCSVIntCost;
					}
					else if(dataCSV[i][4].equals("교통비")){
						spentMoney[1] += dataCSVIntCost;
					}
					else if(dataCSV[i][4].equals("문화생활비")){
						spentMoney[2] += dataCSVIntCost;
					}
					else if(dataCSV[i][4].equals("학비")){
						spentMoney[3] += dataCSVIntCost;
					}
					else if(dataCSV[i][4].equals("저축")){
						spentMoney[4] += dataCSVIntCost;
					}
					
				}
			}
		}	
		
		/* 총 지출 */
		int sumExpense = 0;
		for(int i=0;i<5;i++){
			sumExpense += spentMoney[i];
		}
		
		T1.setText("식비 : "+spentMoney[0]+"원");
		T2.setText("교통비 : "+spentMoney[1]+"원");
		T3.setText("문화생활비 : "+spentMoney[2]+"원");
		T4.setText("학비 : "+spentMoney[3]+"원");
		T5.setText("저축 : "+spentMoney[4]+"원");
		Texpense.setText("총 지출 : "+sumExpense+"원");
		
		T1.setForeground(new Color(185,24,35));
		T2.setForeground(new Color(231,146,20));
		T3.setForeground(new Color(138,186,43));
		T4.setForeground(new Color(50,175,219));
		T5.setForeground(new Color(135,17,126));
		
		T1.setFont(titlef);
		T1.setBackground(new Color(255,255,255));
		T1.setOpaque(false);
		T2.setFont(titlef);
		T2.setBackground(new Color(255,255,255));
		T2.setOpaque(false);
		T3.setFont(titlef);
		T3.setBackground(new Color(255,255,255));
		T3.setOpaque(false);
		T4.setFont(titlef);
		T4.setBackground(new Color(255,255,255));
		T4.setOpaque(false);
		T5.setFont(titlef);
		T5.setBackground(new Color(255,255,255));
		T5.setOpaque(false);
		Texpense.setFont(titlef);
		Texpense.setBackground(new Color(255,255,255));
		Texpense.setOpaque(false);
	
		color.add(T1);
		color.add(T2);
		color.add(T3);
		color.add(T4);
		color.add(T5);
		color.add(Texpense);
		
		color.setLayout(new GridLayout(7,1));
		
		l.add(color);
	}
	
	/**
	 * 수입 원형 그래프를 그릴때 지금의 달의 각 카테고리별 수입 금액과 총 수입액을 구하고 그 내역을 보여준다. 
	 */
	private void buttonSet2(){
		
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		cal.set(Calendar.YEAR,currentYear);
		cal.set(Calendar.MONTH,currentMonth-1);
		
		color2 = new JPanel();
		color2.setLocation(1430,450);
		color2.setSize(350,600);
		color2.setOpaque(false);
		
		for(int i=0;i<2;i++) earnedMoney[i] = 0;

		int dataCSVIntCost,dataCSVIntYear,dataCSVIntMonth;
		for(int i=0;i<cnt;i++){
			dataCSVIntYear = Integer.parseInt(dataCSV[i][1]);
			dataCSVIntMonth = Integer.parseInt(dataCSV[i][2]);
			if(dataCSVIntYear==currentYear && dataCSVIntMonth==currentMonth){
				dataCSVIntCost = Integer.parseInt(dataCSV[i][8]);
				
				if(dataCSV[i][5].equals("수입")){
					if(dataCSV[i][4].equals("용돈")){
						earnedMoney[0] += dataCSVIntCost;
					}
					else if(dataCSV[i][4].equals("월급")){
						earnedMoney[1] += dataCSVIntCost;
					}
				}
			}
		}	
		
		
		/* 총 수입 */
		int sumEarning = 0;
		for(int i=0;i<2;i++){
			sumEarning += earnedMoney[i];
		}
		
		T12.setText("용돈 : "+earnedMoney[0]+"원");
		T22.setText("월급 : "+earnedMoney[1]+"원");
		Tearning.setText("총 수입 : "+sumEarning+"원");
		
		T12.setForeground(new Color(185,24,35));
		T22.setForeground(new Color(231,146,20));
		
		T12.setFont(titlef);
		T12.setBackground(new Color(255,255,255));
		T12.setOpaque(false);
		T22.setFont(titlef);
		T22.setBackground(new Color(255,255,255));
		T22.setOpaque(false);
		Tearning.setFont(titlef);
		Tearning.setBackground(new Color(255,255,255));
		Tearning.setOpaque(false);
	
		color2.add(T12);
		color2.add(T22);
		color2.add(Tearning);
		
		color2.setLayout(new GridLayout(7,1));
		
		l.add(color2);
	}

	/**
	 * 달을 앞, 뒤로 이동하는 버튼을 눌렀을 때 할 명령
	 */
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==before){
			calInput(-1);
			buttonSet();
			drawChart();
			buttonSet2();
			drawChart2();
			this.TcurrentYear.setText(currentYear+"년");
			this.TcurrentMonth.setText(currentMonth+"월");
		}
		else if(arg0.getSource()==after){
			calInput(1);
			buttonSet();
			drawChart();
			buttonSet2();
			drawChart2();
			this.TcurrentYear.setText(currentYear+"년");
			this.TcurrentMonth.setText(currentMonth+"월");
		}		
	}
}