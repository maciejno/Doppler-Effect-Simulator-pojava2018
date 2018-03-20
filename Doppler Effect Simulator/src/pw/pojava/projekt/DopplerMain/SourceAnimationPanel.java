package pw.pojava.projekt.DopplerMain;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SourceAnimationPanel extends JPanel{

	public SourceAnimationPanel() {
		this.setBackground(new Color(250,250,250));
	}	
	    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);             
    }  

}
