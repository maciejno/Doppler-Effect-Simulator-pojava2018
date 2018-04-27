package pw.pojava.projekt.DopplerMain;

import java.util.List;

import javax.swing.SwingWorker;

import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;


public class Observer1AnimationPanel extends ChartPanel{

	private static final long serialVersionUID = 1L;
	XYSeries xySeries;									
	ObserverSwingWorker worker;	
	Double x,y,f;	
	GUI superior;//referencja do GUI
	
	public Observer1AnimationPanel(GUI superior) {
		super(superior.fchart[1]);
		xySeries = new XYSeries("Observer 1 signal");
		superior.observer1Collection.addSeries(xySeries);
		this.superior = superior;	
		worker = new ObserverSwingWorker();
	}
	
	class ObserverSwingWorker extends SwingWorker<Void,XYDataItem>{
			
			double pi = Math.PI;
			double time =0;//aktualna chwila czasu
			int sleep = 1;//ms ile spi
			int maxCount = 3000;//maksymalna liczba punktow na wykresie * freq
			double timeDelay = 0; //czas zanim fala dotrze do obserwatora
			
			ObserverSwingWorker(){
				xySeries.clear(); //usuwa wszystkie dane z serii
				for(int i=0; i<maxCount/((double)superior.soundFreq/100);i++) {
					xySeries.add(time-i,0.0);//wype³nia zerami dane
				}
			}
			//UWAGA: jest dzielenie freq przez 100, zeby pracowalo dla szerszego zakresu czestotliwosci - dzieki temu jest do 10-15kHzkHz, a nie do 100-150Hz
			@Override
	 	   	protected void process(List<XYDataItem> dane) {//dodaje dane do serii i jak jest ich za duzo to usuwa
	 		   	for(XYDataItem d : dane) {
	 		   		xySeries.add(d);
	 		   	while(xySeries.getItemCount()>maxCount/((double)superior.soundFreq/100))//if(xySeries.getItemCount()>500)//jak sie zmieni wartosc maxCount, to szerokosc inna
	 		   			xySeries.remove(0);	//to na gorze co zakomentowane jesli ma sie nie dostosowywac do czestotliwosci szerokosc okna 
	 		   	}
	 	   	}
			@Override
			protected Void doInBackground() throws Exception {//oblicza wartosi sinusa, czas w ms i przesyla do process 
				//timeDelay = 	
				while(superior.isRunning) {						
					if(time>=timeDelay) {	
						f = new Double(superior.soundFreq * ( (superior.soundSpeed + getVObserver() ) / (superior.soundSpeed - getVSource() ) ));
						x = new Double(time);
						y = new Double(Math.sin(2*pi*(f/100)*time/1000));
						XYDataItem dataItem = new XYDataItem(x, y);
						publish(dataItem);
						time+=sleep;
						Thread.sleep(sleep);
					}else {
						
					}
				}
				return null;
			}
		}
		
		public void newWorker() {//metoda do tworzenia nowego swing workera
			worker = new ObserverSwingWorker();		
		}
		
		//DOPRACOWAC
		
		//zwracaja chwilowa predkosc 
		//obie ponizsze metody uwzgledniaja wzgledne polozenie obserwatora i zrodla
		public double getVObserver() {//zwraca skladowa predkosci obserwatora wzdluz linii laczacej go ze zrodlem
			double vObserver = superior.pAnimation.observer1.getVx()*Math.cos(getPhiObserver()) + superior.pAnimation.observer1.getVy()*Math.sin(getPhiObserver());			
			//if(superior.pAnimation.observer1.getX()>superior.pAnimation.source.getX()) {
			//	vObserver = -vObserver;
			//}
			return vObserver;		
		}
		public double getVSource() {//zwraca skladowa predkosci zrodla wzdluz linii laczacej je z obserwatorem
			double vSource = superior.pAnimation.source.getVx()*Math.cos(getPhiSource()) + superior.pAnimation.source.getVy()*Math.sin(getPhiSource());			
			//if(superior.pAnimation.observer1.getX()>superior.pAnimation.source.getX()) {
			//	vSource = -vSource;
			//}
			return vSource;			
		}
		//katy jak w specyfikacji we wzorze
		public double getPhiObserver() {
			//phi = arctg((ys-yo)/(xs-xo))
			
			double phiObserver = Math.atan((superior.pAnimation.source.getY() - superior.pAnimation.observer1.getY()) / (superior.pAnimation.source.getX() - superior.pAnimation.observer1.getX()));
			return phiObserver;
		}
		public double getPhiSource() {
			//phi = arctg((yo-ys)/(xo-xs))
			double phiSource = Math.atan((superior.pAnimation.observer1.getY() - superior.pAnimation.source.getY()) / (superior.pAnimation.observer1.getX() - superior.pAnimation.source.getX()));
			return phiSource;
		}
}
