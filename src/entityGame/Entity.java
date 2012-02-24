package entityGame;

import java.awt.Graphics2D;
import java.awt.Rectangle;


public interface Entity {
	
	// list of components
	//public LinkedList<Component> componentList = new LinkedList<Component>();
	
	//public void AddComponent(Component c);
	
	//public Component getComponent(String id);
	
	public Rectangle getBoundingBox();
	
	public String getId();
	
	//public void setId(String id);
	
	public void update(EntityGame eg);
	
	public void render(Graphics2D g, EntityGame eg);
}
