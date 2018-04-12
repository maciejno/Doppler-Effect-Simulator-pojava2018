package pw.pojava.projekt.DopplerMain;

import java.awt.Color;
import java.awt.Graphics;

public class AnimationObject {

	int vx = 0, vy = 0;//predkosc
	double x = 0, y = 0;//polozenie
	boolean appearance=false; //w��cza i wy��cza rysowanie obiektu (zrobione dla obserwator�w)
	
	
	Color color; //kolor obiektu
	
	public AnimationObject() {
		super();
		color = new Color (100,150,200);
	}
	
	public void setAppearance(boolean apr) { //ustawia pojawianie si�
		appearance=apr;
	}
	
	public void paint(Graphics g){
		if(appearance==true)
		{
        g.setColor(color);
        //rzutuje double na int
        g.fillOval((int)x-5, (int)y-5, 10, 10);//szerokosc kola 10, przesuwa polozenie jego lewego gornego rogu tak by srodek kola byl w punkcie x,y(czyli jak szerokosc 10, to o 5)
		}
		else if(appearance==false)
		{
			g.setColor(Color.WHITE); //nikt go nie zobaczy, czyli go nie ma
	        //rzutuje double na int
	        g.fillOval((int)x-5, (int)y-5, 10, 10);
		}
	}
	
	public void setVx(int vx) {this.vx = vx;}
	public void setVy(int vy) {this.vy = vy;}
	public int getVx() {return vx;}
	public int getVy() {return vy;}
	public void setX(double x) {this.x = x;}
	public void setY(double y) {this.y = y;}
	public double getX() {return x;}
	public double getY() {return y;}
	
}
