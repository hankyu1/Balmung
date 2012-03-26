package entityGame;

import java.awt.Color;


import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.LinkedList;

import javax.swing.JApplet;
import javax.swing.JFrame;

public abstract class EntityGame extends JApplet implements GameLoop{
	
	private String title;
	private int width, height;
	private long lastFrame;
	protected boolean gameRunning = false;
	private int frameCount = 0;
	private int fps = 60;
	private Canvas drawArea;
	
	private BufferStrategy bf;// = this.getBufferStrategy();
	
	// scenes
	protected LinkedList<LinkedList<Entity>> scenes;// = new LinkedList<LinkedList<Entity>>();
	protected LinkedList<Entity> currentScene;
	protected LinkedList<Entity> removeList, addList;
	protected LinkedList<Entity> UI;
	
	protected GridBox gb;
	protected File gameFolder;
	
	// camera
	protected Camera Camera;
	
	// input handle
	protected InputHandler inputHandler = new InputHandler();
	protected MouseHandler mouseHandler = new MouseHandler();
	
	// resourcesmanager
	protected ResourcesManager resourcesManager = ResourcesManager.getInstance();
	
	// constructor
	public EntityGame(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}
	
	// initialize game
	public void init(EntityGame eg) {
		
		getContentPane().setVisible(true);
		getContentPane().setPreferredSize(new Dimension(width,height));
		//System.out.println("RootPanel: " + this.getRootPane().getLocation().toString());
		//pack();
		getContentPane().setLocation(getRootPane().getX(), getRootPane().getY());
		
		
		this.setIgnoreRepaint(true);
		drawArea = new Canvas();
		
		drawArea.setSize(width,height);
		drawArea.setVisible(true);
		System.out.println(this.isShowing());
		getContentPane().add(drawArea);
		
		drawArea.createBufferStrategy(3);
		bf = drawArea.getBufferStrategy();
		//System.out.println("bf: " + bf.toString());
		//scenes = new LinkedList<LinkedList<Entity>>();
		UI = new LinkedList<Entity>();
		//currentScene = scenes.get(0);

		
		// add camera
		Camera = new Camera(0, 0, width, height);
		//System.out.println("CameraSize: " + Camera.toString());
		//System.out.println("WindowSize: " + this.getRootPane().getLocation().toString());
		//System.out.println("PanelSize: " + this.getContentPane().getLocation().toString());
		gb = new GridBox(new Dimension(width,height));
		
		// add listener
		drawArea.addKeyListener(inputHandler);
		drawArea.addMouseListener(mouseHandler);
		drawArea.requestFocus();
		
		// set resourcesManager
		resourcesManager.setEG(this);
	}
	
	// start game
	public void start() {
		//setTitle(title);
		
		// set up the frame
		//setPreferredSize(new Dimension(width,height));
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		// initialize and load game content
		// initialize game frame
		init(this);
		
		// load custom content
		//init();
		currentScene = scenes.get(0);
		removeList = new LinkedList<Entity>();
		addList = new LinkedList<Entity>();

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
		
		// tick for mouseHandler
		mouseHandler.tick();
		
		// get new grid for collision detection
		gb.addToGrid(currentScene);
		
		// update all entities in current scene
		for(Entity e : currentScene)
			e.update(this);
		
		// modify list
		for(Entity e : removeList)
			if(currentScene.contains(e)) {
				currentScene.remove(e);
				e = null;
			}
		
		for(Entity e : addList) {
			currentScene.add(e);
		}
		removeList.clear();
		addList.clear();
		
		//inputHandler.cleanList();
	}
	
	// render
	private void drawGame(float interpolation) {
		// draw entities
		Graphics2D g = null;
		
		
		try {
			g = (Graphics2D) bf.getDrawGraphics();
			g.setColor(Color.BLACK);
			g.fillRect(this.getRootPane().getX(), this.getRootPane().getY(), this.getRootPane().getWidth(), this.getRootPane().getHeight());
			
			//translate camera
			g.translate(-Camera.x+getRootPane().getX(), -Camera.y+getRootPane().getY());
			
			// draw on g
			for(Entity e : currentScene) {
				// findUI on the way
				if(UIComponent.class.isInstance(e)) {
					//System.out.println("Entity ID: " + e.getId());
					UI.add(e);
				}
				
				// only render the entity that is on camera
				else if(e.getBoundingBox().intersects(Camera)) {
						e.render(g, this);
				}
			}
			
			for(Entity e : UI) {
				//System.out.println("Start rendering: " + e.getId());
				
				int offsetX = e.getBoundingBox().x, 
					offsetY = e.getBoundingBox().y;
				
				e.getBoundingBox().x += Camera.x;
				e.getBoundingBox().y += Camera.y;
				
				
				e.render(g, this);
				
				e.getBoundingBox().x = offsetX;
				e.getBoundingBox().y = offsetY;
				
			}
			UI.clear();
			//g.copyArea(Camera.x, Camera.y, Camera.width, Camera.height, 0, 0);
			//System.out.println("Camera Position: (" + Camera.x + "," + Camera.y + ")");
		}
		finally {
			// draw camera center point
			//g.draw(new Ellipse2D.Float(Camera.getCenter().x, Camera.getCenter().y, 5.0f, 5.0f));
			
			g.dispose();
		}
		
		bf.show();
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	// switch scene
	
	
	// set camera size
	public void setCameraSize(int width, int height) {
		Camera.width = width;
		Camera.height = height;
	}
	
	public void setCameraPosition(int x, int y) {
		Camera.x = x;
		Camera.y = y;
	}
	
	// target camera on a point
	public void targetCamera(int x, int y) {
		Camera.x = x - Camera.width/2;
		Camera.y = y - Camera.height/2;
	}
	
	// collision detection with grid
	public boolean collisionDetection(Rectangle thisEntity, Rectangle thatEntity) {
		boolean hit = false;
		
		if(thisEntity.intersects(thatEntity))
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
	
	public Canvas getCanvas() {
		return drawArea;
	}
	
	public InputHandler getInputHandler() {
		return inputHandler;
	}
	
	public MouseHandler getMouseHandler() {
		return mouseHandler;
	}
	
	public ResourcesManager getResourcesManager() {
		return resourcesManager;
	}
	
	public Point getMousePosition() {
		return new Point(Camera.x+drawArea.getMousePosition().x, Camera.y+drawArea.getMousePosition().y);
	}
	
	public void setDirectory(String directory) {
		// set directory for file caching
		try {
			gameFolder = new File(directory);
			try{
				if(!gameFolder.exists()) {
					gameFolder.mkdir();
					System.out.println("Directory Created");
				}
				else
					System.out.println("Directory Already exist");
			}
			catch(Exception ex) {
				System.out.println(ex.toString());
			}
			
		}
		catch(Exception ex) {
			System.out.println(ex.toString());
		}
		
		//System.out.println(getDirectory());
	}
	
	public File getDirectory() {
		return gameFolder;
	}
	
	public Camera getCamera() {
		return Camera;
	}
	
	public void removeFromCurrentScene(Entity e) {
		removeList.add(e);
	}
	
	public int getEntityIndex(Entity e) {
		return currentScene.indexOf(e);
	}
	
	public void insertEntity(Entity e) {
		
	}
	
	public void addToCurrentScene(Entity e) {
		addList.add(e);
	}
	
	public LinkedList<Entity> getCurrentScene() {
		return currentScene;
	}
}
