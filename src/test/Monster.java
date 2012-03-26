package test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import entityGame.Entity;
import entityGame.EntityGame;

public class Monster implements Entity {

	private String id;
	private double rotation;
	private int HP, velocity, dmg;
	private Image img;
	private Rectangle bound;
	
	public Monster(String id, int HP, int velocity, int dmg, Image img, int x, int y, int width, int height) {
		this.id = id;
		this.HP = HP;
		this.velocity = velocity;
		this.dmg = dmg;
		this.img = img;
		rotation = 0;
		bound = new Rectangle(x, y, width, height);
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
		Point PlayerPoint = new Point();
		
		// find player
		FindPlayerPoint:
		for(Entity e : eg.getCurrentScene()) {
			if(Player.class.isInstance(e)) {
				PlayerPoint = e.getBoundingBox().getLocation();
				break FindPlayerPoint;
			}
		}
		
		rotation = Math.toDegrees(Math.atan2(PlayerPoint.y-(getCenter().y), PlayerPoint.x-(getCenter().x)));
		
		double newX = Math.cos(Math.toRadians(rotation))*velocity + bound.x;
		double newY = Math.sin(Math.toRadians(rotation))*velocity + bound.y;
		
		bound.setLocation((int)newX, (int)newY);
	}
	
	private Point getCenter() {
		return new Point(getBoundingBox().x+getBoundingBox().width/2, getBoundingBox().y+getBoundingBox().height/2);
	}

	@Override
	public void render(Graphics2D g, EntityGame eg) {
		// TODO Auto-generated method stub
		g.rotate(Math.toRadians(rotation), bound.x+bound.width/2, bound.y+bound.height/2);
		//spriteSheet.drawSpriteFrame(g, new Point(bound.x, bound.y), eg.getCanvas());
		g.drawImage(img, bound.x, bound.y, eg.getCanvas());
		g.rotate(-Math.toRadians(rotation), bound.x+bound.width/2, bound.y+bound.height/2);
	}

}
