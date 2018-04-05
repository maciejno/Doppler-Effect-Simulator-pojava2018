package pw.pojava.projekt.DopplerMain;

import java.awt.Graphics;

public class WaveCrest {

	int x,y, v;//polozenie srodka grzbietu i predkosc fali
		
	public WaveCrest() {
		super();
	}

	public void setV(int v) {this.v = v;}
	public int getV() {return v;}
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	public int getX() {return x;}
	public int getY() {return y;}
	
	public void paint(Graphics g) {
	}
	
	
}
