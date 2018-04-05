package pw.pojava.projekt.DopplerMain;

public class AnimationObject {

	int vx, vy;//predkosc
	int x, y;//polozenie
	
	public AnimationObject() {
		super();
	}

	public void setVx(int vx) {this.vx = vx;}
	public void setVy(int vy) {this.vy = vy;}
	public int getVx() {return vx;}
	public int getVy() {return vy;}
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	public int getX() {return x;}
	public int getY() {return y;}
}
