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
	private Point centerPoint;
	private AudioInputStream audioInputStream = null;// = AudioSystem.getAudioInputStream(new)
	private Clip clip;
	private double distance, strength;
	
	public Sound(String URL, double strength) {
		
		// strength of the sound
		this.strength = strength;
		
		// set up music
		try{
			audioInputStream = AudioSystem.getAudioInputStream(new URL(URL));
			/*
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			*/
		}
		catch(Exception ex) {
			// do nothing
			System.out.println("Cant find music... \n" + ex.toString());
			System.exit(0);
		}
	}
	
	public void playSound(Point HearingPoint, Point centerPoint) {
		
		double volume = HearingPoint.distance((Point2D) centerPoint) / strength;
		
		try {
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			control.setValue((float) volume);
			clip.start();
		}
		catch(Exception ex) {
			System.out.println("Cant play music... \n" + ex.toString());
		}
	}
	
	public void stopSound() {
		clip.stop();
	}
}
