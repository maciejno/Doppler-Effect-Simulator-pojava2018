package pw.pojava.projekt.DopplerMain;

import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingWorker;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class SourceAnimationPanel extends ChartPanel{

	private static final long serialVersionUID = 1L;
	
	XYSeriesCollection xySeriesCollection;//pola
	XYSeries xySeries;					//przepisane
	JFreeChart chart;					//przez konstruktor
	
	MySwingWorker worker;
	Boolean isRunning = false;//czy glowna animacja leci
	
	double frequency;//czestotliwosc dzwieku przekazana w konstruktorze

	public SourceAnimationPanel(JFreeChart chart, XYSeriesCollection collection, XYSeries series, double frequency) {
		super(chart);
		xySeries = series;
		xySeriesCollection = collection;
		this.frequency = frequency;
		
	}

	class MySwingWorker extends SwingWorker<Void,double[]>{

		LinkedList <Double> listOfData = new LinkedList<Double>();//lista do dodawania wynikow w doInBackground()
		double pi = Math.PI;
		int time =0;//aktualna chwila czasu
		int sleep = 10;//ms ile spi
		
		MySwingWorker(){
			listOfData.add(0.0);//dodaje pierwszy element - zero
			//this.execute();
		}
		
		@Override
		protected Void doInBackground() throws Exception {
			
			while(isRunning) {
				
				listOfData.addFirst(Math.sin(2*pi*frequency*(double)time));//dodaje na poczatku punkt "y" do listy (dla obecnej chwili czasu)
				
				if(listOfData.size()>300) {//jak wiecej punktow niz: , to usuwa ostatni element
					listOfData.removeLast();
				}
				
				double [] data = new double [listOfData.size()];
				for(int i = 0;i<data.length;i++) {
					data[i] = listOfData.get(i);
				}
				
				publish(data);
				
				try {
					Thread.sleep(sleep);//uspienie watku na jakis czas
					time += sleep;
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			return null;
		}
		
		protected void process(List<double[]> data) {//otrzymuje liste tablic
			//MOZNA TO TEZ KROCEJ - PO PROSTU PRZYPISANIE TABLICY DO TABLICY, A NIE POSZCZEGOLNYCH ELEMENTOW
			double [] mostRecentData = new double [data.get(data.size()-1).length];//tworzy tablice o rozmiarze tablicy bedacej ostatnim elementem listy danych
			xySeries.clear();
			for(int i = 0; i < mostRecentData.length; i++){
				mostRecentData[i] = data.get(data.size()-1)[i];//przepisuje tablice
				xySeries.add(i*sleep, mostRecentData[i]);
			}
			
			try {
				Thread.sleep(40);//40ms, poniewaz 1000/40 = 25FPS
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		protected void done() {
			
		}
		
	}
	
	public void setIsRunning(Boolean bool) {this.isRunning = bool;}
}
