package test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Date;
import java.util.LinkedList;

import entityGame.Entity;
import entityGame.EntityGame;

public class Monster implements Entity, Cloneable {

	private String id;
	private double rotation;
	private int HP, velocity, dmg;
	private Image img;
	private Rectangle bound;
	private LinkedList<LinkedList<Entity>> list;
	private LinkedList<Point> points;
	private boolean attacking;
	private long currentTime, nextTime, attackingRate;
	
	public Monster(String id, int HP, int velocity, int dmg, Image img, int x, int y, int width, int height) {
		this.id = id;
		this.HP = HP;
		this.velocity = velocity;
		this.dmg = dmg;
		this.img = img;
		rotation = 0;
		bound = new Rectangle(x, y, width, height);
		points = new LinkedList<Point>();
		attacking = false;
		currentTime = 1;
		nextTime = 0;
		attackingRate = 800;
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
		System.out.println("HP: " + HP);
		if(HP > 0) {
			Entity Player = null;
			currentTime = new Date().getTime();
			
			FindPlayerPoint:
			for(Entity e : eg.getCurrentScene()) {
				if(Player.class.isInstance(e)) {
					Player = e;
					
					break FindPlayerPoint;
				}
			}
			
			rotation = Math.toDegrees(Math.atan2(Player.getBoundingBox().y-(getCenter().y), Player.getBoundingBox().x-(getCenter().x)));
			
			double newY, newX;
			newX = Math.cos(Math.toRadians(rotation))*velocity;
			newY = Math.sin(Math.toRadians(rotation))*velocity;
			
			int attackRange = 5;
			
			double attackX = Math.cos(Math.toRadians(rotation))*attackRange + bound.x + bound.width/2, 
				   attackY = Math.sin(Math.toRadians(rotation))*attackRange + bound.y + bound.height/2;
			int attackWidth = 1, attackHeight = 1;
			
			Rectangle attackingRect = new Rectangle((int)attackX, (int)attackY, attackWidth, attackHeight);
			
			if(attackingRect.intersects(Player.getBoundingBox())) {
				attacking = true;
			}
			
			if(attacking) {
				if(currentTime >= nextTime) {
					nextTime = currentTime + attackingRate;
					
					// do dmg
					((Player)Player).injur(dmg);
					
					attacking = false;
				}
				
				
			}
			else
				bound.setLocation((int) newX+bound.x,(int) newY + bound.y);
		}
		else {
			eg.removeFromCurrentScene(this);
		}
		
		
		
		/*
		list = eg.getGB().getGridList(this);
		
		LinkedList<Entity> collideList = new LinkedList<Entity>();
		
		double newY, newX;
		newX = Math.cos(Math.toRadians(rotation))*velocity;
		newY = Math.sin(Math.toRadians(rotation))*velocity;
		
		
		
		list.clear();
		
		// if collideList is empty, there is nothing on the way so keep moving. Else, make the monster goes around the blocking object
		if(collideList.isEmpty()) {
			bound.x += (int) newX;
			bound.y += (int) newY;
		}
		else {
			//System.out.println("Size of collideList: " + collideList.size());
			Point minPoint = new Point();
			for(int i = 1; i <= 360; i++) {
				boolean isCollide = false;
				newX = Math.cos(Math.toRadians(rotation+i))*velocity + bound.x;
				newY = Math.sin(Math.toRadians(rotation+i))*velocity + bound.y; 
				
				for(Entity e : collideList) {
					if(e.getBoundingBox().intersects(new Rectangle((int)newX, (int)newY, bound.width, bound.height))) {
						isCollide = true;
					}
				}
				
				if(!isCollide) {
					//System.out.println("ASDFASDFASDFA");
					points.add(new Point((int)newX, (int)newY));
				}
				
			}
			// if points is empty, that means there is no way to go around the object.
			if(!points.isEmpty()) {
				//System.out.println("ASDFASDF");
				
				if(points.size() == 1)
					minPoint = points.get(0);
				else {
					minPoint = points.get(0);
					for(int i = 1; i < points.size(); i++) {
						double distance1 = Math.sqrt(Math.pow(minPoint.x-playerPoint.x, 2) + Math.pow(minPoint.y, 2)),
							   distance2 = Math.sqrt(Math.pow(points.get(i).x-playerPoint.x, 2) + Math.pow(points.get(i).y, 2));
						if(distance2 < distance1) {
							minPoint = points.get(i);
						}
					}
				}
			}
			bound.setLocation(minPoint);
		}
		collideList.clear();
		points.clear();
		*/
		/*
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
				if(!e.equals(this) && (Monster.class.isInstance(e) || Player.class.isInstance(e) )&&
				   eg.collisionDetection(new Rectangle(bound.x+(int)newX, bound.y+(int)newY, bound.width, bound.height),e.getBoundingBox())) {
					collideList.add(e);
				}
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
			tempRotation = Math.toDegrees(Math.atan2(nP.y-(getCenter().y), nP.x-(getCenter().x)))-180;
			rotation = Math.toDegrees(Math.atan2(nP.y-(getCenter().y), nP.x-(getCenter().x)));
			points.clear();
			newX = Math.cos(Math.toRadians(tempRotation))*velocity + bound.x;
			newY = Math.sin(Math.toRadians(tempRotation))*velocity + bound.y;
			bound.setLocation((int)newX, (int)newY);
		}
		else {
			newX = Math.cos(Math.toRadians(rotation))*velocity + bound.x;
			newY = Math.sin(Math.toRadians(rotation))*velocity + bound.y;
			bound.setLocation((int)newX, (int)newY);
		}
		*/
	}
	
	public void injur(int dmg) {
		HP -= dmg;
	}
	
	private Point getCenter() {
		return new Point(bound.x+bound.width/2, bound.y+bound.height/2);
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
