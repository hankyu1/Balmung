package test;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import entityGame.Entity;
import entityGame.EntityGame;
import entityGame.UIComponent;

public class UITest implements Entity, UIComponent {

	private String id, txt;
	private Rectangle bound;
	
	public UITest(String id, int x, int y, int w, int h, String txt) {
		this.id = id;
		bound = new Rectangle(x, y, w, h);
		this.txt = txt;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		//System.out.println(txt);
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString(txt, bound.x, bound.y);
	}

}
