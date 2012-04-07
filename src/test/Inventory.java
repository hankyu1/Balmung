package test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import entityGame.Entity;
import entityGame.EntityGame;
import entityGame.UIComponent;

public class Inventory implements Entity, UIComponent {

	private String id;
	private Rectangle bound;
	private Image inventory, monsterCap;
	private Image[] hotkeys;
	
	public Inventory(String id, int x, int y, int width, int height, EntityGame eg) {
		this.id = id;
		bound = new Rectangle(x, y, width, height);
		inventory = eg.getResourcesManager().getImageResources().get("Inventory");
		hotkeys = new Image[6];
		
		for(int i = 0; i < 6; i++) {
			hotkeys[i] = eg.getResourcesManager().getImageResources().get("hotkey" + (i+1));
		}
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
		g.drawImage(inventory, bound.x, bound.y, eg.getCanvas());
		
		// items
		
		// hotkeys
		g.drawImage(hotkeys[0], bound.x+8, bound.y+39, eg.getCanvas());
		g.drawImage(hotkeys[1], bound.x+38, bound.y+39, eg.getCanvas());
		g.drawImage(hotkeys[2], bound.x+68, bound.y+39, eg.getCanvas());
		g.drawImage(hotkeys[3], bound.x+8, bound.y+68, eg.getCanvas());
		g.drawImage(hotkeys[4], bound.x+38, bound.y+68, eg.getCanvas());
		g.drawImage(hotkeys[5], bound.x+68, bound.y+68, eg.getCanvas());
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}

}
