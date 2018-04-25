package pw.pojava.projekt.DopplerMain;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class GUI extends JPanel  implements ChangeListener, ActionListener, ItemListener, KeyListener {
	
	private static final long serialVersionUID = 1L;

	//SERWIS EGZEKUCYJNY DLA WATKU ANIMACJI
	ExecutorService exec = Executors.newSingleThreadExecutor();
	
	//zmienne przechowuj¹ce nastawy komponentów
	int Observer1X = 30; 
	int Observer1Y = 30;
	int Observer1V = 40;
	int Observer2X = 10;
	int Observer2Y = 10;
	int Observer2V = 50;
	int SourceX = 480;
	int SourceY = 270;
	int SourceV = -60;
	int SoundSpeed = 300;
	int SoundFreq = 20;
			
	boolean Observer1State = true;
	boolean Observer2State = false;
	
	//panels in main panel
	JPanel pWest, pEast, pChart, pLanguage, pOptions, pControl, pObserver1,pObserver2,pSource;//panels left, right, for animation, for sinuses, for sinuses from:source and both observers, for language options, for paint panel options, for start&save button
	JPanel pNorthObserver1, pCenterObserver1, pSouthObserver1, pNorthObserver2, pCenterObserver2, pSouthObserver2, pNorthSource, pCenterSource, pSouthSource;
	
	MainAnimationPanel pAnimation;
	SourceAnimationPanel pChartSource;
	ObserverAnimationPanel pChartObserver1;
	ObserverAnimationPanel pChartObserver2;
	
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
	short MalaSamotnaZmienna;//zaopiekuj sie nia
	
	JTextField Observer1XField, Observer1YField, Observer2XField, Observer2YField;//TextFields for Spectators' parameters
	JTextField SourceXField, SourceYField, SourceFreqField; //TextFields for Source parameters
	JTextField SoundSpeedField; //TextField for speed of Sound
	JTextField Observer1SliderField, Observer2SliderField, SourceSliderField; //text fields for obs1,obs2, soutce values
	
	JLabel ObserverMainLabel, SourceMainLabel;//Title labels
	JLabel ValueXObserver1Label, ValueYObserver1Label, ValueXObserver2Label, ValueYObserver2Label, ValueXSourceLabel, ValueYSourceLabel;
	JLabel SliderObserver1LabelV, SliderObserver1LabelMS, SliderObserver2LabelV, SliderObserver2LabelMS, SliderSourceLabelV, SliderSourceLabelMS;//Slider labels
	JLabel SoundSpeedLabel, SoundSpeedLabelMS; // sound speed label
	JLabel FreqLabel1, FreqLabel2;

	JFreeChart [] chart = new JFreeChart [3];//tablica wykresow
	XYSeriesCollection Observer1Collection,Observer2Collection,SourceCollection;//kolekcje na dane do wykresow
	
	public GUI() {
//MOZLIWE ZE TO POWINNO BYC NIE TU, TYLKO W TYCH KLASACH, ALE POZNIEJ O TYM POMYSLE
Observer1Collection = new XYSeriesCollection();  
Observer2Collection = new XYSeriesCollection(); 
SourceCollection = new XYSeriesCollection(); 
chart[0] = ChartFactory.createXYLineChart (null, null, null ,Observer1Collection, PlotOrientation.VERTICAL, false, false,false);
chart[1] = ChartFactory.createXYLineChart (null, null, null ,Observer2Collection, PlotOrientation.VERTICAL, false, false,false);
chart[2] = ChartFactory.createXYLineChart (null, null, null ,SourceCollection, PlotOrientation.VERTICAL, false, false,false);

XYSeries dataSet1= new XYSeries("Sinus");
for (double i=0; i <26; i+=0.05) dataSet1.add(i,Math.sin(i)); 
Observer1Collection.addSeries(dataSet1);
XYSeries dataSet2= new XYSeries("Cosinus");
for (double i=0; i <26; i+=0.05) dataSet2.add(i,0.5*Math.cos(i/2)); 
Observer2Collection.addSeries(dataSet2);
XYSeries dataSet3= new XYSeries("Dziwny sinus");
for (double i=0; i <26; i+=0.05) dataSet3.add(i,Math.cos(i)*i); 
SourceCollection.addSeries(dataSet3);
		
		//tworzy panele
		pWest = new JPanel();
		pEast = new JPanel();
		pAnimation = new MainAnimationPanel(this);
		pChart = new JPanel();
		pChartSource = new SourceAnimationPanel(chart[0],SourceCollection, dataSet3, (double)SoundFreq); 
		pChartObserver1 = new ObserverAnimationPanel(chart[1],Observer1Collection, dataSet1);
		pChartObserver2 = new ObserverAnimationPanel(chart[2],Observer2Collection, dataSet2);
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
	/*	ImageIcon polish =  createImageIcon("pw/pojava/projekt/DopplerMain/icons/polish.jpg", "a pretty but meaningless splat");
		ImageIcon english = new ImageIcon("pw\\pojava\\projekt\\DopplerMain\\icons\\english.png");
		ImageIcon audio = new ImageIcon("icons\\audio.png");
		ImageIcon start = new ImageIcon("icons\\start.png");
		ImageIcon zapisz = new ImageIcon("icons\\zapisz.png");*/
  
		
		//tworzenie komponentów
		SwitchPolishButton = new JButton("POLSKI");
		SwitchEnglishButton = new JButton("ENGLISH");
		StartButton = new JButton("START");
		SaveButton = new JButton("ZAPISZ");
		SoundButton1 = new JButton("<))");
		SoundButton2 = new JButton("<))");
				
		Observer1Checkbox = new JCheckBox("Obserwator 1"); Observer1Checkbox.setSelected(true);
		Observer2Checkbox = new JCheckBox("Obserwator 2");
			
		//ustawienia sliderów
		Font SliderFont = new Font("Calibri", Font.BOLD, 11); //kosmetyka
		Observer1Slider = new JSlider(JSlider.HORIZONTAL, ObserverSlider_MIN, ObserverSlider_MAX, Observer1V);
		Observer1Slider.setMajorTickSpacing(500); //wiêcej kosmetyki
		Observer1Slider.setMinorTickSpacing(250);
		Observer1Slider.setPaintTicks(true);
		Observer1Slider.setPaintLabels(true);
		Observer1Slider.setFont(SliderFont);
		Observer2Slider = new JSlider(JSlider.HORIZONTAL, ObserverSlider_MIN, ObserverSlider_MAX, Observer2V);
		Observer2Slider.setMajorTickSpacing(500);
		Observer2Slider.setMinorTickSpacing(250);
		Observer2Slider.setPaintTicks(true);
		Observer2Slider.setPaintLabels(true);
		Observer2Slider.setFont(SliderFont);
		SourceSlider = new JSlider(JSlider.HORIZONTAL, SourceSlider_MIN, SourceSlider_MAX, SourceV);
		SourceSlider.setMajorTickSpacing(500);
		SourceSlider.setMinorTickSpacing(250);
		SourceSlider.setPaintTicks(true);
		SourceSlider.setPaintLabels(true);
		SourceSlider.setFont(SliderFont);
		
		Observer1SliderField = new JTextField(Integer.toString(Observer1V)); Observer1SliderField.setColumns(4);//ustawia rozmiar pola tekstowego
		Observer2SliderField = new JTextField(Integer.toString(Observer2V)); Observer2SliderField.setColumns(4);
		SourceSliderField = new JTextField(Integer.toString(SourceV)); SourceSliderField.setColumns(4);
		
		Observer1XField = new JTextField(Integer.toString(Observer1X)); Observer1XField.setColumns(4);
		Observer1YField = new JTextField(Integer.toString(Observer1Y)); Observer1YField.setColumns(4);
		Observer2XField = new JTextField(Integer.toString(Observer2X)); Observer2XField.setColumns(4);
		Observer2YField = new JTextField(Integer.toString(Observer2Y)); Observer2YField.setColumns(4);
		SourceXField = new JTextField(Integer.toString(SourceX)); SourceXField.setColumns(4);
		SourceYField = new JTextField(Integer.toString(SourceY)); SourceYField.setColumns(4);
		SourceFreqField = new JTextField(Integer.toString(SoundFreq)); SourceFreqField.setColumns(5);
		SoundSpeedField = new JTextField(Integer.toString(SoundSpeed)); SoundSpeedField.setColumns(4);
		
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
		pWest.setLayout(new GridLayout(2,1));
		
		pEast.setLayout(new BorderLayout());
		pChart.setLayout(new GridLayout(3,1));
		pOptions.setLayout(new GridLayout(3,1));
		pLanguage.setLayout(new FlowLayout(FlowLayout.TRAILING));//trailing ustawia z prawej strony
		pControl.setLayout(new FlowLayout()); //ustawienia layoutu dla panelu pControl
		
		int flowLayoutOrientation = FlowLayout.LEFT;
		
		pObserver1.setLayout(new GridLayout(3,1));
		pObserver2.setLayout(new GridLayout(3,1));
		pSource.setLayout(new GridLayout(3,1));
		pNorthObserver1.setLayout(new FlowLayout(flowLayoutOrientation));
		pCenterObserver1.setLayout(new FlowLayout(flowLayoutOrientation));
		pSouthObserver1.setLayout(new FlowLayout(flowLayoutOrientation));
		pNorthObserver2.setLayout(new FlowLayout(flowLayoutOrientation));
		pCenterObserver2.setLayout(new FlowLayout(flowLayoutOrientation));
		pSouthObserver2.setLayout(new FlowLayout(flowLayoutOrientation));
		pNorthSource.setLayout(new FlowLayout(flowLayoutOrientation));
		pCenterSource.setLayout(new FlowLayout(flowLayoutOrientation));
		pSouthSource.setLayout(new FlowLayout(flowLayoutOrientation));
		
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
		this.add(pWest, BorderLayout.CENTER);
		this.add(pEast, BorderLayout.EAST);
		//dodaje i ustawia panele do lewego panelu
		pWest.add(pAnimation);
		pWest.add(pChart);
		//dodaje i ustawia panele do prawego panelu
		pEast.add(BorderLayout.NORTH, pLanguage);
		pEast.add(BorderLayout.CENTER, pOptions);
		pEast.add(BorderLayout.SOUTH, pControl);
		
/*xySeriesCollection = new XYSeriesCollection();  
JFreeChart lineGraph = ChartFactory.createXYLineChart (null, null, null ,xySeriesCollection, PlotOrientation.VERTICAL, false, false,false);
ChartPanel wykres = new ChartPanel(lineGraph);
pChartSource.setLayout(new GridLayout(1,1));
pChartSource.add(wykres);*/
								
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
		
		StartButton.addActionListener(this);
		StartButton.setActionCommand("run");
		SaveButton.addActionListener(this);
		SaveButton.setActionCommand("save");
		
		setAnimationParameters();
		
	}//koniec konstruktora

	private ImageIcon createImageIcon(String path, String description) { //wa¿ne coœ do dodawania ikonek
		java.net.URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL, description);
	    } else {
	        System.err.println("Couldn't find file:  " + path + " :(");
	        return null;
	    }
	}
	
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
			pAnimation.observer1.setAppearance(Observer1State);
			pAnimation.observer2.setAppearance(Observer2State);
			//debugging
			System.out.print("Observer1 : ");
			System.out.println(Observer1State);
			System.out.println(pAnimation.observer1.getAppearance());
			System.out.print("Observer2 : ");
			System.out.println(Observer2State);
			System.out.println(pAnimation.observer2.getAppearance());
			
			pAnimation.repaint();
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
				if(Integer.parseInt(Observer1XField.getText())!=Observer1X) {
					isTextFieldEmpty(Observer1XField);
					Observer1X = Integer.parseInt(Observer1XField.getText());	
					System.out.println("O1x:" + Observer1X);
				}
				else if(Integer.parseInt(Observer1YField.getText())!=Observer1Y) {
					Observer1Y = Integer.parseInt(Observer1YField.getText());	
					System.out.println("O1y:" + Observer1Y);
				}	
				else if(Integer.parseInt(Observer2XField.getText())!=Observer2X) {
					Observer2X = Integer.parseInt(Observer2XField.getText());	
					System.out.println("O2x:" + Observer2X);
				}
				else if(Integer.parseInt(Observer2YField.getText())!=Observer2Y) {
					Observer2Y = Integer.parseInt(Observer2YField.getText());	
					System.out.println("O2y:" + Observer2Y);
				}
				else if(Integer.parseInt(SourceXField.getText())!=SourceX) {
					SourceX = Integer.parseInt(SourceXField.getText());		
					System.out.println("Sx:" + SourceX);
				}
				else if(Integer.parseInt(SourceYField.getText())!=SourceY) {
					SourceY = Integer.parseInt(SourceYField.getText());	
					System.out.println("Sy:" + SourceY);
				}
				else if(Integer.parseInt(SourceFreqField.getText())!=SoundFreq) {
					SoundFreq = Integer.parseInt(SourceFreqField.getText());	
					System.out.println("Sf:" + SoundFreq);
					pAnimation.setFrequency(SoundFreq);
				}
				else if(Integer.parseInt(SoundSpeedField.getText())!=SoundSpeed) {
					SoundSpeed = Integer.parseInt(SoundSpeedField.getText());	
					System.out.println("Ss:" + SoundSpeed);
					pAnimation.setSoundSpeed(SoundSpeed);
				}
				else if(Integer.parseInt(Observer1SliderField.getText())!=Observer1V) {
					Observer1V = Integer.parseInt(Observer1SliderField.getText());
					Observer1Slider.setValue(Integer.parseInt(Observer1SliderField.getText()));
					System.out.println("O1V:" + Observer1V);
				}
				else if(Integer.parseInt(Observer2SliderField.getText())!=Observer2V) {
					Observer2V = Integer.parseInt(Observer2SliderField.getText());
					Observer2Slider.setValue(Integer.parseInt(Observer2SliderField.getText()));
					System.out.println("O2V:" + Observer2V);
				}
				else if(Integer.parseInt(SourceSliderField.getText())!=SourceV) {
					SourceV = Integer.parseInt(SourceSliderField.getText());
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
		pAnimation.repaint();
		//USTAWIA WSZYSTKIE PARAMETRY OBIEKTOW NA ANIMACJI
		setAnimationParameters();
}


	public void actionPerformed(ActionEvent ae) {
		String action = ae.getActionCommand();
		if ((action.equals("run"))&&pAnimation.isRunning==false) {
			pAnimation.mainAnimator.execute();	
			//STARE
			/*try {//dzieki temu mozna na nowo puscic animacje jak sie skonczy
				pChartSource.setIsRunning(true);//wazne - ustawia pole w klasie do wykresu zrodla
				exec.execute(pAnimation);
				exec.shutdown();
				exec = Executors.newSingleThreadExecutor();
				
			}catch(RejectedExecutionException e) {
				
				e.printStackTrace();
			}*/			
		}

			
	}
	
	public void keyPressed(KeyEvent arg0) {	}
	public void keyTyped(KeyEvent arg0) {}

	public void setObserver1XField(Double val) {Observer1XField.setText(val.toString());}

	public void setNewMainAnimationThread() {exec = Executors.newSingleThreadExecutor();}
	
	public void setAnimationParameters() { //ustawia parametry animacji
		pAnimation.observer1.setX(Observer1X); 
		pAnimation.observer1.setY(Observer1Y);
		pAnimation.observer1.setVx(Observer1V);
		pAnimation.observer2.setX(Observer2X); 
		pAnimation.observer2.setY(Observer2Y);
		pAnimation.observer2.setVy(Observer2V);
		pAnimation.source.setX(SourceX);
		pAnimation.source.setY(SourceY);
		pAnimation.source.setVx(SourceV);
		pAnimation.setSoundSpeed(SoundSpeed);
		pAnimation.setFrequency(SoundFreq);
	}

}
