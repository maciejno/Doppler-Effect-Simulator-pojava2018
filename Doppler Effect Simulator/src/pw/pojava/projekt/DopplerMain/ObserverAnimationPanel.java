package pw.pojava.projekt.DopplerMain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;
//Z TEJ KLASY DZIEDZICZA 2 PANELE DO TWORZENIA OBRAZU DZWIEKU DLA OBSERWATOROW
public class ObserverAnimationPanel extends JPanel {

	Dimension preferredSize = new Dimension(530,68);
	
	public ObserverAnimationPanel() {
		this.setBackground(Color.WHITE);
		this.setPreferredSize(preferredSize);
	}	
	// jak sie dowiemy jak robic wyresy to trzeba to sprawdzic    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);             
    }  

}
