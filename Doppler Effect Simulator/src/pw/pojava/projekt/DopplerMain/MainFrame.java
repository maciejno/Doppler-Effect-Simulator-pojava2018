package pw.pojava.projekt.DopplerMain;

import java.awt.*;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	GUI userInterface;
	
	public MainFrame() throws LineUnavailableException{		
		this.setSize(860,640);
		this.setResizable(true);//zeby rozmiar okna byl staly
		this.setMinimumSize(new Dimension(860,640));//ustawia minimalny rozmiar okna
		this.setTitle("Symulator efektu Dopplera");
		ImageIcon mainIcon = new ImageIcon(this.getClass().getResource("/MainIcon.png"));
		this.setIconImage(mainIcon.getImage());
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		userInterface = new GUI();
		this.add(userInterface);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame mainFrame = null;
				try {
					mainFrame = new MainFrame();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mainFrame.setVisible(true);
			}
		});
				
	}

}
