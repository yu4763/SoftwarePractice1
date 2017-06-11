package client;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class WritingPanel extends JPanel{

	ImageIcon daram;

	JLabel l;
	Label statusLabel;
	JPanel tablePanel;
	Calendar now;

	private JTextField tyear; 
	private JTextField tmonth;
	private JTextField tday;
	private JTextField tmemo; 
	private JTextField tprice;
	private JTextField tdelete;
	private JComboBox<String> category;

	private JRadioButton in;
	private JRadioButton out;
	private JRadioButton cash;
	private JRadioButton card;
	private JRadioButton no1;
	private JRadioButton no2;
	

	WritingPanel(String[][] tmp, int count){


		setLayout(null);

		ImageIcon oldIcon = new ImageIcon("./resources/darami.jpg");
		Image oldImage = oldIcon.getImage();
		Image newImage = oldImage.getScaledInstance(1700,1000,java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newImage);		
		JLabel l = new JLabel(newIcon);
		l.setSize(1700,1000);
		l.setLocation(0,0);

		now = Calendar.getInstance();

		JLabel date_lb = new JLabel();
		JLabel year = new JLabel();
		JLabel month = new JLabel();
		JLabel day = new JLabel();
		JLabel memo = new JLabel();
		JLabel price = new JLabel();
		JLabel cate = new JLabel();
		JLabel inout = new JLabel();
		JLabel cashcard = new JLabel();

		tyear = new JTextField(Integer.toString(now.get(Calendar.YEAR)));
		tmonth = new JTextField(Integer.toString(now.get(Calendar.MONTH)+1));
		tday = new JTextField(Integer.toString(now.get(Calendar.DAY_OF_MONTH)));
		tmemo = new JTextField(20);
		tprice = new JTextField(10);
		tdelete = new JTextField(4);

		tmemo.setText(null);
		tprice.setText(null);


		Font titlef = new Font("서울남산체 B", Font.PLAIN, 30);
		Font contentf = new Font("서울남산체 L", Font.PLAIN, 25);
		Font tabletif = new Font("서울남산체 B", Font.PLAIN, 17);
		Font tablecof = new Font("서울남산체 L", Font.PLAIN, 20);

		date_lb.setText("날      짜 : ");
		year.setText("년");
		month.setText("월");
		day.setText("일");
		memo.setText("메      모 : ");
		price.setText("금액 : ");
		cate.setText("카테고리 : ");
		inout.setText("수입/지출 : ");
		cashcard.setText("현금/카드 : ");


		/* 홈 버튼 */
		JButton home = new JButton("홈");
		home.setSize(80,80);
		home.setLocation(100,80);
		home.setBackground(new Color(236,230,204));
		home.setFont(titlef);
		l.add(home);

		home.addActionListener(new ChangePanel());
		/* 홈버튼 완료 */



		/* 여기 부터 날짜 입력 창 띄우기 */
		date_lb.setSize(160, 50);
		date_lb.setLocation(100, 200);
		date_lb.setFont(titlef);
		l.add(date_lb);

		tyear.setSize(130,50);
		tyear.setLocation(260, 200);
		tyear.setFont(contentf);
		tyear.setHorizontalAlignment(JTextField.CENTER);
		l.add(tyear);

		year.setSize(50, 50);
		year.setLocation(395, 200);
		year.setFont(contentf);
		l.add(year);

		tmonth.setSize(90,50);
		tmonth.setLocation(445, 200);
		tmonth.setFont(contentf);
		tmonth.setHorizontalAlignment(JTextField.CENTER);
		l.add(tmonth);

		month.setSize(50, 50);
		month.setLocation(535, 200);
		month.setFont(contentf);
		l.add(month);

		tday.setSize(90,50);
		tday.setLocation(590, 200);
		tday.setFont(contentf);
		tday.setHorizontalAlignment(JTextField.CENTER);
		l.add(tday);

		day.setSize(90, 50);
		day.setLocation(685, 200);
		day.setFont(contentf);
		l.add(day);
		/* 날짜 입력 끝 */




		/* 카테고리 입력 띄우기 */
		cate.setSize(160,50);
		cate.setLocation(100,300);
		cate.setFont(titlef);
		l.add(cate);

		String[] types = {"식비", "교통비", "문화생활비", "학비", "저축", "용돈","월급"};
		category = new JComboBox<String>(types);
		category.setEditable(false);
		category.setFont(contentf);
		category.setSize(200, 50);
		category.setLocation(260, 300);
		l.add(category);
		/* 카테고리 입력 끝 */



	
		 
		/* 수입 or 지출 입력 띄우기 */
		inout.setSize(170,50);
		inout.setLocation(580,300);
		inout.setFont(titlef);
		l.add(inout);


		in = new JRadioButton("수입");
		out = new JRadioButton("지출");
		in.setOpaque(false);
		out.setOpaque(false);
		in.setFont(contentf);
		out.setFont(contentf);
		no1 = new JRadioButton();
		

		ButtonGroup group1 = new ButtonGroup();
		group1.add(in);
		group1.add(out);
		group1.add(no1);


		JPanel controlPanel1 = new JPanel();
		controlPanel1.setFont(titlef);
		controlPanel1.setSize(500,300);
		controlPanel1.setLocation(570,300);
		controlPanel1.setOpaque(false);
		controlPanel1.add(in);
		controlPanel1.add(out);

		l.add(controlPanel1);
		/* 수입 or 지출 입력 완료 */





		/* 현금 or 카드 입력 띄우기  */
		cashcard.setSize(170,50);
		cashcard.setLocation(1020,300);
		cashcard.setFont(titlef);
		l.add(cashcard);

		cash = new JRadioButton("현금");
		card = new JRadioButton("카드");
		cash.setOpaque(false);
		card.setOpaque(false);
		cash.setFont(contentf);
		card.setFont(contentf);
		no2 = new JRadioButton();

		ButtonGroup group2 = new ButtonGroup();
		group2.add(cash);
		group2.add(card);
		group2.add(no2);


		JPanel controlPanel2 = new JPanel();
		controlPanel2.setFont(titlef);
		controlPanel2.setSize(500,300);
		controlPanel2.setLocation(1025,300);
		controlPanel2.setOpaque(false);
		controlPanel2.add(cash);
		controlPanel2.add(card);

		l.add(controlPanel2);
		/* 현금 or 카드 입력 완료 */




		/* 메모 입력 띄우기 */
		memo.setSize(160,50);
		memo.setLocation(100,400);
		memo.setFont(titlef);
		l.add(memo);

		tmemo.setSize(660,50);
		tmemo.setLocation(260,400);
		tmemo.setFont(contentf);
		tmemo.setHorizontalAlignment(JTextField.LEFT);
		l.add(tmemo);
		/* 메모 입력 완료 */



		/* 가격 입력 띄우기 */
		price.setSize(150,50);
		price.setLocation(1020,400);
		price.setFont(titlef);
		l.add(price);

		tprice.setSize(150,50);
		tprice.setLocation(1130,400);
		tprice.setFont(contentf);
		tprice.setHorizontalAlignment(JTextField.CENTER);
		l.add(tprice);
		/* 가격 입력 완료 */


		/* 표  출력 */
		String[][] data = new String[2000][7];
		String[] title = new String[7];
		title[0] = "번호";
		title[1] = "날짜";
		title[2] = "카테고리";
		title[3] = "수입/지출";
		title[4] = "카드/현금";
		title[5] = "메모";
		title[6] = "금액";
				
		int i;
		int k;
		
		for(i=0; i<count; i++){
			for( k=0; k<9; k++){
				if(k==0)
					data[i][k] = tmp[i][k];
				else if(k==1)
					data[i][1] = tmp[i][k];
				else if(k==2 || k==3){
					if(tmp[i][k] != null)
						data[i][1] +=   "/ " + tmp[i][k];
				}
				else
					data[i][k-2] = tmp[i][k];
			}
			
		}
				
		DefaultTableModel model = new DefaultTableModel(data,title);
		JTable table = new JTable(model);
		table.getTableHeader().setFont(tabletif);
		table.setFont(tablecof);
		table.setRowHeight(35); 
		JScrollPane scroll = new JScrollPane(table);
		scroll.setSize(1250,430);
		scroll.setLocation(100,510);
		l.add(scroll);
		/* 표출력 끝 */
		
		



		/* 입력, 삭제 버튼 띄우기 */
		JButton add = new JButton("입력");
		JButton delete = new JButton("삭제");

		add.setSize(100,50);
		add.setLocation(1320,400);
		add.setFont(titlef);
		add.addActionListener(new EnterInformation( ));
		l.add(add);

		delete.setSize(100,50);
		delete.setLocation(1450,400);
		delete.setFont(titlef);
		delete.addActionListener(new EnterInformation( ));
		l.add(delete);

		tdelete.setSize(100,50);
		tdelete.setLocation(1550,400);
		tdelete.setFont(titlef);
		tdelete.setHorizontalAlignment(JTextField.CENTER);
		l.add(tdelete);
		/* 입력 삭제 버튼 완료 */

		
		
	
		
	
		add(l);
		setSize(1700,1000);
		

	}

	String getyear(){
		
		String year = tyear.getText();
		tyear.setText(Integer.toString(now.get(Calendar.YEAR)));
		return year;
	
	}

	String getmonth(){

		String month = tmonth.getText();
		tmonth.setText(Integer.toString(now.get(Calendar.MONTH)+1));
		return month;
	
	}

	String getday(){

		String day = tday.getText();
		tday.setText(Integer.toString(now.get(Calendar.DAY_OF_MONTH)));
		return day;
	
	}

	String getmemo(){

		String memo = tmemo.getText();
		tmemo.setText("");
		return memo;
	
	}

	String getprice(){

		String price = tprice.getText();
		tprice.setText("");
		return price;
	
	}

	String getCategory(){
		
		String c = category.getSelectedItem().toString();
		category.setSelectedIndex(0);
		return c;
		
	}

	String getInout(){

		if(in.isSelected()){
			no1.setSelected(true);
			return "수입";
		}

		else if(out.isSelected()){
			no1.setSelected(true);
			return "지출";
		}

		else
			return null;

	}

	String getIscash(){

		if(cash.isSelected()){
			no2.setSelected(true);
			return "현금";
		}

		else if(card.isSelected()){
			no2.setSelected(true);
			return "카드";
		}

		else
			return null;

	}
	
	String getdelete(){

		String del = tdelete.getText();
		tdelete.setText("");
		return del;
	
	}

	
	
}