package pw.pojava.projekt.DopplerMain;


import java.awt.*;

public class WaveCrest { //grzbiet fali

	double x = 0, y = 0, r = 0;//polozenie srodka grzbietu 
	int v = 0;//i predkosc fali
	
	Color color = new Color (100,100,100);//kolor grzbietu
	
	public WaveCrest() {
		super();
	}

	public synchronized void paint(Graphics g){
        g.setColor(color);
        //rzutuje double na int
        g.drawOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));//szerokosc okregu 2r, przesuwa polozenie jego lewego gornego rogu tak by srodek okregu byl w punkcie x,y(czyli jak szerokosc 2r, to o r)
    }
	
	public synchronized void setV(int v) {this.v = v;}
	public int getV() {return v;}
	public synchronized void setX(double x) {this.x = x;}
	public synchronized void setY(double y) {this.y = y;}
	public synchronized void setR(double r) {this.r = r;}
	public double getX() {return x;}
	public double getY() {return y;}
	public double getR() {return r;}
	public Color getColor() {return color;}
	public void setColor(Color color) {this.color = color;}
	
}
