package entityGame;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

public class ResourcesManager {
	private HashMap<String, HashMap> Resources;
	
	private Map<String, Sprite> sprites = new HashMap<String, Sprite>();
	private Map<String, Image> images = new HashMap<String, Image>();
	private Map<String, Sound> sounds = new HashMap<String, Sound>();
	
	private ResourcesManager() {
		Resources = new HashMap<String, HashMap>();
	}
	
	public void addImage(String elementName, Image img) {
		images.put(elementName, img);
	}
	
	public void addSprite(String elementName, Sprite spt) {
		sprites.put(elementName, spt);
	}
	
	public void addSound(String elementName, Sound sound) {
		sounds.put(elementName, sound);
	}
	
	public Map<String, Image> getImageResources() {
		return images;
	}
	
	public Map<String, Sprite> getSpriteResources() {
		return sprites;
	}
	
	public Map<String, Sound> getSoundResources() {
		return sounds;
	}
	
	/*
	public HashMap getMap(String mapName) {
		return Resources.get(mapName);
	}
	*/
	private static class SingletonHolder {
		public static final ResourcesManager instance = new ResourcesManager();
	}
	
	public static ResourcesManager getInstance() {
		return SingletonHolder.instance;
	}
	
}
