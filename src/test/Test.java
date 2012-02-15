package test;

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
		
		
		Box b1 = new Box("Box1", 0, 0, 28, 31, 1, "src/img/box.jpg"),
			b2 = new Box("Box2", 100, 0, 28, 31, 5, "src/img/box.jpg");
			//b2 = new Box("Box", 50, 100, 28, 31, 5, "src/img/box.jpg");
		UITest uTest = new UITest("Test", 0, 100, 100, 100, "Hank");
		
		
		LinkedList<Entity> testScene = new LinkedList<Entity>();
		testScene.add(b1);
		testScene.add(b2);
		testScene.add(uTest);
		//testScene.add(b2);
		
		scenes.add(testScene);
	}
	
	public static void main(String[] args) {
		Test t = new Test();
		//t.init();
		t.start();
	}
}
