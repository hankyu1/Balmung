package entityGame;


public class Key {
	
	private String actionName;
	private boolean state;
	
	public Key(String actionName, boolean state) {
		this.actionName = actionName;
		this.state = state;
	}
	
	public String getName() {
		return actionName;
	}
	
	public boolean isPressed() {
		return state;
	}
	
	public void setState(boolean state) {
		this.state = state;
	}
}
