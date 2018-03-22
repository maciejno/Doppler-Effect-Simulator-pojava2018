package pw.pojava.projekt.DopplerMain;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	public MainFrame(){		
		this.setSize(860,640);
		this.setResizable(false);//zeby rozmiar okna byl staly
		this.setTitle("Symulator efektu Dopplera");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.add(new GUI());
	}

	public static void main(String[] args) {
		JFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}

}
