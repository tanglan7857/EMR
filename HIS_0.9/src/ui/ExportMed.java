package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.JDBCUtil;

public class ExportMed extends JPanel{
	JTextField category_textField = new JTextField();
	JTextField name_textField = new JTextField();
    JButton save = new JButton("导出");
	JButton returned = new JButton("返回");
	Med med = null;
	private String title;

	public ExportMed(String title, Med med) {
		this.med = med;
		this.title = title;
		this.setBackground(new Color(167,213,253));
		this.setLayout(null);
		setLayout_setcenter();
		save.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				ExportMed.this.savaTemplate();
			}
		});
		returned.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				med.homePage();
			}
		});
	}

	protected void savaTemplate() {
		String s1 = category_textField.getText() ;
		String s2 = name_textField.getText();
		if(s1.equals("")) 
			s1 = "未分类";
		if(s2.equals("")) {
			JOptionPane.showMessageDialog(null, "请输入模板名字！");
			return;
		}
			
		
		String sql = "SELECT * from TemplateInfo WHERE TemplateID=?";
		List<Object> params = new ArrayList<>();
		Map<String, Object> map;
		String key = s1
				+ "#" 
				+ s2;
		try {
			map = JDBCUtil.findSimpleResult(sql, Arrays.asList(key));			
	
			if(map == null || map.size() == 0) {
				sql = "insert into TemplateInfo (TemplateID,主诉,现病史,既往史,婚育史,"
												+ "月经史,家族史,体格检查,专科检查,辅助检查,"
												+ "鉴别诊断,初步诊断,诊疗计划)"
												+ " values ('" + key + "',?,?,?,?,"
														+ "?,?,?,?,?,"
														+ "?,?,?)";
			}else {
				sql = "update TemplateInfo set 主诉=?,现病史=?,既往史=?,婚育史=?,"
						+ "月经史=?,家族史=?,体格检查=?,专科检查=?,辅助检查=?,"
						+ "鉴别诊断=?,初步诊断=?,诊疗计划=? where TemplateID='" + key + "'";
			}
			params.add(med.ta1.getText());
			params.add(med.ta2.getText());
			params.add(med.ta3.getText());
			params.add(med.ta4.getText());
			params.add(med.ta5.getText());
			params.add(med.ta6.getText());
			params.add(med.ta7.getText());
			params.add(med.ta8.getText());
			params.add(med.ta9.getText());
			params.add(med.ta10.getText());
			params.add(med.ta11.getText());
			params.add(med.ta12.getText());
			JDBCUtil.updateByPreparedStatement(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	
		JOptionPane.showMessageDialog(null, "保存病历模板成功");				
	}

	public void setLayout_setcenter() {
		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font("黑体", Font.BOLD, 30));
		int size = titleLabel.getText().length();
		titleLabel.setBounds((520 - size * 30)/2, 50, size * 30 + 100, 30);
		this.add(titleLabel);
		
	    JLabel category = new JLabel("类别");
	    category.setForeground(Color.DARK_GRAY );
	    category.setFont(new Font("宋体", Font.BOLD, 20));
	    this.add(category).setBounds(100,120, 100,50);
	    
	    JLabel name = new JLabel("名字");
	    name.setForeground(Color.DARK_GRAY );
	    name.setFont(new Font("宋体", Font.BOLD, 20));
	    this.add( name).setBounds(100, 200, 100, 50);
	    
	    this.add(save).setBounds(160,300 ,100 , 50);
	    this.add(returned).setBounds(340,300 ,100 , 50);

	    this.add(category_textField).setBounds(150,120,300,50);
	    this.add(name_textField).setBounds(150,200,300,50);
	    
	    category_textField.setFont(new Font("宋体", Font.PLAIN, 30));
	    name_textField.setFont(new Font("宋体", Font.PLAIN, 30) );

	}
}
