package test;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import javax.swing.ImageIcon;


import entityGame.*;

public class Test extends EntityGame{

	
	public Test() {
		super("Test", 1024, 768, 800, 600);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init() { 
		// TODO Auto-generated method stub
		// load resources first
		
		// download resources (optional, you can load stuff on the fly if you want to)
		setDirectory("C:\\manatee");
		try {
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/tileSheet.png"), getDirectory().toString(), "tileSheet.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/Angel.png"), getDirectory().toString(), "Angel.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/missile.png"), getDirectory().toString(), "missile.png", 1024);
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
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/Inventory.png"), getDirectory().toString(), "Inventory.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/hotkey1.png"), getDirectory().toString(), "hotkey1.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/hotkey2.png"), getDirectory().toString(), "hotkey2.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/hotkey3.png"), getDirectory().toString(), "hotkey3.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/hotkey4.png"), getDirectory().toString(), "hotkey4.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/hotkey5.png"), getDirectory().toString(), "hotkey5.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/hotkey6.png"), getDirectory().toString(), "hotkey6.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/fontSheet.png"), getDirectory().toString(), "fontSheet.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/Player.png"), getDirectory().toString(), "Player.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/missile%20gun.png"), getDirectory().toString(), "MissileGun.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/missile.png"), getDirectory().toString(), "Missile.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/MachineGun.png"), getDirectory().toString(), "MachineGun.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/MachineGunBullet.png"), getDirectory().toString(), "MachineGunBullet.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/ShotGun.png"), getDirectory().toString(), "ShotGun.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/BulletExplosion.png"), getDirectory().toString(), "BulletExplosion.png", 1024);
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// image resources
		getResourcesManager().addImage("Ground", new ImageIcon("C:/manatee/tileSheet.png").getImage());
		getResourcesManager().addImage("missile", new ImageIcon("C:/manatee/missile.png").getImage());
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
		getResourcesManager().addImage("Inventory", new ImageIcon("C:/manatee/Inventory.png").getImage());
		getResourcesManager().addImage("hotkey1", new ImageIcon("C:/manatee/hotkey1.png").getImage());
		getResourcesManager().addImage("hotkey2", new ImageIcon("C:/manatee/hotkey2.png").getImage());
		getResourcesManager().addImage("hotkey3", new ImageIcon("C:/manatee/hotkey3.png").getImage());
		getResourcesManager().addImage("hotkey4", new ImageIcon("C:/manatee/hotkey4.png").getImage());
		getResourcesManager().addImage("hotkey5", new ImageIcon("C:/manatee/hotkey5.png").getImage());
		getResourcesManager().addImage("hotkey6", new ImageIcon("C:/manatee/hotkey6.png").getImage());
		getResourcesManager().addImage("fontSheet", new ImageIcon("C:/manatee/fontSheet.png").getImage());
		getResourcesManager().addImage("Player", new ImageIcon("C:/manatee/Player.png").getImage());
		getResourcesManager().addImage("MissileGun", new ImageIcon("C:/manatee/MissileGun.png").getImage());
		getResourcesManager().addImage("Missile", new ImageIcon("C:/manatee/Missile.png").getImage());
		getResourcesManager().addImage("MachineGun", new ImageIcon("C:/manatee/MachineGun.png").getImage());
		getResourcesManager().addImage("MachineGunBullet", new ImageIcon("C:/manatee/MachineGunBullet.png").getImage());
		getResourcesManager().addImage("ShotGun", new ImageIcon("C:/manatee/ShotGun.png").getImage());
		
		// sprite resources
		/*
		getResourcesManager().addSprite("BoxSprite", new Sprite("BoxSprite",
																1000,
																new ImageIcon("C:/manatee/boxSprite.jpg").getImage(), 
																new Dimension(28, 31), 
																new Point(0, 0), 
																2));*/
		getResourcesManager().addSprite("BulletExplosion", new Sprite("BulletExplosion",
																50,
																new ImageIcon("C:/manatee/BulletExplosion.png").getImage(), 
																new Dimension(60, 60), 
																new Point(0, 0), 
																12));
		
		
		scenes = new LinkedList<LinkedList<Entity>>();
		Map1 m1 = new Map1(this);
		scenes.add(m1.getMap());
		
		// map keys
		inputHandler.addInput(KeyEvent.VK_W, "UP");
		inputHandler.addInput(KeyEvent.VK_S, "DOWN");
		inputHandler.addInput(KeyEvent.VK_A, "LEFT");
		inputHandler.addInput(KeyEvent.VK_D, "RIGHT");
		inputHandler.addInput(KeyEvent.VK_1, "HandGun");
		inputHandler.addInput(KeyEvent.VK_2, "MachineGun");
		inputHandler.addInput(KeyEvent.VK_3, "ShotGun");
		inputHandler.addInput(KeyEvent.VK_M, "MapToggle");
	}
	public static void main(String[] args) {
		Test t = new Test();
		t.start();
	}
}
