package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
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
	private int width, height;
	private Image img = null, fontsheet = null;
	
	public DmgDisplay(String id, String text, long lifeTime, int x, int y, int width, int height) {
		this.id = id;
		this.text = text;
		this.lifeTime = lifeTime;
		bound = new Rectangle(x, y, width, height);
		f = new Font("Helvetica", Font.BOLD, 16);
		as = new AttributedString(text);
		as.addAttribute(TextAttribute.FONT, f);
		as.addAttribute(TextAttribute.FOREGROUND, Color.red);
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
		// get fontsheet
		if(fontsheet == null) {
			fontsheet = eg.getResourcesManager().getImageResources().get("fontSheet");
		}
		
		// create image
		if(img == null) {
			
			int size = text.length();
			img = eg.createImage(size*16, 16);
			width = size*16;
			height = 16;
			
			ImageFilter filter = new RGBImageFilter() {

				@Override
				public int filterRGB(int x, int y, int rgb) {
					// TODO Auto-generated method stub
					return (rgb << 8) & 0XFF000000;
				}
				
			};
			ImageProducer ip = new FilteredImageSource(img.getSource(), filter);

			img = Toolkit.getDefaultToolkit().createImage(ip);
			
			Graphics2D g2d = (Graphics2D)img.getGraphics();
			
			//g2d.fillRect(0, 0, 16, 16);
			for(int i = 0; i < size; i++) {
				//System.out.println("ASDFASDFASDF"+text.charAt(i));
				char c = text.charAt(i);
				if(c == '-') {
					//System.out.println("AS");
					g2d.drawImage(fontsheet, i*16, 0, i*16+16, 16, 176, 0, 192, 16, eg.getCanvas());
				}
				else if(c == '+') {
					g2d.drawImage(fontsheet, i*16, 0, i*16+16, 16, 160, 0, 175, 16, eg.getCanvas());
				}
				else {
					int index = Integer.parseInt(c+"");
					//System.out.println("index: " + index);
					g2d.drawImage(fontsheet, i*16, 0, i*16+16, 16, index*16, 0, index*16+16, 16, eg.getCanvas());
				}
			}
		}
		
		
		if(lifeTime <= 0) {
			eg.removeFromCurrentScene(this);
			System.out.println("Dead");
		}
		else {
			/*
			double y;
			bound.x += 1;
			y = -Math.pow(count-5, 2)+5;
			System.out.println(y);
			bound.y += (int)y;	
			count++;
			*/
			bound.y--;
			lifeTime--;
		}
		
		
	}

	@Override
	public void render(Graphics2D g, EntityGame eg) {
		// TODO Auto-generated method stub
		//FontRenderContext frc = g.getFontRenderContext();
		//long currentTime = new Date().getTime();
		
		//TextLayout tl = new TextLayout(text, f, frc);
		//g.setColor(Color.red);
		//tl.draw(g, bound.x, bound.y);
		//g.setColor(Color.red);
		//g.setFont(f);
		//g.drawString(as.getIterator(), bound.x, bound.y);
		//g.drawString(text, bound.x, bound.y);
		//System.out.println("DrawTime: " + (new Date().getTime()-currentTime));
		try {
			g.drawImage(img, bound.x, bound.y, (int)(width*.7), (int)(height*.7), eg.getCanvas());
		}
		catch(Exception ex) {};
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}

}
