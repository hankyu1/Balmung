package entityGame;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;


public class Sound {
	//private Point centerPoint;
	private AudioInputStream audioInputStream = null;// = AudioSystem.getAudioInputStream(new)
	private Clip clip;
	private double strength;
	private double volume;
	private FloatControl control, balanceControl;
	private int framePos;
	
	public Sound(URL URL, File file, double strength) {
		
		// strength of the sound
		this.strength = strength;
		
		// set up music
		try{
			if(URL != null)
				audioInputStream = AudioSystem.getAudioInputStream(URL);
			else
				audioInputStream = AudioSystem.getAudioInputStream(file);
		}
		catch(Exception ex) {
			// do nothing
			System.out.println("Cant find music... \n" + ex.toString());
			System.exit(0);
		}
	}
	
	public void playSound(Point HearingPoint, Point centerPoint) {
		double dis = HearingPoint.distance((Point2D) centerPoint);
		if(dis <= strength) {
			volume = (strength - HearingPoint.distance(centerPoint)) / strength;
			
			try {
				if(clip != null && clip.isRunning()) {
					clip.stop();
				}
				if(clip == null) {
					clip = AudioSystem.getClip();
					clip.open(audioInputStream);
				}
				
				control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				balanceControl = (FloatControl) clip.getControl(FloatControl.Type.BALANCE);
				float keyVolume = (float) (volume*(control.getMaximum()-control.getMinimum()) + control.getMinimum());
				control.setValue(keyVolume);
				float xDiff = (float) (-(HearingPoint.x-centerPoint.x)/strength);
				System.out.println("Position: " + xDiff);
				balanceControl.setValue(xDiff);
				System.out.println("Sound volume: " + keyVolume);
				clip.setFramePosition(0);
				clip.loop(0);
			}
			catch(Exception ex) {
				System.out.println("Cant play music... \n" + ex.toString());
			}
		}
		else
			System.out.println("Cant hear music; object is too far away from you...");
	}
	
	public double getStrength() {
		return strength;
	}
	
	public void stopSound() {
		clip.stop();
	}
	
	public void pauseSound() {
		clip.stop();
		framePos = clip.getFramePosition();
	}
	
	public void resumeSound() {
		clip.setFramePosition(framePos);
		clip.loop(0);
	}
	
	/*
	public void setVolume(float v) {
		
		System.out.println(clip.isControlSupported(FloatControl.Type.MASTER_GAIN)? "Gain True":"Gain False");
		
		FloatControl fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		control.shift(control.getValue(), v, control.getUpdatePeriod());
		//fc.setValue(v);
		System.out.println("Value from: " + fc.getValue() + " to:" + v + ", in " + fc.getUpdatePeriod() + " microsecond...");
		System.out.println("CurrentValue: " + fc.getValue());
	}
	*/
	public float getMaxSound() {
		return control.getMaximum();
	}
	
	public float getMinSound() {
		return control.getMinimum();
	}
	
	public boolean isPlaying() {
		return clip.isRunning();
	}
	
	public Clip getClip() {
		return clip;
	}
	
	public float getVolume() {
		return control.getValue();
	}
}
