/**
 * Loader 
 * 
 * loads Transepted Images and Clips. 
 * 
 * 
 * 
 * 
 * Future thoughts: 
 *  -Loads Object Files.
 *  -Write Objects to Files. 
 * 
 *  Updates:
 *  Loader 1.1
 * 	
 * loads animation; 
 * 	
 * Loader 1.0 : 
 *   - All new. 
 */


package KLD;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Formatter;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
/**
 * Loader loads images and .wav files from /res file 
 * 
 * 
 * @author KLD
 *
 */
//TODO create path instead of always using /res 
public class Loader {

	private String folder; 
	private String ex;
	
	private String path;//TODO
	
	private Color alpha; //TODO alpha color should'nt be always white 
	
	public Loader(){
		folder = "";
		ex= "";
	
	}
	
	// ### set ###
	
	/**
	 * Sets a common path prefix that will be used for loading. 
	 * @param s  prefix 
	 */
	public void setFolder(String s){
		folder = s;
	}
	/**
	 * Sets a common path suffix that will be used for loading
	 * @param s  suffix 
	 */
	public void setExtention(String s){
		ex = s;
	}
	// ### loads ###
	
	public void setAlpha(Color alpha)
	{
		this.alpha = alpha; 
	}
	
	/**
	 * 
	 * @param fileName full file name.
	 * @return a transparent image 
	 */
	public Image loadImage(String fileName){
		BufferedImage image ;
		try {
			image = ImageIO.read(getClass().getResource(folder+fileName+ex));// TODO
			
			ImageFilter filter = new RGBImageFilter()
		    { //TODO use alpha color
				int markerRGB = alpha.getRGB() | 0xFF000000;
				
				@Override
		      public final int filterRGB(int x, int y, int rgb){
				if ((rgb | 0xFF000000) == markerRGB) {
    				// Mark the alpha bits as zero - transparent
    				return 0x00FFFFFF & rgb;
    			} else {
    				// nothing to do
    				return rgb;
    			}
				 }
		    };
		    ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
		    return Toolkit.getDefaultToolkit().createImage(ip);
			
		    
		} 
		catch (Exception e ) {
			System.out.println("Cannot find "+folder+path+ex);
			e.printStackTrace();
		}
	
		return null;
	}//end 
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public Image loadRawImage(String fileName){
		BufferedImage image ;
		try {
			image = ImageIO.read(getClass().getResource(folder+fileName+ex));// TODO
	
		    return image; 
		    
		} 
		catch (Exception e ) {
			System.out.println("Cannot find "+folder+path+ex);
			e.printStackTrace();
		}
	
		return null;
	}//end 


	/**
	 * @param fileName
	 * @return
	 */
	public ImageIcon loadImageIcon(String fileName) {

		   return new ImageIcon(getClass().getResource( fileName )); 
		}

	public Clip loadClip(String path){
		 try {
			 AudioInputStream audioIn = AudioSystem.getAudioInputStream(this.getClass().getResource(folder+path+ex));
			 Clip music = AudioSystem.getClip();
			 music.open(audioIn);
			 
			 return music; 
			 
			 
	      } catch (FileNotFoundException e){
	    	 e.printStackTrace();
	    	  Formatter output;
			try {
				output = new Formatter("errorLog.txt");
				 output.format("Error!! %s", e.toString());
				 output.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}  
	      } catch (UnsupportedAudioFileException e) {
	         //e.printStackTrace();
	      } catch (IOException e) {
	         //e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         //e.printStackTrace();
	      }
		 
		 return null; 
}

		
	public Animation loadAnimation(int[] d, String... paths){
		
		Image[] im = new Image[paths.length];
		
		for(int i=0;i<im.length;i++)
			im[i] = loadImage(paths[i]);
		try{
		return new Animation(im,d);
		}
		catch(Exception e){
			 e.printStackTrace();
		}
		
		return null;
	}
	public Animation loadAnimation(int d, String... paths){
		
		Image[] im = new Image[paths.length];
		
		for(int i=0;i<im.length;i++)
			im[i] = loadImage(paths[i]);
		
		return new Animation(im,d);
	}
	public Animation loadAnimation(String... paths){
		
		Image[] im = new Image[paths.length];
		
		for(int i=0;i<im.length;i++){
			im[i] = loadImage(paths[i]);
		}
		
		return new Animation(im);
	}
	
		
	// TODO this is awesome. 
	public <T> T load(T object){
		return object; 
	}

	
	
}//end class
