package pw.pojava.projekt.DopplerMain;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SourceAnimationPanel extends JPanel{

	public SourceAnimationPanel() {
		this.setBackground(Color.red);
	}	
	    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);             
    }  

}
