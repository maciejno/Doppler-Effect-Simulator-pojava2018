package pw.pojava.projekt.DopplerMain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;
//Z TEJ KLASY DZIEDZICZA 2 PANELE DO TWORZENIA OBRAZU DZWIEKU DLA OBSERWATOROW
public class ObserverAnimationPanel extends JPanel {

	public ObserverAnimationPanel() {
		this.setBackground(Color.WHITE);
	}	
	// jak sie dowiemy jak robic wyresy to trzeba to sprawdzic    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);             
    }  

}
