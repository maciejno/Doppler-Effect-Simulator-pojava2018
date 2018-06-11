package pw.pojava.projekt.DopplerMain;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	GUI userInterface;

	JMenuBar menuBar;
	JMenu menu;
	JMenuItem end;
	JMenuItem about;
	JMenuItem tips;
	
	static JFrame f = new JFrame();//do option pane
	
	public MainFrame() throws LineUnavailableException{		
		this.setSize(860,680);
		this.setResizable(true);//zeby rozmiar okna byl staly
		this.setMinimumSize(new Dimension(860,680));//ustawia minimalny rozmiar okna
		this.setTitle("Symulator efektu Dopplera");
		ImageIcon mainIcon = new ImageIcon(this.getClass().getResource("/MainIcon.png"));
		this.setIconImage(mainIcon.getImage());
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		userInterface = new GUI(this);
		this.add(userInterface);

		menuBar = new JMenuBar();
		menu = new JMenu("Plik");
		end = new JMenuItem("Koniec programu");
		about = new JMenuItem("O programie");
		tips = new JMenuItem("Tips & tricks");
				
		this.setJMenuBar(menuBar);
		menuBar.add(menu);
		menu.add(about);menu.add(tips);menu.add(end);		
		
		end.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
		        	dispose();	
			}			
		});
		
		//USTAWIENIE LOOKAND FEEL
		try{          
        	UIManager.setLookAndFeel ("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");            
        }catch (Exception e1){
            e1.printStackTrace();
            System.err.println("Blad podczas ustawiania LookAndFeel");
        }		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame mainFrame = null;
				try {
					mainFrame = new MainFrame();
				} catch (LineUnavailableException e) {					
					e.printStackTrace();
				}
				mainFrame.setVisible(true);
			}
		});				
	}
	public JMenuItem getEnd() {return end;};
	public JMenuItem getAbout() {return about;} 
}
