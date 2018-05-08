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
	GUI gui;//referencja do GUI
	double pi = Math.PI;
	
	public Observer1AnimationPanel(GUI superior) {
		super(superior.fchart[1]);
		xySeries = new XYSeries("Observer 1 signal");
		superior.observer1Collection.addSeries(xySeries);
		this.gui = superior;	
		worker = new ObserverSwingWorker();
	}
	
	class ObserverSwingWorker extends SwingWorker<Void,XYDataItem>{
			
			double time =0;//aktualna chwila czasu [ms]
			int sleep = 1;//ms ile spi
			int maxCount = 3000;//maksymalna liczba punktow na wykresie * freq
			double timeDelay = 0.0; //czas zanim fala dotrze do obserwatora
			double timeRunaway = 0.0;//czas po ktorym obserwator ucieknie od fali
			
			ObserverSwingWorker(){
				xySeries.clear(); //usuwa wszystkie dane z serii
				for(int i=0; i<maxCount/((double)gui.soundFreq/100);i++) {
					xySeries.add(time-i,0.0);//wype³nia zerami dane
				}
				countTimeDelayAndRunaway();//oblicza czasy dotarcia fali i kiedy obserwator juz nie slyszy fali	
				//debugging
				System.out.println(timeDelay);
				System.out.println(timeRunaway);
			}
			
			private void countTimeDelayAndRunaway() {
				double deltaSqrt = Math.sqrt( gui.soundSpeed*gui.soundSpeed * 
						(gui.observer1X*gui.observer1X - 2*gui.observer1X*gui.sourceX + gui.sourceX*gui.sourceX + Math.pow( (gui.observer1Y-gui.sourceY), 2.0))
						-gui.observer1V*gui.observer1V*Math.pow( (gui.observer1Y-gui.sourceY), 2.0));				
				timeRunaway = -( (-gui.observer1V*gui.observer1X+gui.observer1V*gui.sourceX+deltaSqrt)
						/ (gui.soundSpeed*gui.soundSpeed-gui.observer1V*gui.observer1V) );
				timeRunaway = timeRunaway*1000;//zeby w ms
				
				timeDelay =  (gui.observer1V*gui.observer1X - gui.observer1V*gui.sourceX+deltaSqrt)
						/ (gui.soundSpeed*gui.soundSpeed-gui.observer1V*gui.observer1V) ;
				timeDelay = timeDelay*1000;//zeby w ms			
			}
			
			//UWAGA: jest dzielenie freq przez 100, zeby pracowalo dla szerszego zakresu czestotliwosci - dzieki temu jest do 10-15kHzkHz, a nie do 100-150Hz
			@Override
	 	   	protected void process(List<XYDataItem> dane) {//dodaje dane do serii i jak jest ich za duzo to usuwa
	 		   	for(XYDataItem d : dane) {
	 		   		xySeries.add(d);
	 		   	while(xySeries.getItemCount()>maxCount/((double)gui.soundFreq/100))//if(xySeries.getItemCount()>500)//jak sie zmieni wartosc maxCount, to szerokosc inna
	 		   			xySeries.remove(0);	//to na gorze co zakomentowane jesli ma sie nie dostosowywac do czestotliwosci szerokosc okna 
	 		   	}
	 	   	}
			@Override
			protected Void doInBackground() throws Exception {//oblicza wartosi sinusa, czas w ms i przesyla do process 
				//timeDelay = 	
				while(gui.isRunning) {		
					
					if(!gui.isPaused){ //pauzowanie  && (time <= timeRunaway)
						if((time >= timeDelay)  ) {	//jesli juz fala dotarla i obserwator jej nie uciekl
						if(gui.pAnimation.observer1.getX() < gui.pAnimation.source.getX()) {
							f = new Double(gui.soundFreq * ( (gui.soundSpeed + getVObserver() ) / (gui.soundSpeed + getVSource() ) ));
							}else {
								f = new Double(gui.soundFreq * ( (gui.soundSpeed - getVObserver() ) / (gui.soundSpeed - getVSource() ) ));
							}
							x = new Double(time);
							y = new Double(Math.sin(2*pi*(f/100)*time/1000));
							XYDataItem dataItem = new XYDataItem(x, y);
							publish(dataItem);
							time+=sleep;
							Thread.sleep(sleep);
						}else {//jesli jeszcze fala nie dotarla
							XYDataItem dataItem = new XYDataItem(time, 0.0);
							publish(dataItem);
							time+=sleep;
							Thread.sleep(sleep);
						}
					
					}else { //pauza
						Thread.sleep(1);
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
			double vObserver = gui.pAnimation.observer1.getVx()*Math.cos(getPhiObserver()) + gui.pAnimation.observer1.getVy()*Math.sin(getPhiObserver());			
			return vObserver;		
		}
		public double getVSource() {//zwraca skladowa predkosci zrodla wzdluz linii laczacej je z obserwatorem
			double vSource = gui.pAnimation.source.getVx()*Math.cos(getPhiSource()) + gui.pAnimation.source.getVy()*Math.sin(getPhiSource());			
			return vSource;			
		}
		//katy jak w specyfikacji we wzorze
		public double getPhiObserver() {//zwraca kat miedzy wektorem predkosci obserwatora a linia laczaca zrodlo z obserwatorem
			//phi = arctg((ys-yo)/(xs-xo)) - tak to liczy
			double phiObserver = Math.atan((gui.pAnimation.source.getY() - gui.pAnimation.observer1.getY()) / (gui.pAnimation.source.getX() - gui.pAnimation.observer1.getX()));
			
			if(gui.pAnimation.observer1.getY() < gui.pAnimation.source.getY())return module(phiObserver);
			else return (2*pi-module(phiObserver));
		}
		public double getPhiSource() {//zwraca kat miedzy wektorem predkosci zrodla a linia laczaca zrodlo z obserwatorem
			//phi = arctg((ys-yo)/(xs-xo)) - tak to liczy
			double phiSource = Math.atan((gui.pAnimation.source.getY() - gui.pAnimation.observer1.getY()) / (gui.pAnimation.source.getX() - gui.pAnimation.observer1.getX()));
			if(gui.pAnimation.observer1.getY() < gui.pAnimation.source.getY())return module(phiSource);
			else return (2*pi-module(phiSource));
		}
		public double module(double m) {//zwraca modul liczby
			if(m >= 0) return m;
			else return (-m);
		}
}
