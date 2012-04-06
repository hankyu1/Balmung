package test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import entityGame.Entity;
import entityGame.EntityGame;
import entityGame.UIComponent;

public class PlayerUI implements Entity, UIComponent {

	private Rectangle bound;
	private Image ui, bHP, pHP, energy, weapon, wAbility;
	Player player;
	Protal protal;
	
	public PlayerUI(EntityGame eg, Player player, Protal protal) {
		bound = new Rectangle(100, 370, 440, 110);
		this.player = player;
		this.protal = protal;
		ui = eg.getResourcesManager().getImageResources().get("UI");
		bHP = eg.getResourcesManager().getImageResources().get("BaseHP");
		pHP = eg.getResourcesManager().getImageResources().get("PlayerHP");
		energy = eg.getResourcesManager().getImageResources().get("Energy");
	}
	
	@Override
	public Rectangle getBoundingBox() {
		// TODO Auto-generated method stub
		return bound;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(EntityGame eg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D g, EntityGame eg) {
		// TODO Auto-generated method stub
		g.drawImage(ui, bound.x, bound.y, eg.getCanvas());
		
		// get lenght
		double BaseHPLength = 296*protal.getHP()/100,
			   PlayerHPLength = 296*player.getHP()/100,
			   PlayerEnergyLenght = 296*player.getEnergy()/100;
		
		// (71, 40), (60), (80)
		g.drawImage(bHP, bound.x+71, bound.y+40, (int)BaseHPLength, 17, eg.getCanvas());
		g.drawImage(pHP, bound.x+71, bound.y+60, (int)PlayerHPLength, 17, eg.getCanvas());
		g.drawImage(energy, bound.x+71, bound.y+80, (int)PlayerEnergyLenght, 17, eg.getCanvas());
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}

}
