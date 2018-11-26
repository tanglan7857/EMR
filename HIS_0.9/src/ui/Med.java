package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
//import javax.swing.JTextArea;

import util.JDBCUtil;

public class Med extends JFrame {
	JMenu homeMenu = new JMenu("首页");
	JMenu saveMenu = new JMenu("保存");
	JMenu importMenu = new JMenu("导入模板");
	JMenu exportMenu = new JMenu("导出模板");
	JMenu exit = new JMenu("退出");
	JMenuBar menubar = new JMenuBar();
	
	JScrollPane scroll  = null;
	protected JPanel abstractPanel = new JPanel();;
	protected JPanel contextPanel = new JPanel();
	
	protected TextArea ta1 = new TextArea("",5,13,TextArea.SCROLLBARS_NONE);
	protected TextArea ta2 = new TextArea("",5,13,TextArea.SCROLLBARS_NONE);
	protected TextArea ta3 = new TextArea("",5,13,TextArea.SCROLLBARS_NONE);
	protected TextArea ta4 = new TextArea("",5,13,TextArea.SCROLLBARS_NONE);
	protected TextArea ta5 = new TextArea("",5,13,TextArea.SCROLLBARS_NONE);
	protected TextArea ta6 = new TextArea("",5,13,TextArea.SCROLLBARS_NONE);
	protected TextArea ta7 = new TextArea("",5,13,TextArea.SCROLLBARS_NONE);
	protected TextArea ta8 = new TextArea("",5,13,TextArea.SCROLLBARS_NONE);
	protected TextArea ta9 = new TextArea("",5,13,TextArea.SCROLLBARS_NONE);
	protected TextArea ta10 = new TextArea("",5,13,TextArea.SCROLLBARS_NONE);
	protected TextArea ta11 = new TextArea("",5,13,TextArea.SCROLLBARS_NONE);
	protected TextArea ta12 = new TextArea("",5,13,TextArea.SCROLLBARS_NONE);
	
	protected String hspID = null;
	MedRecord mr = null;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 1000;
	public static final int AB_WIDTH = 580;
	public static final int AB_HEIGHT = 400;
	
