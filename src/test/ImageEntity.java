package test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import entityGame.Entity;
import entityGame.EntityGame;

public class ImageEntity implements Entity {

	private String id;
	private Image img;
	private Rectangle bound;
	
	public ImageEntity(String id, Image img, int width, int height, int x, int y) {
		this.id = id;
		this.img = img;
		bound = new Rectangle(x, y, width, height);
		//System.out.println("I am a tile");
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
		
	}

	@Override
	public void render(Graphics2D g, EntityGame eg) {
		// TODO Auto-generated method stub
		g.drawImage(img, bound.x, bound.y, eg.getCanvas());
	}

}
