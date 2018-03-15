package pw.pojava.projekt.DopplerMain;

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


	private static final Component SwitchEnglishhButton = null;
	
	JTextField Observer1XField, Observer1YField, Observer2XField, Observer2YField;//TextFields for Spectators' parameters
	JTextField SourceXField, SuorceYField, SourceFreqField; //TextFields for Source parameters
	JTextField SoundSpeedField; //TextField for speed of Sound
	JTextField Observer1SliderField, Observer2SliderField;
	
	JLabel ObserverMainLabel, SourceMainLabel;//Title labels
	JLabel Observer1SliderLabel, Observer2SliderLabel;

	public GUI() {
		this.setLayout(new BorderLayout());//sets layout for main panel
		
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
		
		BoxLayout pLanguageLayout = new BoxLayout(pLanguage, BoxLayout.LINE_AXIS);
		pLanguage.setAlignmentX(Component.RIGHT_ALIGNMENT);
		pLanguage.setLayout(new BoxLayout(pLanguage, BoxLayout.LINE_AXIS)); //ustawienia layoutu dla panelu pLanguage
		pOptions.setLayout(new BoxLayout(pOptions, BoxLayout.PAGE_AXIS)); // ustawienie layoutu dla pOptions
		pControl.setLayout(new GridLayout(0,4,0,2)); //ustawienia layoutu dla panelu pControl
		
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
		pChart.add(BorderLayout.NORTH, pChartSource);
		pChart.add(BorderLayout.CENTER, pChartObserver1);
		pChart.add(BorderLayout.SOUTH, pChartObserver2);
		
		//tworzenie komponentów
		SwitchPolishButton = new JButton("POLSKI");
		SwitchEnglishButton = new JButton("ENGLISH");
		StartButton = new JButton("START");
		SaveButton = new JButton("ZAPISZ");
		
		Observer1Checkbox = new JCheckBox("Obserwator 1");
		Observer2Checkbox = new JCheckBox("Obserwator 2");
		
		Observer1Slider = new JSlider(JSlider.VERTICAL, ObserverSlider_MIN, ObserverSlider_MAX, Slider_INIT);
		Observer2Slider = new JSlider(JSlider.VERTICAL, ObserverSlider_MIN, ObserverSlider_MAX, Slider_INIT);
		SourceSlider = new JSlider(JSlider.VERTICAL, SourceSlider_MIN, SourceSlider_MAX, Slider_INIT);
		Observer1SliderField = new JTextField();
		Observer2SliderField = new JTextField();
		
		Observer1XField = new JTextField();
		Observer1YField = new JTextField();
		Observer2XField = new JTextField();
		Observer2YField = new JTextField();
		SourceXField = new JTextField();
		SuorceYField = new JTextField();
		SourceFreqField = new JTextField();
		SoundSpeedField = new JTextField();
		
		ObserverMainLabel = new JLabel("Obserwatorzy");
		SourceMainLabel = new JLabel("Zrodlo");		
		
		//wstawianie komponentów
		pLanguage.add(SwitchPolishButton);
		pLanguage.add(SwitchEnglishButton);
		pControl.add(StartButton);
		pControl.add(SaveButton);
		
		
		
			
		
	}


}
