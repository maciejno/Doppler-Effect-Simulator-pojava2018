package pw.pojava.projekt.DopplerMain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


public class MainAnimationPanel extends JPanel implements Runnable {
	
	List<WaveCrest> crests = new ArrayList<WaveCrest>();
	AnimationObject observer1, observer2, source;
	boolean isObserver1=true;
	boolean isObserver2=false;
	double refreshRate = 0.04; //[ms] -> 25FPS ???
	int soundSpeed=0; //przechowuje robocza predkosc dzwieku
	int soundFreq=0;
	
	Dimension preferredSize = new Dimension(530,400);
	
	public MainAnimationPanel() {
		this.setBackground(new Color(200,255,255));
		this.setPreferredSize(preferredSize);
		
		observer1 = new AnimationObject();
		observer2 = new AnimationObject();
		source = new AnimationObject();
		source.setAppearance(true); //zrodlo jest zawsze.
	}	
	
	    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        observer1.setAppearance(isObserver1); //ustawia istnienie obserwatorów
        observer2.setAppearance(isObserver2);
        
        observer1.paint(g);
        observer2.paint(g);
        source.paint(g);
        
        if(crests.isEmpty()==false)
        {
        	for (WaveCrest cr : crests) {
        		cr.paint(g);
			}   
        }
    }
    
	@Override
	public void run() {
		int counter=0; //licznik ustawiajacy grzbiety fal
		while(true) 
		{
			if((counter%100)==0) { // tworzy grzbiet
			WaveCrest crN = new WaveCrest();
			crN.setV(soundSpeed);
			crN.setX(source.getX());
			crN.setY(source.getY());
			crests.add(crN);
			}
			
			observer1.setX(observer1.getX()+observer1.getVx()*refreshRate);
			observer1.setY(observer1.getY()+observer1.getVy()*refreshRate);
			observer2.setX(observer2.getX()+observer2.getVx()*refreshRate);
			observer2.setY(observer2.getY()+observer2.getVy()*refreshRate);
			source.setX(source.getX()+source.getVx()*refreshRate);
			source.setY(source.getY()+source.getVy()*refreshRate);
			
			for (WaveCrest cr : crests) {
				cr.setR(cr.getR()+cr.getV()*refreshRate);
			}

			repaint();
			
			try {
				Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
			counter=counter+40;
			if((((observer1.getX()>this.getWidth())||(observer1.getX()<0)||(observer1.getVx()==0))&&((observer2.getX()>this.getWidth())||(observer2.getX()<0)||(observer2.getVx()==0))&&((source.getX()>this.getWidth())||(source.getX()<0)||(source.getVx()==0)))||(counter>250000)) {
				counter=0;
				System.out.println("statement...");
				break;
			}
		}
		System.out.println("End of animation");
		
	}
	
	public void setSoundSpeed(int v) {
		soundSpeed=v;
	}
	
	public void setFrequency(int freq) {
		soundFreq=freq;
	}
	
	/*
	 *juz zaimplementowane
	 *
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
	*/
}

	

