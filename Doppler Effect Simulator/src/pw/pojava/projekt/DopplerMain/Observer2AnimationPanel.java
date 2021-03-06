package pw.pojava.projekt.DopplerMain;

import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.BorderFactory;

import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;


public class Observer2AnimationPanel extends ObserverAnimationPanel {

	private static final long serialVersionUID = 1L;			
	
	Sound sound2;
	
	public Observer2AnimationPanel(GUI gui, JFreeChart chart) throws LineUnavailableException {
		super(gui,chart);		
		super.xySeries = new XYSeries("Observer 2 signal");
		gui.observer2Collection.addSeries(xySeries);
		super.worker = new Observer1SwingWorker();
		sound2 = new Sound(gui);
	}
	
	class Observer1SwingWorker extends ObserverSwingWorker{
		//UWAGA: jest dzielenie freq przez 100, zeby pracowalo dla szerszego zakresu czestotliwosci - dzieki temu jest do 10-15kHzkHz, a nie do 100-150Hz
		@Override
		protected void process(List<DataToSimulate> data) {//dodaje dane do serii i jak jest ich za duzo to usuwa
			if(gui.language.equals("polish")) gui.pChartObserver2.setBorder(BorderFactory.createTitledBorder("Dzwiek docierajacy do Obserwatora 2:     " + (data.get(data.size()-1).getFreq()).intValue() + "Hz"));							
			else gui.pChartObserver2.setBorder(BorderFactory.createTitledBorder("Sound reaching Observer 2:     " + (data.get(data.size()-1).getFreq()).intValue() + "Hz"));
			
			for(DataToSimulate d : data) {
 		   		xySeries.add(d.getXY());
 		   	sound2.setSound(d.getFreq());
 		   	while(xySeries.getItemCount()>maxCount/(soundFreq/100))//if(xySeries.getItemCount()>500)//jak sie zmieni wartosc maxCount, to szerokosc inna
 		   			xySeries.remove(0);	//to na gorze co zakomentowane jesli ma sie nie dostosowywac do czestotliwosci szerokosc okna 
 		   	}
 	   	}
		@Override
		protected Void doInBackground() throws Exception {//oblicza wartosi sinusa, czas w ms i przesyla do process 
			while(gui.isRunning) {
				sound2.status=true;
				if(!gui.isPaused){ //pauzowanie  
					if((time >= timeDelay && (time <= timeRunaway))  ) {	//jesli juz fala dotarla i obserwator jej nie uciekl
						if(gui.pAnimation.observer2.getX() < gui.pAnimation.source.getX()) {
							f = new Double(soundFreq * ( (soundSpeed + getVObserver() ) / (soundSpeed + getVSource() ) ));
						}else {
								f = new Double(soundFreq * ( (soundSpeed - getVObserver() ) / (soundSpeed - getVSource() ) ));
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
					sound2.setSound(0);
					Thread.sleep(1);
				}					
			}
			sound2.status=false;
			sound2.line.close();
			return null;
		}		
}

	@Override
	void countTimeDelayAndRunaway() {
		
		double deltaSqrt = 0.0;		
		deltaSqrt = Math.sqrt( soundSpeed*soundSpeed * 
				(gui.observer2X*gui.observer2X - 2*gui.observer2X*gui.sourceX + gui.sourceX*gui.sourceX + Math.pow( (gui.observer2Y-gui.sourceY), 2.0))
				-gui.observer2V*gui.observer2V*Math.pow( (gui.observer2X-gui.sourceX), 2.0));			
		//DO PRZYBLIZANIA CZAS POTRZEBNY
		double time = module((gui.getOb2Y()-gui.getSourceY())/(gui.getSoundV()-gui.getOb2V()));//do przyblizania przecinania
				
		if(module((double)gui.sourceV)<=soundSpeed) {//przypadek nienaddzwiekowy		
			timeDelay =  (gui.observer2V*gui.observer2Y - gui.observer2V*gui.sourceY+deltaSqrt)
					/ (soundSpeed*soundSpeed-gui.observer2V*gui.observer2V) ;
			timeDelay = timeDelay*1000;//zeby w ms	
			timeRunaway = -( (-gui.observer2V*gui.observer2Y+gui.observer2V*gui.sourceY+deltaSqrt)
					/ (soundSpeed*soundSpeed-gui.observer2V*gui.observer2V) );
			timeRunaway = timeRunaway*1000;//zeby w ms
		}
		else {//przypadek naddzwiekowy
			double machNumber = module(gui.sourceV) / soundSpeed;//liczba Macha
			double machAngle = Math.asin(1/machNumber);//kat Macha
			double cotMA = 1/(Math.tan(machAngle));//cotangens kata Macha - nachylenie ramienia stozka do pionu
			//czas przeciecia z gornym ramieniem stozka
			double tUpperArm = (gui.observer2X - gui.sourceX + cotMA*(gui.observer2Y - gui.sourceY)) 
					/ (gui.sourceV - cotMA*gui.observer2V);
			//czas przeciecia z dolnym ramieniem stozka
			double tLowerArm = (gui.observer2X - gui.sourceX - cotMA*(gui.observer2Y - gui.sourceY)) 
					/ (cotMA*gui.observer2V + gui.sourceV);
						
			if(gui.observer2Y<gui.sourceY) {//jak obserwator jest wyzej niz zrodlo
				//wybiera ktore ramie przetnie i odpowiedni czas
				//observer2X to x przeciecia z liniami
				if(gui.sourceV<0) {//jesli zrodlo leci w lewo
					if( (gui.observer2X>(gui.sourceX+gui.sourceV*tUpperArm)) ){//jesli przetnie sie na stozku a nie poza nim z gornym ramieniem			
						timeDelay=tUpperArm*1000 ;//mnozenie zeby bylo w ms
						timeRunaway=tLowerArm*1000;
					}else if( (gui.observer2X > (gui.sourceX+gui.sourceV*tLowerArm)) ){//jesli przetnie sie na stozku a nie poza nim z dolnym ramieniem			
						timeDelay=tLowerArm*1000 ;//mnozenie zeby bylo w ms
						timeRunaway=200000000;//bo juz nie ucieknie						
					}
					else {
						timeDelay = 200000000;
						timeRunaway = 20000000;
					}
					//A CO JESLI PRZECINA TEN OKRAG NA TYLE STOZKA? --> TU WLASNIE LICZY PRZYBLIZONA METODA
					System.out.println("kat Macha: " + machAngle);
					if( (machAngle<0.785) && (gui.getOb2X()>gui.getSourceX()) ){//jesli mniejszy niz 45 stopni i jesli przecina okrag z tylu					
						timeDelay =  (gui.observer2V*gui.observer2Y - gui.observer2V*gui.sourceY+deltaSqrt)
								/ (soundSpeed*soundSpeed-gui.observer2V*gui.observer2V) ;
						timeDelay = timeDelay*1000;//zeby w ms	
						timeRunaway = -( (-gui.observer2V*gui.observer2Y+gui.observer2V*gui.sourceY+deltaSqrt)
								/ (soundSpeed*soundSpeed-gui.observer2V*gui.observer2V) );
						timeRunaway = timeRunaway*1000;//zeby w ms												
					}else if( (machAngle>=0.785) && (gui.getOb2X()>(gui.getSourceX() - gui.getSoundV()* time)) ){//jesli kat jest wiekszy niz 45 stopni i jesli przecina okrag z tylu ~ przyblizone						 
						timeDelay =  (gui.observer2V*gui.observer2Y - gui.observer2V*gui.sourceY+deltaSqrt)
								/ (soundSpeed*soundSpeed-gui.observer2V*gui.observer2V) ;
						timeDelay = timeDelay*1000;//zeby w ms	
						timeRunaway = -( (-gui.observer2V*gui.observer2Y+gui.observer2V*gui.sourceY+deltaSqrt)
								/ (soundSpeed*soundSpeed-gui.observer2V*gui.observer2V) );
						timeRunaway = timeRunaway*1000;//zeby w ms												
					}
					

				}else if(gui.sourceV>0){//jesli zrodlo leci w prawo
					if(gui.observer2X<(gui.sourceX+gui.sourceV*tLowerArm)){//jesli przetnie sie na stozku a nie poza nim z gornym ramieniem			
						timeDelay=tLowerArm*1000 ;//mnozenie zeby bylo w ms
						timeRunaway=tUpperArm*1000;
					}else if(gui.observer2X < (gui.sourceX+gui.sourceV*tUpperArm)){//jesli przetnie sie na stozku a nie poza nim z dolnym ramieniem			
						timeDelay=tUpperArm*1000 ;//mnozenie zeby bylo w ms
						timeRunaway=200000000;//bo juz nie ucieknie
					}else {
						timeDelay = 200000000;
						timeRunaway = 20000000;
					}
						//A CO JESLI PRZECINA TEN OKRAG NA TYLE STOZKA? --> TU WLASNIE LICZY PRZYBLIZONA METODA
					System.out.println("kat Macha: " + machAngle);
					if(machAngle<0.785){//jesli mniejszy niz 45 stopni
						if(gui.getOb2X()<gui.getSourceX()) {//jesli przecina okrag z tylu
							timeDelay =  (gui.observer2V*gui.observer2Y - gui.observer2V*gui.sourceY+deltaSqrt)
									/ (soundSpeed*soundSpeed-gui.observer2V*gui.observer2V) ;
							timeDelay = timeDelay*1000;//zeby w ms	
							timeRunaway = -( (-gui.observer2V*gui.observer2Y+gui.observer2V*gui.sourceY+deltaSqrt)
									/ (soundSpeed*soundSpeed-gui.observer2V*gui.observer2V) );
							timeRunaway = timeRunaway*1000;//zeby w ms							
						}
					}else {//jesli kat jest wiekszy niz 45 stopni
						if(gui.getOb2X()<(gui.getSourceX() + gui.getSoundV()* time)) {//jesli przecina okrag z tylu ~ przyblizone
							timeDelay =  (gui.observer2V*gui.observer2Y - gui.observer2V*gui.sourceY+deltaSqrt)
									/ (soundSpeed*soundSpeed-gui.observer2V*gui.observer2V) ;
							timeDelay = timeDelay*1000;//zeby w ms	
							timeRunaway = -( (-gui.observer2V*gui.observer2Y+gui.observer2V*gui.sourceY+deltaSqrt)
									/ (soundSpeed*soundSpeed-gui.observer2V*gui.observer2V) );
							timeRunaway = timeRunaway*1000;//zeby w ms							
						}
					}
				}
				
				
			}else {//jak obserwator jest nizej niz zrodlo
				//wybiera ktore ramie przetnie i odpowiedni czas
				//observer2X to x przeciecia z liniami
				if(gui.sourceV<0) {//jesli zrodlo leci w lewo
					if(gui.observer2X>(gui.sourceX+gui.sourceV*tLowerArm)){//jesli przetnie sie na stozku a nie poza nim z dolnym ramieniem			
						timeDelay=tLowerArm*1000 ;//mnozenie zeby bylo w ms
						timeRunaway=tUpperArm*1000;
					}else {
						if(gui.observer2X > (gui.sourceX+gui.sourceV*tUpperArm)){//jesli przetnie sie na stozku a nie poza nim z gornym ramieniem			
							timeDelay=tUpperArm*1000 ;//mnozenie zeby bylo w ms
							timeRunaway=200000000;//bo juz nie ucieknie
						}
					}
					//A CO JESLI PRZECINA TEN OKRAG NA TYLE STOZKA? --> TU WLASNIE LICZY PRZYBLIZONA METODA
					System.out.println("kat Macha: " + machAngle);
					if(machAngle<0.785){//jesli mniejszy niz 45 stopni
						if(gui.getOb2X()>gui.getSourceX()) {//jesli przecina okrag z tylu
							timeDelay =  (gui.observer2V*gui.observer2Y - gui.observer2V*gui.sourceY+deltaSqrt)
									/ (soundSpeed*soundSpeed-gui.observer2V*gui.observer2V) ;
							timeDelay = timeDelay*1000;//zeby w ms	
							timeRunaway = -( (-gui.observer2V*gui.observer2Y+gui.observer2V*gui.sourceY+deltaSqrt)
									/ (soundSpeed*soundSpeed-gui.observer2V*gui.observer2V) );
							timeRunaway = timeRunaway*1000;//zeby w ms							
						}
					}else {//jesli kat jest wiekszy niz 45 stopni
						if(gui.getOb2X()>(gui.getSourceX() - gui.getSoundV()* time)) {//jesli przecina okrag z tylu ~ przyblizone
							timeDelay =  (gui.observer2V*gui.observer2Y - gui.observer2V*gui.sourceY+deltaSqrt)
									/ (soundSpeed*soundSpeed-gui.observer2V*gui.observer2V) ;
							timeDelay = timeDelay*1000;//zeby w ms	
							timeRunaway = -( (-gui.observer2V*gui.observer2Y+gui.observer2V*gui.sourceY+deltaSqrt)
									/ (soundSpeed*soundSpeed-gui.observer2V*gui.observer2V) );
							timeRunaway = timeRunaway*1000;//zeby w ms							
						}
					}
				}else {	//jesli w prawo leci zrodlo			
					if(gui.observer2X<(gui.sourceX+gui.sourceV*tUpperArm)){//jesli przetnie sie na stozku a nie poza nim z dolnym ramieniem			
						timeDelay=tUpperArm*1000 ;//mnozenie zeby bylo w ms
						timeRunaway=tLowerArm*1000;
					}else if(gui.observer2X < (gui.sourceX+gui.sourceV*tLowerArm)){//jesli przetnie sie na stozku a nie poza nim z gornym ramieniem			
						timeDelay=tLowerArm*1000 ;//mnozenie zeby bylo w ms
						timeRunaway=200000000;//bo juz nie ucieknie
					}
					else {
						timeDelay = 200000000;
						timeRunaway = 20000000;
					}
					//A CO JESLI PRZECINA TEN OKRAG NA TYLE STOZKA? --> TU WLASNIE LICZY PRZYBLIZONA METODA
					System.out.println("kat Macha: " + machAngle);
					if(machAngle<0.785){//jesli mniejszy niz 45 stopni
						if(gui.getOb2X()<gui.getSourceX()) {//jesli przecina okrag z tylu
							timeDelay =  (gui.observer2V*gui.observer2Y - gui.observer2V*gui.sourceY+deltaSqrt)
									/ (soundSpeed*soundSpeed-gui.observer2V*gui.observer2V) ;
							timeDelay = timeDelay*1000;//zeby w ms	
							timeRunaway = -( (-gui.observer2V*gui.observer2Y+gui.observer2V*gui.sourceY+deltaSqrt)
									/ (soundSpeed*soundSpeed-gui.observer2V*gui.observer2V) );
							timeRunaway = timeRunaway*1000;//zeby w ms							
						}
					}else {//jesli kat jest wiekszy niz 45 stopni
						if(gui.getOb2X()<(gui.getSourceX() + gui.getSoundV()* time)) {//jesli przecina okrag z tylu ~ przyblizone
							timeDelay =  (gui.observer2V*gui.observer2Y - gui.observer2V*gui.sourceY+deltaSqrt)
									/ (soundSpeed*soundSpeed-gui.observer2V*gui.observer2V) ;
							timeDelay = timeDelay*1000;//zeby w ms	
							timeRunaway = -( (-gui.observer2V*gui.observer2Y+gui.observer2V*gui.sourceY+deltaSqrt)
									/ (soundSpeed*soundSpeed-gui.observer2V*gui.observer2V) );
							timeRunaway = timeRunaway*1000;//zeby w ms							
						}
					}
				}	
			}
		}
		if(timeDelay<0)timeDelay = 200000000;//duzy czas jak jest ujemna wartosc, zeby nigdy nie zaczal	
		if(timeRunaway<0)timeRunaway = 200000000;//duzy czas jak jest ujemna wartosc, zeby nigdy nie uciekl
		if(timeRunaway<timeDelay)timeRunaway = 200000000;//jezeli czas ucieczki bylby mniejszy niz dotarcia
		System.out.println(timeDelay + "-->koncowe Delay");System.out.println(timeRunaway+ "-->koncowe Runaway");
	}

	@Override
	public void newWorker() {//metoda do tworzenia nowego swing workera
		worker = new Observer1SwingWorker();
		try {
			sound2.newSound();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sound2.status=true;
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
