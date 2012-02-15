package test;

import java.awt.Graphics2D;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import entityGame.Entity;
import entityGame.EntityGame;

public class Box implements Entity {

	protected BufferedImage img;
	protected String id;
	protected Rectangle bound;
	protected LinkedList<LinkedList<Entity>> list; 
	protected int velocity;
	
	public Box(String id, int x, int y, int w, int h, int velocity, String imgURL) {
		this.id = id;
		bound = new Rectangle(x, y, w, h);
		this.velocity = velocity;
		try {
			img = ImageIO.read(new File(imgURL));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//list = new LinkedList<LinkedList<Entity>>();
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
		
		bound.x += velocity;
		//eg.targetCamera(bound.x, bound.y);
		
		list = eg.getGB().getGridList(this);
		
		for(LinkedList<Entity> le : list)
			for(Entity e : le) {
				if(!e.equals(this) && eg.collisionDetection(e, this) && e instanceof Box) {
					velocity = - velocity;
				}
			}
		
		if(bound.x < 0 || bound.x + bound.width > 800) {
			velocity = -velocity;
		}
			
		
		list.clear();
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawImage(img, bound.x, bound.y, null);
	}
	
}
