package pw.pojava.projekt.DopplerMain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class MainAnimationPanel extends JPanel implements Runnable {
	
	List<WaveCrest> crests = new ArrayList<WaveCrest>();

	Dimension preferredSize = new Dimension(530,400);
	
	public MainAnimationPanel() {
		this.setBackground(new Color(180,180,200));
		this.setPreferredSize(preferredSize);
	}	
	    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (WaveCrest cr : crests) {
			cr.paint(g);
		}
        
    }

	@Override
	public void run() {
		
		while(true) {
		}
			
			
			
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
		crests.add(c);
	}



}
