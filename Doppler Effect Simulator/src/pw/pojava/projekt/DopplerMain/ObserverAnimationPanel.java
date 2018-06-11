package pw.pojava.projekt.DopplerMain;

import javax.swing.SwingWorker;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
//Z TEJ KLASY DZIEDZICZA 2 PANELE DO TWORZENIA OBRAZU DZWIEKU DLA OBSERWATOROW
public abstract class ObserverAnimationPanel extends ChartPanel {
	
	private static final long serialVersionUID = 1L;
	JFreeChart chart;
	GUI gui;//referencja do GUI	
	XYSeries xySeries;	
	ObserverSwingWorker worker;	
	Double x,y,f;	
	Double pi = Math.PI;
	double timeDelay = 0.0; //czas zanim fala dotrze do obserwatora
	double timeRunaway = 0.0;//czas po ktorym obserwator ucieknie od fali
	double soundFreq = 100.0;	
	double soundSpeed = 100.0;
	
	public ObserverAnimationPanel(GUI gui, JFreeChart chart) {
		super(chart);
		this.chart = chart;		
		this.gui = gui;	
		this.soundFreq = (double)gui.soundFreq;
		this.soundSpeed = (double)gui.soundSpeed;
	}
	
	public class DataToSimulate{//klasa przechowujaca dane przesylane do processa - punkt wykresu i czestotliwosc chwilowa dzwieku
		XYDataItem xyDataItem;
		Double freq;
		
		DataToSimulate(XYDataItem xy, Double f){
			xyDataItem = xy;
			freq = f;
		}
		public XYDataItem getXY() {return xyDataItem;}
		public Double getFreq() {return freq;}
	}
	
	public abstract class ObserverSwingWorker extends SwingWorker<Void,DataToSimulate>{
			
			double time =0;//aktualna chwila czasu [ms]
			int sleep = 1;//ms ile spi
			int maxCount = 3000;//maksymalna liczba punktow na wykresie * freq
						
			ObserverSwingWorker(){
				xySeries.clear(); //usuwa wszystkie dane z serii
				for(int i=0; i<maxCount/(soundFreq/100);i++) {
					xySeries.add(time-i,0.0);//wype³nia zerami dane
				}
				countTimeDelayAndRunaway();//oblicza czasy dotarcia fali i kiedy obserwator juz nie slyszy fali	
				//debugging
				//System.out.println(timeDelay);
				//System.out.println(timeRunaway);
			}
											
			@Override
			abstract protected Void doInBackground() throws Exception; //oblicza wartosi sinusa, czas w ms i przesyla do process 								
		}
		abstract void countTimeDelayAndRunaway();//liczy czasy opoznienia i ucieczki
		public abstract void newWorker(); //metoda do tworzenia nowego swing workera
		
		//katy jak w specyfikacji we wzorze					
		//zwracaja chwilowa predkosc 
		//ponizsze metody uwzgledniaja wzgledne polozenie obserwatora i zrodla
		public abstract double getVObserver();//zwraca skladowa predkosci obserwatora wzdluz linii laczacej go ze zrodlem			
		public abstract double getPhiObserver();//zwraca kat miedzy wektorem predkosci obserwatora a linia laczaca zrodlo z obserwatorem		
		public abstract double getPhiSource();//zwraca kat miedzy wektorem predkosci zrodla a linia laczaca zrodlo z obserwatorem			
		
		public void setFrequency(double freq) {soundFreq = freq;}
		public void setSoundSpeed(double speed) {soundSpeed = speed;}
		
		public double getVSource() {//zwraca skladowa predkosci zrodla wzdluz linii laczacej je z obserwatorem
			double vSource = gui.pAnimation.source.getVx()*Math.cos(getPhiSource()) + gui.pAnimation.source.getVy()*Math.sin(getPhiSource());			
			return vSource;			
		}
			
		public double module(double m) {//zwraca modul liczby
			if(m >= 0) return m;
			else return (-m);
		}
}
