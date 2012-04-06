package test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Date;

import entityGame.Entity;
import entityGame.EntityGame;

public class Protal implements Entity {

	private String id;
	private Image img;
	private Rectangle bound;
	private int HP, HPReg, HPMax;
	private double rotation;
	private long currentTime, nextTime, regTime;
	
	public Protal(String id, Image img, int x, int y, int width, int height, int HP, int HPReg, long regTime) {
		this.id = id;
		this.img = img;
		bound = new Rectangle(x, y, width, height);
		this.HP = HP;
		this.HPReg = HPReg;
		HPMax = HP;
		this.regTime = regTime;
		nextTime = 0;
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
		
		currentTime = new Date().getTime();
		
		if(rotation >= 360) {
			rotation = 0;
		}
		else
			rotation++;
		
		// HP/HPReg
		if(HP >= HPMax) {
			HP = HPMax;
		}
		else {
			if(currentTime >= nextTime) {
				if(HP + HPReg > HPMax)
					HP = HPMax;
				else
					HP += HPReg;
				nextTime = currentTime + regTime;
			}
		}
		
		//System.out.println("HP: " + HP);
	}

	@Override
	public void render(Graphics2D g, EntityGame eg) {
		// TODO Auto-generated method stub
		g.rotate(Math.toRadians(rotation), bound.x+bound.width/2, bound.y+bound.height/2);
		g.drawImage(img, bound.x, bound.y, bound.width, bound.height, eg.getCanvas());
		g.rotate(-Math.toRadians(rotation), bound.x+bound.width/2, bound.y+bound.height/2);
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void injur(int dmg) {
		if(HP - dmg >= 0)
			HP -= dmg;
		else
			HP = 0;
	}
	
	public int getHP() {
		return HP;
	}

}
