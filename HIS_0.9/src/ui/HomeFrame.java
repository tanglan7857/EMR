package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import clock.MyClock;
import util.JDBCUtil;
import util.MyButton;
import util.MyTable;

// #clock
public class HomeFrame extends JFrame {
	private static final int CLOCK_RADIUS = 250;
	JPanel northPanel ;
	MyButton m1;
	MyButton m2;
	MyButton m23;
	MyButton m3;
	MyButton m4;
	MyButton m45;
	MyButton m5;
	JPanel centerPanel;
	JPanel southPanel;
	JPanel westPanel;
	JTextField bed_textField = new JTextField();
	JTextField hspID_textField = new JTextField();
	JTextField name_textField = new JTextField();
	static JTable table;
	String[] condition = {"全部","出院","在院","归档"};
	JComboBox<String> condition_comboBox = new JComboBox<String>(condition);
	public static JPanel tablePanel = new JPanel();
	JButton medButton = new JButton("添加病历");
	
	public  HomeFrame() {
		this.setSize(2000, 2000);
		this.setLocationRelativeTo(this.getOwner());//设置位置为屏幕中央
		this.setResizable(true);
		this.setTitle("Home");
		this.getContentPane().setBackground(new Color(222, 241, 255)); 												
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		homeLayout();
	}
	/**
	 * 设置整个主页面的布局管理器，分为北西中南四部分
	 */
	public void homeLayout() {
		northPanel = new JPanel();
		centerPanel = new JPanel();
		southPanel = new JPanel();
		westPanel = new JPanel();
		
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		this.add(westPanel, BorderLayout.WEST);
		
		setLayout_setmenu();
		setLayout_setContent( );
		setLayout_setBottom();
		setLayout_setWest();
	}
	
