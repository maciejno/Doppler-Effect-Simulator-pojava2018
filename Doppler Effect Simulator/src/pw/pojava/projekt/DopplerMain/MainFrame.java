package pw.pojava.projekt.DopplerMain;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	//frame components
	JPanel pEast;
	JPanel pWest;
	
	int [] inty = {1,2,3};
	double [] doubles = {4,5,6};
	
	public MainFrame(){
		
		this.setSize(860,640);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

	public static void main(String[] args) {
		JFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}

}
