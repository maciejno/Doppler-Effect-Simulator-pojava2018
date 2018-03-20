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
	JPanel pWest, pEast, pAnimation, pChart, pChartSource, pChartObserver1, pChartObserver2, pLanguage, pOptions, pControl;//panels left, right, for animation, for sinuses, for sinuses from:source and both observers, for language options, for paint panel options, for start&save button
	
	JButton SwitchPolishButton, SwitchEnglishButton; //Language switching buttons
	JButton StartButton, SaveButton; //Powerful Buttons :D
	JButton SoundButton1, SoundButton2;
	
	JCheckBox Observer1Checkbox, Observer2Checkbox; // Observers CheckBoxes
	JSlider Observer1Slider, Observer2Slider, SourceSlider;//Sliders for speed of objests
	static final int ObserverSlider_MIN = -21;
	static final int ObserverSlider_MAX = 21; //Parameters for Spectator Speed Sliders
	static final int SourceSlider_MIN = 0;
	static final int SourceSlider_MAX = 42; //Parameters for Source Speed slider
	static final int Slider_INIT = 0; //Initial parameter for sliders

	
	JTextField Observer1XField, Observer1YField, Observer2XField, Observer2YField;//TextFields for Spectators' parameters
	JTextField SourceXField, SourceYField, SourceFreqField; //TextFields for Source parameters
	JTextField SoundSpeedField; //TextField for speed of Sound
	JTextField Observer1SliderField, Observer2SliderField, SourceSliderField; //text fields for obs1,obs2, soutce values
	
	JLabel ObserverMainLabel, SourceMainLabel;//Title labels
	JLabel ValueXLabel, ValueYLabel; // uniwersal labels for "X:" and "Y:"
	JLabel SliderLabel1, SliderLabel2;//Slider labels
	JLabel SoundSpeedLabel; // sound speed label
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
		Observer1Slider.setMajorTickSpacing(10); //wiêcej kosmetyki
		Observer1Slider.setMinorTickSpacing(1);
		Observer1Slider.setPaintTicks(true);
		Observer1Slider.setPaintLabels(true);
		Observer1Slider.setFont(SliderFont);
		Observer2Slider = new JSlider(JSlider.HORIZONTAL, ObserverSlider_MIN, ObserverSlider_MAX, Slider_INIT);
		Observer2Slider.setMajorTickSpacing(10);
		Observer2Slider.setMinorTickSpacing(1);
		Observer2Slider.setPaintTicks(true);
		Observer2Slider.setPaintLabels(true);
		Observer2Slider.setFont(SliderFont);
		SourceSlider = new JSlider(JSlider.HORIZONTAL, SourceSlider_MIN, SourceSlider_MAX, Slider_INIT);
		SourceSlider.setMajorTickSpacing(10);
		SourceSlider.setMinorTickSpacing(1);
		SourceSlider.setPaintTicks(true);
		SourceSlider.setPaintLabels(true);
		SourceSlider.setFont(SliderFont);
		
		Observer1SliderField = new JTextField();
		Observer2SliderField = new JTextField();
		SourceSliderField = new JTextField();
		
		Observer1XField = new JTextField();
		Observer1YField = new JTextField();
		Observer2XField = new JTextField();
		Observer2YField = new JTextField();
		SourceXField = new JTextField();
		SourceYField = new JTextField();
		SourceFreqField = new JTextField();
		SoundSpeedField = new JTextField();
		
		ObserverMainLabel = new JLabel("Obserwatorzy");
		ValueXLabel = new JLabel("X:");
		ValueYLabel = new JLabel("Y:");
		SourceMainLabel = new JLabel("Zrodlo");	
		SliderLabel1 = new JLabel("v:");
		SliderLabel2 = new JLabel("m/s");
		SoundSpeedLabel = new JLabel("Speed of sound");
		FreqLabel1 = new JLabel("f:");	
		FreqLabel2 = new JLabel("Hz");
		
		//ustawia layout managery do paneli
		this.setLayout(new GridLayout(1,2));//sets layout for main panel
		pWest.setLayout(new GridLayout(2,1));
		pControl.setBorder(BorderFactory.createLineBorder(new Color(50,50,50)));
		pEast.setLayout(new BorderLayout());
		pChart.setLayout(new GridLayout(3,1));
		pOptions.setLayout(new GridLayout(11,7));
		pLanguage.setLayout(new FlowLayout(FlowLayout.TRAILING));//trailing ustawia z prawej strony
		pControl.setLayout(new FlowLayout()); //ustawienia layoutu dla panelu pControl
		
		JLabel HackLabel = new JLabel(""); //HACKLABEL
		
		//wstawianie paneli w panele
		//dodaje i ustawia 2 panele: lewy i prawy do glownego panelu
		this.add(pWest);
		this.add(pEast);
		//dodaje i ustawia panele do lewego panelu
		pWest.add(pAnimation);
		pWest.add(pChart);
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
				
		//kolejnoœæ taka jak ma byæ
		//NIE RUSZAÆ BO SIE ROZLECI
		pOptions.add(HackLabel);
		pOptions.add(ObserverMainLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		
		pOptions.add(Observer1Checkbox);
		pOptions.add(SoundButton1);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		
		pOptions.add(ValueXLabel);
		pOptions.add(Observer1XField);
		pOptions.add(ValueYLabel);
		pOptions.add(Observer1YField);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		
		pOptions.add(SliderLabel1);
		pOptions.add(Observer1Slider);
		pOptions.add(Observer1SliderField);
		pOptions.add(SliderLabel2);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		
		pOptions.add(Observer2Checkbox);
		pOptions.add(SoundButton2);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		
		pOptions.add(ValueXLabel);
		pOptions.add(Observer2XField);
		pOptions.add(ValueYLabel);
		pOptions.add(Observer2YField);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		
		pOptions.add(SliderLabel1);
		pOptions.add(Observer2Slider);
		pOptions.add(Observer2SliderField);
		pOptions.add(SliderLabel2);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		
		pOptions.add(HackLabel);
		pOptions.add(SourceMainLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);

		pOptions.add(ValueXLabel);
		pOptions.add(SourceXField);
		pOptions.add(ValueYLabel);
		pOptions.add(SourceYField);
		pOptions.add(FreqLabel1);
		pOptions.add(SourceFreqField);
		pOptions.add(FreqLabel2);
		
		pOptions.add(SliderLabel1);
		pOptions.add(SourceSlider);
		pOptions.add(SourceSliderField);
		pOptions.add(SliderLabel2);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		
		pOptions.add(SoundSpeedLabel);
		pOptions.add(SoundSpeedField);
		pOptions.add(SliderLabel2);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		pOptions.add(HackLabel);
		
		
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
