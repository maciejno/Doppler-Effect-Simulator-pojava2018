package pw.pojava.projekt.DopplerMain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;
import javax.swing.SwingWorker;




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
		source.setColor(new Color(250,50,50));
		observer1.setColor(Color.black);
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
			int quasiTime=0; //licznik mierzacy czas
			double period=(1000/(soundFreq/100));//przelicza czestotliwosc na 100 razy mniejsza zeby bylo lepiej widac
			
			synchronized (crests) { while(true) {
				if(superior.isPaused==false) // warunek do pauzowania
				{
					//System.out.println(quasiTime%period); // debugging, potem do usuniecia
					if(quasiTime%period<1) { //dziala od ok. 30Hz, przy nizszych freq caly program sie zawiesza (?)
						WaveCrest crN = new WaveCrest(); //dziala tez z freq, ktore nie sa wielokrotnosciami 10 - wczesniej nie dzialalo
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
						TimeUnit.MILLISECONDS.sleep(timeQuantum);
					}catch (InterruptedException e) {
							System.err.println("Przerwano animacje");;
					}
					quasiTime=quasiTime+timeQuantum;
					
					//warunek zakonczenia petli while, ktora tworzy animacje
					if((((observer1.getX()>getWidth())||(observer1.getX()<0))||((observer2.getY()>getHeight())||(observer2.getY()<0))||((source.getX()>getWidth())||(source.getX()<0)))||(quasiTime>250000)) {
						quasiTime=0;
						System.out.println("statement...");
						break;
					}
					if(superior.isRunning==false) {//warunek zakonczenia jesli sie wcisnie RESET
						Thread.sleep(50);
						break;
					}
				}else //pauza
				{
					try {
						TimeUnit.MILLISECONDS.sleep(1);
					}catch (InterruptedException e) {
							e.printStackTrace();
					}
				}
					
				}
			}			
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
			superior.startButton.setText("START");
			return null;
		}
	};
	
	public void newWorker() {//metoda do tworzenia nowego swing workera
		worker = new mainAnimationWorker();		
	}   
			
}

	

