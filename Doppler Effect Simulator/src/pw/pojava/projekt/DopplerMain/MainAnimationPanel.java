package pw.pojava.projekt.DopplerMain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


public class MainAnimationPanel extends JPanel implements Runnable {
	
	List<WaveCrest> crests = new ArrayList<WaveCrest>();
	AnimationObject observer1, observer2, source;
	double refreshRate = 0.001; //[ms]
	int timeQuantum=1; //czas spania [ms]
	int soundSpeed; //przechowuje robocza predkosc dzwieku
	double soundFreq;
	Graphics gg;
	Boolean isRunning=false; //przechowuje informacje o biegu animacji
	
	Dimension preferredSize = new Dimension(530,400);
	
	public MainAnimationPanel() {
		this.setBackground(new Color(200,255,255));
		this.setPreferredSize(preferredSize);
		
		//tworzy obiekty na animacjê
		observer1 = new AnimationObject();
		observer2 = new AnimationObject();
		source = new AnimationObject();
		observer1.setAppearance(true); //ustawia domyslne istnienie obserwatorów
        observer2.setAppearance(false);
		source.setAppearance(true); //zrodlo jest zawsze.
	}	
	
	    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);        
        
        gg = g;
        observer1.paint(gg);//rysuje ich w ich po³o¿eniach
        observer2.paint(gg);
        source.paint(gg);
        
        if(crests.isEmpty()==false)//jesli sa jakies grzbiety to je rysuje
        {
        	for (WaveCrest cr : crests) {
        		cr.paint(gg);
			}   
        }
    }
    //METODA RUN
	@Override
	public void run() {
	
		double quasiTime=0; //licznik mierzacy czas
		isRunning=true;
		
		while(true) 
		{
			if((quasiTime%(1000/soundFreq)==0)) { // tworzy grzbiet
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
		erase();
		System.out.println("End of animation");
		isRunning=false;
	}
	 public void erase() {
		 
		 
		 repaint();
	 }
	 
	public void setSoundSpeed(int v) {soundSpeed=v;}
	public void setFrequency(int freq) {soundFreq=freq;}
	
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

	

