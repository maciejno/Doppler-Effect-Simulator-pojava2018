package pw.pojava.projekt.DopplerMain;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import javax.swing.SwingWorker;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class SourceAnimationPanel extends ChartPanel{

	private static final long serialVersionUID = 1L;
	
	XYSeriesCollection xySeriesCollection;//pola
	XYSeries xySeries;					//przepisane
	JFreeChart chart;					//przez konstruktor
	MySwingWorker worker;
	Double x,y;
	
	GUI superior;//referencja do GUI
	
	public SourceAnimationPanel(GUI superior) {
		super(superior.fchart[0]);
		xySeries = new XYSeries("Source signal");
		superior.sourceCollection.addSeries(xySeries);
		this.superior = superior;
		worker = new MySwingWorker();
	}

	class MySwingWorker extends SwingWorker<Void,XYDataItem>{
		
		double pi = Math.PI;
		double time =0;//aktualna chwila czasu
		int sleep = 1;//ms ile spi
		int maxCount = 3000;//maksymalna liczba punktow na wykresie * freq
		
		
		MySwingWorker(){}
		//UWAGA: jest dzielenie freq przez 100, zeby pracowalo dla szerszego zakresu czestotliwosci - dzieki temu jest do 10-15kHzkHz, a nie do 100-150Hz
		@Override
 	   protected void process(List<XYDataItem> dane) {
 		   	for(XYDataItem d : dane) {
 		   		xySeries.add(d);
 		   	if(xySeries.getItemCount()>maxCount/((double)superior.soundFreq/100))//if(xySeries.getItemCount()>500)//jak sie zmieni wartosc maxCount, to szerokosc inna
 		   			xySeries.remove(0);	//to na gorze co zakomentowane jesli ma sie nie dostosowywac do czestotliwosci szerokosc okna 
 		   	}
 	   }
		@Override
		protected Void doInBackground() throws Exception {
				while(superior.isRunning) {
					x = new Double(time);
					y = new Double(Math.sin(2*pi*((double)superior.soundFreq/100)*time/1000));
					XYDataItem dataItem = new XYDataItem(x, y);
					publish(dataItem);
					time+=sleep;
					Thread.sleep(sleep);	
				}
			return null;
		}
		
	}

	
	public void newWorker() {
		worker = new MySwingWorker();		
	}

}

