package pw.pojava.projekt.DopplerMain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.util.List;

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

}
