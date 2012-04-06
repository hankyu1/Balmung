package test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import entityGame.Entity;
import entityGame.EntityGame;
import entityGame.UIComponent;

public class Timer implements Entity, UIComponent {

	private String id, displayTime;
	private Image img;
	private Rectangle bound;
	private long time, interval, second, currentTime, nextTime;
	private NumberFormat formate = new DecimalFormat("00");
	private int fixX, fixY;
	
	public Timer(String id, Image img, int x, int y, int width, int height, int fixX, int fixY, long time) {
		this.id = id;
		this.img = img;
		bound = new Rectangle(x, y, width, height);
		this.time = time;	// 1000 = 1 second
		interval = time;
		second = 1000;
		nextTime = 0;
		displayTime = "00:00";
		this.fixX = fixX; 
		this.fixY = fixY;
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
		
		if(time >= 0) {
			if(currentTime >= nextTime) {
				time-=1000;
				nextTime = currentTime + second;
			}
			int min = (int)time/60000,
				sec = (int)(time-min*60000)/1000;
			displayTime = formate.format(min)+":"+formate.format(sec);
		}
		else {
			time = interval;
		}
	}

	@Override
	public void render(Graphics2D g, EntityGame eg) {
		// TODO Auto-generated method stub
		g.drawImage(img, bound.x, bound.y, eg.getCanvas());
		g.drawString(displayTime, bound.x+fixX, bound.y+fixY);
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
