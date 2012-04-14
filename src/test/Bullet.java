package test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Date;
import java.util.LinkedList;

import entityGame.Entity;
import entityGame.EntityGame;
import entityGame.Sprite;

public class Bullet implements Entity {

	private String id;
	private Image img;
	private int dmg, acceleration;
	private double angle;
	private long lifeTime, explosionTime;
	private Rectangle bound;
	private boolean explosion;
	private Sprite explosionSheet = null;
	
	public Bullet(String id, Image img, int dmg, int acceleration, double angle, long lifeTime, int x, int y, int width, int height) {
		this.id = id;
		this.img = img;
		this.dmg = dmg;
		this.acceleration = acceleration;
		this.angle = angle;
		this.lifeTime = lifeTime;
		bound = new Rectangle(x-width/2,y-height/2,width,height);
		explosion = false;
		explosionTime = 500;
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
		
		if(explosionSheet == null)
			try {
				explosionSheet = (Sprite)eg.getResourcesManager().getSpriteResources().get("BulletExplosion").clone();
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		Entity target = null;
		
		if(lifeTime >= 0) {
			
			LinkedList<LinkedList<Entity>> list = eg.getGB().getGridList(this);
			
			FindMonster:
			for(LinkedList<Entity> el : list) {
				for(Entity e : el) {
					if(!this.equals(e) && Monster.class.isInstance(e) && eg.collisionDetection(e.getBoundingBox(), bound)) {
						target = e;
						break FindMonster;
					}
				}
			}
			
			if(target == null) {
				double newX = Math.cos(Math.toRadians(angle))*acceleration + bound.x;
				double newY = Math.sin(Math.toRadians(angle))*acceleration + bound.y;
				
				//System.out.println("LifeTime: " + lifeTime + "\nX: " + newX + "\nY: " + newY);
				
				bound.setLocation((int)newX, (int)newY);
				acceleration += acceleration;
				lifeTime --;
			}
			else {
				if(!explosion) {
					((Monster) target).injur(dmg);
					DmgDisplay dp = new DmgDisplay("bulletDmg", "-"+dmg, 50, bound.x, bound.y, 1, 1);
					eg.addToCurrentScene(dp);
					explosionTime += new Date().getTime();
				}
				//eg.removeFromCurrentScene(this);
				explosion = true;
			}
			
		}
		else
			eg.removeFromCurrentScene(this);
	}

	@Override
	public void render(Graphics2D g, EntityGame eg) {
		// TODO Auto-generated method stub
		if(explosion) {
			long currentTime = new Date().getTime();
			explosionSheet.drawSpriteFrame(g, new Point(bound.x-30, bound.y-25), eg.getCanvas());
			//System.out.println(currentTime +", " + explosionTime);
			if(currentTime > explosionTime)
				eg.removeFromCurrentScene(this);
		}
		else {
			g.rotate(Math.toRadians(angle), bound.x+bound.width/2, bound.y+bound.height/2);
			g.drawImage(img, bound.x, bound.y, eg.getCanvas());
			g.rotate(-Math.toRadians(angle), bound.x+bound.width/2, bound.y+bound.height/2);
		}
		
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return true;
	}

}
