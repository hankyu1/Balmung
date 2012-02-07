package entityGame;

import java.awt.Dimension;
import java.util.Date;

import javax.swing.JFrame;

public abstract class EntityGame extends JFrame implements GameLoop{
	
	String title;
	int width, height;
	long lastFrame;
	
	// constructor
	public EntityGame(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}
	/*
	// initialize game
	public void init(EntityGame eg) {
		
	}
	
	// update game
	public void update(EntityGame eg, int delta) {
		
	};
	
	// render game content
	public void render(){
		
	};
	*/
	// start game
	public void start() {
		setTitle(title);
		getContentPane().setPreferredSize(new Dimension(width,height));
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		// game loop
		gameLoop();
	}
	
	private void gameLoop() {
		update();
		render();
	}
	
	private long getTime() {
		return System.nanoTime() / 1000000; //new Date().getTime();
	}
	
	// time between each frame
	private long getDelta() {
		long time = getTime();
		long delta = (time - lastFrame);
		lastFrame = time;
		
		return delta;
	}
}
