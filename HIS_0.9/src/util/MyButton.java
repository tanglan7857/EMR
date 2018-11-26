package util;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class MyButton extends JButton{
	ImageIcon normal;
	int weight;
	int height;
	ImageIcon click;
	int x;
	int y;
	boolean enter = false;
	
	public MyButton(ImageIcon click,ImageIcon exit, int weight, int height) {
		this.normal = click;
		this.weight = weight;
		this.height = height;
		this.click = exit;
		this.setPreferredSize(new Dimension(weight, height));
		addMouseSysle();
	}
	
	public void addMouseSysle() {
        this.addMouseListener(new MouseAdapter() {
            public void mouseExited(MouseEvent e) {
                enter = false;
                MyButton.this.setForeground(Color.BLACK);
                MyButton.this.repaint();
            }
        });
        this.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
            	MyButton.this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            	MyButton.this.setForeground(Color.white);
                enter = true;
                MyButton.this.repaint();
            }
        });
    }

	@Override
	protected void paintComponent(Graphics g) {
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		super.paintComponent(g);
		g.drawImage(normal.getImage(), 0, 0, weight, height,null);
		if(normal == null || click == null) {
            return;
        }
        if (enter) {
            g.drawImage(click.getImage(), 0, 0, weight, height ,null);
        } else {
            g.drawImage(normal.getImage(), 0, 0, weight, height ,null);
        }
		
	}
	
}
