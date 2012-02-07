package entityGame;

import java.util.LinkedList;


public interface Entity {
	
	// list of components
	public LinkedList<Component> componentList = new LinkedList<Component>();
	
	public void AddComponent(Component c);
	
	public Component getComponent(String id);
	
	public void update(EntityGame eg, int delta);
	
	public void render(EntityGame eg);
}