	public Med(String hspID) {     //基本页面显示
		this.hspID = hspID;
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(this.getOwner());
		this.setResizable(false);
		this.abstractPanel.setLayout(new BorderLayout());
		//this.setUndecorated(true);  //去掉边框
		this.getContentPane().setBackground(new Color(222, 241, 255)); 
		//layout
		this.set_context();
		this.setMenue();
		this.addMenuListener();
		
		// 添加病人信息面板
		mr = new MedRecord(hspID, this);
		this.abstractPanel.add(mr);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	
	private void addMenuListener() {
		// home
		homeMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				homePage();
			}
			
		});
		
		
		// save
		saveMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				saveMedListener();
			}
			
		});
		
		//导出
		exportMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				exportMedListener();
			}	
		});	
		
		//导入
		importMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				importMedListener();
			}
		});
		
		//退出
		exit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Med.this.dispose();
	
			}
		});

	}


	private void setMenue() {
		homeMenu.setFont(new Font("宋体", Font.BOLD, 30));
		saveMenu.setFont(new Font("宋体", Font.BOLD, 30));
		importMenu.setFont(new Font("宋体", Font.BOLD, 30));
		exportMenu.setFont(new Font("宋体", Font.BOLD, 30));
		exit.setFont(new Font("宋体", Font.BOLD, 30));
		this.setJMenuBar(menubar);
		menubar.add(homeMenu);
		menubar.add(saveMenu);
		menubar.add(importMenu);
		menubar.add(exportMenu);
		menubar.add(exit);
		
	}
	
	public void set_context() {	             //内容面板
		this.setLayout(null);
		scroll = new JScrollPane(contextPanel);
		scroll.getVerticalScrollBar().setUnitIncrement(20);		//更改滚轮速度
		scroll.setBounds(0, 0, WIDTH, HEIGHT);	
		this.add(scroll);
		
		//设置内容
		contextPanel.setPreferredSize(new Dimension(WIDTH, 3000));
		contextPanel.setBackground(new Color(216, 246, 255));
		contextPanel.setLayout(null);
		contextPanel.add(abstractPanel);
		abstractPanel.setBounds(80, 30, 580, 400);
		abstractPanel.setBackground(Color.green);
		set_medInformation();	
	}

	public void setLabel(JLabel l, int x, int y) {        //设置指定label的位置大小和字体
		l.setFont(new Font("宋体", Font.BOLD, 20));
		l.setBounds(x, y, 700, 30);
	}
	
	private void set_medInformation() {           //病例信息
		set_txexArea(ta1, 80, 500);
		set_txexArea(ta2, 80, 700);
		set_txexArea(ta3, 80, 900);
		set_txexArea(ta4, 80, 1100);
		set_txexArea(ta5, 80, 1300);
		set_txexArea(ta6, 80, 1500);
		set_txexArea(ta7, 80, 1700);
		set_txexArea(ta8, 80, 1900);
		set_txexArea(ta9, 80, 2100);
		set_txexArea(ta10, 80, 2300);
		set_txexArea(ta11, 80, 2500);
		set_txexArea(ta12, 80, 2700);
		
		JLabel l1 = new JLabel(" 主诉：");
		JLabel l2 = new JLabel(" 现病史：");
		JLabel l3 = new JLabel(" 既往史：");
		JLabel l4 = new JLabel(" 婚育史： ");
		JLabel l5 = new JLabel(" 月经史：");
		JLabel l6 = new JLabel(" 家族史：");
		JLabel l7 = new JLabel("-----------------体  格  检  查-----------------");
		JLabel l8 = new JLabel("-----------------专  科  检  查-----------------");
		JLabel l9 = new JLabel("-----------------辅  助  检  查-----------------");
		JLabel l10 = new JLabel("----------------鉴  别  诊  断-----------------");
		JLabel l11 = new JLabel("----------------初  步  诊  断-----------------");
		JLabel l12 = new JLabel("----------------诊  疗  计  划-----------------");
		
		contextPanel.add(l1);
		setLabel(l1, 80, 450);
		contextPanel.add(ta1);
		
		contextPanel.add(l2);
		setLabel(l2, 80, 650);
		contextPanel.add(ta2);
		
		contextPanel.add(l3);
		setLabel(l3, 80, 850);
		contextPanel.add(ta3);
		
		contextPanel.add(l4);
		setLabel(l4, 80, 1050);
		contextPanel.add(ta4);
		
		contextPanel.add(l5);
		setLabel(l5, 80, 1250);
		contextPanel.add(ta5);
		
		contextPanel.add(l6);
		setLabel(l6, 80, 1450);
		contextPanel.add(ta6);
		
		contextPanel.add(l7);
		setLabel(l7, 80, 1650);
		contextPanel.add(ta7);
		
		contextPanel.add(l8);
		setLabel(l8, 80, 1850);
		contextPanel.add(ta8);
		
		contextPanel.add(l9);
		setLabel(l9, 80, 2050);
		contextPanel.add(ta9);
		
		contextPanel.add(l10);
		setLabel(l10, 80, 2250);
		contextPanel.add(ta10);
		
		contextPanel.add(l11);
		setLabel(l11, 80, 2450);
		contextPanel.add(ta11);
		
		contextPanel.add(l12);
		setLabel(l12, 80, 2650);
		contextPanel.add(ta12);	
		
	}
	
	private void set_txexArea(TextArea ta,int x, int y ) {      //设置病历textarea的位置大小及字体
		ta.setFont(new Font("宋体", Font.BOLD, 20));			
		ta.setBounds(x, y, 580, 130);
	}		
	

	public void homePage() {
		this.abstractPanel.removeAll();
		this.abstractPanel.add(mr);
		this.abstractPanel.updateUI();
		
	}
	
	protected void saveMedListener()  {	
		String sql = "SELECT HspID from MedRecordInfo WHERE HspID=?";
		List<Object> params = new ArrayList<>();
		Map<String, Object> map;
		try {
			map = JDBCUtil.findSimpleResult(sql, Arrays.asList(hspID));			
	
			if(map == null || map.size() == 0) {
				sql = "insert into MedRecordInfo (HspID,主诉,现病史,既往史,婚育史,"
												+ "月经史,家族史,体格检查,专科检查,辅助检查,"
												+ "鉴别诊断,初步诊断,诊疗计划)"
												+ " values ('" + hspID + "',?,?,?,?,"
														+ "?,?,?,?,?,"
														+ "?,?,?)";
			}else {
				sql = "update MedRecordInfo set 主诉=?,现病史=?,既往史=?,婚育史=?,"
						+ "月经史=?,家族史=?,体格检查=?,专科检查=?,辅助检查=?,"
						+ "鉴别诊断=?,初步诊断=?,诊疗计划=? where HspID='" + hspID + "'";
			}
			params.add(this.ta1.getText());
			params.add(this.ta2.getText());
			params.add(this.ta3.getText());
			params.add(this.ta4.getText());
			params.add(this.ta5.getText());
			params.add(this.ta6.getText());
			params.add(this.ta7.getText());
			params.add(this.ta8.getText());
			params.add(this.ta9.getText());
			params.add(this.ta10.getText());
			params.add(this.ta11.getText());
			params.add(this.ta12.getText());
			JDBCUtil.updateByPreparedStatement(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	
		JOptionPane.showMessageDialog(null, "保存病历信息成功");				
	}
	
	private void exportMedListener() {
		this.abstractPanel.removeAll();
		ExportMed em = new ExportMed("导出模板", this);
		this.abstractPanel.add(em);
		this.abstractPanel.updateUI();
		
	}
	
	private void importMedListener() {
		this.abstractPanel.removeAll();
		ImportMed im = new ImportMed("导入模板", this);
		this.abstractPanel.add(im);
		this.abstractPanel.updateUI();
		
	}	
}
