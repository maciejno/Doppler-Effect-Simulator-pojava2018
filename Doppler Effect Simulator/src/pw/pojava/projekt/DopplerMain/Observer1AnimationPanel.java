package pw.pojava.projekt.DopplerMain;

import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;


public class Observer1AnimationPanel extends ObserverAnimationPanel{

private static final long serialVersionUID = 1L;									
	
	public Observer1AnimationPanel(GUI gui, JFreeChart chart) {
		super(gui,chart);		
		super.xySeries = new XYSeries("Observer 1 signal");
		gui.observer1Collection.addSeries(xySeries);
		super.worker = new Observer1SwingWorker();
	}

	class Observer1SwingWorker extends ObserverSwingWorker{
		@Override
		protected Void doInBackground() throws Exception {
			//oblicza wartosi sinusa, czas w ms i przesyla do process 				
			while(gui.isRunning) {							
				if(!gui.isPaused){ //pauzowanie  
					if((time >= timeDelay && (time <= timeRunaway))  ) {	//jesli juz fala dotarla i obserwator jej nie uciekl
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
	@Override
	void countTimeDelayAndRunaway() {//nieraz daje NaN, ale interpretuje jako 0, wiec dziala
		double deltaSqrt = 0.0;		
		deltaSqrt = Math.sqrt( gui.soundSpeed*gui.soundSpeed * 
				(gui.observer1X*gui.observer1X - 2*gui.observer1X*gui.sourceX + gui.sourceX*gui.sourceX + Math.pow( (gui.observer1Y-gui.sourceY), 2.0))
				-gui.observer1V*gui.observer1V*Math.pow( (gui.observer1Y-gui.sourceY), 2.0));
		timeRunaway = -( (-gui.observer1V*gui.observer1X+gui.observer1V*gui.sourceX+deltaSqrt)
			/ (gui.soundSpeed*gui.soundSpeed-gui.observer1V*gui.observer1V) );
		timeRunaway = timeRunaway*1000;//zeby w ms		
		
		if(timeRunaway<0)timeRunaway = 200000000;//duzy czas jak jest ujemna wartosc, zeby nigdy nie uciekl	
						
		timeDelay =  (gui.observer1V*gui.observer1X - gui.observer1V*gui.sourceX+deltaSqrt)
				/ (gui.soundSpeed*gui.soundSpeed-gui.observer1V*gui.observer1V) ;
		timeDelay = timeDelay*1000;//zeby w ms
	}
	@Override
	public void newWorker() {//metoda do tworzenia nowego swing workera
		worker = new Observer1SwingWorker();	
	}
	@Override
	public double getVObserver() {//zwraca skladowa predkosci obserwatora wzdluz linii laczacej go ze zrodlem
		double vObserver = gui.pAnimation.observer1.getVx()*Math.cos(getPhiObserver()) + gui.pAnimation.observer1.getVy()*Math.sin(getPhiObserver());			
		return vObserver;		
	}

	@Override
	public double getPhiObserver() {//zwraca kat miedzy wektorem predkosci obserwatora a linia laczaca zrodlo z obserwatorem
		//phi = arctg((ys-yo)/(xs-xo)) - tak to liczy
		double phiObserver = Math.atan((gui.pAnimation.source.getY() - gui.pAnimation.observer1.getY()) / (gui.pAnimation.source.getX() - gui.pAnimation.observer1.getX()));
		
		if(gui.pAnimation.observer1.getY() < gui.pAnimation.source.getY())return module(phiObserver);
		else return (2*pi-module(phiObserver));
	}

	@Override
	public double getPhiSource() {//zwraca kat miedzy wektorem predkosci zrodla a linia laczaca zrodlo z obserwatorem
		//phi = arctg((ys-yo)/(xs-xo)) - tak to liczy
		double phiSource = Math.atan((gui.pAnimation.source.getY() - gui.pAnimation.observer1.getY()) / (gui.pAnimation.source.getX() - gui.pAnimation.observer1.getX()));
		if(gui.pAnimation.observer1.getY() < gui.pAnimation.source.getY())return module(phiSource);
		else return (2*pi-module(phiSource));
	}
}