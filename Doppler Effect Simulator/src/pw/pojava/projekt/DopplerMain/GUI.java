package pw.pojava.projekt.DopplerMain; /* a etykiety na x, y i f te¿ robimy?, ustawic rozmiary paneli chyba trzeba */

import java.awt.*;

import javax.swing.*;

public class GUI extends JPanel {
	//panels in main panel
	JPanel pWest, pEast, pAnimation, pChart, pChartSource, pChartObserver1, pChartObserver2, pLanguage, pOptions, pControl;//panels left, right, for animation, for sinuses, for sinuses from:source and both observers, for language options, for paint panel options, for start&save button
	
	JButton SwitchPolishButton, SwitchEnglishButton; //Language switching buttons
	JButton StartButton, SaveButton; //Powerful Buttons :D
	
	JCheckBox Observer1Checkbox, Observer2Checkbox; // Observers CheckBoxes
	JSlider Observer1Slider, Observer2Slider, SourceSlider;//Sliders for speed of objests
	static final int ObserverSlider_MIN = -42;
	static final int ObserverSlider_MAX = 42; //Parameters for Spectator Speed Sliders
	static final int SourceSlider_MIN = -100;
	static final int SourceSlider_MAX = 100; //Parameters for Source Speed slider
	static final int Slider_INIT = 0; //Initial parameter for sliders

	
	JTextField Observer1XField, Observer1YField, Observer2XField, Observer2YField;//TextFields for Spectators' parameters
	JTextField SourceXField, SourceYField, SourceFreqField; //TextFields for Source parameters
	JTextField SoundSpeedField; //TextField for speed of Sound
	JTextField Observer1SliderField, Observer2SliderField, SourceSliderField;
	
	JLabel ObserverMainLabel, SourceMainLabel;//Title labels
	JLabel Observer1SliderLabel, Observer2SliderLabel, SourceSliderLabel;//Slider labels
	JLabel SoundSpeedLabel;

	public GUI() {
		this.setLayout(new GridLayout(1,2));//sets layout for main panel
		
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
		
		Observer1Checkbox = new JCheckBox("Obserwator 1");
		Observer2Checkbox = new JCheckBox("Obserwator 2");
		
		Observer1Slider = new JSlider(JSlider.HORIZONTAL, ObserverSlider_MIN, ObserverSlider_MAX, Slider_INIT);
		Observer2Slider = new JSlider(JSlider.HORIZONTAL, ObserverSlider_MIN, ObserverSlider_MAX, Slider_INIT);
		SourceSlider = new JSlider(JSlider.HORIZONTAL, SourceSlider_MIN, SourceSlider_MAX, Slider_INIT);
		
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
		SourceMainLabel = new JLabel("Zrodlo");	
		Observer1SliderLabel = new JLabel("v:");
		Observer2SliderLabel = new JLabel("v:");
		SourceSliderLabel = new JLabel("v:");
		SoundSpeedLabel = new JLabel("Speed of sound[m/s]");
		
		//ustawianie layoutów
		pLanguage.setLayout(new FlowLayout(FlowLayout.TRAILING));//trailing ustawia z prawej strony
		pControl.setLayout(new FlowLayout()); //ustawienia layoutu dla panelu pControl
		
		//ustawia layout managery do paneli
		pWest.setLayout(new GridLayout(2,1));
		pControl.setBorder(BorderFactory.createLineBorder(new Color(50,50,50)));
		pEast.setLayout(new BorderLayout());
		pChart.setLayout(new GridLayout(3,1));
		pOptions.setLayout(new GridLayout(9,4));
		
		
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
				
		pOptions.add(ObserverMainLabel);
		pOptions.add(Observer1Checkbox);
		pOptions.add(Observer1XField);
		pOptions.add(Observer1YField);
		pOptions.add(Observer1SliderLabel);
		pOptions.add(Observer1Slider);
		pOptions.add(Observer1SliderField);
		
		pOptions.add(Observer2Checkbox);
		pOptions.add(Observer2XField);
		pOptions.add(Observer2YField);
		pOptions.add(Observer2SliderLabel);
		pOptions.add(Observer2Slider);
		pOptions.add(Observer2SliderField);
		
		pOptions.add(SourceMainLabel);
		pOptions.add(SourceXField);
		pOptions.add(SourceYField);
		pOptions.add(SourceFreqField);
		pOptions.add(SourceSliderLabel);
		pOptions.add(SourceSlider);
		pOptions.add(SourceSliderField);
		pOptions.add(SoundSpeedField);
		
		pControl.add(StartButton);
		pControl.add(SaveButton);
									
		
	}


}
