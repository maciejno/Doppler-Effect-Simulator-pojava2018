package pw.pojava.projekt.DopplerMain;

import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class GUI extends JPanel {
	
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
		SourceMainLabel = new Label("Zrodlo");		
		
			
		
	}


}
