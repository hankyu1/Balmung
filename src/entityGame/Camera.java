package entityGame;

import java.awt.Point;
import java.awt.Rectangle;

public class Camera extends Rectangle{

	public Camera(int x, int y, int w, int h) {
		this.setBounds(x, y, w, h);
	}
	
	public Rectangle getBound() {
		return this.getBound();
	}
	
	public void setPosition(int x, int y) {
		this.setPosition(x, y);
	}
	
	public void setDimension(int w, int h) {
		this.setSize(w, h);
	}
	
	public Point getCenter() {
		return this.getCenter();
	}
}
