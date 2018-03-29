package pw.pojava.projekt.DopplerMain;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class GUI extends JPanel  implements ChangeListener, ActionListener, ItemListener, KeyListener {
	
	//zmienne przechowuj¹ce nastawy komponentów
	double Observer1X = 0; 
	double Observer1Y = 0;
	double Observer1V = 0;
	double Observer2X = 0;
	double Observer2Y = 0;
	double Observer2V = 0;
	double SourceX = 0;
	double SourceY = 0;
	double SourceV = 0;
	double SoundSpeed = 0;
	double SoundFreq = 0;
	
	boolean Observer1State = true;
	boolean Observer2State = false;
		
	
	//panels in main panel
	JPanel pWest, pEast, pChart, pLanguage, pOptions, pControl, pObserver1,pObserver2,pSource;//panels left, right, for animation, for sinuses, for sinuses from:source and both observers, for language options, for paint panel options, for start&save button
	JPanel pNorthObserver1, pCenterObserver1, pSouthObserver1, pNorthObserver2, pCenterObserver2, pSouthObserver2, pNorthSource, pCenterSource, pSouthSource;
	
	MainAnimationPanel pAnimation;
	SourceAnimationPanel pChartSource;
	Observer1AnimationPanel pChartObserver1;
	Observer2AnimationPanel pChartObserver2;
	
	JButton SwitchPolishButton, SwitchEnglishButton; //przyciski do zmiany jezyka
	JButton StartButton, SaveButton; //przyciski ktore maja moc sprawcza :D
	JButton SoundButton1, SoundButton2;
	
	JCheckBox Observer1Checkbox, Observer2Checkbox; // Observers CheckBoxes
	JSlider Observer1Slider, Observer2Slider, SourceSlider;//Sliders for speed of objests
	static final int ObserverSlider_MIN = -1000;
	static final int ObserverSlider_MAX = 1000; //Parameters for Spectator Speed Sliders
	static final int SourceSlider_MIN = -1000;
	static final int SourceSlider_MAX = 1000; //Parameters for Source Speed slider
	static final int Slider_INIT = 0; //Initial parameter for sliders

	
	JTextField Observer1XField, Observer1YField, Observer2XField, Observer2YField;//TextFields for Spectators' parameters
	JTextField SourceXField, SourceYField, SourceFreqField; //TextFields for Source parameters
	JTextField SoundSpeedField; //TextField for speed of Sound
	JTextField Observer1SliderField, Observer2SliderField, SourceSliderField; //text fields for obs1,obs2, soutce values
	
	JLabel ObserverMainLabel, SourceMainLabel;//Title labels
	JLabel ValueXObserver1Label, ValueYObserver1Label, ValueXObserver2Label, ValueYObserver2Label, ValueXSourceLabel, ValueYSourceLabel;
	JLabel SliderObserver1LabelV, SliderObserver1LabelMS, SliderObserver2LabelV, SliderObserver2LabelMS, SliderSourceLabelV, SliderSourceLabelMS;//Slider labels
	JLabel SoundSpeedLabel, SoundSpeedLabelMS; // sound speed label
	JLabel FreqLabel1, FreqLabel2;

	public GUI() {
		
		//tworzy panele
		pWest = new JPanel();
		pEast = new JPanel();
		pAnimation = new MainAnimationPanel();
		pChart = new JPanel();
		pChartSource = new SourceAnimationPanel(); 
		pChartObserver1 = new Observer1AnimationPanel();
		pChartObserver2 = new Observer2AnimationPanel();
		pLanguage = new JPanel();
		pOptions = new JPanel();
		pControl = new JPanel();
		
		//to bedzie w panelu Options
		pObserver1 = new JPanel();
		pObserver2 = new JPanel();
		pSource = new JPanel(); 
		
		pNorthObserver1 = new JPanel();
		pCenterObserver1 = new JPanel();
		pSouthObserver1 = new JPanel();
		
		pNorthObserver2 = new JPanel();
		pCenterObserver2 = new JPanel();
		pSouthObserver2 = new JPanel();
		
		pNorthSource = new JPanel();
		pCenterSource = new JPanel();
		pSouthSource = new JPanel();
		
		//ikonki
		ImageIcon polish = new ImageIcon("C:\\Users\\Jakub\\git\\doppler-effect-simulator-pojava2018\\Doppler Effect Simulator\\src\\pw\\pojava\\projekt\\DopplerMain\\icons\\polish.png");
		ImageIcon english = new ImageIcon("C:\\Users\\Jakub\\git\\doppler-effect-simulator-pojava2018\\Doppler Effect Simulator\\src\\pw\\pojava\\projekt\\DopplerMain\\icons\\english.png");
		ImageIcon audio = new ImageIcon("C:\\Users\\Jakub\\git\\doppler-effect-simulator-pojava2018\\Doppler Effect Simulator\\src\\pw\\pojava\\projekt\\DopplerMain\\icons\\audio.png");
		ImageIcon start = new ImageIcon("C:\\Users\\Jakub\\git\\doppler-effect-simulator-pojava2018\\Doppler Effect Simulator\\src\\pw\\pojava\\projekt\\DopplerMain\\icons\\start.png");
		ImageIcon zapisz = new ImageIcon("C:\\Users\\Jakub\\git\\doppler-effect-simulator-pojava2018\\Doppler Effect Simulator\\src\\pw\\pojava\\projekt\\DopplerMain\\icons\\zapisz.png");
		
		//tworzenie komponentów
		SwitchPolishButton = new JButton(polish);
		SwitchEnglishButton = new JButton(english);
		StartButton = new JButton(start);
		SaveButton = new JButton(zapisz);
		SoundButton1 = new JButton(audio);
		SoundButton2 = new JButton(audio);
				
		Observer1Checkbox = new JCheckBox("Obserwator 1"); Observer1Checkbox.setSelected(true);
		Observer2Checkbox = new JCheckBox("Obserwator 2");
			
		//ustawienia sliderów
		Font SliderFont = new Font("Calibri", Font.BOLD, 11); //kosmetyka
		Observer1Slider = new JSlider(JSlider.HORIZONTAL, ObserverSlider_MIN, ObserverSlider_MAX, Slider_INIT);
		Observer1Slider.setMajorTickSpacing(500); //wiêcej kosmetyki
		Observer1Slider.setMinorTickSpacing(250);
		Observer1Slider.setPaintTicks(true);
		Observer1Slider.setPaintLabels(true);
		Observer1Slider.setFont(SliderFont);
		Observer2Slider = new JSlider(JSlider.HORIZONTAL, ObserverSlider_MIN, ObserverSlider_MAX, Slider_INIT);
		Observer2Slider.setMajorTickSpacing(500);
		Observer2Slider.setMinorTickSpacing(250);
		Observer2Slider.setPaintTicks(true);
		Observer2Slider.setPaintLabels(true);
		Observer2Slider.setFont(SliderFont);
		SourceSlider = new JSlider(JSlider.HORIZONTAL, SourceSlider_MIN, SourceSlider_MAX, Slider_INIT);
		SourceSlider.setMajorTickSpacing(500);
		SourceSlider.setMinorTickSpacing(250);
		SourceSlider.setPaintTicks(true);
		SourceSlider.setPaintLabels(true);
		SourceSlider.setFont(SliderFont);
		
		Observer1SliderField = new JTextField("0"); Observer1SliderField.setColumns(4);//ustawia rozmiar pola tekstowego
		Observer2SliderField = new JTextField("0"); Observer2SliderField.setColumns(4);
		SourceSliderField = new JTextField("0"); SourceSliderField.setColumns(4);
		
		Observer1XField = new JTextField("0"); Observer1XField.setColumns(4);
		Observer1YField = new JTextField("0"); Observer1YField.setColumns(4);
		Observer2XField = new JTextField("0"); Observer2XField.setColumns(4);
		Observer2YField = new JTextField("0"); Observer2YField.setColumns(4);
		SourceXField = new JTextField("0"); SourceXField.setColumns(4);
		SourceYField = new JTextField("0"); SourceYField.setColumns(4);
		SourceFreqField = new JTextField("0"); SourceFreqField.setColumns(5);
		SoundSpeedField = new JTextField("0"); SoundSpeedField.setColumns(4);
		
		ObserverMainLabel = new JLabel("Obserwatorzy");
		ValueXObserver1Label = new JLabel("X:");
		ValueYObserver1Label = new JLabel("Y:");
		ValueXObserver2Label = new JLabel("X:");
		ValueYObserver2Label = new JLabel("Y:");
		ValueXSourceLabel = new JLabel("X:");
		ValueYSourceLabel = new JLabel("Y:");
		SourceMainLabel = new JLabel("Zrodlo");	
		SliderObserver1LabelV = new JLabel("v:");
		SliderObserver1LabelMS = new JLabel("m/s");
		SliderObserver2LabelV = new JLabel("v:");
		SliderObserver2LabelMS = new JLabel("m/s");
		SliderSourceLabelV = new JLabel("v:");
		SliderSourceLabelMS = new JLabel("m/s");
		SoundSpeedLabel = new JLabel("Predkosc dzwieku:");
		SoundSpeedLabelMS = new JLabel("m/s");
		FreqLabel1 = new JLabel("f:");	
		FreqLabel2 = new JLabel("Hz");
		
		//ustawia layout managery do paneli
		this.setLayout(new BorderLayout());//sets layout for main panel
		pWest.setLayout(new BorderLayout());
		
		pEast.setLayout(new BorderLayout());
		pChart.setLayout(new GridLayout(3,1));
		pOptions.setLayout(new GridLayout(3,1));
		pLanguage.setLayout(new FlowLayout(FlowLayout.TRAILING));//trailing ustawia z prawej strony
		pControl.setLayout(new FlowLayout()); //ustawienia layoutu dla panelu pControl
		
		pObserver1.setLayout(new GridLayout(3,1));
		pObserver2.setLayout(new GridLayout(3,1));
		pSource.setLayout(new GridLayout(3,1));
		pNorthObserver1.setLayout(new FlowLayout(FlowLayout.LEFT));
		pCenterObserver1.setLayout(new FlowLayout(FlowLayout.LEFT));
		pSouthObserver1.setLayout(new FlowLayout(FlowLayout.LEFT));
		pNorthObserver2.setLayout(new FlowLayout(FlowLayout.LEFT));
		pCenterObserver2.setLayout(new FlowLayout(FlowLayout.LEFT));
		pSouthObserver2.setLayout(new FlowLayout(FlowLayout.LEFT));
		pNorthSource.setLayout(new FlowLayout(FlowLayout.LEFT));
		pCenterSource.setLayout(new FlowLayout(FlowLayout.LEFT));
		pSouthSource.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		//ramki do paneli
		pLanguage.setBorder(BorderFactory.createTitledBorder(" "));
		pControl.setBorder(BorderFactory.createTitledBorder(" "));
		pObserver1.setBorder(BorderFactory.createTitledBorder(" "));
		pObserver2.setBorder(BorderFactory.createTitledBorder(" "));
		pSource.setBorder(BorderFactory.createTitledBorder("Zrodlo"));
		pChartSource.setBorder(BorderFactory.createTitledBorder("Dzwiek ze zrodla"));
		pChartObserver1.setBorder(BorderFactory.createTitledBorder("Dzwiek docierajacy do Obserwatora1"));
		pChartObserver2.setBorder(BorderFactory.createTitledBorder("Dzwiek docierajacy do Obserwatora2"));
		//wstawianie paneli w panele
		//dodaje i ustawia 2 panele: lewy i prawy do glownego panelu
		this.add(BorderLayout.WEST, pWest);
		this.add(BorderLayout.EAST, pEast);
		//dodaje i ustawia panele do lewego panelu
		pWest.add(BorderLayout.NORTH, pAnimation);
		pWest.add(BorderLayout.SOUTH, pChart);
		//dodaje i ustawia panele do prawego panelu
		pEast.add(BorderLayout.NORTH, pLanguage);
		pEast.add(BorderLayout.CENTER, pOptions);
		pEast.add(BorderLayout.SOUTH, pControl);
		//dodaje i ustawia panele z wykresami do panelu pChart
		pChart.add(pChartSource);
		pChart.add(pChartObserver1);
		pChart.add(pChartObserver2);		
		
		//wstawianie komponentów do paneli
		pLanguage.add(SwitchPolishButton);
		pLanguage.add(SwitchEnglishButton);
		
			//3 panele do pOptions, do kazdego z tych paneli 3 panele, a do nich komponenty			
		pOptions.add(pObserver1);
		pOptions.add(pObserver2);
		pOptions.add(pSource);
		
		pObserver1.add(pNorthObserver1);
		pObserver1.add(pCenterObserver1);
		pObserver1.add(pSouthObserver1);
		
		pObserver2.add(pNorthObserver2);
		pObserver2.add(pCenterObserver2);
		pObserver2.add(pSouthObserver2);
		
		pSource.add(pNorthSource);
		pSource.add(pCenterSource);
		pSource.add(pSouthSource);
		
		pNorthObserver1.add(Observer1Checkbox);
		pNorthObserver1.add(SoundButton1);
		pCenterObserver1.add(ValueXObserver1Label);
		pCenterObserver1.add(Observer1XField);
		pCenterObserver1.add(ValueYObserver1Label);
		pCenterObserver1.add(Observer1YField);
		pSouthObserver1.add(SliderObserver1LabelV);
		pSouthObserver1.add(Observer1Slider);
		pSouthObserver1.add(Observer1SliderField);
		pSouthObserver1.add(SliderObserver1LabelMS);
		
		pNorthObserver2.add(Observer2Checkbox);
		pNorthObserver2.add(SoundButton2);
		pCenterObserver2.add(ValueXObserver2Label);
		pCenterObserver2.add(Observer2XField);
		pCenterObserver2.add(ValueYObserver2Label);
		pCenterObserver2.add(Observer2YField);
		pSouthObserver2.add(SliderObserver2LabelV);
		pSouthObserver2.add(Observer2Slider);
		pSouthObserver2.add(Observer2SliderField);
		pSouthObserver2.add(SliderObserver2LabelMS);
		
		pNorthSource.add(ValueXSourceLabel);
		pNorthSource.add(SourceXField);
		pNorthSource.add(ValueYSourceLabel);
		pNorthSource.add(SourceYField);
		pNorthSource.add(FreqLabel1);
		pNorthSource.add(SourceFreqField);
		pNorthSource.add(FreqLabel2);
		pCenterSource.add(SliderSourceLabelV);
		pCenterSource.add(SourceSlider);
		pCenterSource.add(SourceSliderField);
		pCenterSource.add(SliderSourceLabelMS);
		pSouthSource.add(SoundSpeedLabel);
		pSouthSource.add(SoundSpeedField);
		pSouthSource.add(SoundSpeedLabelMS);
		
		// a tu juz oddzielny panel na 2 przyciski
		pControl.add(StartButton);
		pControl.add(SaveButton); 
									
		//LISTENERY
		Observer1Checkbox.addItemListener(this); // dodawanie listenerów do Checkboxów
		Observer2Checkbox.addItemListener(this);
		
		Observer1Slider.addChangeListener(this); //dodawanie listenerów do sliderów
		Observer2Slider.addChangeListener(this);
		SourceSlider.addChangeListener(this);
		
		Observer1XField.addKeyListener(this);
		Observer1YField.addKeyListener(this);
		Observer2XField.addKeyListener(this);
		Observer2YField.addKeyListener(this);
		SourceXField.addKeyListener(this);
		SourceYField.addKeyListener(this);
		SourceFreqField.addKeyListener(this);
		SoundSpeedField.addKeyListener(this);
		
		Observer1SliderField.addKeyListener(this);
		Observer2SliderField.addKeyListener(this);
		SourceSliderField.addKeyListener(this);
		
	}//koniec konstruktora

	public void stateChanged(ChangeEvent arg0) { //listener do Sliderów
		
		if(Observer1Slider.getValue()!=Observer1V) { //Slider obserwatora 1			
			String Slider1String = new Double(Observer1Slider.getValue()).toString();
			Observer1SliderField.setText(Slider1String);
			Observer1V=Observer1Slider.getValue();
		}		
		if(Observer2Slider.getValue()!=Observer2V) { //Slider obserwatora 2			
			String Slider2String = new Double(Observer2Slider.getValue()).toString();
			Observer2SliderField.setText(Slider2String);
			Observer2V=Observer2Slider.getValue();
		}		
		if(SourceSlider.getValue()!=SourceV) { //Slider zrodla			
			String SliderSourceString = new Double(SourceSlider.getValue()).toString();
			SourceSliderField.setText(SliderSourceString);
			SourceV=SourceSlider.getValue();
			}
	}
	
	public void itemStateChanged(ItemEvent arg0) { // Listener do checkboxów
			
			Observer1State=Observer1Checkbox.isSelected();
			Observer2State=Observer2Checkbox.isSelected();
			//debugging
			System.out.print("Observer1 : ");
			System.out.println(Observer1State);
			System.out.print("Observer1 : ");
			System.out.println(Observer2State);
	}
	
	//DOPRAWIC - jak jest puste pole tekstowe ma przypisywac 0
	//metoda obslugujaca wyjatek
	static boolean isTextFieldEmpty(JTextField triedTextField) throws EmptyTextFieldException{
		if (triedTextField.getText()==null) 
			throw new EmptyTextFieldException(triedTextField);
		return true;
	}
	
	//listener do pol tekstowych
	public void keyReleased(KeyEvent arg0) { //cos tu nie do konca dziala, bo musi byc cos wpisane w Ob1X zeby inne dzialaly
		try {//DEBUGGING - wypisuje do konsoli co sie wpisalo do zmiennej
			//listenery do pol tekstowych
			try {
				if(Double.parseDouble(Observer1XField.getText())!=Observer1X) {
					isTextFieldEmpty(Observer1XField);
					Observer1X = Double.parseDouble(Observer1XField.getText());	
					System.out.println("O1x:" + Observer1X);
				}
				else if(Double.parseDouble(Observer1YField.getText())!=Observer1Y) {
					Observer1Y = Double.parseDouble(Observer1YField.getText());	
					System.out.println("O1y:" + Observer1Y);
				}	
				else if(Double.parseDouble(Observer2XField.getText())!=Observer2X) {
					Observer2X = Double.parseDouble(Observer2XField.getText());	
					System.out.println("O2x:" + Observer2X);
				}
				else if(Double.parseDouble(Observer2YField.getText())!=Observer2Y) {
					Observer2Y = Double.parseDouble(Observer2YField.getText());	
					System.out.println("O2y:" + Observer2Y);
				}
				else if(Double.parseDouble(SourceXField.getText())!=SourceX) {
					SourceX = Double.parseDouble(SourceXField.getText());		
					System.out.println("Sx:" + SourceX);
				}
				else if(Double.parseDouble(SourceYField.getText())!=SourceY) {
					SourceY = Double.parseDouble(SourceYField.getText());	
					System.out.println("Sy:" + SourceY);
				}
				else if(Double.parseDouble(SourceFreqField.getText())!=SoundFreq) {
					SoundFreq = Double.parseDouble(SourceFreqField.getText());	
					System.out.println("Sf:" + SoundFreq);
				}
				else if(Double.parseDouble(SoundSpeedField.getText())!=SoundSpeed) {
					SoundSpeed = Double.parseDouble(SoundSpeedField.getText());	
					System.out.println("Ss:" + SoundSpeed);
				}
				else if(Double.parseDouble(Observer1SliderField.getText())!=Observer1V) {
					Observer1V = Double.parseDouble(Observer1SliderField.getText());
					Observer1Slider.setValue(Integer.parseInt(Observer1SliderField.getText()));
					System.out.println("O1V:" + Observer1V);
				}
				else if(Double.parseDouble(Observer2SliderField.getText())!=Observer2V) {
					Observer2V = Double.parseDouble(Observer2SliderField.getText());
					Observer2Slider.setValue(Integer.parseInt(Observer2SliderField.getText()));
					System.out.println("O2V:" + Observer2V);
				}
				else if(Double.parseDouble(SourceSliderField.getText())!=SourceV) {
					SourceV = Double.parseDouble(SourceSliderField.getText());
					SourceSlider.setValue(Integer.parseInt(SourceSliderField.getText()));
					System.out.println("SV:" + SourceV);
				}
			}
			catch(EmptyTextFieldException e) {
				System.err.println("Puste pole tekstowe");
			}
		}
		catch(Exception e) {
			System.err.println("Blad key listener!");
		}
}


	public void actionPerformed(ActionEvent arg0) {} //Listener do przycisków
	public void keyPressed(KeyEvent arg0) {	}
	public void keyTyped(KeyEvent arg0) {}

	public void setObserver1XField(Double val) {Observer1XField.setText(val.toString());}

}
