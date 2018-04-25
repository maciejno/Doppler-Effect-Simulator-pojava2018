package pw.pojava.projekt.DopplerMain;

import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingWorker;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class SourceAnimationPanel extends ChartPanel{

	private static final long serialVersionUID = 1L;
	
	XYSeriesCollection xySeriesCollection;//pola
	XYSeries xySeries;					//przepisane
	JFreeChart chart;					//przez konstruktor
	MySwingWorker worker;
	
	double freq;//czestotliwosc dzwieku przekazana w konstruktorze
	GUI superior;//referencja do GUI
	
	public SourceAnimationPanel(GUI superior) {
		super(superior.chart[0]);
		xySeries = superior.dataSet3;
		xySeriesCollection = superior.sourceCollection;
		this.freq = superior.soundFreq;
		this.superior = superior;
		worker = new MySwingWorker();
	}

	class MySwingWorker extends SwingWorker<Void,double[]>{

		LinkedList <Double> listOfData = new LinkedList<Double>();//lista do dodawania wynikow w doInBackground()
		double pi = Math.PI;
		double time =0;//aktualna chwila czasu
		int sleep = 10;//ms ile spi
		int maxCount = 500;//maksymalna liczba punktow na wykresie
		MySwingWorker(){
			listOfData.add(0.0);//dodaje pierwszy element - zero
		}
		
		@Override
		protected Void doInBackground() throws Exception {
			
			while(superior.isRunning) {
				listOfData.addFirst(Math.sin(2*pi*freq*time/1000));
				if(listOfData.size()>maxCount) listOfData.removeLast();//usuwa ostatni element listy jak jest za duzo
				
				double [] dataToPublish = new double [listOfData.size()];
				for(int i=0; i<listOfData.size();i++) {
					dataToPublish[i] = listOfData.get(i);
					System.out.println(dataToPublish[i]);
				}
				publish(dataToPublish);
				time += sleep;
			}
			return null;
		}
		
		protected void process(List<double[]> data) {//otrzymuje liste tablic
			//double [] mostRecentData = data.get(data.size());
		}
		
		protected void done() {
			
		}		
	}
}
