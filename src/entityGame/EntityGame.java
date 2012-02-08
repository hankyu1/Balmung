package entityGame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.JFrame;

public abstract class EntityGame extends JFrame implements GameLoop{
	
	private String title;
	private int width, height;
	private long lastFrame;
	private boolean gameRunning = false;
	private int frameCount = 0;
	private int fps = 60;
	
	private BufferStrategy bf;// = this.getBufferStrategy();
	
	// scenes
	private LinkedList<Entity> scenes;
	
	// camera
	private Rectangle Camera;
	
	// constructor
	public EntityGame(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}
	
	// initialize game
	public void init(EntityGame eg) {
		bf = this.getBufferStrategy();
		scenes = new LinkedList<Entity>();
		Camera = new Rectangle(new Dimension(width, height));
	}
	/*
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
		
		// set up the frame
		getContentPane().setPreferredSize(new Dimension(width,height));
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		// initialize and load game content
		init();
		
		// game loop
		//gameLoop();
		runGameLoop();
	}
	
	// run game loop
	public void runGameLoop() {
		Thread loop = new Thread() {
			public void run() {
				gameLoop();
			}
		};
		loop.start();
	}
	
	// game loop
	private void gameLoop() {
		final double GAME_HERTZ = 30.0;
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		final int MAX_UPDATES_BEFORE_RENDER = 5;
		double lastUpdateTime = System.nanoTime();
		double lastRenderTime = System.nanoTime();
		
		final double TARGET_FPS = 60;
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
		
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);
		
		while (gameRunning) {
			double now = System.nanoTime();
			int updateCount = 0;
			
			while(now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
				updateGame();
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				updateCount++;
			}
			if(lastUpdateTime - now > TIME_BETWEEN_UPDATES) {
				lastUpdateTime = now - TIME_BETWEEN_UPDATES;
			}
			float interpolation = Math.min(1.0f, (float)((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
			drawGame(interpolation);
			lastRenderTime = now;
			
			int thisSecond = (int)(lastUpdateTime / 1000000000);
			if(thisSecond > lastSecondTime) {
				System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
				fps = frameCount;
				frameCount = 0;
				lastSecondTime = thisSecond;
			}
			
			while(now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
				Thread.yield();
				
				now = System.nanoTime();
			}
		}
	}
	
	private void updateGame() {
		// update all entities in current scene
		for(Entity e : scenes)
			e.update();
	}
	
	private void drawGame(float interpolation) {
		// draw entities
		Graphics g = null;
		try {
			g = bf.getDrawGraphics();
			
			// draw on g
			for(Entity e : scenes) {
				// only render the entity that is on camera
				if(e.getBoundingBox().intersects(Camera)) {
					e.render();
				}
			}
			g.copyArea(Camera.x, Camera.y, Camera.width, Camera.height, 0, 0);
			
		}
		finally {
			g.dispose();
		}
		
		bf.show();
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	
	// set camera size
	public void setCamera(int x, int y, int width, int height) {
		Camera.x = x;
		Camera.y = y;
		Camera.width = width;
		Camera.height = height;
	}
	
	// target camera on a point
	public void targetCamera(int x, int y) {
		Camera.x = x - Camera.width/2;
		Camera.y = y - Camera.height/2;
	}
	
	// collision detection with quad tree
	public boolean collisionDetection(Entity thisEntity, Entity thatEntity) {
		boolean hit = false;
		
		if(thisEntity.getBoundingBox().intersects(thatEntity.getBoundingBox()))
			hit = true;
		
		return hit;
	}
	
	
	/*
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
	*/
}
