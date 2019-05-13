package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultButtonModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import model.Patient;
import util.JDBCUtil;

//添加#1处的信息
public class PatientFrame extends JFrame{
	String hspID = null;
	
	public PatientFrame() {
		setLayout_1() ;	
	}
	
	public PatientFrame(String hspID) {
		setLayout_1() ;
		this.hspID = hspID;
		String sql = "select * from patientinfo  where hspID = '" + hspID + "'";
		SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd");
		try {
			Map<String, Object> maps = JDBCUtil.findSimpleResult(sql, null);
			System.out.println(maps);
// #1
			hspID_textField.setText(maps.get( "hspid".toUpperCase()).toString());
			name_textField.setText(maps.get( "name".toUpperCase()).toString());
			bed_textField.setText(maps.get( "BEDID".toUpperCase()).toString());
			
			// 生日
			String birth = maps.get("birthday".toUpperCase()).toString();
			Timestamp t = Timestamp.valueOf(birth);   //时间戳
			birthday_textField.setText(sdf.format(t));
			
			String inHsp = maps.get( "inHspTimes".toUpperCase() ).toString();
			Timestamp t2 = Timestamp.valueOf(inHsp);
			inHspTimes.setText(sdf.format(t2));
			
			String medCollect =maps.get( "medHisCollectTime".toUpperCase()).toString();
			Timestamp t3 = Timestamp.valueOf(medCollect);
			medHisCollectTime.setText(sdf.format(t3));
			
			inHspDiagnose.setText(maps.get( "inHspDiagnose".toUpperCase()).toString());
			iDCardNo.setText(maps.get( "iDCardNo".toUpperCase()).toString());
			medInsuranceID.setText((String) maps.get( "medInsuranceID".toUpperCase() )    );
			address.setText((String) maps.get( "address".toUpperCase() )    );
			contactsName.setText((String) maps.get( "contactsName".toUpperCase() )    );
			relation.setText((String) maps.get( "relation".toUpperCase() )    );
			contactsPhone.setText((String) maps.get( "contactsPhone".toUpperCase() )    );
			origin.setText((String) maps.get( "origin".toUpperCase() )    );
			remarks.setText((String) maps.get( "remarks".toUpperCase() )    );
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 显示窗口
	 */
	public void openFrame() {
		this.setSize(1000, 800);
		this.setLocationRelativeTo(this.getOwner());//设置位置为屏幕中央
		this.setResizable(false);
		this.setTitle("病人登记");
		this.getContentPane().setBackground(new Color(200,180,200)); 												
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);     //关闭子窗口，父窗口不会关闭
		this.setVisible(true);
	}
	
	/**
	 * 将窗口分为上中下三部分
	 */
	public void setLayout_1() {
		JPanel northPanel = new JPanel();
		northPanel.setBackground(new Color(117, 218, 231));
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.blue);
		
		JPanel southPanel = new JPanel();
		southPanel.setBackground(new Color(117, 218, 231));
		
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		
		setLayout_setLabel(northPanel);
		setLayout_setContent(centerPanel);
		setLayout_setButton(southPanel);
	}
	
	/**
	 *设置 “上”的信息
	 * @param northPanel
	 */
	public void setLayout_setLabel(JPanel northPanel) {
		JLabel name = new JLabel("新增患者信息");
		name.setFont(new Font("宋体", Font.BOLD, 35));
		northPanel.add(name);
	}
	
	/**
	 * 设置“中”的信息
	 * @param centerPanel
	 */
	public void setLayout_setContent(JPanel centerPanel) {
		JPanel north1 = new JPanel();
		JPanel south1 = new JPanel();
		centerPanel.setLayout(new BorderLayout());//JPanel默认布局管理器是flow
		setContent_north(north1);
		setContent_south(south1);
		
		centerPanel.add(north1, BorderLayout.CENTER);
		centerPanel.add(south1,BorderLayout.SOUTH);
	}
	
