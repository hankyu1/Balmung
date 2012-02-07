package entityGame;

public interface Component {
	
	public String getId();
	
	public void setOwnerEntity(Entity owner);
	
	public void update(EntityGame eg, int delta);
	
	public void render(EntityGame eg);
}
