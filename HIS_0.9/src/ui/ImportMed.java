package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import util.JDBCUtil;

public class ImportMed extends JPanel {
	JPanel treePanel = new JPanel();
	JTree tree = null;
	JButton importButton =  new JButton("����");
	JButton returned = new JButton("����");
	
	Med med = null;
	private String title;

	public ImportMed(String title, Med m) {
		this.title = title;
		this.med = m;
		this.setAbstractPanel();
	}

	public void setAbstractPanel() {
		this.setLayout(null);
		this.setBackground(new Color(117, 218, 231));
		JScrollPane js = new JScrollPane(treePanel);
		js.getVerticalScrollBar().setUnitIncrement(20);		//���Ĺ����ٶ�
		this.initTree();
		this.add(js);
		this.add(importButton);
		this.add(returned);
		
		js.setBounds(20, 20, 540, 250);
		treePanel.setPreferredSize(new Dimension(400, 400));
		importButton.setBounds(150, 300, 80, 50);
		importButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				med.homePage();
			}
		});
		returned.setBounds(350, 300, 80, 50);
		importAddlistener();
		
	}

	private void initTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("����ģ��");
		this.getNode(root);
		
		tree = new JTree(root);			
		treePanel.add(tree);
		tree.setFont(new Font("����", Font.BOLD, 30));
		//tree.setForeground(Color.red);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node  = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				String key = node.getParent() + "#" + node;	
				if(key.startsWith("����ģ��")) return;
				ImportMed.this.getInfo(key);
			}
		});
		
	}

	protected void getInfo(String key) {
		try {
			String sql = "SELECT * FROM TemplateInfo WHERE TemplateID='" + key + "'";
			Map<String, Object> map = JDBCUtil.findSimpleResult(sql, null);
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

	private void getNode(DefaultMutableTreeNode root ) {
		String sql = "select * from TemplateInfo";
		try {
			List<Map<String,Object>> result = JDBCUtil.findModeResult(sql, null);
			
			// map�洢���� keyΪ����   valueΪ��Ӧ�����µ�ID  
			Map<String, List<String>> maps = new HashMap<>();
			for(Map<String,Object> map : result) {
				String s = map.get("TemplateID".toUpperCase()).toString();
				String[] array = s.split("#");
//System.out.println(Arrays.toString(array));
				if(maps.containsKey((array[0]))) {
					maps.get(array[0]).add(array[1]);
				} else {
					List<String> tmp = new ArrayList<>();
					tmp.add(array[1]);
					maps.put(array[0], tmp);
				}
			}		
			for(String s : maps.keySet()) {
				DefaultMutableTreeNode keShi = new DefaultMutableTreeNode(s);
				root.add(keShi);
				for(String s1 : maps.get(s)) {
					keShi.add(new DefaultMutableTreeNode(s1));
				}	
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void importAddlistener() {
		returned.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				med.homePage();
			}
		});
		
	}
	
	

}
