package pw.pojava.projekt.DopplerMain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class AnimationObject {//obserwator, badz zrodlo dzwieku

	GUI gui;
	int vx = 0, vy = 0;//predkosc
	double x = 0, y = 0;//polozenie
	boolean appearance=true; //w³¹cza i wy³¹cza rysowanie obiektu (zrobione dla obserwatorów)
	Color color; //kolor obiektu
	Image icon;//ikonka docelowa
	
	public AnimationObject(GUI gui) {
		super();
		this.gui = gui;
		color = new Color (50,50,250);
	}	
	public void paint(Graphics g){
		if(appearance==true)
			g.setColor(color);
		if(appearance==false)
			g.setColor(new Color(0,0,0,0)); //nikt go nie zobaczy, czyli go nie ma	        
		//rzutuje double na int
		if( gui.getOption().equals("NaMorzaDnie") || gui.getOption().equals("OnTheSeaBed")) {    		
    		g.drawImage(icon,(int)x-5,(int)y-5,null);
    	}else  g.fillOval((int)x-5, (int)y-5, 10, 10);
       
	}
	
	public void setVx(int vx) {this.vx = vx;}
	public void setVy(int vy) {this.vy = vy;}
	public int getVx() {return vx;}
	public int getVy() {return vy;}
	public void setX(double x) {this.x = x;}
	public void setY(double y) {this.y = y;}
	public void setColor(Color c) {color=c;}
	public double getX() {return x;}
	public double getY() {return y;}
	public void setAppearance(boolean apr) {appearance=apr;}//ustawia pojawianie siê
	public boolean getAppearance() {return appearance;}
	public void setIcon(Image icon) {this.icon = icon;}
}
