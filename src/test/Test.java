package test;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


import entityGame.*;

public class Test extends EntityGame{

	
	public Test() {
		super("Test", 800, 600, 640, 480);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init() { 
		// TODO Auto-generated method stub
		// load resources first
		
		// download resources (optional, you can load stuff on the fly if you want to)
		setDirectory("C:\\manatee");
		try {
			//System.out.println(getDirectory());
			//getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/sound/Boxing%20bag%20punches.wav"), getDirectory().toString(), "BoxSound.wav", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/tileSheet.png"), getDirectory().toString(), "tileSheet.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/boxSprite.jpg"), getDirectory().toString(), "boxSprite.jpg", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/Angel.png"), getDirectory().toString(), "Angel.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/handgunBullet.png"), getDirectory().toString(), "handgunBullet.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/machinegunBullet.png"), getDirectory().toString(), "machinegunBullet.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/shotgunBullet.png"), getDirectory().toString(), "shotgunBullet.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/MonsterSpawnPoint.png"), getDirectory().toString(), "SpawnPoint.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/bulletExplosionSheet.png"), getDirectory().toString(), "BulletExposion.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/protal.png"), getDirectory().toString(), "protal.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/WaveTimer.png"), getDirectory().toString(), "WaveTimer.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/UI.png"), getDirectory().toString(), "UI.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/protalHP.png"), getDirectory().toString(), "BaseHP.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/PlayerHP.png"), getDirectory().toString(), "PlayerHP.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/Energy.png"), getDirectory().toString(), "Energy.png", 1024);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// image resources
		getResourcesManager().addImage("Ground", new ImageIcon("C:/manatee/tileSheet.png").getImage());
		getResourcesManager().addImage("HandgunBullet", new ImageIcon("C:/manatee/handgunBullet.png").getImage());
		getResourcesManager().addImage("Angel", new ImageIcon("C:/manatee/Angel.png").getImage());
		getResourcesManager().addImage("MachinegunBullet", new ImageIcon("C:/manatee/machinegunBullet.png").getImage());
		getResourcesManager().addImage("ShotgunBullet", new ImageIcon("C:/manatee/shotgunBullet.png").getImage());
		getResourcesManager().addImage("SpawnPoint", new ImageIcon("C:/manatee/SpawnPoint.png").getImage());
		getResourcesManager().addImage("Protal", new ImageIcon("C:/manatee/protal.png").getImage());
		getResourcesManager().addImage("WaveTimer", new ImageIcon("C:/manatee/WaveTimer.png").getImage());
		getResourcesManager().addImage("UI", new ImageIcon("C:/manatee/UI.png").getImage());
		getResourcesManager().addImage("BaseHP", new ImageIcon("C:/manatee/BaseHP.png").getImage());
		getResourcesManager().addImage("PlayerHP", new ImageIcon("C:/manatee/PlayerHP.png").getImage());
		getResourcesManager().addImage("Energy", new ImageIcon("C:/manatee/Energy.png").getImage());
		
		// sprite resources
		getResourcesManager().addSprite("BoxSprite", new Sprite("BoxSprite",
																1000,
																new ImageIcon("C:/manatee/boxSprite.jpg").getImage(), 
																new Dimension(28, 31), 
																new Point(0, 0), 
																2));
		getResourcesManager().addSprite("BulletExplosion", new Sprite("BulletExplosion",
																50,
																new ImageIcon("C:/manatee/BulletExposion.png").getImage(), 
																new Dimension(30, 30), 
																new Point(0, 0), 
																12));
		
		//System.out.println("Image?"+Toolkit.getDefaultToolkit().getImage("C:/manatee/boxSprit.jpg")==null? "null":"not null");
		// music resources
		//getResourcesManager().addSound("BoxSound", new Sound(null, new File("C:/manatee/BoxSound.wav"), 400));
		//System.out.println(getResourcesManager().getSoundResources().size());
		
		// testScene
		LinkedList<Entity> Map1 = new LinkedList<Entity>();
		
		// map1
		//setCameraSize(400, 320);
		Random randomGenerator = new Random();
		
		Player p1 = new Player("Player1", 100, 300, 28, 31, 3, getResourcesManager().getSpriteResources().get("BoxSprite"), this);
		//Monster Angel = new Monster("Angel", randomGenerator.nextInt(9)+1, 2, 10, getResourcesManager().getImageResources().get("Angel"), 0, 0, 35, 50);
		//UITest uTest = new UITest("Test", 0, 100, 100, 100, "Balmung Test");
		SpawnPoint sp1 = new SpawnPoint("SpawnPoint 1", getResourcesManager().getImageResources().get("SpawnPoint"), 1000, 10, 0, 0, 80, 80),
				   sp2 = new SpawnPoint("SpawnPoint 2", getResourcesManager().getImageResources().get("SpawnPoint"), 1000, 10, 320, 0, 80, 80),
				   sp3 = new SpawnPoint("SpawnPoint 3", getResourcesManager().getImageResources().get("SpawnPoint"), 1000, 10, 720, 0, 80, 80);
		sp1.setSpawning(true);
		sp2.setSpawning(true);
		sp3.setSpawning(true);
		
		Protal protal = new Protal("Protal", getResourcesManager().getImageResources().get("Protal"), 303, 400, 194, 192, 100, 3, 1000);
		Timer waveTimer = new Timer("waveTimer", getResourcesManager().getImageResources().get("WaveTimer"), 10, 10, 100, 30, 25, 22, 120000);
		
		PlayerUI pui = new PlayerUI(this, p1, protal);
		
		BufferedImage groundSheet;
		try {
			groundSheet = ImageIO.read(new File("C:/manatee/tileSheet.png"));
			for(int row = 0; row < 20; row++)
				for(int col = 0; col < 25; col++) {
					int x, y=0;
					if((x = randomGenerator.nextInt(24)) > 4) {
						y = x/5;
						x -= y*5;
					}
					Image img = Toolkit.getDefaultToolkit().createImage(groundSheet.getSubimage(x*32, y*32, 32, 32).getSource());
					ImageEntity tile = new ImageEntity("ground("+row+","+col+")", img, 32, 32, col*32, row*32);
					tile.setPrerender(true);
					Map1.add(tile);
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//getResourcesManager().getImageResources().get("Gound");
		
		Map1.add(protal);
		Map1.add(p1);
		//Map1.add(Angel);
		//Map1.add(uTest);
		Map1.add(sp1);
		Map1.add(sp2);
		Map1.add(sp3);
		Map1.add(waveTimer);
		Map1.add(pui);
		
		scenes = new LinkedList<LinkedList<Entity>>();
		scenes.add(Map1);
		
		// map keys
		inputHandler.addInput(KeyEvent.VK_W, "UP");
		inputHandler.addInput(KeyEvent.VK_S, "DOWN");
		inputHandler.addInput(KeyEvent.VK_A, "LEFT");
		inputHandler.addInput(KeyEvent.VK_D, "RIGHT");
		inputHandler.addInput(KeyEvent.VK_1, "HandGun");
		inputHandler.addInput(KeyEvent.VK_2, "MachineGun");
		inputHandler.addInput(KeyEvent.VK_3, "ShotGun");
	}
	public static void main(String[] args) {
		Test t = new Test();
		t.start();
	}
}
