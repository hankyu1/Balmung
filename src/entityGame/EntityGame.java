package entityGame;

import java.awt.Color;


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

import javax.swing.JFrame;

public abstract class EntityGame extends JFrame implements GameLoop{
	
	private String title;
	private int width, height;
	private long lastFrame;
	protected boolean gameRunning = false;
	private int frameCount = 0;
	private int fps = 60;
	
	private BufferStrategy bf;// = this.getBufferStrategy();
	
	// scenes
	protected LinkedList<LinkedList<Entity>> scenes;// = new LinkedList<LinkedList<Entity>>();
	protected LinkedList<Entity> currentScene;
	protected LinkedList<Entity> UI;
	protected GridBox gb;
	
	// camera
	protected Camera Camera;
	
	// constructor
	public EntityGame(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}
	
	// initialize game
	public void init(EntityGame eg) {
		createBufferStrategy(2);
		bf = getBufferStrategy();
		//System.out.println("bf: " + bf.toString());
		scenes = new LinkedList<LinkedList<Entity>>();
		UI = new LinkedList<Entity>();
		//currentScene = scenes.get(0);
		Camera = new Camera(0, 0, width, height);
		System.out.println("CameraSize:" + Camera.toString());
		gb = new GridBox(new Dimension(width,height));
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
		setPreferredSize(new Dimension(width,height));
		getContentPane().setPreferredSize(new Dimension(width,height));
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		// initialize and load game content
		// initialize game frame
		init(this);
		
		// load custom content
		init();
		currentScene = scenes.get(0);

		// game loop
		//gameLoop();
		// start game
		gameRunning = true;
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
			
			// update entities
			while(now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
				updateGame();
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				updateCount++;
			}
			if(lastUpdateTime - now > TIME_BETWEEN_UPDATES) {
				lastUpdateTime = now - TIME_BETWEEN_UPDATES;
			}
			
			// render game
			float interpolation = Math.min(1.0f, (float)((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
			drawGame(interpolation);
			lastRenderTime = now;
			
			int thisSecond = (int)(lastUpdateTime / 1000000000);
			frameCount++;
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
	
	// update 
	private void updateGame() {
		
		// get new grid for collision detection
		gb.addToGrid(currentScene);
		
		// update all entities in current scene
		for(Entity e : currentScene)
			e.update(this);
	}
	
	// render
	private void drawGame(float interpolation) {
		// draw entities
		Graphics2D g = null;
		
		
		try {
			g = (Graphics2D) bf.getDrawGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width, height);
			
			//translate camera
			g.translate(-Camera.x, -Camera.y);
			
			// draw on g
			for(Entity e : currentScene) {
				// findUI on the way
				if(UIComponent.class.isInstance(e)) {
					//System.out.println("Entity ID: " + e.getId());
					UI.add(e);
				}
				
				// only render the entity that is on camera
				else if(e.getBoundingBox().intersects(Camera)) {
						e.render(g);
				}
			}
			
			for(Entity e : UI) {
				//System.out.println("Start rendering: " + e.getId());
				
				int offsetX = e.getBoundingBox().x, 
					offsetY = e.getBoundingBox().y;
				
				e.getBoundingBox().x += Camera.x;
				e.getBoundingBox().y += Camera.y;
				
				
				e.render(g);
				
				e.getBoundingBox().x = offsetX;
				e.getBoundingBox().y = offsetY;
				
			}
			UI.clear();
			//g.copyArea(Camera.x, Camera.y, Camera.width, Camera.height, 0, 0);
			//System.out.println("Camera Position: (" + Camera.x + "," + Camera.y + ")");
		}
		finally {
			g.dispose();
		}
		
		bf.show();
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	// switch scene
	
	
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
	
	// collision detection with grid
	public boolean collisionDetection(Entity thisEntity, Entity thatEntity) {
		boolean hit = false;
		
		if(thisEntity.getBoundingBox().intersects(thatEntity.getBoundingBox()))
			hit = true;
		
		return hit;
	}
	
	// get collision list
	public GridBox getGB() {
		return gb;
	}

	// get fps
	public int getFPS() {
		return fps;
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
