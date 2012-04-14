package test;

import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.Point;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Date;

import entityGame.Entity;
import entityGame.EntityGame;
import entityGame.Sprite;

public class Player implements Entity {

	//protected Sprite spriteSheet;
	private String id;
	private Rectangle bound;
	//protected LinkedList<LinkedList<Entity>> list; 
	private Image img;
	private Image[] gunsImg;
	private int velocity,
				MissileGun = 0, machinegun = 1, shotgun = 2,
				currentGun = MissileGun;
	private double rotation = 0;
	private long[] fireRate = {500, 100, 1000};
	private long currentFireRate = fireRate[currentGun],
				 currentTime = 0, nextTime = 0,
				 HPRegTime = 3000, nextHpReg = 0, EnergyRegTime = 1000, nextEnReg = 0,
				 abilityTime = 1000, nextAbilityTime = 0;
	private int[] gunDmg = {10, 3, 3},
				  gunEnergy = {10, 15, 20};
	private int HP = 100, HPMax = 100, Energy = 100, EnMax = 100;
	
	
	public Player(String id, int x, int y, int w, int h, int velocity, Image img, EntityGame eg) {
		this.id = id;
		bound = new Rectangle(x, y, w, h);
		this.velocity = velocity;
		this.img = img;
		
		gunsImg = new Image[3];
		
		gunsImg[0] = eg.getResourcesManager().getImageResources().get("MissileGun");
		gunsImg[1] = eg.getResourcesManager().getImageResources().get("MachineGun");
		gunsImg[2] = eg.getResourcesManager().getImageResources().get("ShotGun");
		
		/*
		try {
			this.spriteSheet = spriteSheet;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//list = new LinkedList<LinkedList<Entity>>();
	}
	
	private Point getCenter() {
		return new Point(getBoundingBox().x+getBoundingBox().width/2, getBoundingBox().y+getBoundingBox().height/2);
	}
	
	public void injur(int dmg) {
		if(HP - dmg >= 0)
			HP -= dmg;
		else
			HP = 0;
	}
	
	@Override
	public Rectangle getBoundingBox() {
		// TODO Auto-generated method stub
		return bound;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void update(EntityGame eg) {
		// rotation
		currentTime = new Date().getTime();
		
		// health/energy regeneration
		// HP
		if(currentTime >= nextHpReg) {
			if(HP + 3 > HPMax)
				HP = HPMax;
			else
				HP += 3;
			
			nextHpReg = currentTime + HPRegTime;
		}
		if( currentTime >= nextEnReg) {
			if(Energy + 6 > EnMax)
				Energy = EnMax;
			else
				Energy += 6;
			
			nextEnReg = currentTime + EnergyRegTime;
		}
		
		try{
			Point mousePoint = eg.getMousePosition();
			rotation = Math.toDegrees(Math.atan2(mousePoint.y-(getCenter().y), mousePoint.x-(getCenter().x)));
		}catch(Exception ex) {
			//System.out.println("Mouse out of screen...");
		}
		
		//list = eg.getGB().getGridList(this);
		/*
		 * collision
		Rectangle thisRect, thatRect;
		
		for(LinkedList<Entity> le : list)
			for(Entity e : le) {
				if(!e.equals(this) && 
				   eg.collisionDetection(thisRect = new Rectangle(this.getBoundingBox().x + velocity, 
																  this.getBoundingBox().y, 
																  this.getBoundingBox().width, 
																  this.getBoundingBox().height), 
										 thatRect = new Rectangle(e.getBoundingBox().x, 
															   	  e.getBoundingBox().y, 
										   		 				  e.getBoundingBox().width, 
															   	  e.getBoundingBox().height)) && 
				   e instanceof Box) {
					velocity = - velocity;
				}
			}
		
		if(bound.x < 0 || bound.x + bound.width > 800) {
			velocity = -velocity;
		}
		*/
		
		//list.clear();
		
		// camera
		eg.targetCamera(getCenter().x, getCenter().y);
		
		// test keyboard input
		// movement
		if(eg.getInputHandler().getMap().get(KeyEvent.VK_W).isPressed() && bound.y-velocity > 0)
			bound.y -= velocity;
		if(eg.getInputHandler().getMap().get(KeyEvent.VK_S).isPressed() && bound.y+bound.height+velocity < 768)
			bound.y += velocity;
		if(eg.getInputHandler().getMap().get(KeyEvent.VK_A).isPressed() && bound.x-velocity > 0)
			bound.x -= velocity;
		if(eg.getInputHandler().getMap().get(KeyEvent.VK_D).isPressed() && bound.x+bound.width+velocity < 1024)
			bound.x += velocity;
		// guns
		if(eg.getInputHandler().getMap().get(KeyEvent.VK_1).isPressed()) {
			currentFireRate = fireRate[MissileGun];
			currentGun = MissileGun;
		}
		if(eg.getInputHandler().getMap().get(KeyEvent.VK_2).isPressed()) {
			currentFireRate = fireRate[machinegun];
			currentGun = machinegun;
		}
		if(eg.getInputHandler().getMap().get(KeyEvent.VK_3).isPressed()) {
			currentFireRate = fireRate[shotgun];
			currentGun = shotgun;
		}
		
		// LeftMouse Press (Fire)
		if(eg.getMouseHandler().getCurrentState()[MouseEvent.BUTTON1]) {
			
			if(currentTime >= nextTime) {
				try {
					if(currentGun == MissileGun) {
						Bullet bullet = new Bullet("Bullet"+Math.random(), eg.getResourcesManager().getImageResources().get("Missile"), gunDmg[0], 1, rotation, 7, 
								getCenter().x + (int)(Math.cos(Math.toRadians(rotation+20))*70), 
								getCenter().y + (int)(Math.sin(Math.toRadians(rotation+20))*70), 
								50, 15);
						eg.addToCurrentScene(bullet);
					}
					else if(currentGun == machinegun) {
						Bullet bullet = new Bullet("Bullet"+Math.random(), eg.getResourcesManager().getImageResources().get("MachinegunBullet"), gunDmg[1], 1, rotation, 7, 
								getCenter().x + (int)(Math.cos(Math.toRadians(rotation+26))*60), 
								getCenter().y + (int)(Math.sin(Math.toRadians(rotation+26))*60), 
								18, 12);
						eg.addToCurrentScene(bullet);
					}
					else if(currentGun == shotgun) {
						// shot gun makes a lot of bullets ._./
						for(int i = 0; i < 23; i++) {
							Bullet bullet = new Bullet("Bullet"+Math.random(), eg.getResourcesManager().getImageResources().get("ShotgunBullet"), gunDmg[2], 1, rotation+72-i*7, 5, 
									getCenter().x + (int)(Math.cos(Math.toRadians(rotation+20))*70), 
									getCenter().y + (int)(Math.sin(Math.toRadians(rotation+20))*70), 
									16, 16);
							eg.addToCurrentScene(bullet);
						}
					}
					
					nextTime = currentFireRate+currentTime;
			}
			catch(Exception ex) {}
			}
			
		}
		
		// RighMouse Press (Ability)
		if(eg.getMouseHandler().getCurrentState()[MouseEvent.BUTTON3]) {
			if(currentTime >= nextAbilityTime) {
				if(Energy - gunEnergy[currentGun] >= 0) {
					Energy -= gunEnergy[currentGun];
				}
				else
					System.out.println("Not Enough Energy...");
				nextAbilityTime = currentTime + abilityTime;
			}
		}
	}

	@Override
	public void render(Graphics2D g, EntityGame eg) {
		// TODO Auto-generated method stub
		//System.out.println("ASDF");
		
		g.rotate(Math.toRadians(rotation), bound.x+bound.width/2, bound.y+bound.height/2);
		//spriteSheet.drawSpriteFrame(g, new Point(bound.x, bound.y), eg.getCanvas());
		switch(currentGun) {
			case 0:
				g.drawImage(gunsImg[currentGun], bound.x-20, bound.y+40, eg.getCanvas());
				break;
			case 1:
				g.drawImage(gunsImg[currentGun], bound.x+20, bound.y+40, eg.getCanvas());
				break;
			case 2:
				g.drawImage(gunsImg[currentGun], bound.x-15, bound.y+40, eg.getCanvas());
				break;
		}
		
		g.drawImage(img, bound.x, bound.y, eg.getCanvas());
		g.rotate(-Math.toRadians(rotation), bound.x+bound.width/2, bound.y+bound.height/2);
		g.drawRect(getCenter().x, getCenter().y, 10, 10);
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public int getHP() {
		return HP;
	}
	
	public int getEnergy() {
		return Energy;
	}
}
