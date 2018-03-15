package pw.pojava.projekt.DopplerMain;

import java.awt.*;

import javax.swing.*;

public class GUI extends JPanel {
	//panels in main panel
	JPanel pWest, pEast, pAnimation, pChart, pChartSource, pChartObserver1, pChartObserver2, pLanguage, pOptions, pControl;//panels left, right, for animation, for sinuses, for sinuses from:source and both observers, for language options, for paint panel options, for start&save button
	
	
	JButton SwitchPolishButton, SwitchEnglishButton; //Language switching buttons
	JButton StartButton, SaveButton; //Powerful Buttons :D
	
	JCheckBox Spect1Checkbox, Spect2Checkbox; // Spectators CheckBoxes
	JSlider Spect1Slider, Spect2Slider, SourceSlider;//Sliders for speed of objests
	final int SpectSlider_MIN = -42;
	final int SpectSlider_MAX = 42; //Parameters for Spectator Speed Sliders
	final int SourceSlider_MIN = -100;
	final int SourceSlider_MAX = 100; //Parameters for Source Speed slider
	final int Slider_INIT = 0; //Initial parameter for sliders
	
	JTextField Spect1XField, Spect1YField, Spect2XField, Spect2YField;//TextFields for Spectators' parameters
	JTextField SourceXField, SuorceYField, SourceFreqField; //TextFields for Source parameters
	JTextField SoundSpeedField; //TextField for speed of Sound
	JTextField Spect1SliderField, Spect2SliderField;
	
	JLabel SpectMainLabel, SourceMainLabel;//Title labels
	JLabel Spect1SliderLabel, Spect2SliderLabel;

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
		
		SwitchPolishButton = new JButton("POLSKI");
		SwitchEnglishButton = new JButton("ENGLISH");
		StartButton = new JButton("START");
		SaveButton = new JButton("ZAPISZ");
		
		Spect1Checkbox = new JCheckBox("Obserwator 1");
		Spect2Checkbox = new JCheckBox("Obserwator 2");
		
		Spect1Slider = new JSlider(JSlider.VERTICAL, SpectSlider_MIN, SpectSlider_MAX, Slider_INIT);
		Spect2Slider = new JSlider(JSlider.VERTICAL, SpectSlider_MIN, SpectSlider_MAX, Slider_INIT);
		SourceSlider = new JSlider(JSlider.VERTICAL, SourceSlider_MIN, SourceSlider_MAX, Slider_INIT);
		Spect1SliderField = new JTextField();
		Spect2SliderField = new JTextField();
		
		Spect1XField = new JTextField();
		Spect1YField = new JTextField();
		Spect2XField = new JTextField();
		Spect2YField = new JTextField();
		SourceXField = new JTextField();
		SuorceYField = new JTextField();
		SourceFreqField = new JTextField();
		SoundSpeedField = new JTextField();
		
		SpectMainLabel = new JLabel("Obserwatorzy");
		SourceMainLabel = new JLabel("Zrodlo");		
		
			
		
	}


}
