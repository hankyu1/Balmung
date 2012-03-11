package test;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
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
		super("Test", 800, 600);
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
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/box.jpg"), getDirectory().toString(), "box.jpg", 1024);
			getResourcesManager().downloadResources(false, new URL("http://dl.dropbox.com/u/27745240/applet/img/boxSprite.jpg"), getDirectory().toString(), "boxSprite.jpg", 1024);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// image resources
		getResourcesManager().addImage("Box", new ImageIcon("C:/manatee/boxSprite.jpg").getImage());
		
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
		
		Box b1 = new Box("Box1", 100, 300, 28, 31, 1, getResourcesManager().getSpriteResources().get("BoxSprite"), this);
		UITest uTest = new UITest("Test", 0, 100, 100, 100, "Balmung Test");
		
		
		LinkedList<Entity> testScene = new LinkedList<Entity>();
		testScene.add(b1);
		testScene.add(uTest);
		scenes = new LinkedList<LinkedList<Entity>>();
		
		scenes.add(testScene);
		// map keys
		inputHandler.addInput(KeyEvent.VK_UP, "UP");
		inputHandler.addInput(KeyEvent.VK_DOWN, "DOWN");
		inputHandler.addInput(KeyEvent.VK_LEFT, "LEFT");
		inputHandler.addInput(KeyEvent.VK_RIGHT, "RIGHT");
		
		
	}
	public static void main(String[] args) {
		Test t = new Test();
		t.start();
	}
}
