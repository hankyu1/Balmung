package test;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;


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
			getResourcesManager().downloadResources(new URL("http://dl.dropbox.com/u/27745240/applet/sound/Boxing%20bag%20punches.wav"), getDirectory().toString(), "BoxSound.wav", 1730151);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// image resources
		getResourcesManager().addImage("Box", getImage(getDocumentBase(), "http://dl.dropbox.com/u/27745240/applet/img/box.jpg"));
		
		// sprite resources
		getResourcesManager().addSprite("BoxSprite", new Sprite("BoxSprite",
																1000,
																getImage(getDocumentBase(), "http://dl.dropbox.com/u/27745240/applet/img/boxSprite.jpg"), 
																new Dimension(28, 31), 
																new Point(0, 0), 
																2));
		
		// music resources
		getResourcesManager().addSound("BoxSound", new Sound(null, new File("C:/manatee/BoxSound.wav"), 400));
		System.out.println(getResourcesManager().getSoundResources().size());
		
		Box b1 = new Box("Box1", 100, 300, 28, 31, 1, getResourcesManager().getSpriteResources().get("BoxSprite"), this);
		UITest uTest = new UITest("Test", 0, 100, 100, 100, "Balmung Test");
		
		
		LinkedList<Entity> testScene = new LinkedList<Entity>();
		testScene.add(b1);
		//testScene.add(b2);
		testScene.add(uTest);
		//testScene.add(b2);
		scenes = new LinkedList<LinkedList<Entity>>();
		//System.out.println("testScene: "+testScene.size());
		
		scenes.add(testScene);
		// map keys
		inputHandler.addInput(KeyEvent.VK_UP, "UP");
		inputHandler.addInput(KeyEvent.VK_DOWN, "DOWN");
		inputHandler.addInput(KeyEvent.VK_LEFT, "LEFT");
		inputHandler.addInput(KeyEvent.VK_RIGHT, "RIGHT");
		/*
		if(inputHandler.getInputList() != null && inputHandler.getInputList().size() > 0)
			System.out.println("inputHandler: " + inputHandler.getInputList().get(0).getName());
		*/
		
		
	}
	public static void main(String[] args) {
		Test t = new Test();
		//t.init();
		t.start();
	}
}
