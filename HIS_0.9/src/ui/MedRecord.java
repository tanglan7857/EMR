package ui;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import util.JDBCUtil;


public class MedRecord extends JPanel {
	String hspID = null;
	String birthday = null;
	String theAge = null;
	String templateID = null;
	Choice selectTemplate = new Choice();  
	Med med = null;
	
	JLabel l = new JLabel("---------------------��  �� ��  Ϣ----------------------------");
	JLabel l1 = new  JLabel("��������");					
	JTextField name = new JTextField();		
	JLabel l2 = new  JLabel("��    ��");					
	JTextField gender = new JTextField();		
	JLabel l3 = new  JLabel("��    ��");					
	JTextField age = new JTextField();	
	JLabel l4 = new  JLabel("��    ��");				
	JTextField nation = new JTextField();	
	JLabel l5 = new  JLabel("����״��");				
	JTextField marriage = new JTextField();	
	JLabel l6 = new  JLabel("ְ    ҵ");				
	JTextField profession = new JTextField();
	JLabel l7 = new  JLabel("��������");					
	JTextField departName = new JTextField();
	JLabel l8 = new  JLabel("����סַ");					
	JTextField address = new JTextField();
	
	public MedRecord(String hspID, Med med) { //����ҳ����ʾ
		this.hspID = hspID;    
		this.med = med;
		this.setBackground(new Color(207, 223, 238));
		this.setLayout(null);
		set_information();	
		set_info();
	}
	
	private void set_information() {             //���˻�����Ϣ
		this.setLabel(l, 0, 30);
		this.add(l);
		
		setLabel_field(l1, name, 20, 120);
		this.add(l1);
		this.add(name);	
		setLabel_field(l2, gender, 20, 190);
		this.add(l2);
		this.add(gender);	
		setLabel_field(l3, age, 20, 260);
		this.add(l3);
		this.add(age);		
		setLabel_field(l4, nation, 20, 330);
		this.add(l4);
		this.add(nation);		
		setLabel_field(l5, marriage, 320, 120);
		this.add(l5);
		this.add(marriage);	
		setLabel_field(l6, profession, 320, 190);
		this.add(l6);
		this.add(profession);	
		setLabel_field(l7, departName, 320, 260);
		this.add(l7);
		this.add(departName);	
		setLabel_field(l8, address, 320, 330);
		this.add(l8);
		this.add(address);
	}
	
	public void set_info() {
		String sql = "select * from PatientInfo where HspID= ?";
		List<Object> params = Arrays.asList(hspID);
		
		try {
			Map<String, Object> map = JDBCUtil.findSimpleResult(sql, params);
			if(0 == map.size() || null == map) return;
			
			birthday = map.get("Birthday".toUpperCase()).toString();
			getAge();
			name.setText(map.get("Name".toUpperCase()).toString());
			gender.setText(map.get("Gender".toUpperCase()).toString());
			age.setText(theAge);
			nation.setText(map.get("Nation".toUpperCase()).toString());
			marriage.setText(map.get("Marriage".toUpperCase()).toString());
			profession.setText(map.get("Profession".toUpperCase()).toString());
			departName.setText(map.get("DepartName".toUpperCase()).toString());
			address.setText(map.get("Address".toUpperCase()).toString());
			
			map = null;
			sql = "SELECT * FROM MedRecordInfo WHERE HspID='"+hspID+"'";
			map = JDBCUtil.findSimpleResult(sql, null);
			if(0 == map.size() || null == map) return;
	
			med.ta1.setText(map.get("����").toString());
			med.ta2.setText(map.get("�ֲ�ʷ").toString());
			med.ta3.setText(map.get("����ʷ").toString());
			med.ta4.setText(map.get("����ʷ").toString());
			med.ta5.setText(map.get("�¾�ʷ").toString());
			med.ta6.setText(map.get("����ʷ").toString());
			med.ta7.setText(map.get("�����").toString());
			med.ta8.setText(map.get("ר�Ƽ��").toString());
			med.ta9.setText(map.get("�������").toString());
			med.ta10.setText(map.get("�������").toString());
			med.ta11.setText(map.get("�������").toString());
			med.ta12.setText(map.get("���Ƽƻ�").toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	protected void setLabel_field(JLabel l, JTextField tf, int x, int y) {      //����label�Ͷ�Ӧfield��λ�ô�С������
		l.setBounds(x, y, 100, 30);
		l.setFont(new Font("����", Font.BOLD, 18));
		tf.setBounds(x+100, y, 120, 40);
		tf.setFont(new Font("����", Font.PLAIN, 18));
	}	

	protected void getAge() {							
		if(!(birthday.equals(""))) {
			try {
			String s1 = birthday + " 00:00:00.0";		//�õ��ı����е�ʱ�� ��תΪDate���� �ٷŽ�Calendar
			DateFormat df1 = DateFormat.getDateInstance();
			java.util.Date d1 = null;
			try {
				d1 = df1.parse(s1);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			Calendar c1 = Calendar.getInstance();
			c1.setTime(d1);
			int year1 = c1.get(Calendar.YEAR);
			int month1 = c1.get(Calendar.MONTH);
			
			Calendar c2 = Calendar.getInstance();				//�õ�ǰʱ�䴴��һ��Calendar
			int year2 = c2.get(Calendar.YEAR);
			int month2 = c2.get(Calendar.MONTH);
			int intAgeYear;
			int intAgeMonth;
			if(month2>month1) {
				intAgeYear = year2 - year1;
				intAgeMonth = month2 - month1;
			} else  {
				intAgeYear = year2 - year1 - 1;
				intAgeMonth = 12 + month2 - month1;
			}
			String stringAgeYear = String.valueOf(intAgeYear);
			String stringAgeMonth = String.valueOf(intAgeMonth);
			theAge = (stringAgeYear + "��" + stringAgeMonth + "����");
			} catch (ParseException e1) {					
				e1.printStackTrace();
			}
			
		} else {
			theAge = "";  
		}
	}
	
	public void setLabel(JLabel l, int x, int y) {        //����ָ��label��λ�ô�С������
		l.setFont(new Font("����", Font.BOLD, 20));
		l.setBounds(x, y, 700, 30);
	}
	
	
}
