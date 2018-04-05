package pw.pojava.projekt.DopplerMain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import pl.edu.pw.fizyka.java.lab7.zadanie2.Prostokat;





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
			
			
			if(true)
			break;
		}
		
	}

	



}
