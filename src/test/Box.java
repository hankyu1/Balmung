package test;

import java.awt.Graphics2D;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.applet.*;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

import entityGame.Entity;
import entityGame.EntityGame;
import entityGame.Key;
import entityGame.Sound;
import entityGame.Sprite;

public class Box implements Entity {

	//protected BufferedImage img;
	protected Sprite spriteSheet;
	protected String id;
	protected Rectangle bound;
	protected LinkedList<LinkedList<Entity>> list; 
	protected int velocity;
	private double rotatingCount = 0,
				   rotatingSide = 1;
	
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
		// TODO Auto-generated method stub
		
		bound.x += velocity;
		//eg.targetCamera(bound.x, bound.y);
		
		list = eg.getGB().getGridList(this);
		
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
			
		
		list.clear();
		
		//eg.targetCamera(bound.x, bound.y);
		
		// test keyboard input
		if(eg.getInputHandler().hasInput()) {
			FindKey:
				for(Key k : eg.getInputHandler().getInputList()) {
					if(k != null && k.getName() == "UP" && k.isPressed())
						System.out.println("Name From Box: " + k.getName());
					break FindKey;
				}
		}
		
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
		/*
		if(s.getClip() != null) {
			if(s.isPlaying()) {
				float strength = (float) ((s.getStrength() - eg.getCamera().getCenter().distance((Point2D) new Point(bound.x+bound.width/2, bound.y+bound.height/2))) / s.getStrength());
				strength *= (s.getMaxSound()-s.getMinSound()) + s.getMinSound();
				//s.setVolume(strength);
				//System.out.println("current sound volume: " + s.getVolume());
			}
		}
		*/
	}

	@Override
	public void render(Graphics2D g, EntityGame eg) {
		// TODO Auto-generated method stub
		//System.out.println("ASDF");
		
		/*
		if(rotatingCount > 1 || rotatingCount < 0)
			rotatingSide = -rotatingSide;
		*/
		if(Math.toRadians(rotatingCount) >= Math.PI)
			rotatingCount = 0;
		rotatingCount += rotatingSide;
		//System.out.println("rotatingCount: " + rotatingCount);
		
		g.rotate(Math.toRadians(rotatingCount), bound.x+bound.width/2, bound.y+bound.height/2);
		spriteSheet.drawSpriteFrame(g, new Point(bound.x, bound.y), eg.getCanvas());
		g.rotate(-Math.toRadians(rotatingCount), bound.x+bound.width/2, bound.y+bound.height/2);
		//g.drawImage(img, bound.x, bound.y, eg.getCanvas());
	}
	
}
