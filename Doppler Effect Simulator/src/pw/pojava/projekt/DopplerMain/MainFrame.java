package pw.pojava.projekt.DopplerMain;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	//frame components
	JPanel pEast;
	JPanel pWest;
	
	int [] inty = {42,43,44};
	double [] doubles = {7,9,8,96};
	
	public MainFrame(){
		
		this.setSize(860,640);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

	public static void main(String[] args) {
		JFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}

}
