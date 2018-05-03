package pw.pojava.projekt.DopplerMain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import pw.pojava.projekt.DopplerMain.Observer1AnimationPanel.ObserverSwingWorker;


public class MainAnimationPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	List<WaveCrest> crests = new ArrayList<WaveCrest>();
	AnimationObject observer1, observer2, source;
	double refreshRate = 0.001; //[ms]
	int timeQuantum=1; //czas spania [ms]
	int soundSpeed; //przechowuje robocza predkosc dzwieku
	double soundFreq;
	Graphics gg;
	GUI superior; //referencja do klasy w ktorej siedzi
	mainAnimationWorker worker;	
	
	Dimension preferredSize = new Dimension(530,400);
	
	public MainAnimationPanel(GUI superior) {
		this.setBackground(new Color(200,255,255));
		this.setSize(preferredSize);	
		this.superior = superior;
		worker = new mainAnimationWorker();
		
		//tworzy obiekty na animacjê
		observer1 = new AnimationObject();
		observer2 = new AnimationObject();
		source = new AnimationObject();
		observer1.setAppearance(true); //ustawia domyslne istnienie obserwatorów
        observer2.setAppearance(false);
		source.setAppearance(true); //zrodlo jest zawsze.
	}		
	//settery porzebnych danych
	public void setSoundSpeed(int v) {soundSpeed=v;}
	public void setFrequency(int freq) {soundFreq=freq;}
		    
    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);              
        gg = g;
        observer1.paint(gg);//rysuje ich w ich po³o¿eniach
        observer2.paint(gg);
        source.paint(gg);        
        if(crests.isEmpty()==false){//jesli sa jakies grzbiety to je rysuje
        	for (WaveCrest cr : crests) {
        		cr.paint(gg);
			}   
        }
    }
    
    synchronized MainAnimationPanel takeThisPanel() {return this;} //metoda, ktora zwraca ten panel  
    
    class mainAnimationWorker extends SwingWorker<Void, MainAnimationPanel> {   	
    	
    	protected void process(List<MainAnimationPanel> thisPanel) {
    		for(JPanel pn : thisPanel)
    		pn.repaint();
    	}
		
		@Override
		protected Void doInBackground() throws Exception {
			superior.isRunning=true;
			double quasiTime=0; //licznik mierzacy czas
			double period=1000/(soundFreq/100);//przelicza czestotliwosc na 100 razy mniejsza zeby bylo lepiej widac
			
			synchronized (crests) { while(true) {
				if(((quasiTime)%period==0)) { // tworzy grzbiet //ten warunek nie dziala poprawnie ponizej 100Hz, bo tam wchodza ulamki
					WaveCrest crN = new WaveCrest();
					crN.setV(soundSpeed);
					crN.setX(source.getX());
					crN.setY(source.getY());
					crests.add(crN);
				}
				//oblicza nowe polozenia obiektow na podstawie ich predkosci
				if(observer1.appearance) {
					observer1.setX(observer1.getX()+observer1.getVx()*refreshRate);
					observer1.setY(observer1.getY()+observer1.getVy()*refreshRate);
				}
				if(observer2.appearance) {
				observer2.setX(observer2.getX()+observer2.getVx()*refreshRate);
				observer2.setY(observer2.getY()+observer2.getVy()*refreshRate);
				}
				source.setX(source.getX()+source.getVx()*refreshRate);
				source.setY(source.getY()+source.getVy()*refreshRate);
				
				if(!crests.isEmpty()) {//jesli sa jakies grzbiety
					for (WaveCrest cr : crests) {//ustawia promien kazdego z okregow tworzacych grzbiety fali
						cr.setR(cr.getR()+cr.getV()*refreshRate);
					}
				}
				//jak juz policzy to na nowo wszystko rysuje
				publish(takeThisPanel());
				//usypia watek na chwile
				try {
					TimeUnit.MILLISECONDS.sleep(1);
				}catch (InterruptedException e) {
						e.printStackTrace();
				}
				quasiTime=quasiTime+timeQuantum;
				
				//warunek zakonczenia petli while, ktora tworzy animacje
				if((((observer1.getX()>getWidth())||(observer1.getX()<0))||((observer2.getY()>getHeight())||(observer2.getY()<0))||((source.getX()>getWidth())||(source.getX()<0)))||(quasiTime>250000)) {
					quasiTime=0;
					System.out.println("statement...");
					break;
				}
			}}			
			superior.isRunning=false;//koniec animacji
			try {//usupia na pewien czas a potem czysci ekran
				TimeUnit.MILLISECONDS.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			crests.clear();//usuwa wszystkie grzbiety z listy
			superior.setAnimationParameters();
			repaint();
			System.out.println("End of animation");		
			return null;
		}
	};
	
	public void newWorker() {//metoda do tworzenia nowego swing workera
		worker = new mainAnimationWorker();		
	}
    
    /*METODA RUN
	@Override
	public void run() {
	
		double quasiTime=0; //licznik mierzacy czas
		isRunning=true;
		
		while(true) 
		{
			if(((quasiTime/1000)%(1/soundFreq)==0)) { // tworzy grzbiet
				WaveCrest crN = new WaveCrest();
				crN.setV(soundSpeed);
				crN.setX(source.getX());
				crN.setY(source.getY());
				crests.add(crN);
			}
			//oblicza nowe polozenia obiektow na podstawie ich predkosci
			if(observer1.appearance) {
				observer1.setX(observer1.getX()+observer1.getVx()*refreshRate);
				observer1.setY(observer1.getY()+observer1.getVy()*refreshRate);
			}
			if(observer2.appearance) {
			observer2.setX(observer2.getX()+observer2.getVx()*refreshRate);
			observer2.setY(observer2.getY()+observer2.getVy()*refreshRate);
			}
			source.setX(source.getX()+source.getVx()*refreshRate);
			source.setY(source.getY()+source.getVy()*refreshRate);
			
			if(!crests.isEmpty()) {//jesli sa jakies grzbiety
				for (WaveCrest cr : crests) {//ustawia promien kazdego z okregow tworzacych grzbiety fali
					cr.setR(cr.getR()+cr.getV()*refreshRate);
				}
			}
			//jak juz policzy to na nowo wszystko rysuje
			repaint();
			//usypia watek na chwile
			try {
				Thread.sleep(timeQuantum);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			quasiTime=quasiTime+timeQuantum;
			
			//warunek zakonczenia petli while, ktora tworzy animacje
			if((((observer1.getX()>this.getWidth())||(observer1.getX()<0))||((observer2.getY()>this.getHeight())||(observer2.getY()<0))||((source.getX()>this.getWidth())||(source.getX()<0)))||(quasiTime>250000)) {
				quasiTime=0;
				System.out.println("statement...");
				//!!!!!!!!!!!!!
				//trzeba tu dodac tworzenie nowego executora w gui zeby mozna bylo na nowo animowac
				break;
			}
		}
		crests.clear();//usuwa wszystkie grzbiety z listy
		try {//usupia na pewien czas a potem czysci ekran
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		superior.setAnimationParameters();
		repaint();
		System.out.println("End of animation");
		isRunning=false;
	}
	 

	
	
	
	
	/*
	 *juz zaimplementowane - w gui ustawia wszystkie parametry, a tu tworzy puste obiekty
	 *
	public void addObserver1(int vx, int vy, double x, double y) {
		observer1 = new AnimationObject();
		observer1.setX(x);
		observer1.setY(y);
		observer1.setVx(vx);
		observer1.setVy(vy);		
	}
	public void addObserver2(int vx, int vy, double x, double y, Color color) {
		observer2 = new AnimationObject();
		observer2.setX(x);
		observer2.setY(y);
		observer2.setVx(vx);
		observer2.setVy(vy);
	}
	public void addSource(int vx, int vy, double x, double y, Color color) {
		source = new AnimationObject();
		source.setX(x);
		source.setY(y);
		source.setVx(vx);
		source.setVy(vy);
	}
	public void addWaveCrest(int v, double x, double y, double r) {
		WaveCrest c = new WaveCrest();
		c.setX(x);
		c.setY(y);
		c.setV(v);
		c.setR(r);
		
		crests.add(c);
	}
	*/
	
	
	
}

	

