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
	AnimationObject observer1, observer2, source;
	

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
			
			
			
			
			if(true)
				break;
		}
	}
	public void addObserver1(int vx, int vy, double x, double y) {
		observer1 = new AnimationObject();
		observer1.setX(x);
		observer1.setY(y);
		observer1.setVx(vx);
		observer1.setVy(vy);		
	}
	public void addObserver2(int vx, int vy, double x, double y, Color color) {
		observer2 = new AnimationObject();
		observer2.setX(x);
		observer2.setY(y);
		observer2.setVx(vx);
		observer2.setVy(vy);
	}
	public void addSource(int vx, int vy, double x, double y, Color color) {
		source = new AnimationObject();
		source.setX(x);
		source.setY(y);
		source.setVx(vx);
		source.setVy(vy);
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

	

