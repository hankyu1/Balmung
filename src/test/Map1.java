package test;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

import entityGame.Entity;
import entityGame.EntityGame;

public class Map1 {
	
	private LinkedList<Entity> Map1 = new LinkedList<Entity>();
	
	public Map1(EntityGame eg) {
		// map1
		
		Random randomGenerator = new Random();
		
		Player p1 = new Player("Player1", 100, 300, 41, 70, 3, eg.getResourcesManager().getImageResources().get("Player"), eg);
		SpawnPoint sp1 = new SpawnPoint("SpawnPoint 1", eg.getResourcesManager().getImageResources().get("SpawnPoint"), 1000, 10, 0, 0, 80, 80),
				   sp2 = new SpawnPoint("SpawnPoint 2", eg.getResourcesManager().getImageResources().get("SpawnPoint"), 1000, 10, 320, 0, 80, 80),
				   sp3 = new SpawnPoint("SpawnPoint 3", eg.getResourcesManager().getImageResources().get("SpawnPoint"), 1000, 10, 720, 0, 80, 80);
		
		
		Protal protal = new Protal("Protal", eg.getResourcesManager().getImageResources().get("Protal"), 415, 576, 194, 192, 100, 3, 1000);
		Timer waveTimer = new Timer("waveTimer", eg.getResourcesManager().getImageResources().get("WaveTimer"), 10, 10, 100, 30, 25, 22, 1000);
		
		PlayerUI pui = new PlayerUI(eg, p1, protal);
		
		MiniMap minimap = new MiniMap("minimap", 0, 490, 100, 100, 1024, 768);
		WaveController wc = new WaveController("controller", waveTimer);
		wc.addSpawnPoint(sp1);
		wc.addSpawnPoint(sp2);
		wc.addSpawnPoint(sp3);
		BufferedImage groundSheet;
		try {
			groundSheet = ImageIO.read(new File("C:/manatee/tileSheet.png"));
			for(int row = 0; row < 24; row++)
				for(int col = 0; col < 32; col++) {
					int x, y=0;
					if((x = randomGenerator.nextInt(24)) > 4) {
						y = x/5;
						x -= y*5;
					}
					Image img = Toolkit.getDefaultToolkit().createImage(groundSheet.getSubimage(x*32, y*32, 32, 32).getSource());
					ImageEntity tile = new ImageEntity("ground("+row+","+col+")", img, 32, 32, col*32, row*32);
					Map1.add(tile);
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//getResourcesManager().getImageResources().get("Gound");
		Inventory invenSlot = new Inventory("Inventory", 700, 490, 100, 100, eg);
		
		
		Map1.add(wc);
		Map1.add(protal);
		Map1.add(sp1);
		Map1.add(sp2);
		Map1.add(sp3);
		Map1.add(p1);
		Map1.add(waveTimer);
		Map1.add(pui);
		Map1.add(minimap);
		Map1.add(invenSlot);
	}
	
	public LinkedList<Entity> getMap() {
		return Map1;
	}
}
