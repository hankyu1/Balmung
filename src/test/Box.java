package test;

import java.awt.Graphics2D;

import java.awt.Point;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import entityGame.Entity;
import entityGame.EntityGame;
import entityGame.Sound;
import entityGame.Sprite;

public class Box implements Entity {

	protected Sprite spriteSheet;
	protected String id;
	protected Rectangle bound;
	protected LinkedList<LinkedList<Entity>> list; 
	protected int velocity;
	private double rotation = 0;
	
	public Box(String id, int x, int y, int w, int h, int velocity, Sprite spriteSheet, EntityGame eg) {
		this.id = id;
		bound = new Rectangle(x, y, w, h);
		this.velocity = velocity;
		try {
			this.spriteSheet = spriteSheet;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//list = new LinkedList<LinkedList<Entity>>();
		
		
	}
	
	private Point getCenter() {
		return new Point(getBoundingBox().x+getBoundingBox().width/2, getBoundingBox().y+getBoundingBox().height/2);
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
		
		// rotation
		try{
			Point mousePoint = eg.getMousePosition();
			rotation = Math.toDegrees(Math.atan2(mousePoint.y-(bound.y+bound.height/2), mousePoint.x-(bound.x+bound.width/2)));
		}catch(Exception ex) {
			System.out.println("Mouse out of screen...");
		}
		
		list = eg.getGB().getGridList(this);
		/*
		 * collision
		Rectangle thisRect, thatRect;
		
		for(LinkedList<Entity> le : list)
			for(Entity e : le) {
				if(!e.equals(this) && 
				   eg.collisionDetection(thisRect = new Rectangle(this.getBoundingBox().x + velocity, 
																  this.getBoundingBox().y, 
																  this.getBoundingBox().width, 
																  this.getBoundingBox().height), 
										 thatRect = new Rectangle(e.getBoundingBox().x, 
															   	  e.getBoundingBox().y, 
										   		 				  e.getBoundingBox().width, 
															   	  e.getBoundingBox().height)) && 
				   e instanceof Box) {
					velocity = - velocity;
				}
			}
		
		if(bound.x < 0 || bound.x + bound.width > 800) {
			velocity = -velocity;
		}
		*/
		
		list.clear();
		
		// camera
		//eg.targetCamera(bound.x, bound.y);
		
		// test keyboard input
		if(eg.getInputHandler().getMap().get(KeyEvent.VK_W).isPressed())
			bound.y -= velocity;
		if(eg.getInputHandler().getMap().get(KeyEvent.VK_S).isPressed())
			bound.y += velocity;
		if(eg.getInputHandler().getMap().get(KeyEvent.VK_A).isPressed())
			bound.x -= velocity;
		if(eg.getInputHandler().getMap().get(KeyEvent.VK_D).isPressed())
			bound.x += velocity;
		
		
		// test mouse input
		Sound s = eg.getResourcesManager().getSoundResources().get("BoxSound");// = eg.getResourcesManager().getSoundResources().get("BoxSound").playSound(eg.getCamera().getCenter(), getCenter());
		
		if(eg.getMouseHandler().getCurrentState()[MouseEvent.BUTTON1]) {
			try {
				if(bound.contains(eg.getMousePosition())) {
					//eg.targetCamera(bound.x+bound.width/2, bound.y+bound.height/2);
					if(s.getClip() == null) {
						//System.out.println("Distance: " + getCenter().distance(eg.getCamera().getCenter()));
						s.playSound(eg.getCamera().getCenter(), getCenter());
					}
					else {
						s.stopSound();
						//System.out.println("Distance: " + getCenter().distance(eg.getCamera().getCenter()));
						s.playSound(eg.getCamera().getCenter(), getCenter());
					}
				}
			}
			catch(Exception ex) {}
		}
	}

	@Override
	public void render(Graphics2D g, EntityGame eg) {
		// TODO Auto-generated method stub
		//System.out.println("ASDF");
		
		/*
		if(rotatingCount > 1 || rotatingCount < 0)
			rotatingSide = -rotatingSide;
		
		if(Math.toRadians(rotatingCount) >= Math.PI)
			rotatingCount = 0;
		rotatingCount += rotatingSide;
		//System.out.println("rotatingCount: " + rotatingCount);
		
		g.rotate(Math.toRadians(rotatingCount), bound.x+bound.width/2, bound.y+bound.height/2);
		spriteSheet.drawSpriteFrame(g, new Point(bound.x, bound.y), eg.getCanvas());
		g.rotate(-Math.toRadians(rotatingCount), bound.x+bound.width/2, bound.y+bound.height/2);
		//g.drawImage(img, bound.x, bound.y, eg.getCanvas());
		 * 
		 */
		g.rotate(Math.toRadians(rotation), bound.x+bound.width/2, bound.y+bound.height/2);
		spriteSheet.drawSpriteFrame(g, new Point(bound.x, bound.y), eg.getCanvas());
		g.rotate(-Math.toRadians(rotation), bound.x+bound.width/2, bound.y+bound.height/2);
		
	}
	
}
