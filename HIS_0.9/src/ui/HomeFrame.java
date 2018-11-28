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
	String[] condition = {"ȫ��","��Ժ","��Ժ","�鵵"};
	JComboBox<String> condition_comboBox = new JComboBox<String>(condition);
	public static JPanel tablePanel = new JPanel();
	JButton medButton = new JButton("��Ӳ���");
	
	public  HomeFrame() {
		this.setSize(2000, 2000);
		this.setLocationRelativeTo(this.getOwner());//����λ��Ϊ��Ļ����
		this.setResizable(true);
		this.setTitle("Home");
		this.getContentPane().setBackground(new Color(222, 241, 255)); 												
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		homeLayout();
	}
	/**
	 * ����������ҳ��Ĳ��ֹ���������Ϊ���������Ĳ���
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
	 * ���ñ��ߵĲ˵���
	 */
	public void setLayout_setmenu() {
		northPanel.setPreferredSize(new Dimension(100, 130));
		northPanel.setLayout(null);
		northPanel.setBackground(new Color(216, 246, 255));
		
	    m1 = new MyButton(new ImageIcon("img/1.jpg"), new ImageIcon("img/1_2.jpg"),100, 80);
	    JPanel j1 = getMyPanel(m1, " ��������");
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
		 JPanel j2 = getMyPanel(m2, " ��Ӳ���");
		 j2.setBounds(500, 10, 120, 100);
		 northPanel.add(j2); 
		 m2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new PatientFrame().openFrame();					
				}
			});
		
		m23 = new MyButton(new ImageIcon("img/ɾ��1.jpg"), new ImageIcon("img/ɾ��2.jpg"),100, 80);
		JPanel j23 = getMyPanel(m23, "  ɾ������");
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
						int isDelete = JOptionPane.showConfirmDialog(null, "ȷ��ɾ�����ˣ�", "��ʾ", JOptionPane.YES_NO_CANCEL_OPTION); 
						//��������������JOptionPane.YES_OPTION����˵���������ǡ�ȷ������ť������������������������
						if(isDelete == JOptionPane.YES_OPTION){
							JDBCUtil.updateByPreparedStatement(sql, null);
							HomeFrame.tablePanel.removeAll();
							HomeFrame.setContent_north();
							HomeFrame.tablePanel.updateUI();
							HomeFrame.tablePanel.repaint();
						    JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
						}
	
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
		
		m3 = new MyButton(new ImageIcon("img/3.jpg"), new ImageIcon("img/3_2.jpg"),100, 80);
		JPanel j3 = getMyPanel(m3, "  �༭����");
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
		
		m4 = new MyButton(new ImageIcon("img/��Ժ.jpg"), new ImageIcon("img/��Ժ2.jpg"),100, 80);
		JPanel j4 = getMyPanel(m4, "  ���˳�Ժ");
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
					SimpleDateFormat sdf = new  SimpleDateFormat("yyyy��MM��dd��--HH:mm:ss");
					String currentTime = sdf.format(time);
					
					try {
						String code = JOptionPane.showInputDialog("���˳�Ժ", currentTime);
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
		
		m45 = new MyButton(new ImageIcon("img/ת��12.jpg"), new ImageIcon("img/ת��2.jpg"),100, 80);
		JPanel j45 =getMyPanel(m45, " ����ת��");
		j45.setBounds(1300, 10, 120, 100);
		northPanel.add(j45);
		
		m5 = new MyButton(new ImageIcon("img/5.jpg"), new ImageIcon("img/5_2.jpg"),100, 80);
		JPanel j5 =getMyPanel(m5, "   �˳�");
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
	 * ���в���Ϊ������
	 */
	public void setLayout_setContent() {
		JPanel south1 = new JPanel();
		centerPanel.setLayout(new BorderLayout());//JPanelĬ�ϲ��ֹ�������flow
		setContent_north();
		setContent_south(south1);
		
		centerPanel.add(tablePanel, BorderLayout.CENTER);
		centerPanel.add(south1,BorderLayout.NORTH);
	}
	/**
	 * �����в����ϲ���
	 * @param north1
	 */
	public void setContent_south(JPanel north1) {
		north1.setLayout(null);
		north1.setPreferredSize(new Dimension(300, 160));
		north1.setBackground(new Color(117, 218, 231));

		JButton clearButton = new JButton("��ղ�ѯ");
		JButton advancedqueryButton = new JButton("��ʼ��ѯ");
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
		
		north1.add(getMyPanel("����          ", bed_textField)).setBounds(80, 50, 200, 50);
		north1.add(getMyPanel("��Ժ��   ", hspID_textField)).setBounds(300, 50,200, 50);
		north1.add(getMyPanel("����", name_textField)).setBounds(520, 50, 200, 50);
		north1.add(getMyPanel("��������   ",condition_comboBox)).setBounds(750, 50, 230, 50);
		condition_comboBox.setBackground(Color.WHITE);
	}
	                                                        //ģ����ѯ
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
	 * �����в����²���
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
		//��ͷ
		String[] colNames = {"סԺ��", "����", "�Ա�","��������","����","����", "��Ժ���"};
		//���������
		Object[][] obj = new Object[rowSize][colSize]; 
		//��������ݸ�ֵ
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
	 * ���õײ���Ϣ
	 */
	public void setLayout_setBottom() {
		JLabel l1 = new JLabel("������Ա");
		southPanel.add(l1);
		southPanel.setBackground(new Color(128, 225, 253));
		southPanel.setPreferredSize(new Dimension(50,50));
		
	}
/**
 *  #clock
 * ���ò���
 */
	public void setLayout_setWest() {
		westPanel.setPreferredSize(new Dimension(300,80));
		westPanel.setBackground(new Color(209, 224, 239));
		westPanel.setLayout(new BorderLayout());
        MyClock clock = new MyClock(0, 0, CLOCK_RADIUS);
        JPanel primary = new JPanel();
        primary.setBackground(new Color(209, 224, 239)); //����ɫ
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
		j.setText("<h1>��������</h1>"
				+ ""
				+ "");
	}

/**
 * �˵���button��ͼƬ˵��	
 * @param j
 * @param s
 * @return
 */
	private JPanel getMyPanel(JButton j, String s) {
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new BorderLayout());
		jpanel.setBackground(new Color(216, 246, 255));
		
		JLabel jlabel = new JLabel(s);
		jlabel.setFont(new Font("����",Font.BOLD,20));
		jlabel.setForeground(Color.black);
		
		jpanel.add(j, BorderLayout.CENTER);
		jpanel.add(jlabel,BorderLayout.SOUTH);
		return jpanel;
	}


	/**
	 * �в�Jlabel�������
	 * @param name
	 * @param component
	 * @return
	 */
	private JPanel getMyPanel(String name,JComponent component ) {
		JPanel jpanel = new JPanel();
		jpanel.setBackground(new Color(117, 218, 231));
		jpanel.setLayout(new BorderLayout());
		
		JLabel jlabel = new JLabel(name);
		jlabel.setFont(new Font("����",Font.BOLD,20));
		
		component.setPreferredSize(new Dimension(140, 50));
		component.setFont(new Font("����",Font.BOLD,20));
		
		jpanel.add(jlabel,BorderLayout.WEST);
		jpanel.add(component,BorderLayout.EAST);
		return jpanel;
	}
	

}
