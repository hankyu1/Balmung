package test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import entityGame.Entity;
import entityGame.EntityGame;

public class Bullet implements Entity {

	private String id;
	private Image img;
	private int dmg, acceleration;
	private double angle;
	private long lifeTime;
	private Rectangle bound;
	
	public Bullet(String id, Image img, int dmg, int acceleration, double angle, long lifeTime, int x, int y, int width, int height) {
		this.id = id;
		this.img = img;
		this.dmg = dmg;
		this.acceleration = acceleration;
		this.angle = angle;
		this.lifeTime = lifeTime;
		bound = new Rectangle(x-width/2,y-height/2,width,height);
	}
	
	@Override
	public Rectangle getBoundingBox() {
		// TODO Auto-generated method stub
		return bound;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void update(EntityGame eg) {
		// TODO Auto-generated method stub
		if(lifeTime >= 0) {
			double newX = Math.cos(Math.toRadians(angle))*acceleration + bound.x;
			double newY = Math.sin(Math.toRadians(angle))*acceleration + bound.y;
			
			//System.out.println("LifeTime: " + lifeTime + "\nX: " + newX + "\nY: " + newY);
			
			bound.setLocation((int)newX, (int)newY);
			acceleration += acceleration;
			lifeTime --;
		}
		else
			eg.removeFromCurrentScene(this);
	}

	@Override
	public void render(Graphics2D g, EntityGame eg) {
		// TODO Auto-generated method stub
		g.drawImage(img, bound.x, bound.y, eg.getCanvas());
	}

}
