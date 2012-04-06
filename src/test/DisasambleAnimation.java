package test;

import java.awt.Graphics2D;


import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.util.Date;

import entityGame.Entity;
import entityGame.EntityGame;

public class DisasambleAnimation implements Entity {
	
	private Rectangle bound;
	private long animationTime, currentTime, nextTime, lifeTime;
	private Image[] particle;
	private Point[] position;
	private double rotation;
	private Point rotationPoint;
	private int speed;
	
	public DisasambleAnimation(Image img, int x, int y, long animationTime, long lifeTime, int particleNum, double rotation, Point rotationPoint, int speed) {
		
		this.speed = speed;
		this.rotationPoint = rotationPoint;
		this.rotation = rotation;
		this.animationTime = animationTime;
		this.lifeTime = lifeTime;
		particle = new Image[particleNum];
		position = new Point[particleNum];
		
		int row = (int)Math.sqrt(particleNum),
			col = (int)Math.sqrt(particleNum);
		
		int width = img.getWidth(null)/col,
			height = img.getHeight(null)/row;
		
		int counter = 0;
		for(int i = 0; i < row; i++) 
			for(int j = 0; j < col; j++) {
				position[counter] = new Point(j*width+x, row*height+y);
				particle[counter] = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(img.getSource(), new CropImageFilter(j*width, i*height , width, height)));
				counter++;
			}
		//System.out.println("Particle Number: " + counter);
		bound = new Rectangle(x,y,width,height);
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
		return null;
	}

	@Override
	public void update(EntityGame eg) {
		// TODO Auto-generated method stub
		currentTime = new Date().getTime();
		
		
		if(lifeTime > 0) {
			if(currentTime > nextTime) {
				for(int i = 0; i < position.length; i++) {
					long degree = 360*i/position.length;
					
					position[i].x += (int)((Math.cos(Math.toRadians(degree)))*speed);
					position[i].y += (int)((Math.sin(Math.toRadians(degree)))*speed);
					nextTime = currentTime + animationTime;
				}
			}
			lifeTime--;
		}
		else {
			eg.removeFromCurrentScene(this);
		}
	}

	@Override
	public void render(Graphics2D g, EntityGame eg) {
		// TODO Auto-generated method stub
		g.rotate(Math.toRadians(rotation), rotationPoint.x+bound.width, rotationPoint.y+bound.height);
		for(int i = 0; i < position.length; i++) {
			
			g.drawImage(particle[i], position[i].x, position[i].y, eg.getCanvas());
			
		}
		g.rotate(-Math.toRadians(rotation), rotationPoint.x+bound.width, rotationPoint.y+bound.height);
		
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}

}
