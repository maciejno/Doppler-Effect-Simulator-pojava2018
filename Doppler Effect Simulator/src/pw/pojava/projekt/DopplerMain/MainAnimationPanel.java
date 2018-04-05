package pw.pojava.projekt.DopplerMain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;



public class MainAnimationPanel extends JPanel implements Runnable {
	
	

	Dimension preferredSize = new Dimension(530,400);
	
	public MainAnimationPanel() {
		this.setBackground(new Color(180,180,200));
		this.setPreferredSize(preferredSize);
	}	
	    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
    }

	@Override
	public void run() {
		
	}

	public void addObserver1(int vx, int vy, double x, double y, Color color) {
		
	}
	public void addObserver2(int vx, int vy, double x, double y, Color color) {
		
	}
	public void addSource(int vx, int vy, double x, double y, Color color) {
		
	}
	public void addWaveCrest(int v, double x, double y, double r) {
		WaveCrest c = new WaveCrest();
		c.setX(x);
		c.setY(y);
		c.setV(v);
		c.setR(r);
		
		Crests.add(c);
	}
}
