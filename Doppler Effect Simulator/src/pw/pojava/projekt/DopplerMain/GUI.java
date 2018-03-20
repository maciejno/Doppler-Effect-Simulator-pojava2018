package pw.pojava.projekt.DopplerMain; /* a etykiety na x, y i f te¿ robimy?, ustawic rozmiary paneli chyba trzeba */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.print.DocFlavor.URL;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class GUI extends JPanel  implements ChangeListener, ActionListener, ItemListener {
	//panels in main panel
	JPanel pWest, pEast, pAnimation, pChart, pChartSource, pChartObserver1, pChartObserver2, pLanguage, pOptions, pControl, pObserver1,pObserver2,pSource;//panels left, right, for animation, for sinuses, for sinuses from:source and both observers, for language options, for paint panel options, for start&save button
	JPanel pNorthObserver1, pCenterObserver1, pSouthObserver1, pNorthObserver2, pCenterObserver2, pSouthObserver2, pNorthSource, pCenterSource, pSouthSource;
	
	JButton SwitchPolishButton, SwitchEnglishButton; //Language switching buttons
	JButton StartButton, SaveButton; //Powerful Buttons :D
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
		
		//tworzenie komponentów
		SwitchPolishButton = new JButton("POLSKI");
		SwitchEnglishButton = new JButton("ENGLISH");
		StartButton = new JButton("START");
		SaveButton = new JButton("ZAPISZ");
		SoundButton1 = new JButton("<)))");
		SoundButton2 = new JButton();
		
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
		
		Observer1SliderField = new JTextField(); Observer1SliderField.setColumns(4);
		Observer2SliderField = new JTextField(); Observer2SliderField.setColumns(4);
		SourceSliderField = new JTextField(); SourceSliderField.setColumns(4);
		
		Observer1XField = new JTextField(); Observer1XField.setColumns(4);
		Observer1YField = new JTextField(); Observer1YField.setColumns(4);
		Observer2XField = new JTextField(); Observer2XField.setColumns(4);
		Observer2YField = new JTextField(); Observer2YField.setColumns(4);
		SourceXField = new JTextField(); SourceXField.setColumns(4);
		SourceYField = new JTextField(); SourceYField.setColumns(4);
		SourceFreqField = new JTextField(); SourceFreqField.setColumns(5);
		SoundSpeedField = new JTextField(); SoundSpeedField.setColumns(4);
		
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
		pLanguage.setBorder(BorderFactory.createLineBorder(new Color(50,50,50)));
		pControl.setBorder(BorderFactory.createLineBorder(new Color(50,50,50)));
		pObserver1.setBorder(BorderFactory.createLineBorder(new Color(50,50,50)));
		pObserver2.setBorder(BorderFactory.createLineBorder(new Color(50,50,50)));
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
		
	}//koniec konstruktora
	
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
	
	boolean Observer1State = true;
	boolean Observer2State = false;
	

	@Override
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
		
		if(SourceSlider.getValue()!=SourceV) { //Slider obserwatora 3
			
			String SliderSourceString = new Double(SourceSlider.getValue()).toString();
			SourceSliderField.setText(SliderSourceString);
			SourceV=SourceSlider.getValue();
			}
	}
	
	@Override
	public void itemStateChanged(ItemEvent arg0) { // Listener do checkboxów
			
			Observer1State=Observer1Checkbox.isSelected();
			Observer2State=Observer2Checkbox.isSelected();
			//debugging
			System.out.print("Observer1 : ");
			System.out.println(Observer1State);
			System.out.print("Observer1 : ");
			System.out.println(Observer2State);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) { //Listener do przycisków
		
		
	}



}