	/**
	 * 设置北边的菜单栏
	 */
	public void setLayout_setmenu() {
		northPanel.setPreferredSize(new Dimension(100, 130));
		northPanel.setLayout(null);
		northPanel.setBackground(new Color(216, 246, 255));
		
	    m1 = new MyButton(new ImageIcon("img/1.jpg"), new ImageIcon("img/1_2.jpg"),100, 80);
	    JPanel j1 = getMyPanel(m1, " 病历管理");
	    j1.setBounds(300, 10, 120, 100);
		northPanel.add(j1); 
		m1.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				String hspID = null;
				if(selectedRow != -1) {
					hspID= (String) table.getValueAt(selectedRow, 0);
					new Med(hspID);		
				}			}
		});
		
		m2 = new MyButton(new ImageIcon("img/2.jpg"), new ImageIcon("img/2_2.jpg"),100, 80);
		 JPanel j2 = getMyPanel(m2, " 添加病人");
		 j2.setBounds(500, 10, 120, 100);
		 northPanel.add(j2); 
		 m2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new PatientFrame().openFrame();					
				}
			});
		
		m23 = new MyButton(new ImageIcon("img/删除1.jpg"), new ImageIcon("img/删除2.jpg"),100, 80);
		JPanel j23 = getMyPanel(m23, "  删除病人");
		j23.setBounds(700, 10, 120, 100);
		northPanel.add(j23);
		m23.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				String hspID = null;
				if(selectedRow != -1) {
					hspID= (String) table.getValueAt(selectedRow, 0);
					String sql = "delete from PatientInfo where hspID = '" + hspID + "'";
					try {
						int isDelete = JOptionPane.showConfirmDialog(null, "确认删除病人？", "提示", JOptionPane.YES_NO_CANCEL_OPTION); 
						//如果这个整数等于JOptionPane.YES_OPTION，则说明你点击的是“确定”按钮，则允许继续操作，否则结束
						if(isDelete == JOptionPane.YES_OPTION){
							JDBCUtil.updateByPreparedStatement(sql, null);
							HomeFrame.tablePanel.removeAll();
							HomeFrame.setContent_north();
							HomeFrame.tablePanel.updateUI();
							HomeFrame.tablePanel.repaint();
						    JOptionPane.showMessageDialog(null, "删除成功");
						}
	
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
		
		m3 = new MyButton(new ImageIcon("img/3.jpg"), new ImageIcon("img/3_2.jpg"),100, 80);
		JPanel j3 = getMyPanel(m3, "  编辑病人");
		 j3.setBounds(900, 10, 120, 100);
		northPanel.add(j3);
		m3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int selectedRow = table.getSelectedRow();
				String hspID = null;
				if(selectedRow != -1) {
					hspID= (String) table.getValueAt(selectedRow, 0);
					new PatientFrame(hspID).openFrame();		
				}
			}
		});
		
		m4 = new MyButton(new ImageIcon("img/出院.jpg"), new ImageIcon("img/出院2.jpg"),100, 80);
		JPanel j4 = getMyPanel(m4, "  病人出院");
		j4.setBounds(1100, 10, 120, 100);
		northPanel.add(j4);
		m4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				String hspID = null;
				if(selectedRow != -1) {
					hspID= (String) table.getValueAt(selectedRow, 0);
					String sql1 = "delete from PatientInfo  where hspID = '" + hspID + "'";
					String sql2 = "delete from MedRecordInfo where hspID = '"+hspID+"'";
					long time = System.currentTimeMillis();
					SimpleDateFormat sdf = new  SimpleDateFormat("yyyy年MM月dd日--HH:mm:ss");
					String currentTime = sdf.format(time);
					
					try {
						String code = JOptionPane.showInputDialog("病人出院", currentTime);
						if(code!=null) {
							JDBCUtil.updateByPreparedStatement(sql1, null);
							HomeFrame.tablePanel.removeAll();
							HomeFrame.setContent_north();
							HomeFrame.tablePanel.updateUI();
							HomeFrame.tablePanel.repaint();
							JDBCUtil.updateByPreparedStatement(sql2, null);
						}	
					} catch (SQLException e1) {
						e1.printStackTrace();
					} 

				}
				
			}
		});
		
		m45 = new MyButton(new ImageIcon("img/转科12.jpg"), new ImageIcon("img/转科2.jpg"),100, 80);
		JPanel j45 =getMyPanel(m45, " 病人转科");
		j45.setBounds(1300, 10, 120, 100);
		northPanel.add(j45);
		
		m5 = new MyButton(new ImageIcon("img/5.jpg"), new ImageIcon("img/5_2.jpg"),100, 80);
		JPanel j5 =getMyPanel(m5, "   退出");
		j5.setBounds(1500, 10, 120, 100);
		northPanel.add(j5);
		m5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
	}
	
	/**
	 * 将中部分为两部分
	 */
	public void setLayout_setContent() {
		JPanel south1 = new JPanel();
		centerPanel.setLayout(new BorderLayout());//JPanel默认布局管理器是flow
		setContent_north();
		setContent_south(south1);
		
		centerPanel.add(tablePanel, BorderLayout.CENTER);
		centerPanel.add(south1,BorderLayout.NORTH);
	}
	/**
	 * 设置中部的上部分
	 * @param north1
	 */
	public void setContent_south(JPanel north1) {
		north1.setLayout(null);
		north1.setPreferredSize(new Dimension(300, 160));
		north1.setBackground(new Color(117, 218, 231));

		JButton clearButton = new JButton("清空查询");
		JButton advancedqueryButton = new JButton("开始查询");
		advancedqueryButton.setBounds(1030, 60, 90, 40);
		clearButton.setBounds(1150, 60, 90, 40);
		north1.add(advancedqueryButton);
		north1.add(clearButton);
		advancedqueryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				queryByLike();
			}
		});
		
		clearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bed_textField.setText("");
				name_textField.setText("");
				hspID_textField.setText("");
				
			}
		});
		
		north1.add(getMyPanel("床号          ", bed_textField)).setBounds(80, 50, 200, 50);
		north1.add(getMyPanel("入院号   ", hspID_textField)).setBounds(300, 50,200, 50);
		north1.add(getMyPanel("姓名", name_textField)).setBounds(520, 50, 200, 50);
		north1.add(getMyPanel("所属科室   ",condition_comboBox)).setBounds(750, 50, 230, 50);
		condition_comboBox.setBackground(Color.WHITE);
	}
	                                                        //模糊查询
	private void queryByLike() {
		String s1 = bed_textField.getText();
		String s2 = hspID_textField.getText();
		String s3 = name_textField.getText();
		if(s1.equals("")) s1 ="%";
		if(s2.equals("")) s2 ="%";
		if(s3.equals("")) s3 ="%";
		s1 = "%" + s1 + "%";
		s2 = "%" + s2 + "%";
		s3 = "%" + s3 + "%";
		String sql = "select hspID,name,gender,departName,doctorName,bedID,inHspDiagnose from patientinfo where BedID like ? "
				+ "and HspID like ? and Name like ?";
		try {
			List<Map<String, Object>> lists = JDBCUtil.findModeResult(sql, Arrays.asList(s1, s2, s3));
			loadMsg_table(lists);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * 设置中部的下部分
	 * @param south1
	 */
	public static void setContent_north() {
		tablePanel.setLayout(new BorderLayout());
		tablePanel.setBackground(Color.white);
		String sql = "select hspID,name,gender,departName,doctorName,bedID,inHspDiagnose from patientinfo";
		List<Map<String, Object>> lists = null;
		try {
			lists = JDBCUtil.findModeResult(sql, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}   
		loadMsg_table(lists);
	}
	
	public static void loadMsg_table(List<Map<String, Object>> lists) {
		int colSize ;
		if(lists.size() == 0) colSize = 0;
		colSize = lists.get(0).size();
		int rowSize = lists.size();
		String[] names = { "hspid","NAME","gender","departName","doctorName","BEDID","INHSPDIAGNOSE"};
		//表头
		String[] colNames = {"住院号", "姓名", "性别","所属科室","主治","床号", "入院诊断"};
		//表里的数据
		Object[][] obj = new Object[rowSize][colSize]; 
		//表里的数据赋值
		for(int i=0; i<rowSize; i++) {
			Map<String, Object> map = lists.get(i);
			for(int j=0; j<colSize; j++) {
				String s = names[j].toUpperCase();
				obj[i][j] = map.get(s);
			}		
		} 
		table = new MyTable(obj, colNames);  
		JScrollPane scroll = new JScrollPane(table); 
		tablePanel.removeAll();
		tablePanel.add(scroll);  
		tablePanel.updateUI();
	}

	/**
	 * 设置底部信息
	 */
	public void setLayout_setBottom() {
		JLabel l1 = new JLabel("工作人员");
		southPanel.add(l1);
		southPanel.setBackground(new Color(128, 225, 253));
		southPanel.setPreferredSize(new Dimension(50,50));
		
	}
/**
 *  #clock
 * 设置侧面
 */
	public void setLayout_setWest() {
		westPanel.setPreferredSize(new Dimension(300,80));
		westPanel.setBackground(new Color(209, 224, 239));
		westPanel.setLayout(new BorderLayout());
        MyClock clock = new MyClock(0, 0, CLOCK_RADIUS);
        JPanel primary = new JPanel();
        primary.setBackground(new Color(209, 224, 239)); //背景色
        primary.add(clock);
        westPanel.add(primary, BorderLayout.NORTH);	
        setPatientInfo() ;
	}
	
	private void setPatientInfo() {
		JTextPane j = new JTextPane();
		j.setPreferredSize(new Dimension(0, 500));
		j.setBackground(Color.red);
		westPanel.add(j, BorderLayout.SOUTH);	
		
		j.setEditable(false);
		j.setContentType("html/text");
		j.setText("<h1>病人姓名</h1>"
				+ ""
				+ "");
	}

/**
 * 菜单栏button和图片说明	
 * @param j
 * @param s
 * @return
 */
	private JPanel getMyPanel(JButton j, String s) {
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new BorderLayout());
		jpanel.setBackground(new Color(216, 246, 255));
		
		JLabel jlabel = new JLabel(s);
		jlabel.setFont(new Font("宋体",Font.BOLD,20));
		jlabel.setForeground(Color.black);
		
		jpanel.add(j, BorderLayout.CENTER);
		jpanel.add(jlabel,BorderLayout.SOUTH);
		return jpanel;
	}


	/**
	 * 中部Jlabel和输入框
	 * @param name
	 * @param component
	 * @return
	 */
	private JPanel getMyPanel(String name,JComponent component ) {
		JPanel jpanel = new JPanel();
		jpanel.setBackground(new Color(117, 218, 231));
		jpanel.setLayout(new BorderLayout());
		
		JLabel jlabel = new JLabel(name);
		jlabel.setFont(new Font("宋体",Font.BOLD,20));
		
		component.setPreferredSize(new Dimension(140, 50));
		component.setFont(new Font("宋体",Font.BOLD,20));
		
		jpanel.add(jlabel,BorderLayout.WEST);
		jpanel.add(component,BorderLayout.EAST);
		return jpanel;
	}
	

}
