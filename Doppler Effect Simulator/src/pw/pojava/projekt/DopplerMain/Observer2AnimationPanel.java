package pw.pojava.projekt.DopplerMain;

import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

public class Observer2AnimationPanel extends ObserverAnimationPanel {

	private static final long serialVersionUID = 1L;									
	
	public Observer2AnimationPanel(GUI gui, JFreeChart chart) {
		super(gui,chart);		
		super.xySeries = new XYSeries("Observer 2 signal");
		gui.observer2Collection.addSeries(xySeries);
		super.worker = new Observer1SwingWorker();
	}
	
	class Observer1SwingWorker extends ObserverSwingWorker{
		@Override
		protected Void doInBackground() throws Exception {//oblicza wartosi sinusa, czas w ms i przesyla do process 
			while(gui.isRunning) {							
				if(!gui.isPaused){ //pauzowanie  
					if((time >= timeDelay && (time <= timeRunaway))  ) {	//jesli juz fala dotarla i obserwator jej nie uciekl
						if(gui.pAnimation.observer2.getX() < gui.pAnimation.source.getX()) {
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
	void countTimeDelayAndRunaway() {
		double deltaSqrt = 0.0;		
		deltaSqrt = Math.sqrt( gui.soundSpeed*gui.soundSpeed * 
				(gui.observer2X*gui.observer2X - 2*gui.observer2X*gui.sourceX + gui.sourceX*gui.sourceX + Math.pow( (gui.observer2Y-gui.sourceY), 2.0))
				-gui.observer2V*gui.observer2V*Math.pow( (gui.observer2X-gui.sourceX), 2.0));					
		
		if(module((double)gui.sourceV)<=gui.soundSpeed) {//przypadek nienaddzwiekowy		
			timeDelay =  (gui.observer2V*gui.observer2Y - gui.observer2V*gui.sourceY+deltaSqrt)
					/ (gui.soundSpeed*gui.soundSpeed-gui.observer2V*gui.observer2V) ;
			timeDelay = timeDelay*1000;//zeby w ms	
			timeRunaway = -( (-gui.observer2V*gui.observer2Y+gui.observer2V*gui.sourceY+deltaSqrt)
					/ (gui.soundSpeed*gui.soundSpeed-gui.observer2V*gui.observer2V) );
				timeRunaway = timeRunaway*1000;//zeby w ms
		}
		//DOPRACOWAC
		else {//przypadek naddzwiekowy
			double machNumber = module(gui.sourceV) / gui.soundSpeed;//liczba Macha
			double machAngle = Math.asin(1/machNumber);//kat Macha
			double cotMA = 1/(Math.tan(machAngle));//cotangens kata Macha - nachylenie ramienia stozka do pionu
			//czas przeciecia z gornym ramieniem stozka
			double tUpperArm = (gui.observer2X - gui.sourceX + cotMA*(gui.observer2Y - gui.sourceY)) 
					/ (gui.sourceV - cotMA*gui.observer2V);
			//BLEDNE
			//czas przeciecia z dolnym ramieniem stozka
			double tLowerArm = (gui.observer2X - gui.sourceX - cotMA*(gui.observer2Y - gui.sourceY)) 
					/ (cotMA*(gui.observer2V - gui.sourceV));
			if(gui.observer2Y>gui.sourceY) {
				//wybiera ktore ramie przetnie i odpowiedni czas
				timeDelay=tLowerArm*1000 ;//mnozenie zeby bylo w ms
				timeRunaway=tUpperArm*1000;
			}else {
				timeDelay=tUpperArm*1000 ;//mnozenie zeby bylo w ms
				timeRunaway=tLowerArm*1000;
			}
		}
		if(timeRunaway<0)timeRunaway = 200000000;//duzy czas jak jest ujemna wartosc, zeby nigdy nie uciekl	
	}

	@Override
	public void newWorker() {//metoda do tworzenia nowego swing workera
		worker = new Observer1SwingWorker();		
	}

	@Override
	public double getVObserver() {//zwraca skladowa predkosci obserwatora wzdluz linii laczacej go ze zrodlem
		double vObserver = gui.pAnimation.observer2.getVx()*Math.cos(getPhiObserver()) + gui.pAnimation.observer2.getVy()*Math.sin(getPhiObserver());			
		return vObserver;	
	}

	@Override
	public double getPhiObserver() {//zwraca kat miedzy wektorem predkosci obserwatora a linia laczaca zrodlo z obserwatorem
		//phi = arctg((ys-yo)/(xs-xo)) - tak to liczy
		double phiObserver = Math.atan((gui.pAnimation.source.getY() - gui.pAnimation.observer2.getY()) / (gui.pAnimation.source.getX() - gui.pAnimation.observer2.getX()));
		
		if(gui.pAnimation.observer2.getY() < gui.pAnimation.source.getY())return module(phiObserver);
		else return (2*pi-module(phiObserver));
	}

	@Override
	public double getPhiSource() {//zwraca kat miedzy wektorem predkosci zrodla a linia laczaca zrodlo z obserwatorem
		//phi = arctg((ys-yo)/(xs-xo)) - tak to liczy
		double phiSource = Math.atan((gui.pAnimation.source.getY() - gui.pAnimation.observer2.getY()) / (gui.pAnimation.source.getX() - gui.pAnimation.observer2.getX()));
		if(gui.pAnimation.observer2.getY() < gui.pAnimation.source.getY())return module(phiSource);
		else return (2*pi-module(phiSource));
	}
}
