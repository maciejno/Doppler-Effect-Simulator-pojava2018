package pw.pojava.projekt.DopplerMain;

import java.util.List;

import javax.swing.BorderFactory;

import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

import pw.pojava.projekt.DopplerMain.ObserverAnimationPanel.DataToSimulate;


public class Observer1AnimationPanel extends ObserverAnimationPanel{

private static final long serialVersionUID = 1L;									
	
	public Observer1AnimationPanel(GUI gui, JFreeChart chart) {
		super(gui,chart);		
		super.xySeries = new XYSeries("Observer 1 signal");
		gui.observer1Collection.addSeries(xySeries);
		super.worker = new Observer1SwingWorker();
	}

	class Observer1SwingWorker extends ObserverSwingWorker{
		
		//UWAGA: jest dzielenie freq przez 100, zeby pracowalo dla szerszego zakresu czestotliwosci - dzieki temu jest do 10-15kHzkHz, a nie do 100-150Hz
		@Override
		protected void process(List<DataToSimulate> data) {//dodaje dane do serii i jak jest ich za duzo to usuwa
			gui.pChartObserver1.setBorder(BorderFactory.createTitledBorder("Dzwiek docierajacy do Obserwatora 1:     " + (data.get(data.size()-1).getFreq()).intValue() + "Hz"));
			for(DataToSimulate d : data) {
 		   		xySeries.add(d.getXY());
 		   	while(xySeries.getItemCount()>maxCount/((double)gui.soundFreq/100))//if(xySeries.getItemCount()>500)//jak sie zmieni wartosc maxCount, to szerokosc inna
 		   			xySeries.remove(0);	//to na gorze co zakomentowane jesli ma sie nie dostosowywac do czestotliwosci szerokosc okna 
 		   	}
 	   	}
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
						XYDataItem xyDataItem = new XYDataItem(x, y);
						
						DataToSimulate dataItem = new DataToSimulate(xyDataItem,f);
						publish(dataItem);
						
						time+=sleep;
						Thread.sleep(sleep);
					}else {//jesli jeszcze fala nie dotarla
						XYDataItem xyDataItem = new XYDataItem(time, 0.0);
						
						DataToSimulate dataItem = new DataToSimulate(xyDataItem,0.0);
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
					
		if((module((double)gui.sourceV)<=gui.soundSpeed)) {//przypadek nienaddzwiekowy
			timeDelay =  (gui.observer1V*gui.observer1X - gui.observer1V*gui.sourceX+deltaSqrt)
					/ (gui.soundSpeed*gui.soundSpeed-gui.observer1V*gui.observer1V) ;
			timeDelay = timeDelay*1000;//zeby w ms
			timeRunaway = -( (-gui.observer1V*gui.observer1X+gui.observer1V*gui.sourceX+deltaSqrt)
					/ (gui.soundSpeed*gui.soundSpeed-gui.observer1V*gui.observer1V) );
			timeRunaway = timeRunaway*1000;//zeby w ms	
		}else {//przypadek naddzwiekowy
			double machNumber = module(gui.sourceV) / gui.soundSpeed;//liczba Macha
			double machAngle = Math.asin(1/machNumber);//kat Macha
			double tanMA = Math.tan(machAngle);//tangens kata Macha - nachylenie ramienia stozka do poziomu
			//czas przeciecia z gornym ramieniem stozka
			double tUpperArm = (-tanMA*(gui.observer1X-gui.sourceX)+gui.observer1Y-gui.sourceY) / (tanMA*(gui.observer1V-gui.sourceV));
			//czas przeciecia z dolnym ramieniem stozka
			double tLowerArm = (-tanMA*(gui.sourceX-gui.observer1X)+gui.observer1Y-gui.sourceY) / (tanMA*(gui.sourceV-gui.observer1V));
									
			//jak jada w ta sama strone to upper jest upper itd, a jak w przeciwna to upper jest lower i vice versa (chyba)
			//jesli jada w te sama strone - wyprzedzanie odrzutowcow
			if((gui.sourceV>0 && gui.observer1V>0)||(gui.sourceV<0 && gui.observer1V<0)) {
				//wybiera ktore ramie przetnie uciekajac
				if(gui.observer1Y > gui.sourceY) timeRunaway=tLowerArm*1000 ;//mnozenie zeby bylo w ms
				else timeRunaway=tUpperArm*1000;
				timeDelay =  (gui.observer1V*gui.observer1X - gui.observer1V*gui.sourceX+deltaSqrt)
						/ (gui.soundSpeed*gui.soundSpeed-gui.observer1V*gui.observer1V) ;
				timeDelay = timeDelay*1000;//zeby w ms	
			}else {//jesli w przeciwna
				//wybiera ktore ramie przetnie i odpowiedni czas
				if(gui.observer1Y > gui.sourceY) timeDelay=tUpperArm*1000 ;//mnozenie zeby bylo w ms
				else timeDelay=tLowerArm*1000;
				timeRunaway = -( (-gui.observer1V*gui.observer1X+gui.observer1V*gui.sourceX+deltaSqrt)
						/ (gui.soundSpeed*gui.soundSpeed-gui.observer1V*gui.observer1V) );
				timeRunaway = timeRunaway*1000;//zeby w ms	
			}
		}
		if(timeDelay<0)timeDelay = 200000000;//duzy czas jak jest ujemna wartosc, zeby nigdy nie zaczal
		if(timeRunaway<0)timeRunaway = 200000000;//duzy czas jak jest ujemna wartosc, zeby nigdy nie uciekl	
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