	/**
	 * 设置“中”上部分的组件
	 * @param north1
	 */
	public void setContent_north(JPanel north1) {
		FlowLayout f = new FlowLayout();
		f.setAlignment(FlowLayout.LEFT);
		f.setHgap(5);
		f.setVgap(15);
		north1.setLayout(f);
		//========== 面板总体  ==============
		north1.setBackground(myColor);
		north1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,2),
	               "入院情况", TitledBorder.LEFT,TitledBorder.TOP,new Font("宋体",Font.BOLD,20)));
		
		//========== 入院号  ==============
		
		north1.add(getMyPanel("入院号", hspID_textField));
		hspID_textField.setPreferredSize(new Dimension(150,40));
		
		//========== 姓名  ==============
		north1.add(getMyPanel("    姓名", name_textField));
		name_textField.setPreferredSize(new Dimension(150,40));
		//========== 性别  ==============
		north1.add(getMyPanel2("  性别",woman, man));
		gender_radioButtonGroup.add(man);gender_radioButtonGroup.add(woman);
		man.setFont(new Font("宋体",Font.BOLD,20));
		woman.setFont(new Font("宋体",Font.BOLD,20));
			
		//========== 所属科室  ==============
		north1.add(getMyPanel(" 所属科室 ",dept_comboBox));
		dept_comboBox.setPreferredSize(new Dimension(150,40));
			dept_comboBox.setBackground(Color.WHITE);
			
		
		
		//========== 床号  ==============
		north1.add(getMyPanel("床号  ",bed_textField));
		bed_textField.setPreferredSize(new Dimension(150,40));
		//========== 出生日期  ==============
		north1.add(getMyPanel("出生日期",birthday_textField));
		birthday_textField.setPreferredSize(new Dimension(150,40));
		birthday_textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new DateUI(birthday_textField);
			}
			
		});
		
		//========== 年龄  ==============
		north1.add(getMyPanel("年龄",age));
		north1.add(age_button);
		age_button.setFont(new Font("宋体",Font.BOLD,20));
		age_button.setBackground(myColor);
		
		north1.add(getMyPanel("入院病型",illness_comboBox));
		illness_comboBox.setPreferredSize(new Dimension(80,40));
		illness_comboBox.setBackground(Color.WHITE);
		
		north1.add(getMyPanel("入院类型",inHspType_comboBox));
		inHspType_comboBox.setPreferredSize(new Dimension(80,40));
		inHspType_comboBox.setBackground(Color.WHITE);
		
		north1.add(getMyPanel("入院时间",inHspTimes));
		inHspTimes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new DateUI(inHspTimes);
			}
			
		});
		
		//========== 病史采集日期  ==============
		north1.add(getMyPanel("病史采集日期",medHisCollectTime));
		medHisCollectTime.setPreferredSize(new Dimension(150,40));
		medHisCollectTime.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			new DateUI(medHisCollectTime);
		}
			
		});
		
		
		
		//========== 主治  ==============
		north1.add(getMyPanel("主治  ",doctor_comboBox));
		doctor_comboBox.setBackground(Color.WHITE);
		doctor_comboBox.setPreferredSize(new Dimension(200,40));
		
		
			
		//========== 入院诊断  ==============	
		inHspDiagnose.setLayout(new BorderLayout());
		inHspDiagnose.add(inquiry, BorderLayout.EAST);
		inquiry.setFont(new Font("宋体", Font.BOLD, 12));
		//inHspDiagnose.setBorder(BorderFactory.createLineBorder(Color.red));
		north1.add(getMyPanel("入院诊断",inHspDiagnose));
		inHspDiagnose.setPreferredSize(new Dimension(300, 40));
		//north1.add(inquiry);
		
		
		
		//========== 所属科室 ==============	
		north1.add(getMyPanel("所属科室",departZone_comboBox ));
			departZone_comboBox .setBackground(Color.WHITE);
			departZone_comboBox.setPreferredSize(new Dimension(200,40));
		north1.add(getMyPanel("身份证号",iDCardNo));
			iDCardNo.setPreferredSize(new Dimension(300,40));
		north1.add(getMyPanel("医保类型",medInsuranceType_comboBox));
			medInsuranceType_comboBox.setPreferredSize(new Dimension(200,40));
			medInsuranceType_comboBox.setBackground(Color.WHITE);
		north1.add(getMyPanel("医保号  ",medInsuranceID));
			medInsuranceID.setPreferredSize(new Dimension(300,40));	
	}
	
	/**
	 * 设置“中”下部分的组件
	 * @param south1
	 */
	public void setContent_south(JPanel south1) {
		FlowLayout f = new FlowLayout();
		f.setAlignment(FlowLayout.LEFT);
		f.setHgap(5);
		f.setVgap(15);
		south1.setLayout(f);
		south1.setBackground(myColor);
		south1.setPreferredSize(new Dimension(400, 230));//设置布局管理器下组件的大小，
		south1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,2),
	               "病人基本情况", TitledBorder.LEFT,TitledBorder.TOP,new Font("宋体",Font.BOLD,20)));
		south1.add(getMyPanel("婚姻状况  ", marriage_comboBox));
			marriage_comboBox.setPreferredSize(new Dimension(150,40));
			marriage_comboBox.setBackground(Color.WHITE);
		south1.add(getMyPanel("     民族 ", nation_comboBox));
			nation_comboBox.setPreferredSize(new Dimension(150,40));
			nation_comboBox.setBackground(Color.WHITE);
		south1.add(getMyPanel("      职业", profession_comboBox));
			profession_comboBox.setPreferredSize(new Dimension(150,40));
			profession_comboBox.setBackground(Color.WHITE);
		south1.add(getMyPanel("现在住址  ", address));
			address.setPreferredSize(new Dimension(150,40));
		south1.add(getMyPanel("联系人姓名", contactsName));
			contactsName.setPreferredSize(new Dimension(150,40));
		south1.add(getMyPanel(" 与患者关系", relation));
			relation.setPreferredSize(new Dimension(150,40));
		south1.add(getMyPanel("联系人电话", contactsPhone));
			contactsPhone.setPreferredSize(new Dimension(150,40));
		south1.add(getMyPanel("      籍贯", origin));
			origin.setPreferredSize(new Dimension(150,40));
		south1.add(getMyPanel("      备注", remarks));
			remarks.setPreferredSize(new Dimension(150,40));
		
		
		
	}
	
	/**
	 *设置“下”的按钮   设置按钮
	 * @param southPanel
	 */
	public void setLayout_setButton(JPanel southPanel) {
		JButton save = new JButton("保存");
		JButton exit = new JButton("退出");
		save.setFont(new Font("宋体", Font.BOLD, 18));
		exit.setFont(new Font("宋体", Font.BOLD, 18));
		southPanel.add(save);
		southPanel.add(exit);
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sava_Action();			
			}
		});
	}
	
	private JPanel getMyPanel(String name,JComponent component ) {
		
		JPanel jpanel = new JPanel();
		jpanel.setBackground(myColor);
		
		JLabel jlabel = new JLabel(name);
		jlabel.setFont(new Font("宋体",Font.BOLD,20));
		
		component.setPreferredSize(new Dimension(100, 40));
		component.setFont(new Font("宋体",Font.BOLD,20));
		
		jpanel.add(jlabel);
		jpanel.add(component);
		return jpanel;
	}
	
	private JPanel getMyPanel2(String name,JRadioButton  woman,JRadioButton man  ) {
		JPanel jpanel = new JPanel();
		jpanel.setBackground(myColor);
		
		JLabel jlabel = new JLabel(name);
		jlabel.setFont(new Font("宋体",Font.BOLD,20));
		woman.setBackground(myColor);
		man.setBackground(myColor);
		jpanel.add(jlabel);
		jpanel.add(woman);
		jpanel.add(man);
		return jpanel;
	}
	
	private void sava_Action() {
		Patient p = new Patient();

		// 格式检查
		try {
			this.setParams(p);
		} catch (Exception e) {
			return;
		}
		
		//	判断是否有此人
		String sql = "SELECT HspID from PatientInfo WHERE HspID=?";
		Map<String, Object> map = null; 
		try {
			map = JDBCUtil.findSimpleResult(sql, Arrays.asList(hspID));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}			
		
		// 执行SQL语句 
		try {
			if(map.size() == 0 || map == null) 	this.insert(p);
			else 					this.update(p);		
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
		JOptionPane.showMessageDialog(null, "保存信息成功");		
		dispose();
		HomeFrame.tablePanel.removeAll();
		HomeFrame.setContent_north();
		HomeFrame.tablePanel.updateUI();

	}
	
	private void setParams(Patient p) throws Exception {
		p.setHspID(hspID_textField.getText());
		p.setName(name_textField.getText());
		String gender = "";
		DefaultButtonModel model = (DefaultButtonModel)man.getModel();
		if(gender_radioButtonGroup.isSelected(model)) {
			gender = "男";
		} else {
			gender = "女";
		}
		p.setGender(gender);
		p.setDepartName(dept_comboBox.getSelectedItem().toString());
		p.setDoctorName(doctor_comboBox.getSelectedItem().toString());
		p.setBedID(bed_textField.getText());
		p.setBirthday(birthday_textField.getText());
		p.setAge(age.getText());
		p.setInHspTimes(inHspTimes.getText());
		p.setMedHisCollectTime(medHisCollectTime.getText());
		p.setInHspDiagnose(inHspDiagnose.getText());
		p.setDepartZone(departZone_comboBox.getSelectedItem().toString());
		p.setiDCardNo(iDCardNo.getText());
		p.setMedInsuranceType(medInsuranceType_comboBox.getSelectedItem().toString());
		p.setMedInsuranceID(medInsuranceID.getText());
		p.setInHspType(inHspType_comboBox.getSelectedItem().toString());
		p.setIllness(illness_comboBox.getSelectedItem().toString());
		p.setMarriage(marriage_comboBox.getSelectedItem().toString());
		p.setNation(nation_comboBox.getSelectedItem().toString());
		p.setprofession(profession_comboBox.getSelectedItem().toString());
		p.setAddress(address.getText());
		p.setContactsName(contactsName.getText());
		p.setRelation(relation.getText());
		p.setContactsPhone(contactsPhone.getText());
		p.setOrigin(origin.getText());
		p.setRemarks(remarks.getText());
	}
	
	private void insert(Patient p) throws SQLException {
		String sql = "insert into PatientInfo (hspid, name,gender,DepartName,DoctorName,"
				+ "BedID,birthday,InHspTimes,MedHisCollectTime,InHspDiagnose,"
				+ "DepartZone,iDCardNo,MedInsuranceType,MedInsuranceID,InHspType,"
				+ "Illness,Marriage,Nation,profession,Address,"
				+ "ContactsName,Relation,ContactsPhone,Origin,Remarks)"
				+ "values (?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?,"
				+ "?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ? ,?)";
		List<Object> params = Arrays.asList(p.getHspID(), p.getName(), p.getGender(),p.getDepartName(),
				p.getDoctorName(),p.getBedID(),p.getBirthday(),p.getInHspTimes(),p.getMedHisCollectTime(),
				p.getInHspDiagnose(),p.getDepartZone(),p.getiDCardNo(),p.getMedInsuranceType(),p.getMedInsuranceID(),
				p.getInHspType(),p.getIllness(),p.getMarriage(),p.getNation(),p.getprofession(),p.getAddress(),
				p.getContactsName(),p.getRelation(),p.getContactsPhone(),p.getOrigin(),p.getRemarks());
		JDBCUtil.updateByPreparedStatement(sql, params);
	}
	
	private void update(Patient p) throws SQLException {
		String sql = "update PatientInfo set name=?, gender=?, DepartName=?, DoctorName=?,BedID=?,"
				+ "birthday=?, InHspTimes=?, MedHisCollectTime=?, InHspDiagnose=?,DepartZone=?,"
				+ "iDCardNo=?,MedInsuranceType=?,MedInsuranceID=?,InHspType=?,Illness=?,"
				+ "Marriage=?,Nation=?,profession=?,Address=?,ContactsName=?,"
				+ "Relation=?,ContactsPhone=?,Origin=?,Remarks=? where HspID='" + hspID + "'";
		List<Object>  params = Arrays.asList(p.getName(), p.getGender(),p.getDepartName(),p.getDoctorName(),p.getBedID(),
				p.getBirthday(),p.getInHspTimes(),p.getMedHisCollectTime(),p.getInHspDiagnose(),p.getDepartZone(),
				p.getiDCardNo(),p.getMedInsuranceType(),p.getMedInsuranceID(),p.getInHspType(),p.getIllness(),
				p.getMarriage(),p.getNation(),p.getprofession(),p.getAddress(),p.getContactsName(),
				p.getRelation(),p.getContactsPhone(),p.getOrigin(),p.getRemarks());
		JDBCUtil.updateByPreparedStatement(sql, params);
	}
// #1	
	JTextField hspID_textField = new JTextField();
	JTextField name_textField = new JTextField();
	ButtonGroup gender_radioButtonGroup = new ButtonGroup();
		JRadioButton man = new JRadioButton("男");
		JRadioButton woman = new JRadioButton("女");
		
	String[] deptList = {"内科","外科","心胸外科","妇科","儿科"};
	JComboBox<String> dept_comboBox = new JComboBox<String>(deptList);
	
	String[] doctorName = {"系统管理员8888","颜团子医生999"};
	JComboBox<String> doctor_comboBox = new JComboBox<String>(doctorName );
	
	JTextField bed_textField = new JTextField();
	JTextField birthday_textField = new JTextField();
	JTextField age = new JTextField();
	JRadioButton age_button = new JRadioButton("只计算年龄");
	JTextField inHspTimes = new JTextField();
	JTextField medHisCollectTime = new JTextField();
	
	JTextField inHspDiagnose = new JTextField();
	JButton inquiry = new JButton("查询");
	
	String[] departZone = {"内科病区","外科病区","心胸外科病区","妇科病区","儿科病区"};
	JComboBox<String> departZone_comboBox = new JComboBox<String>(departZone);
	
	JTextField iDCardNo = new JTextField();
	String[] medInsuranceType = {"社会基本医疗保险","商业医疗保险","大病统筹","新型农村合作医疗","城镇居民基本医疗保险","其他"};
	JComboBox<String> medInsuranceType_comboBox = new JComboBox<String>(medInsuranceType);
	
	JTextField medInsuranceID = new JTextField();
	
	String[] inHspType = {"门诊","急诊","住院","体检"};
	JComboBox<String> inHspType_comboBox = new JComboBox<String>(inHspType );
	
	String[] illness = {"一般","急","病重","病危"};
	JComboBox<String> illness_comboBox = new JComboBox<String>(illness );
	
	String[] marriage = {"已婚","未婚","再婚","离婚","丧偶","未说明的婚姻情况"};
	JComboBox<String> marriage_comboBox = new JComboBox<String>(marriage );
	
	String[] nation = {"汉族","苗族","土家族","回族","蒙古族","傣族"};
	JComboBox<String> nation_comboBox = new JComboBox<String>(nation );
	
	String[] profession = {"工人","干部","农民","其他"};
	JComboBox<String> profession_comboBox = new JComboBox<String>(profession );
	
	JTextField address = new JTextField();
	JTextField contactsName = new JTextField();
	JTextField relation = new JTextField();
	JTextField contactsPhone = new JTextField();
	JTextField origin = new JTextField();
	JTextField remarks = new JTextField();
	Color myColor = new Color(222, 241, 255);

}
