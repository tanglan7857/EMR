package util;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class MyTable extends JTable {					//��table����д	
	public MyTable(Object[][] data, String[] columnNames) {
		super(data, columnNames);
		rowHeight = 40;
		setGridColor(Color.black);
		selectionBackground = new Color(58, 212, 248);		//����ѡ���е���ɫ
		setBackground(new Color(211, 240, 248));			//���ñ�񱳾�ɫ
		setFont(new Font("����", Font.PLAIN, 20));
		getTableHeader().setFont(new Font("����", Font.BOLD, 20));
		getTableHeader().setBackground(new Color(211, 240, 248));
	}

	public MyTable(TableModel dm) {
		super(dm);
		rowHeight = 40;
		setGridColor(Color.black);
		selectionBackground = new Color(58, 212, 248);		//����ѡ���е���ɫ
		setBackground(new Color(211, 240, 248));			//���ñ�񱳾�ɫ
		setFont(new Font("����", Font.PLAIN, 20));
		getTableHeader().setFont(new Font("����", Font.BOLD, 20));
		getTableHeader().setBackground(new Color(211, 240, 248));
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}