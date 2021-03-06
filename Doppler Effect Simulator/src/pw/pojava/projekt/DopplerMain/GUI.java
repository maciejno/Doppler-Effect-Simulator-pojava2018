package pw.pojava.projekt.DopplerMain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class GUI extends JPanel  implements ChangeListener, ActionListener, ItemListener, KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	//SERWIS EGZEKUCYJNY DLA WATKU ANIMACJI
	ExecutorService exec;
	MainFrame mainFrame;
	GUI gui;
	
	//zmienne przechowuj�ce nastawy komponent�w
	int observer1X = 150; 
	int observer1Y = 130;
	int observer1V = 40;
	int observer2X = 200;
	int observer2Y = 10;
	int observer2V = 50;
	int sourceX = 480;
	int sourceY = 150;
	int sourceV = -110;
	int soundSpeed = 300;
	int soundFreq = 250;
	
	boolean observer1State = true;
	boolean observer2State = false;
	boolean isRunning=false;
	boolean isPaused=false;
	boolean isFinished = true;
	String language = "polish";
	String option = "Default";//przechowuje opcje z kombo boksa
	int whoPlay =1; //przechowuje informacje o tym kto odtwarza dzwiek, gdy 0 to nikt. 1, 2 - obserator 1 lub 2
	
	//panels in main panel
	JPanel pWest, pEast, pChart, pLanguage, pOptions, pControl, pObserver1,pObserver2,pSource;//panels left, right, for animation, for sinuses, for sinuses from:source and both observers, for language options, for paint panel options, for start&save button
	JPanel pNorthObserver1, pCenterObserver1, pSouthObserver1, pNorthObserver2, pCenterObserver2, pSouthObserver2, pNorthSource, pCenterSource, pSouthSource;
	
	MainAnimationPanel pAnimation;
	SourceAnimationPanel pChartSource;
	Observer1AnimationPanel pChartObserver1;
	Observer2AnimationPanel pChartObserver2;
	
	JButton switchPolishButton, switchEnglishButton; //przyciski do zmiany jezyka
	JButton startButton, saveButton; //przyciski ktore maja moc sprawcza :D
	JButton soundButton1, soundButton2, resetButton;
	
	String[] options = { "Domyslny",  };//do inicjalizacji comboBoxa
	JComboBox<String> comboBox; //combo box do ciekawych przypadkow
	
	JCheckBox observer1Checkbox, observer2Checkbox; // Observers CheckBoxes
	JSlider observer1Slider, observer2Slider, sourceSlider;//Sliders for speed of objests
	static final int observerSlider_MIN = -1000;
	static final int observerSlider_MAX = 1000; //Parameters for Spectator Speed Sliders
	static final int sourceSlider_MIN = -1000;
	static final int sourceSlider_MAX = 1000; //Parameters for Source Speed slider
	static final int slider_INIT = 0; //Initial parameter for sliders
	short malaSamotnaZmienna;//zaopiekuj sie nia, zobacz jak na ciebie patrzy swoimi malymi oczkami
								//nie robi kompletnie nic, ale jest mala i slodka...
	JTextField observer1XField, observer1YField, observer2XField, observer2YField;//TextFields for Spectators' parameters
	JTextField sourceXField, sourceYField, sourceFreqField; //TextFields for Source parameters
	JTextField soundSpeedField; //TextField for speed of Sound
	JTextField observer1SliderField, observer2SliderField, sourceSliderField; //text fields for obs1,obs2, soutce values
	
	JLabel observerMainLabel, sourceMainLabel;//Title labels
	JLabel valueXObserver1Label, valueYObserver1Label, valueXObserver2Label, valueYObserver2Label, valueXSourceLabel, valueYSourceLabel;
	JLabel sliderObserver1LabelV, sliderObserver1LabelMS, sliderObserver2LabelV, sliderObserver2LabelMS, sliderSourceLabelV, sliderSourceLabelMS;//Slider labels
	JLabel soundSpeedLabel, soundSpeedLabelMS; // sound speed label
	JLabel freqLabel1, freqLabel2;

	JFreeChart [] fchart = new JFreeChart [3];//tablica wykresow
	protected XYSeriesCollection observer1Collection, observer2Collection, sourceCollection;//kolekcje na dane do wykresow
	protected XYSeries dataSet1, dataSet2, dataSet3;
	protected XYDataset observer1Dataset, observer2Dataset, sourceDataset;
	
	//ikonki
	ImageIcon polish =  new ImageIcon(getClass().getResource("/polish.png"));
	ImageIcon english = new ImageIcon(getClass().getResource("/english.png"));
	ImageIcon soundON = new ImageIcon(getClass().getResource("/soundON.png"));
	ImageIcon soundOFF = new ImageIcon(getClass().getResource("/soundOFF.png"));
	ImageIcon start = new ImageIcon(getClass().getResource("/start.png"));
	ImageIcon stop = new ImageIcon(getClass().getResource("/stop.png"));
	ImageIcon reset = new ImageIcon(getClass().getResource("/reset.png"));
	ImageIcon save = new ImageIcon(getClass().getResource("/save.png"));
	
	
	
	public GUI(MainFrame mainFrame) throws LineUnavailableException {
		gui = this;
		this.mainFrame = mainFrame;
		//Tworzenie wykresow
		sourceCollection = new XYSeriesCollection();
		sourceDataset = sourceCollection;
		fchart[0] = ChartFactory.createXYLineChart (null, null, null ,sourceDataset, PlotOrientation.VERTICAL, false, false,false);
		
		observer1Collection = new XYSeriesCollection();
		observer1Dataset = observer1Collection;
		fchart[1] = ChartFactory.createXYLineChart (null, null, null ,observer1Dataset, PlotOrientation.VERTICAL, false, false,false);
		fchart[1].getXYPlot().getRendererForDataset(observer1Dataset).setSeriesPaint(0,Color.black);//ustawia kolor linii
		
		observer2Collection = new XYSeriesCollection();
		observer2Dataset = observer2Collection;
		fchart[2] = ChartFactory.createXYLineChart (null, null, null ,observer2Dataset, PlotOrientation.VERTICAL, false, false,false);
		fchart[2].getXYPlot().getRendererForDataset(observer2Dataset).setSeriesPaint(0,Color.blue);//ustawia kolor linii
		
		for(int i = 0;i<3;i++) {//ustawia zakres osi y wykresow
			fchart[i].getXYPlot().getRangeAxis().setRange(-1.1, 1.1);
		}
		
		//tworzy panele
		pWest = new JPanel();
		pEast = new JPanel();
		pAnimation = new MainAnimationPanel(this);
		pChart = new JPanel();
		pChartSource = new SourceAnimationPanel(this); 
		pChartObserver1 = new Observer1AnimationPanel(this, fchart[1]);
		pChartObserver2 = new Observer2AnimationPanel(this, fchart[2]);
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
  
		//tworzenie komponent�w
		switchPolishButton = new JButton("Polski");
		switchPolishButton.setIcon(polish);
		switchEnglishButton = new JButton("English");
		switchEnglishButton.setIcon(english);
		startButton = new JButton("START");
		startButton.setIcon(start);
		saveButton = new JButton("ZAPISZ");
		saveButton.setIcon(save);
		resetButton = new JButton("RESET");
		resetButton.setIcon(reset);
		soundButton1 = new JButton();
		soundButton1.setIcon(soundON);	
		soundButton2 = new JButton();
		soundButton2.setIcon(soundOFF);	
				
		observer1Checkbox = new JCheckBox("Obserwator 1"); observer1Checkbox.setSelected(true);
		observer2Checkbox = new JCheckBox("Obserwator 2");
		
		comboBox = new JComboBox<String>(options) ;
		comboBox.addItem("Odrzutowce");
		comboBox.addItem("Ucieczka");
		comboBox.addItem("NaMorzaDnie");
				
		//ustawienia slider�w
		Font SliderFont = new Font("Calibri", Font.BOLD, 11); //kosmetyka
		observer1Slider = new JSlider(JSlider.HORIZONTAL, observerSlider_MIN, observerSlider_MAX, observer1V);
		observer1Slider.setMajorTickSpacing(500); //wi�cej kosmetyki
		observer1Slider.setMinorTickSpacing(250);
		observer1Slider.setPaintTicks(true);
		observer1Slider.setPaintLabels(true);
		observer1Slider.setFont(SliderFont);
		observer2Slider = new JSlider(JSlider.HORIZONTAL, observerSlider_MIN, observerSlider_MAX, observer2V);
		observer2Slider.setMajorTickSpacing(500);
		observer2Slider.setMinorTickSpacing(250);
		observer2Slider.setPaintTicks(true);
		observer2Slider.setPaintLabels(true);
		observer2Slider.setFont(SliderFont);
		sourceSlider = new JSlider(JSlider.HORIZONTAL, sourceSlider_MIN, sourceSlider_MAX, sourceV);
		sourceSlider.setMajorTickSpacing(500);
		sourceSlider.setMinorTickSpacing(250);
		sourceSlider.setPaintTicks(true);
		sourceSlider.setPaintLabels(true);
		sourceSlider.setFont(SliderFont);
		
		observer1SliderField = new JTextField(Integer.toString(observer1V)); observer1SliderField.setColumns(4);//ustawia rozmiar pola tekstowego
		observer2SliderField = new JTextField(Integer.toString(observer2V)); observer2SliderField.setColumns(4);
		sourceSliderField = new JTextField(Integer.toString(sourceV)); sourceSliderField.setColumns(4);
		
		observer1XField = new JTextField(Integer.toString(observer1X)); observer1XField.setColumns(4);
		observer1YField = new JTextField(Integer.toString(observer1Y)); observer1YField.setColumns(4);
		observer2XField = new JTextField(Integer.toString(observer2X)); observer2XField.setColumns(4);
		observer2YField = new JTextField(Integer.toString(observer2Y)); observer2YField.setColumns(4);
		sourceXField = new JTextField(Integer.toString(sourceX)); sourceXField.setColumns(4);
		sourceYField = new JTextField(Integer.toString(sourceY)); sourceYField.setColumns(4);
		sourceFreqField = new JTextField(Integer.toString(soundFreq)); sourceFreqField.setColumns(5);
		soundSpeedField = new JTextField(Integer.toString(soundSpeed)); soundSpeedField.setColumns(4);
		
		observerMainLabel = new JLabel("Obserwatorzy");
		valueXObserver1Label = new JLabel("X:");
		valueYObserver1Label = new JLabel("Y:");
		valueXObserver2Label = new JLabel("X:");
		valueYObserver2Label = new JLabel("Y:");
		valueXSourceLabel = new JLabel("X:");
		valueYSourceLabel = new JLabel("Y:");
		sourceMainLabel = new JLabel("Zrodlo");	
		sliderObserver1LabelV = new JLabel("v:");
		sliderObserver1LabelMS = new JLabel("m/s");
		sliderObserver2LabelV = new JLabel("v:");
		sliderObserver2LabelMS = new JLabel("m/s");
		sliderSourceLabelV = new JLabel("v:");
		sliderSourceLabelMS = new JLabel("m/s");
		soundSpeedLabel = new JLabel("Predkosc dzwieku:");
		soundSpeedLabelMS = new JLabel("m/s");
		freqLabel1 = new JLabel("f:");	
		freqLabel2 = new JLabel("Hz");
		
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
		pLanguage.setBorder(BorderFactory.createTitledBorder("Ustawienia"));
		pControl.setBorder(BorderFactory.createTitledBorder(" "));
		pObserver1.setBorder(BorderFactory.createTitledBorder(" "));
		pObserver2.setBorder(BorderFactory.createTitledBorder(" "));
		pSource.setBorder(BorderFactory.createTitledBorder("Zrodlo"));
		pChartSource.setBorder(BorderFactory.createTitledBorder("Dzwiek ze zrodla:     " + soundFreq + "Hz"));
		pChartObserver1.setBorder(BorderFactory.createTitledBorder("Dzwiek docierajacy do Obserwatora 1:     0Hz"));
		pChartObserver2.setBorder(BorderFactory.createTitledBorder("Dzwiek docierajacy do Obserwatora 2:     0Hz"));
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
								
		//dodaje i ustawia panele z wykresami do panelu pChart
		pChart.add(pChartSource);
		pChart.add(pChartObserver1);
		pChart.add(pChartObserver2);	
		
		//wstawianie komponent�w do paneli
		pLanguage.add(comboBox);
		pLanguage.add(switchPolishButton);
		pLanguage.add(switchEnglishButton);
		
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
		
		pNorthObserver1.add(observer1Checkbox);
		pNorthObserver1.add(soundButton1);
		pCenterObserver1.add(valueXObserver1Label);
		pCenterObserver1.add(observer1XField);
		pCenterObserver1.add(valueYObserver1Label);
		pCenterObserver1.add(observer1YField);
		pSouthObserver1.add(sliderObserver1LabelV);
		pSouthObserver1.add(observer1Slider);
		pSouthObserver1.add(observer1SliderField);
		pSouthObserver1.add(sliderObserver1LabelMS);
		
		pNorthObserver2.add(observer2Checkbox);
		pNorthObserver2.add(soundButton2);
		pCenterObserver2.add(valueXObserver2Label);
		pCenterObserver2.add(observer2XField);
		pCenterObserver2.add(valueYObserver2Label);
		pCenterObserver2.add(observer2YField);
		pSouthObserver2.add(sliderObserver2LabelV);
		pSouthObserver2.add(observer2Slider);
		pSouthObserver2.add(observer2SliderField);
		pSouthObserver2.add(sliderObserver2LabelMS);
		
		pNorthSource.add(valueXSourceLabel);
		pNorthSource.add(sourceXField);
		pNorthSource.add(valueYSourceLabel);
		pNorthSource.add(sourceYField);
		pNorthSource.add(freqLabel1);
		pNorthSource.add(sourceFreqField);
		pNorthSource.add(freqLabel2);
		pCenterSource.add(sliderSourceLabelV);
		pCenterSource.add(sourceSlider);
		pCenterSource.add(sourceSliderField);
		pCenterSource.add(sliderSourceLabelMS);
		pSouthSource.add(soundSpeedLabel);
		pSouthSource.add(soundSpeedField);
		pSouthSource.add(soundSpeedLabelMS);
		
		// a tu juz oddzielny panel na 3 przyciski
		pControl.add(startButton);
		pControl.add(saveButton); 
		pControl.add(resetButton);
									
		//LISTENERY
		
		comboBox.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	if(comboBox.getItemCount()!=0) {//zeby nie wywalalo bledow jak nie ma nic w liscie, np. jak sie zmiania jezyk
	        		Integer[] data = new Integer [13];
					try {
						option = (String)comboBox.getSelectedItem();
						data = loadData(option);
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        	observer1X=data [0]; 
		    		observer1Y=data[1];
		    		observer1V=data[2];
		    		observer2X=data[3]; 
		    		observer2Y=data[4];
		    		observer2V=data[5];
		    		sourceX=data[6];
		    		sourceY=data[7];
		    		sourceV=data[8];
		    		soundSpeed=data[9];
		    		soundFreq=data[10];
		    		if(data[11]==1)observer1State=true; else observer1State=false;
		    		if(data[12]==1)observer2State=true; else observer2State=false;
		        	setAnimationParameters();	        	        	
		        	setAllFields();//ustawia pola tekstowe i slidery odpowiednio - mozna to ew. do zewnetrznej funkcji wyrzucic
		        	pAnimation.repaint();
	        	}
		     }
	      });
		
		observer1Checkbox.addItemListener(this); // dodawanie listener�w do Checkbox�w
		observer2Checkbox.addItemListener(this);
		
		observer1Slider.addChangeListener(this); //dodawanie listener�w do slider�w
		observer2Slider.addChangeListener(this);
		sourceSlider.addChangeListener(this);
		
		observer1XField.addKeyListener(this);
		observer1YField.addKeyListener(this);
		observer2XField.addKeyListener(this);
		observer2YField.addKeyListener(this);
		sourceXField.addKeyListener(this);
		sourceYField.addKeyListener(this);
		sourceFreqField.addKeyListener(this);
		soundSpeedField.addKeyListener(this);
		
		observer1SliderField.addKeyListener(this);
		observer2SliderField.addKeyListener(this);
		sourceSliderField.addKeyListener(this);
		
		startButton.addActionListener(this);
		startButton.setActionCommand("run");
		saveButton.addActionListener(this);
		saveButton.setActionCommand("save");
		switchPolishButton.addActionListener(this);
		switchPolishButton.setActionCommand("polish");
		switchEnglishButton.addActionListener(this);
		switchEnglishButton.setActionCommand("english");
		resetButton.addActionListener(this);
		resetButton.setActionCommand("reset");
		soundButton1.addActionListener(this);
		soundButton1.setActionCommand("s1");
		soundButton2.addActionListener(this);
		soundButton2.setActionCommand("s2");
		
		setAnimationParameters();
		
	}//koniec konstruktora
	

	public void stateChanged(ChangeEvent arg0) { //listener do Slider�w
		if(observer1Slider.getValue()!=observer1V) { //Slider obserwatora 1			
			String slider1String = new Integer(observer1Slider.getValue()).toString();
			observer1SliderField.setText(slider1String);
			observer1V=observer1Slider.getValue();
			//System.out.println("o1v: " + observer1V);
		}		
		if(observer2Slider.getValue()!=observer2V) { //Slider obserwatora 2			
			String slider2String = new Integer(observer2Slider.getValue()).toString();
			observer2SliderField.setText(slider2String);
			observer2V=observer2Slider.getValue();
			//System.out.println("o2v: " + observer2V);
		}		
		if(sourceSlider.getValue()!=sourceV) { //Slider zrodla			
			String sliderSourceString = new Integer(sourceSlider.getValue()).toString();
			sourceSliderField.setText(sliderSourceString);
			sourceV=sourceSlider.getValue();
			//System.out.println("sv: " + sourceV);
			}
		if(!isRunning) {//jesli animacja idzie, to nie ustawia
			//USTAWIA PARAMETRY ANIMACJI
			setAnimationParameters();
			pAnimation.repaint();
		}
	}
	
	public void itemStateChanged(ItemEvent arg0) { // Listener do checkbox�w
		
			if((!observer1Checkbox.isSelected())&&whoPlay==1) {
				soundButton1.setIcon(soundOFF);
				whoPlay=0;
			}
			if((!observer2Checkbox.isSelected())&&whoPlay==2) {
				whoPlay=0;
				soundButton2.setIcon(soundOFF);
			}
			
			observer1State=observer1Checkbox.isSelected();
			observer2State=observer2Checkbox.isSelected();
				
			if(!isRunning) {//jesli animacja idzie, to nie mozna dodac obiektu
				setAnimationParameters();
			}						
			pAnimation.repaint();
	}
	
	//DOPRAWIC - jak jest puste pole tekstowe ma przypisywac 0
	//metoda obslugujaca wyjatek
	static boolean isTextFieldEmpty(JTextField triedTextField) throws EmptyTextFieldException{
		if (triedTextField.getText()==null) 
			throw new EmptyTextFieldException(triedTextField);
		return true;
	}
	
	//listenery do pol tekstowych
	public void keyReleased(KeyEvent arg0) {
		try {//DEBUGGING - wypisuje do konsoli co sie wpisalo do zmiennej
			try {
				if(Integer.parseInt(observer1XField.getText())!=observer1X) {
					isTextFieldEmpty(observer1XField);
					observer1X = Integer.parseInt(observer1XField.getText());
						if(observer1X>pAnimation.getWidth()) {
							observer1X=pAnimation.getWidth();
							observer1XField.setText(String.valueOf(observer1X));
						}
						if(observer1X<0) {
							observer1X=0;
							observer1XField.setText(String.valueOf(0));
						}
					//System.out.println("O1x:" + observer1X);
				}
				else if(Integer.parseInt(observer1YField.getText())!=observer1Y) {
					observer1Y = Integer.parseInt(observer1YField.getText());
						if(observer1Y>pAnimation.getHeight()) {
							observer1Y=pAnimation.getHeight();
							observer1YField.setText(String.valueOf(observer1Y));
						}
						if(observer1Y<0) {
							observer1Y=0;
							observer1YField.setText(String.valueOf(0));
						}
					//System.out.println("O1y:" + observer1Y);
				}	
				else if(Integer.parseInt(observer2XField.getText())!=observer2X) {
					observer2X = Integer.parseInt(observer2XField.getText());
						if(observer2X>pAnimation.getWidth()) {
							observer2X=pAnimation.getWidth();
							observer2XField.setText(String.valueOf(observer2X));
						}
						if(observer2X<0) {
							observer2X=0;
							observer2XField.setText(String.valueOf(0));
						}
					//System.out.println("O2x:" + observer2X);
				}
				else if(Integer.parseInt(observer2YField.getText())!=observer2Y) {
					observer2Y = Integer.parseInt(observer2YField.getText());
					if(observer2Y>pAnimation.getHeight()) {
						observer2Y=pAnimation.getHeight();
						observer2YField.setText(String.valueOf(observer2Y));
					}
					if(observer2Y<0) {
						observer2Y=0;
						observer2YField.setText(String.valueOf(0));
					}
					//System.out.println("O2y:" + observer2Y);
				}
				else if(Integer.parseInt(sourceXField.getText())!=sourceX) {
					sourceX = Integer.parseInt(sourceXField.getText());
					if(sourceX>pAnimation.getWidth()) {
						sourceX=pAnimation.getWidth();
						sourceXField.setText(String.valueOf(sourceX));
					}
					if(sourceX<0) {
						sourceX=0;
						sourceXField.setText(String.valueOf(0));
					}
				//System.out.println("Sx:" + sourceX);
				}
				else if(Integer.parseInt(sourceYField.getText())!=sourceY) {
					sourceY = Integer.parseInt(sourceYField.getText());
					if(sourceY>pAnimation.getHeight()) {
						sourceY=pAnimation.getHeight();
						sourceYField.setText(String.valueOf(sourceY));
					}
					if(sourceY<0) {
						sourceY=0;
						sourceYField.setText(String.valueOf(0));
					}
					//System.out.println("Sy:" + sourceY);
				}
				else if(Integer.parseInt(sourceFreqField.getText())!=soundFreq) {
					soundFreq = Integer.parseInt(sourceFreqField.getText());
						if(soundFreq<=50) {
							soundFreq=50;}
						if(soundFreq>10000) {
							soundFreq=10000;}
						pChartSource.setBorder(BorderFactory.createTitledBorder("Dzwiek ze zrodla:     " + soundFreq + "Hz"));					
					//System.out.println("Sf:" + soundFreq);					
				}
				else if(Integer.parseInt(soundSpeedField.getText())!=soundSpeed) {
					soundSpeed = Integer.parseInt(soundSpeedField.getText());
						if(soundSpeed<=0) {
							soundSpeed=340;}
						if(soundSpeed>300000000) {
							soundSpeed=300000000;}
					//System.out.println("Ss:" + soundSpeed);					
				}
				else if(Integer.parseInt(observer1SliderField.getText())!=observer1V) {
					observer1V = Integer.parseInt(observer1SliderField.getText());
					observer1Slider.setValue(Integer.parseInt(observer1SliderField.getText()));
					//System.out.println("O1V:" + observer1V);
				}
				else if(Integer.parseInt(observer2SliderField.getText())!=observer2V) {
					observer2V = Integer.parseInt(observer2SliderField.getText());
					observer2Slider.setValue(Integer.parseInt(observer2SliderField.getText()));
					//System.out.println("O2V:" + observer2V);
				}
				else if(Integer.parseInt(sourceSliderField.getText())!=sourceV) {
					sourceV = Integer.parseInt(sourceSliderField.getText());
					sourceSlider.setValue(Integer.parseInt(sourceSliderField.getText()));
					//System.out.println("SV:" + sourceV);
				}
			}
			catch(EmptyTextFieldException e) {
				System.err.println("Puste pole tekstowe");
			}
		}
		catch(Exception e) {
			System.err.println("Blad key listener - moze nie liczba calkowita albo puste pole!");
		}		
		if(!isRunning) {//jesli animacja idzie, to nie przypisuje wartosci
			//USTAWIA WSZYSTKIE PARAMETRY OBIEKTOW NA ANIMACJI
			setAnimationParameters();
			pAnimation.repaint();
		}
}


	public void actionPerformed(ActionEvent ae) {
		String action = ae.getActionCommand();
		
		sourceFreqField.setText(String.valueOf(soundFreq));
		soundSpeedField.setText(String.valueOf(soundSpeed));
		
		if(action.equals("polish")) { //wieojezyczosc
			setLanguagePolish();
		}
		if(action.equals("english")) {
			setLanguageEnglish();
		}
		
		if (action.equals("run")) {
			if(isRunning==false){
				pAnimation.newWorker();
				pChartSource.newWorker();
				pChartObserver1.newWorker();
				try {
					pChartObserver1.sound1.newSound();
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				pChartObserver1.sound1.status=true;
				pChartObserver2.newWorker();
				isRunning = true;
				isFinished = false;
				startButton.setText("STOP");	
				startButton.setIcon(stop);
				comboBox.setEnabled(false);
				try {				
					System.out.println(whoPlay);
					exec = Executors.newFixedThreadPool(5);
					if(pAnimation.observer1.appearance) {
						exec.execute(pChartObserver1.worker);
						if(whoPlay==1)
							exec.execute(pChartObserver1.sound1);
					}
					if(pAnimation.observer2.appearance) {
						exec.execute(pChartObserver2.worker);
						if(whoPlay==2)
							exec.execute(pChartObserver2.sound2);
					}
					exec.execute(pAnimation.worker);
					exec.execute(pChartSource.worker);
					exec.shutdown();					
				}catch(RejectedExecutionException e) {
					e.printStackTrace();
				}
			}else{//instrukcje do pauzowania
				if(isPaused==true){
				isPaused=false;
				startButton.setText("STOP");
				startButton.setIcon(stop);
				}else{
					isPaused=true;
					startButton.setText("START");
					startButton.setIcon(start);				
				}
			}			
		}
		else if(action.equals("reset")) {
			if(isPaused)isPaused = false;//zeby nie bylo / bylo mniej problemow z pauzowaniem, bo jak byla zapauzowana to byly problemy
			isRunning = false;
			pChartObserver1.sound1.status=false;
			//pChartObserver2.sound2.status=false;
			exec.shutdownNow();	
		}
		else if(action.equals("save")) {
		if(whoPlay==0) {
			if(language=="polish")
				JOptionPane.showMessageDialog(this.getParent(), "Prosze wybrac zrodlo dzwieku", "Symulator Efektu Dopplera", JOptionPane.INFORMATION_MESSAGE);
			else if(language=="english")
				JOptionPane.showMessageDialog(this.getParent(), "Please chose sound source", "Doppler Effect Simulator", JOptionPane.INFORMATION_MESSAGE);
		}
			if(whoPlay==1) {
				if(language=="polish")
					pChartObserver1.sound1.save("Obserwator1");
				else if(language=="english")
					pChartObserver1.sound1.save("Observer1");
			}
			else if(whoPlay==2) {
				if(language=="polish")
					pChartObserver2.sound2.save("Obserwator2");
				else if(language=="english")
					pChartObserver2.sound2.save("Observer2");;
			}
		}
		
		else if(action.equals("s1")) {
			if((whoPlay==0)&&(observer1State==true)) {
				soundButton1.setIcon(soundON);
				whoPlay=1;
			}
			else if((whoPlay==1)&&(observer1State==true)) {
				soundButton1.setIcon(soundOFF);
				whoPlay=0;
			}
			else if(whoPlay==2) {
				soundButton1.setIcon(soundON);
				soundButton2.setIcon(soundOFF);
				whoPlay=1;
			}
		}
		
		else if(action.equals("s2")) {
			if((whoPlay==0)&&(observer2State==true)) {
				soundButton2.setIcon(soundON);
				whoPlay=2;
			}
			else if((whoPlay==1)&&(observer2State==true)) {
				soundButton1.setIcon(soundOFF);
				soundButton2.setIcon(soundON);
				whoPlay=2;
			}
			else if(whoPlay==2) {
				soundButton2.setIcon(soundOFF);
				whoPlay=0;
			}
		
		}
	}
	
	public void keyPressed(KeyEvent arg0) {	}
	public void keyTyped(KeyEvent arg0) {}

	public void setObserver1XField(Double val) {observer1XField.setText(val.toString());}

	public void setNewMainAnimationThread() {exec = Executors.newSingleThreadExecutor();}
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>	
	protected Integer [] loadData(String fileName) throws URISyntaxException {//do wczytywania danych z pliku
		Integer [] data = new Integer [13];//tablica na dane
		InputStreamReader streamReader = null;				
		BufferedReader bufferedReader = null;
		try {				
			InputStream inputStream = getClass().getResourceAsStream("/" + fileName + ".txt");
			streamReader = new InputStreamReader(inputStream,
					Charset.forName("UTF-8")); // Otwieramy readera
			bufferedReader = new BufferedReader(streamReader); // Buforujemy readera
			String line;//na linie tekstu
			line = bufferedReader.readLine();//wczytanie linii tekstu do bufora			
			
			for (int i = 0;i<13;i++) { // readLine() zwraca null jesli plik sie skonczyl
				//System.out.println(line);	 			
				data[i]= Integer.parseInt(line);
				line = bufferedReader.readLine();
			}			
		} catch (IOException e) {	
			System.err.println("Blad przy otwarciu");
			malaSamotnaZmienna=42;
			e.printStackTrace();				
		}
		try {				
			streamReader.close();
			bufferedReader.close();
		} catch (IOException e) {
			System.err.println("BLAD PRZY ZAMYKANIU PLIKU!");
			}
		return data;
	}
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	public void setAnimationParameters() { //ustawia parametry animacji
		pAnimation.observer1.setX(observer1X); 
		pAnimation.observer1.setY(observer1Y);
		pAnimation.observer1.setVx(observer1V);
		pAnimation.observer2.setX(observer2X); 
		pAnimation.observer2.setY(observer2Y);
		pAnimation.observer2.setVy(observer2V);
		pAnimation.source.setX(sourceX);
		pAnimation.source.setY(sourceY);
		pAnimation.source.setVx(sourceV);
		pAnimation.setSoundSpeed(soundSpeed);
		pAnimation.setFrequency(soundFreq);
		pAnimation.observer1.setAppearance(observer1State);
		pAnimation.observer2.setAppearance(observer2State);
		
		pAnimation.setSoundSpeed(soundSpeed);
		pAnimation.setFrequency(soundFreq);
		pChartObserver1.setFrequency(soundFreq);
		pChartObserver2.setFrequency(soundFreq);
		pChartObserver1.setSoundSpeed(soundSpeed);
		pChartObserver2.setSoundSpeed(soundSpeed);
		pChartSource.setFrequency(soundFreq);
		
		
	}
	
	public void setAllFields() {//ustawia suwaczki i pola tekstowe tak jak jest wpisane w zmiennych		
		int ob2v=observer2V;
		int sv=sourceV;
		
		observer1SliderField.setText(Integer.toString(observer1V));
		observer1Slider.setValue(observer1V);
		observer2V = ob2v;
    	observer2SliderField.setText(Integer.toString(observer2V));
    	observer2Slider.setValue(observer2V);
    	sourceV=sv;
    	sourceSliderField.setText(Integer.toString(sourceV));
    	sourceSlider.setValue(sourceV);
    	  	    	
    	observer1XField.setText(String.valueOf(observer1X));
    	observer1YField.setText(String.valueOf(observer1Y));
    	observer2XField.setText(String.valueOf(observer2X));
    	observer2YField.setText(String.valueOf(observer2Y));
    	sourceXField.setText(String.valueOf(sourceX));
    	sourceYField.setText(String.valueOf(sourceY));
    	sourceFreqField.setText(String.valueOf(soundFreq));
    	soundSpeedField.setText(String.valueOf(soundSpeed));
    	    	
    	observer1Checkbox.setSelected(observer1State);
    	observer2Checkbox.setSelected(observer2State);   	
    	if(language.equals("polish"))pChartSource.setBorder(BorderFactory.createTitledBorder("Dzwiek ze zrodla:     " + soundFreq + "Hz"));
    	else pChartSource.setBorder(BorderFactory.createTitledBorder("Sound from source:     " + soundFreq + "Hz"));
	}
	
	void setLanguagePolish() { //zmiana jezyka na POLISH
		if(isFinished) {
			language = "polish";
			mainFrame.setTitle("Symulator efektu Dopplera");
			saveButton.setText("ZAPISZ");
			observer1Checkbox.setText("Obserwator 1");
			observer2Checkbox.setText("Obserwator 2");
			pSource.setBorder(BorderFactory.createTitledBorder("Zrodlo"));
			soundSpeedLabel.setText("Predkosc dzwieku:");
			pChartSource.setBorder(BorderFactory.createTitledBorder("Dzwiek ze zrodla:     " + soundFreq + "Hz"));
			pChartObserver1.setBorder(BorderFactory.createTitledBorder("Dzwiek docierajacy do Obserwatora 1"));
			pChartObserver2.setBorder(BorderFactory.createTitledBorder("Dzwiek docierajacy do Obserwatora 2"));
			pLanguage.setBorder(BorderFactory.createTitledBorder("Ustawienia"));
			
			
			comboBox.removeAllItems();
			comboBox.addItem("Domyslny");
			comboBox.addItem("Odrzutowce");
			comboBox.addItem("Ucieczka");
			comboBox.addItem("NaMorzaDnie");
			
			mainFrame.getMenu().setText("Plik");
			mainFrame.getAbout().setText("O programie");
			mainFrame.getEnd().setText("Koniec programu");
			mainFrame.getPro().setText("Wersja premium");
		}
	}
	
	void setLanguageEnglish() { //Zmiana jezyka na angielski
		if(isFinished) {
			language = "english";
			mainFrame.setTitle("Doppler Effect Simulator");
			saveButton.setText("SAVE");
			observer1Checkbox.setText("Observer 1");
			observer2Checkbox.setText("Observer 2");
			pSource.setBorder(BorderFactory.createTitledBorder("Source"));
			soundSpeedLabel.setText("Sound speed:");
			pChartSource.setBorder(BorderFactory.createTitledBorder("Sound from source:     " + soundFreq + "Hz"));
			pChartObserver1.setBorder(BorderFactory.createTitledBorder("Sound reaching Observer 1"));
			pChartObserver2.setBorder(BorderFactory.createTitledBorder("Sound reaching Observer 2"));
			pLanguage.setBorder(BorderFactory.createTitledBorder("Settings"));
			
			comboBox.removeAllItems();
			comboBox.addItem("Default");
			comboBox.addItem("Jets");
			comboBox.addItem("Runaway");
			comboBox.addItem("OnTheSeaBed");
			
			mainFrame.getMenu().setText("File");
			mainFrame.getAbout().setText("About");
			mainFrame.getEnd().setText("Finish program");
			mainFrame.getPro().setText("Go pro");
		}
	}
	
	// G E T Y
	public double getOb1X() {return (double)observer1X;}
	public double getOb1Y() {return (double)observer1Y;}
	public double getOb1V() {return (double)observer1V;}
	public double getOb2X() {return (double)observer2X;}
	public double getOb2Y() {return (double)observer2Y;}
	public double getOb2V() {return (double)observer2V;}
	public double getSourceX() {return (double)sourceX;}
	public double getSourceY() {return (double)sourceY;}
	public double getSourceV() {return (double)sourceV;}
	public double getSoundV() {return (double)soundSpeed;}
	public double getSoundFreq() {return (double)soundFreq;}
	public String getOption() {return option;}
	public String getLanguage() {return language;}
	public JComboBox<String> getComboBox() {return comboBox;}
	
	
}
