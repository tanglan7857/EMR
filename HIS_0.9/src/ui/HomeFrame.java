package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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
import javax.swing.table.DefaultTableModel;

import util.JDBCUtil;
import util.MyButton;
import util.MyTable;

public class HomeFrame extends JFrame {
	JPanel northPanel ;
	MyButton m1;
	MyButton m2;
	MyButton m3;
	MyButton m4;
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
	    JPanel j1 = getMyPanel(m1, " 模板管理");
	    j1.setBounds(450, 10, 120, 100);
		northPanel.add(j1); 
		
		m2 = new MyButton(new ImageIcon("img/2.jpg"), new ImageIcon("img/2_2.jpg"),100, 80);
		 JPanel j2 = getMyPanel(m2, " 典型病例");
		 j2.setBounds(650, 10, 120, 100);
		 northPanel.add(j2); 
		 
		m3 = new MyButton(new ImageIcon("img/3.jpg"), new ImageIcon("img/3_2.jpg"),100, 80);
		JPanel j3 = getMyPanel(m3, "  子模板");
		 j3.setBounds(850, 10, 120, 100);
		northPanel.add(j3);
		
		m4 = new MyButton(new ImageIcon("img/4.jpg"), new ImageIcon("img/4_2.jpg"),100, 80);
		JPanel j4 = getMyPanel(m4, "  病案首页");
		j4.setBounds(1050, 10, 120, 100);
		northPanel.add(j4);
		
		m5 = new MyButton(new ImageIcon("img/5.jpg"), new ImageIcon("img/5_2.jpg"),100, 80);
		JPanel j5 =getMyPanel(m5, " 退出");
		j5.setBounds(1300, 10, 120, 100);
		northPanel.add(j5);
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
		north1.setPreferredSize(new Dimension(300, 180));
		north1.setBackground(new Color(117, 218, 231));
		
		JButton addButton = new JButton("添加病人");
		addButton.setBounds(50, 50, 90, 40);
		north1.add(addButton);
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new PatientFrame().openFrame();
				
			}
		});
		
		JButton editButton = new JButton("编辑病人");
		editButton.setBounds(180, 50, 90, 40);
		north1.add(editButton);
		editButton.addActionListener(new ActionListener() {
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
	
		JButton deleteButton = new JButton("删除病人");
		deleteButton.setBounds(280, 50, 90, 40);
		north1.add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				String hspID = null;
				if(selectedRow != -1) {
					hspID= (String) table.getValueAt(selectedRow, 0);
					String sql = "delete from PatientInfo where hspID = '" + hspID + "'";
					try {
						JOptionPane.showConfirmDialog(null, "确认删除病人？");	
						JDBCUtil.updateByPreparedStatement(sql, null);
						HomeFrame.tablePanel.removeAll();
						HomeFrame.setContent_north();
						HomeFrame.tablePanel.updateUI();
						HomeFrame.tablePanel.repaint();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
		
		
		JButton leaveButton = new JButton("病人出院");
		leaveButton.setBounds(380, 50, 90, 40);
		north1.add(leaveButton);
		
		JButton transfButton = new JButton("病人转科");
		transfButton.setBounds(480, 50, 90, 40);
		north1.add(transfButton);
		
		JButton fileButton = new JButton("病历归档");
		fileButton.setBounds(580, 50, 90, 40);
		north1.add(fileButton);
		JButton importBufileButtontton = new JButton("导入患者");
		importBufileButtontton.setBounds(680, 50, 90, 40);
		north1.add(importBufileButtontton);
		JButton clearButton = new JButton("清空查询");
		JButton advancedqueryButton = new JButton("开始查询");
		advancedqueryButton.setBounds(800, 100, 90, 40);
		clearButton.setBounds(900, 100, 90, 40);
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
		
		north1.add(getMyPanel("床号  ", bed_textField)).setBounds(50, 100, 150, 40);
		north1.add(getMyPanel("入院号    ", hspID_textField)).setBounds(220, 100,160, 40);
		north1.add(getMyPanel("姓名", name_textField)).setBounds(400, 100, 150, 40);
		north1.add(getMyPanel("所属科室",condition_comboBox)).setBounds(580, 100, 200, 40);
		condition_comboBox.setBackground(Color.WHITE);
	}
	
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
System.out.println(lists);
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
 * 设置侧面
 */
	public void setLayout_setWest() {
		westPanel.add(medButton);
		westPanel.setPreferredSize(new Dimension(300,80));
		westPanel.setBackground(new Color(209, 224, 239));
		medButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				String hspID = null;
				if(selectedRow != -1) {
					hspID= (String) table.getValueAt(selectedRow, 0);
					new Med(hspID);		
				}			}
		});
		
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
		
		component.setPreferredSize(new Dimension(100, 40));
		component.setFont(new Font("宋体",Font.BOLD,20));
		
		jpanel.add(jlabel,BorderLayout.WEST);
		jpanel.add(component,BorderLayout.EAST);
		return jpanel;
	}
	

}
