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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


import entityGame.*;

public class Test extends EntityGame{

	
	public Test() {
		super("Test", 800, 640);
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
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/sound/Boxing%20bag%20punches.wav"), getDirectory().toString(), "BoxSound.wav", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/tileSheet.png"), getDirectory().toString(), "tileSheet.png", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/boxSprite.jpg"), getDirectory().toString(), "boxSprite.jpg", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/bullet.png"), getDirectory().toString(), "bullet.png", 1024);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// image resources
		getResourcesManager().addImage("Ground", new ImageIcon("C:/manatee/tileSheet.png").getImage());
		getResourcesManager().addImage("Bullet", new ImageIcon("C:/manatee/bullet.png").getImage());
		
		// sprite resources
		getResourcesManager().addSprite("BoxSprite", new Sprite("BoxSprite",
																1000,
																new ImageIcon("C:/manatee/boxSprite.jpg").getImage(), 
																new Dimension(28, 31), 
																new Point(0, 0), 
																2));
		//System.out.println("Image?"+Toolkit.getDefaultToolkit().getImage("C:/manatee/boxSprit.jpg")==null? "null":"not null");
		// music resources
		getResourcesManager().addSound("BoxSound", new Sound(null, new File("C:/manatee/BoxSound.wav"), 400));
		//System.out.println(getResourcesManager().getSoundResources().size());
		
		// testScene
		Box b1 = new Box("Box1", 100, 300, 28, 31, 3, getResourcesManager().getSpriteResources().get("BoxSprite"), this);
		UITest uTest = new UITest("Test", 0, 100, 100, 100, "Balmung Test");
		
		LinkedList<Entity> testScene = new LinkedList<Entity>();
		
		// map1
		int[][] map1 = {{0,1,2,3,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{5,6,7,8,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{10,11,12,13,14,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{15,16,17,18,19,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{20,21,22,23,24,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
						{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
		BufferedImage groundSheet;
		try {
			groundSheet = ImageIO.read(new File("C:/manatee/tileSheet.png"));
			//System.out.println(groundSheet + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			for(int row = 0; row < 20; row++)
				for(int col = 0; col < 25; col++) {
					int x, y=0;
					if((x = map1[row][col]) > 4) {
						y = x/5;
						x -= y*5;
					}
					
					Image img = Toolkit.getDefaultToolkit().createImage(groundSheet.getSubimage(x*32, y*32, 32, 32).getSource());
					testScene.add(new ImageEntity("ground("+row+","+col+")", img, 32, 32, col*32, row*32));
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//getResourcesManager().getImageResources().get("Gound");
		
		testScene.add(b1);
		testScene.add(uTest);
		scenes = new LinkedList<LinkedList<Entity>>();
		
		scenes.add(testScene);
		
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
