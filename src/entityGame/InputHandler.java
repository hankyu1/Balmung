package entityGame;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entityGame.Key;

public class InputHandler implements KeyListener{

	private Map<Integer, String> keyMapping = new HashMap<Integer, String>();
	private ArrayList<Key> inputList = new ArrayList<Key>();
	
	// add input mapping
	public void addInput(int KeyEvent, String actionName) {
		keyMapping.put(KeyEvent, actionName);
		//System.out.println("MapSize: " + keyMapping.size());
	} 
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		toggle(arg0, true);
		System.out.println("keyPressed...");
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		toggle(arg0, false);
		System.out.println("keyReleased...");
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	private void toggle(KeyEvent ke, boolean state) {
		Key actionKey = new Key(keyMapping.get(ke.getKeyCode()), state);
		//String action = keyMapping.get(ke.getKeyCode());
		inputList.add(actionKey);
		System.out.println("key Name: " + keyMapping.get(ke.getKeyCode()));
	}
	
	public void cleanList() {
		inputList.clear();
	}
	
	public ArrayList<Key> getInputList() {
		return inputList;
	}
	
	public boolean hasInput() {
		if(inputList.size() > 0)
			return true;
		else
			return false;
	}
}
