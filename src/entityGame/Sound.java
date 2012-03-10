package entityGame;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class Sound {
	//private Point centerPoint;
	private AudioInputStream audioInputStream = null;// = AudioSystem.getAudioInputStream(new)
	private Clip clip;
	private double strength;
	private double volume;
	private FloatControl control;
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
			volume = (strength - HearingPoint.distance((Point2D) centerPoint)) / strength;
			
			try {
				if(clip != null && clip.isRunning()) {
					clip.stop();
				}
				if(clip == null) {
					clip = AudioSystem.getClip();
					clip.open(audioInputStream);
				}
				
				control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				float keyVolume = (float) volume*(control.getMaximum()-control.getMinimum()) + control.getMinimum();
				control.setValue(keyVolume);
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
	
	public void stopSound() {
		clip.stop();
	}
	
	public void pauseSound() {
		framePos = clip.getFramePosition();
		clip.stop();
	}
	
	public void resumeSound() {
		clip.setFramePosition(framePos);
		clip.loop(0);
	}
}
