package test;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

import entityGame.Entity;
import entityGame.EntityGame;

public class DmgDisplay implements Entity {
	
	private String id, text;
	private long lifeTime;
	private Rectangle bound;
	private int width, height;
	private Image fontsheet = null;
	private Image[] img;
	
	public DmgDisplay(String id, String text, long lifeTime, int x, int y, int width, int height) {
		this.id = id;
		this.text = text;
		this.lifeTime = lifeTime;
		bound = new Rectangle(x, y, width, height);
		bound.x += (int)((Math.random()*10) - 20);
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
			img = new Image[size];
			width = 16;
			height = 16;
			
			//g2d.fillRect(0, 0, 16, 16);
			for(int i = 0; i < size; i++) {
				//System.out.println("ASDFASDFASDF"+text.charAt(i));
				char c = text.charAt(i);
				if(c == '-') {
					//System.out.println("AS");
					img[i] = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(fontsheet.getSource(), new CropImageFilter(176, 0, width, height)));
					//g2d.drawImage(fontsheet, i*16, 0, i*16+16, 16, 176, 0, 192, 16, eg.getCanvas());
				}
				else if(c == '+') {
					img[i] = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(fontsheet.getSource(), new CropImageFilter(160, 0, width, height)));
					//g2d.drawImage(fontsheet, i*16, 0, i*16+16, 16, 160, 0, 175, 16, eg.getCanvas());
				}
				else {
					int index = Integer.parseInt(c+"");
					//System.out.println("index: " + index);
					//g2d.drawImage(fontsheet, i*16, 0, i*16+16, 16, index*16, 0, index*16+16, 16, eg.getCanvas());
					img[i] = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(fontsheet.getSource(), new CropImageFilter(index*16, 0, width, height)));
				}
			}
		}
		
		
		if(lifeTime <= 0) {
			eg.removeFromCurrentScene(this);
			System.out.println("Dead");
		}
		else {
			
			bound.y--;
			lifeTime--;
		}
		
		
	}

	@Override
	public void render(Graphics2D g, EntityGame eg) {
		// TODO Auto-generated method stub
		if(img != null) {
			for(int i = 0; i < img.length; i++) {
				g.drawImage(img[i], bound.x+i*8, bound.y, eg.getCanvas());
			}
		}
		
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}

}
