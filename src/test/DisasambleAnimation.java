package test;

import java.awt.Graphics2D;

import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.util.Date;
import java.util.Random;

import entityGame.Entity;
import entityGame.EntityGame;

public class DisasambleAnimation implements Entity {
	
	private Rectangle bound;
	private Image img;
	private long animationTime, currentTime, nextTime, lifeTime;
	private Image[] particle;
	private Point[] position;
	private double rotation;
	private Point rotationPoint;
	private int speed;
	
	public DisasambleAnimation(Image img, int x, int y, long animationTime, long lifeTime, int particleNum, double rotation, Point rotationPoint, int speed) {
		
		this.speed = speed;
		this.rotationPoint = rotationPoint;
		this.img = img;
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
					Random rn = new Random();
					long degree = 360*i/position.length;
					//System.out.println(degree);
					position[i].x += (int)(Math.cos(Math.toRadians(degree)))*speed;
					position[i].y += (int)(Math.sin(Math.toRadians(degree)))*speed;
					
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
		
		
		for(int i = 0; i < position.length; i++) {
			g.rotate(Math.toRadians(rotation), rotationPoint.x+bound.width, rotationPoint.y+bound.height);
			g.drawImage(particle[i], position[i].x, position[i].y, eg.getCanvas());
			g.rotate(-Math.toRadians(rotation), rotationPoint.x+bound.width, rotationPoint.y+bound.height);
		}
		
		
		//g.drawImage(particle[0], 0, 0, 100, 100, eg.getCanvas());
		
		//g.drawRect(position[0].x, position[0].y, 100, 100);
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
