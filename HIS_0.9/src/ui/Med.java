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
	JMenu homeMenu = new JMenu("��ҳ");
	JMenu saveMenu = new JMenu("����");
	JMenu importMenu = new JMenu("����ģ��");
	JMenu exportMenu = new JMenu("����ģ��");
	JMenu exit = new JMenu("�˳�");
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
	
	public Med(String hspID) {     //����ҳ����ʾ
		this.hspID = hspID;
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(this.getOwner());
		this.setResizable(false);
		this.abstractPanel.setLayout(new BorderLayout());
		//this.setUndecorated(true);  //ȥ���߿�
		this.getContentPane().setBackground(new Color(222, 241, 255)); 
		//layout
		this.set_context();
		this.setMenue();
		this.addMenuListener();
		
		// ��Ӳ�����Ϣ���
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
		
		//����
		exportMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				exportMedListener();
			}	
		});	
		
		//����
		importMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				importMedListener();
			}
		});
		
		//�˳�
		exit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Med.this.dispose();
	
			}
		});

	}


	private void setMenue() {
		homeMenu.setFont(new Font("����", Font.BOLD, 30));
		saveMenu.setFont(new Font("����", Font.BOLD, 30));
		importMenu.setFont(new Font("����", Font.BOLD, 30));
		exportMenu.setFont(new Font("����", Font.BOLD, 30));
		exit.setFont(new Font("����", Font.BOLD, 30));
		this.setJMenuBar(menubar);
		menubar.add(homeMenu);
		menubar.add(saveMenu);
		menubar.add(importMenu);
		menubar.add(exportMenu);
		menubar.add(exit);
		
	}
	
	public void set_context() {	             //�������
		this.setLayout(null);
		scroll = new JScrollPane(contextPanel);
		scroll.getVerticalScrollBar().setUnitIncrement(20);		//���Ĺ����ٶ�
		scroll.setBounds(0, 0, WIDTH, HEIGHT);	
		this.add(scroll);
		
		//��������
		contextPanel.setPreferredSize(new Dimension(WIDTH, 3000));
		contextPanel.setBackground(new Color(216, 246, 255));
		contextPanel.setLayout(null);
		contextPanel.add(abstractPanel);
		abstractPanel.setBounds(80, 30, 580, 400);
		abstractPanel.setBackground(Color.green);
		set_medInformation();	
	}

	public void setLabel(JLabel l, int x, int y) {        //����ָ��label��λ�ô�С������
		l.setFont(new Font("����", Font.BOLD, 20));
		l.setBounds(x, y, 700, 30);
	}
	
	private void set_medInformation() {           //������Ϣ
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
		
		JLabel l1 = new JLabel(" ���ߣ�");
		JLabel l2 = new JLabel(" �ֲ�ʷ��");
		JLabel l3 = new JLabel(" ����ʷ��");
		JLabel l4 = new JLabel(" ����ʷ�� ");
		JLabel l5 = new JLabel(" �¾�ʷ��");
		JLabel l6 = new JLabel(" ����ʷ��");
		JLabel l7 = new JLabel("-----------------��  ��  ��  ��-----------------");
		JLabel l8 = new JLabel("-----------------ר  ��  ��  ��-----------------");
		JLabel l9 = new JLabel("-----------------��  ��  ��  ��-----------------");
		JLabel l10 = new JLabel("----------------��  ��  ��  ��-----------------");
		JLabel l11 = new JLabel("----------------��  ��  ��  ��-----------------");
		JLabel l12 = new JLabel("----------------��  ��  ��  ��-----------------");
		
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
	
	private void set_txexArea(TextArea ta,int x, int y ) {      //���ò���textarea��λ�ô�С������
		ta.setFont(new Font("����", Font.BOLD, 20));			
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
				sql = "insert into MedRecordInfo (HspID,����,�ֲ�ʷ,����ʷ,����ʷ,"
												+ "�¾�ʷ,����ʷ,�����,ר�Ƽ��,�������,"
												+ "�������,�������,���Ƽƻ�)"
												+ " values ('" + hspID + "',?,?,?,?,"
														+ "?,?,?,?,?,"
														+ "?,?,?)";
			}else {
				sql = "update MedRecordInfo set ����=?,�ֲ�ʷ=?,����ʷ=?,����ʷ=?,"
						+ "�¾�ʷ=?,����ʷ=?,�����=?,ר�Ƽ��=?,�������=?,"
						+ "�������=?,�������=?,���Ƽƻ�=? where HspID='" + hspID + "'";
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
	
		JOptionPane.showMessageDialog(null, "���没����Ϣ�ɹ�");				
	}
	
	private void exportMedListener() {
		this.abstractPanel.removeAll();
		ExportMed em = new ExportMed("����ģ��", this);
		this.abstractPanel.add(em);
		this.abstractPanel.updateUI();
		
	}
	
	private void importMedListener() {
		this.abstractPanel.removeAll();
		ImportMed im = new ImportMed("����ģ��", this);
		this.abstractPanel.add(im);
		this.abstractPanel.updateUI();
		
	}	
}
