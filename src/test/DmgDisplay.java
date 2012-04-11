package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedString;
import java.util.Date;

import entityGame.Entity;
import entityGame.EntityGame;

public class DmgDisplay implements Entity {
	
	private String id, text;
	private long lifeTime;
	private Rectangle bound;
	private Font f;
	private AttributedString as;
	private int count;
	
	public DmgDisplay(String id, String text, long lifeTime, int x, int y, int width, int height) {
		this.id = id;
		this.text = text;
		this.lifeTime = lifeTime;
		bound = new Rectangle(x, y, width, height);
		f = new Font("Helvetica", Font.BOLD, 16);
		as = new AttributedString(text);
		as.addAttribute(TextAttribute.FONT, f);
		as.addAttribute(TextAttribute.FOREGROUND, Color.red);
		count = 0;
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
		
		
		
		if(lifeTime <= 0) {
			eg.removeFromCurrentScene(this);
			System.out.println("Dead");
		}
		else {
			
			double y;
			bound.x += 1;
			y = -Math.pow(count-5, 2)+5;
			System.out.println(y);
			bound.y += (int)y;	
			count++;
			lifeTime--;
		}
		
		
	}

	@Override
	public void render(Graphics2D g, EntityGame eg) {
		// TODO Auto-generated method stub
		//FontRenderContext frc = g.getFontRenderContext();
		long currentTime = new Date().getTime();
		
		//TextLayout tl = new TextLayout(text, f, frc);
		g.setColor(Color.red);
		//tl.draw(g, bound.x, bound.y);
		//g.setColor(Color.red);
		//g.setFont(f);
		//g.drawString(as.getIterator(), bound.x, bound.y);
		g.drawString(text, bound.x, bound.y);
		System.out.println("DrawTime: " + (new Date().getTime()-currentTime));
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}

}
