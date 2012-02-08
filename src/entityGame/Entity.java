package entityGame;

import java.awt.Rectangle;
import java.util.LinkedList;


public interface Entity {
	
	// list of components
	public LinkedList<Component> componentList = new LinkedList<Component>();
	
	public void AddComponent(Component c);
	
	public Component getComponent(String id);
	
	public Rectangle getBoundingBox();
	
	public String getId();
	
	public void setId();
	
	public void update();
	
	public void render();
}
