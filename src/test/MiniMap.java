package test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import entityGame.Entity;
import entityGame.EntityGame;
import entityGame.UIComponent;

public class MiniMap implements Entity, UIComponent {

	private String id;
	private Rectangle bound;
	private boolean showMap;
	private LinkedList<Point> points;
	private LinkedList<String> type;
	private int screenWidth, screenHeight;
	
	public MiniMap(String id, int x, int y, int width, int height, int screenWidth, int screenHeight) {
		this.id = id;
		bound = new Rectangle(x, y, width, height);
		showMap = false;
		points = new LinkedList<Point>();
		type = new LinkedList<String>();
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		//ratioX = width/screenWidth;
		//ratioY = height/screenHeight;
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
		type.clear();
		points.clear();
		if(showMap) {
			LinkedList<Entity> list = eg.getCurrentScene();
			int size = list.size();
			for(int i = 0; i < size; i++) {
				
				if(list.get(i) instanceof Monster) {
					type.add("monster");
					points.add(list.get(i).getBoundingBox().getLocation());
				}
				else if(list.get(i) instanceof Player) {
					type.add("player");
					points.add(list.get(i).getBoundingBox().getLocation());
				}
				else if(list.get(i) instanceof SpawnPoint) {
					type.add("spawn point");
					points.add(list.get(i).getBoundingBox().getLocation());
				}
			}
			//System.out.println("Size: " + size);
		}
		
		if(eg.getInputHandler().getMap().get(KeyEvent.VK_M).isPressed()) {
			showMap = !showMap;
		}
	}

	@Override
	public void render(Graphics2D g, EntityGame eg) {
		// TODO Auto-generated method stub
		
		if(showMap) {
			//System.out.println("Point size: " + points.size());
			g.setColor(Color.BLACK);
			g.fillRect(bound.x, bound.y, bound.width, bound.height);
			for(int i = 0; i < points.size(); i++) {
				String name = type.get(i);
				
				if(name.equals("monster")) 
					g.setColor(Color.RED);
				else if(name.equals("player"))
					g.setColor(Color.YELLOW);
				else if(name.equals("spawn point"))
					g.setColor(Color.WHITE);
				
				double ratioX = (double)(points.get(i).x)/screenWidth;
				double ratioY = (double)(points.get(i).y)/screenHeight;
				
				g.fillRect((int)(bound.width*ratioX+bound.x), (int)(bound.height*ratioY+bound.y), 5, 5);
			}
			
		}
		
		
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}

}
