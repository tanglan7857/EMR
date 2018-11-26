package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.JDBCUtil;

public class Login extends JFrame{
	JTextField user_textField = new JTextField();
	JPasswordField password_textField = new JPasswordField();
    JButton login = new JButton("��¼");
	JButton exit = new JButton("�˳�");

	 private static JPanel backPanel = new JPanel() {
		 @Override
		protected void paintComponent(Graphics g) {
				ImageIcon i = new ImageIcon("img/111.jpg");
				g.drawImage(i.getImage(), 0, 0,700, 500, null);	
		}  
	   };
	
	public  Login() {	
		this.add(backPanel, BorderLayout.CENTER);
		setLayout_setcenter();
		
		this.setSize(700, 500);
		this.setResizable(false);
		this.setTitle("��¼");	
		this.setLocationRelativeTo(this.getOwner());//����λ��Ϊ��Ļ����
		this.setVisible(true);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loginAction();
			}
		});
	

		
	}
	
	public void setLayout_setcenter() {
		backPanel.setLayout(null);

	    JLabel user = new JLabel("�û���");
	    user.setForeground(Color.DARK_GRAY );
	    user.setFont(new Font("����", Font.BOLD, 20));
	    backPanel.add(user).setBounds(130,230, 100,50);
	    
	    JLabel passward = new JLabel("����");
	    passward.setForeground(Color.DARK_GRAY );
	    passward.setFont(new Font("����", Font.BOLD, 20));
	    backPanel.add( passward).setBounds(130, 320, 100, 50);
	    
	    backPanel.add(login).setBounds(200,380 ,100 , 50);
	    backPanel.add(exit).setBounds(400,380 ,100 , 50);

	    backPanel.add(user_textField).setBounds(210,230,300,50);
	    backPanel.add(password_textField).setBounds(210,320,300,50);
	    
	    user_textField.setFont(new Font("����", Font.PLAIN, 30));
	    password_textField.setFont(new Font("����", Font.PLAIN, 30) );

	}
	
	public void loginAction() {
		String user_string = user_textField.getText().trim();
		String password_string = password_textField.getText().trim();
		password_textField.setEchoChar('*');

		if(user_string.equals("")) {
			JOptionPane.showMessageDialog(null, "�û�������Ϊ�գ�");
			return;
		} else if(password_string.equals("")) {
			JOptionPane.showMessageDialog(null, "���벻��Ϊ�գ�");
			return;
		}
		
		String sql = "select * from T_User where UName=? AND UPassword=?";
		List<Object> params = Arrays.asList(user_string, password_string);
		
		try {
			if(JDBCUtil.findSimpleResult(sql,params).size() == 0) {
				JOptionPane.showMessageDialog(null, "�û������������");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Login.this.dispose();
		new HomeFrame().setVisible(true);	
	}
}
