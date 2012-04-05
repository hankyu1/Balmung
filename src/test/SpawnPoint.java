package test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Date;

import entityGame.Entity;
import entityGame.EntityGame;

public class SpawnPoint implements Entity {

	private long spawnTime, currentTime, nextTime;
	private String id;
	private Image img;
	private Rectangle bound;
	private boolean isSpawning;
	private double rotation;
	private int NumToSpawn, counter;
	
	public SpawnPoint(String id, Image img, long spawnTime, int NumToSpawn, int x, int y, int width, int height) {
		this.id = id;
		this.img = img;
		this.spawnTime = spawnTime;
		bound = new Rectangle(x, y, width, height);
		isSpawning = false;
		rotation = 0;
		nextTime = 0;
		this.NumToSpawn = NumToSpawn;
		counter = 0;
	}
	
	public Point getCenterPoint() {
		return new Point(bound.x+bound.width/2, bound.y+bound.height/2);
	}
	
	public void setSpawning(boolean flag) {
		isSpawning = flag;
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
		// spinning
		if(rotation >= 360) {
			rotation = 0;
		}
		else
			rotation++;
		
		// if it is spawning
		if(isSpawning) {
			if(counter < NumToSpawn) {
				currentTime = new Date().getTime();
				if(currentTime >= nextTime) {
					// set next time
					nextTime = currentTime + spawnTime;
					
					//System.out.println("Spawning...");
					Monster monster = new Monster("Monster", 10, 3, 5, eg.getResourcesManager().getImageResources().get("Angel"), getCenterPoint().x, getCenterPoint().y, 35, 50);
					eg.addToCurrentScene(monster);
					counter++;
				}
			}
			else {
				counter = 0;
				isSpawning = false;
			}
		}
	}

	@Override
	public void render(Graphics2D g, EntityGame eg) {
		// TODO Auto-generated method stub
		g.rotate(Math.toRadians(rotation), bound.x+bound.width/2, bound.y+bound.height/2);
		//spriteSheet.drawSpriteFrame(g, new Point(bound.x, bound.y), eg.getCanvas());
		g.drawImage(img, bound.x, bound.y, bound.width, bound.height, eg.getCanvas());
		g.rotate(-Math.toRadians(rotation), bound.x+bound.width/2, bound.y+bound.height/2);
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPrerender() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPrerender(boolean flag) {
		// TODO Auto-generated method stub
		
	}

}
