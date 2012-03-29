package test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

import entityGame.Entity;
import entityGame.EntityGame;

public class Monster implements Entity, Cloneable {

	private String id;
	private double rotation, tempRotation = 1000;
	private int HP, velocity, dmg;
	private Image img;
	private Rectangle bound;
	private LinkedList<LinkedList<Entity>> list;
	private boolean isCollided;
	private LinkedList<Point> points;
	
	
	public Monster(String id, int HP, int velocity, int dmg, Image img, int x, int y, int width, int height) {
		this.id = id;
		this.HP = HP;
		this.velocity = velocity;
		this.dmg = dmg;
		this.img = img;
		rotation = 0;
		bound = new Rectangle(x, y, width, height);
		isCollided = false;
	}
	
	public void setPosition(int x, int y) {
		bound.x = x;
		bound.y = y;
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
				PlayerPoint.x = e.getBoundingBox().getLocation().x+e.getBoundingBox().width/2;
				PlayerPoint.y = e.getBoundingBox().getLocation().y+e.getBoundingBox().height/2;
				break FindPlayerPoint;
			}
		}
		
		rotation = Math.toDegrees(Math.atan2(PlayerPoint.y-(getCenter().y), PlayerPoint.x-(getCenter().x)));
		
		// if nothing is in the way
		list = eg.getGB().getGridList(this);

		//System.out.println("Size of list: " + list.size());
		
		//Point collidedPoint = null;
		points = new LinkedList<Point>();
		LinkedList<Entity> collideList = new LinkedList<Entity>();
		
		double newY, newX, aveX = 0, aveY = 0;
		newX = Math.cos(Math.toRadians(rotation))*velocity;
		newY = Math.sin(Math.toRadians(rotation))*velocity;
		
		for(LinkedList<Entity> le : list)
			for(Entity e : le) {
				if(!e.equals(this) && Player.class.isInstance(e) &&
				   eg.collisionDetection(new Rectangle(bound.x+(int)newX, bound.y+(int)newY, bound.width, bound.height),e.getBoundingBox())) {
					collideList.add(e);
					/*
					isCollided = true;
					Rectangle rect = e.getBoundingBox();
					points.add(new Point(rect.x+rect.width/2, rect.y+rect.height/2));*/
				}
				//else
					//isCollided = false;
			}
		
		if(collideList.isEmpty()) {
			isCollided = false;
		}
		else {
			isCollided = true;
			for(Entity e : collideList) {
				Rectangle rect = e.getBoundingBox();
				points.add(new Point(rect.x+rect.width/2, rect.y+rect.height/2));
			}
		}
		
		list = null;
		
		
		int pSize = points.size();
		if(isCollided) {
			//rotation = Math.toDegrees(Math.atan2(collidedPoint.y-(getCenter().y), collidedPoint.x-(getCenter().x)))+180;
			for(Point p : points) {
				aveX += p.x/pSize;
				aveY += p.y/pSize;
			}
			Point nP = new Point((int)aveX, (int)aveY);
			tempRotation = Math.toDegrees(Math.atan2(nP.y-(getCenter().y), nP.x-(getCenter().x)))+90;
			rotation = Math.toDegrees(Math.atan2(nP.y-(getCenter().y), nP.x-(getCenter().x)));
			points.clear();
		}
		/*
		newX = Math.cos(Math.toRadians(rotation))*velocity + bound.x;
		newY = Math.sin(Math.toRadians(rotation))*velocity + bound.y;
		
		if(tempRotation != 1000) {
			rotation = tempRotation;
			tempRotation = 1000;
		}
		*/
		if(!isCollided) {
			newX = Math.cos(Math.toRadians(rotation))*velocity + bound.x;
			newY = Math.sin(Math.toRadians(rotation))*velocity + bound.y;
			bound.setLocation((int)newX, (int)newY);
		}
		else {
			newX = Math.cos(Math.toRadians(tempRotation))*velocity + bound.x;
			newY = Math.sin(Math.toRadians(tempRotation))*velocity + bound.y;
			bound.setLocation((int)newX, (int)newY);
		}
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

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return true;
	}

}
