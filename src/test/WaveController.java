package test;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Date;
import java.util.LinkedList;

import entityGame.Entity;
import entityGame.EntityGame;

public class WaveController implements Entity {

	private String id;
	private Timer timer;
	private LinkedList<SpawnPoint> sps;
	
	public WaveController(String id, Timer timer) {
		sps = new LinkedList<SpawnPoint>();
		this.id = id;
		this.timer = timer;
	}
	
	@Override
	public Rectangle getBoundingBox() {
		// TODO Auto-generated method stub
		return new Rectangle();
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void update(EntityGame eg) {
		// TODO Auto-generated method stub
		//long currentTime = new Date().getTime();
		
		if(timer.isDone()) {
			if(!sps.isEmpty()) {
				for(SpawnPoint sp : sps) {
					sp.setSpawning(true);
					timer.setTimer(false);
					timer.restartTimer();
				}
			}
		}
	}

	@Override
	public void render(Graphics2D g, EntityGame eg) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void addSpawnPoint(SpawnPoint sp) {
		sps.add(sp);
	}

}
