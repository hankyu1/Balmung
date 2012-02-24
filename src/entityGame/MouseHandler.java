package entityGame;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MouseHandler implements MouseListener, MouseMotionListener {
	
	private boolean[] currentState = new boolean[4];
	private boolean[] nextState = new boolean[4];
	
	private int x;
	private int y;

	public void setNextState(int button, boolean value) {
		nextState[button] = value;
	}

	public boolean isDown(int button) {
		return currentState[button];
	}

	public boolean isPressed(int button) {
		return !currentState[button] && nextState[button];
	}

	public boolean isRelased(int button) {
		return currentState[button] && !nextState[button];
	}

	public void tick() {
		for (int i = 0; i < currentState.length; i++) {
			currentState[i] = nextState[i];
		}
	}

	public void releaseAll() {
		for (int i = 0; i < nextState.length; i++) {
			nextState[i] = false;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setPosition(Point mousePosition) {
		if (mousePosition != null) {
			x = mousePosition.x;
			y = mousePosition.y;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("mouseClicked: " + arg0.getButton() + ", " + arg0.getPoint());
		//setPosition(arg0.getPoint());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		setPosition(arg0.getPoint());
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		releaseAll();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		setNextState(arg0.getButton(), true);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		setNextState(arg0.getButton(), false);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		setPosition(arg0.getPoint());
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		setPosition(arg0.getPoint());
	}

}
