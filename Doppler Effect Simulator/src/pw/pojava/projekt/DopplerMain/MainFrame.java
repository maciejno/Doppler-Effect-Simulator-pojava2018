package pw.pojava.projekt.DopplerMain;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	GUI userInterface;
	
	public MainFrame(){		
		this.setSize(860,640);
		this.setResizable(true);//zeby rozmiar okna byl staly
		this.setMinimumSize(new Dimension(860,640));//ustawia minimalny rozmiar okna
		this.setTitle("Symulator efektu Dopplera");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		userInterface = new GUI();
		this.add(userInterface);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame mainFrame = new MainFrame();
				mainFrame.setVisible(true);
			}
		});
				
	}

}
