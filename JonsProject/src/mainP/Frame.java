package mainP;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


@SuppressWarnings("serial")
public class Frame extends JPanel implements MouseListener, KeyListener{
	
	public Sudoku sk=new Sudoku(this);
	
	
	

	public Frame() throws InterruptedException {
		
		
		
		
		JFrame jf=new JFrame("Jon's Project");
		
		jf.add(this);
		jf.setSize(600, 600);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.addMouseListener(this);
		jf.addKeyListener(this);
		this.setBackground(Color.GRAY);
		
		while (true) {
			repaint();
			Thread.sleep(10);
			update();
		}
		}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		drawGraphics(g2d);
		
	}
	public void drawGraphics(Graphics2D g2d) {
		
		sk.drawGrid(g2d);
		
	}
	public void update() {
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		sk.mousePressed(e.getX(), e.getY());
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		sk.keyPressed(e);
		
		
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
