package entityGame;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.util.Date;

public class Sprite implements Cloneable{

	private long duration;
	private int frameNum;
	private Dimension frameSize;
	private Point startPoint;
	private int frameCount;
	private String spriteName;
	
	private long currentTime, nextDrawTime;
	private Image SpriteSheet;
	
	public Sprite(String spriteName, long duration, Image SpriteSheet, Dimension frameSize, Point startPoint, int frameNum) {
		this.duration = duration;
		this.frameSize = frameSize;
		this.frameNum = frameNum;
		this.startPoint = startPoint;
		this.spriteName = spriteName;
		
		currentTime = 0;
		nextDrawTime = 0;
		
		this.SpriteSheet = SpriteSheet;
		frameCount = 0;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	
	public void drawSpriteFrame(Graphics2D g, Point position, ImageObserver observer) {
		
		currentTime = new Date().getTime();
		
		// time to draw
		//System.out.println("currentTime: " + currentTime + "\nNextDrawTime: " + nextDrawTime);
		if(currentTime >= nextDrawTime) {
			// draw current area on the sprite sheet
			if(frameCount < frameNum - 1)
				frameCount++;
			else 
				frameCount = 0;
		
			// set time
			nextDrawTime = currentTime + duration;
		}
		
		// draw images
		g.drawImage(SpriteSheet, 								
					position.x,
					position.y,
					position.x + frameSize.width,							
					position.y + frameSize.height,							
					startPoint.x + frameCount*frameSize.width, 	
					startPoint.y, 								
					startPoint.x + frameCount*frameSize.width + frameSize.width, 
					startPoint.y + frameSize.height, 
					observer);
	}
	
	public Image getSpriteSheet() {
		return SpriteSheet;
	}
	
	public String getSpriteName() {
		return spriteName;
	}
}
