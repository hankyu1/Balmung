package entityGame;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;

public class ResourcesManager {
	private HashMap<String, HashMap> Resources;
	
	private Map<String, Sprite> sprites = new HashMap<String, Sprite>();
	private Map<String, Image> images = new HashMap<String, Image>();
	private Map<String, Sound> sounds = new HashMap<String, Sound>();
	private EntityGame eg;
	
	private ResourcesManager() {
		Resources = new HashMap<String, HashMap>();
	}
	
	public void setEG(EntityGame eg) {
		this.eg = eg;
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
	
	public void downloadResources(boolean overWrite, URL url, String DestDir, String fileName, int size) {
		OutputStream os = null;
		URLConnection uCon = null;
		InputStream is = null;
		byte[] buf;
		int ByteRead, ByteWritten=0;
		
		try {
			
			if(!new File(DestDir+"\\"+fileName).exists() || overWrite) {
				os = new BufferedOutputStream(new FileOutputStream(DestDir+"\\"+fileName));
				uCon = url.openConnection();
				is = uCon.getInputStream();
				buf = new byte[size];
				while((ByteRead = is.read(buf)) != -1) {
					os.write(buf, 0, ByteRead);
					ByteWritten += ByteRead;
					System.out.println(fileName+":" + ByteWritten);
				}
				System.out.println(fileName + " Downloaded Successfully.");
				is.close();
				os.close();
			}
			else
				System.out.println(fileName + " already exist, don't need to download this file...");
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
