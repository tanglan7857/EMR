package util;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class MyTable extends JTable {					//对table的重写	
	public MyTable(Object[][] data, String[] columnNames) {
		super(data, columnNames);
		rowHeight = 40;
		setGridColor(Color.black);
		selectionBackground = new Color(58, 212, 248);		//设置选中列的颜色
		setBackground(new Color(211, 240, 248));			//设置表格背景色
		setFont(new Font("宋体", Font.PLAIN, 20));
		getTableHeader().setFont(new Font("宋体", Font.BOLD, 20));
		getTableHeader().setBackground(new Color(211, 240, 248));
	}

	public MyTable(TableModel dm) {
		super(dm);
		rowHeight = 40;
		setGridColor(Color.black);
		selectionBackground = new Color(58, 212, 248);		//设置选中列的颜色
		setBackground(new Color(211, 240, 248));			//设置表格背景色
		setFont(new Font("宋体", Font.PLAIN, 20));
		getTableHeader().setFont(new Font("宋体", Font.BOLD, 20));
		getTableHeader().setBackground(new Color(211, 240, 248));
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}