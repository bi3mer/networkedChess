package KLD;

import java.awt.Graphics;
import java.awt.Image;
/**
 *  Animation class allows images to be animated via multiple frames. 
 *  After creating an animation object, call {@link #getNextFrame()} to receive each image in each call.
 *  Also, you can reverse the animation, or ever set it to loop once then stop at the last frame.
 * @author      KLD
 * @version     1.2  Animation can draw itself using  <code>drawNextFrame(Graphics, int, int)</code>         
 * @version     1.1  changed <code>getAnimation()</code> to <code>clone()</code>        
 * @version		1.0  class created
 * @since       2013-04-01          (From package KLD) 
 * 
 * 
 */
public class Animation {
/**
 * Holds images (frames) of the animation
 */
	private Image[] image;
	/**
	 * Holds delays for each image frame (default delay is 1) 
	 */
	private int[] delay; 
	/**
	 * NOT TO BE USED YET! Holds the length of the animation  
	 */
	//private int length;
	/**
	 * Holds an int value of the current frame
	 */
	private int frame = 0;
	/**
	 * Holds an int time that is used to switch between frames according to the delay.
	 */
	private int timer = -1;
	/**
	 * Holds a flag that reverse the animation when true (initialized false)
	 */
	private boolean isReversed; 
	/**
	 * Holds a flag that allows the animation to loop (initialized true)
	 */
	private boolean loop;
	/**
	 * Holds a flag that makes the animation loop once then stops at the last frame (initialized false)
	 */
	private boolean once;

	// IT'S A PRIVATE CONSTRUCTOR
	
	/**
	 * sets <code>loop</code> to true, <code>once</code> and <code>isReversed</code> to false
	 */
	private Animation() {
		loop = true;
		once = false;
		isReversed = false; 
	}

	
	/**
	 * calls the private constructor, then sets delays and images. The constructor also matches delays to images if the length of delays is less than images
	 * @param im array of images that will represent the animation frame
	 * @param d array of delays that delays each frame
	 * @throws Exception if the length of delays is more than length of images
	 * @see #Animation()
	 */
	public Animation(Image[] im, int[] d) throws Exception{
		this();
		if(im.length<d.length)
			throw new Exception("images array doesn't match delay length");
		if(im.length>d.length){
			int[] newdelay = new int[im.length];
			for(int i=0; i<newdelay.length;i++)
				newdelay[i] = d[i%d.length];
			d = newdelay;
		}
		image = im;
		delay = d;
		//length=im.length;

	}
	
	// overloaded constructor takes image array and single delay for all images
	/**
	 * calls the private constructor, then sets delays and images. 
	 * @param im array of images that will represent the animation frame
	 * @param d shared delay for all frames
	 * @see #Animation()
	 */
	public Animation(Image[] im, int d) {
		this();
		if (d <= 0)
			d = 1;//TODO test for 0 
		int[] delay = new int[im.length]; /// 

		for (int i = 0; i < delay.length; i++)
			delay[i] = d;

		image = im;
		this.delay = delay;
	}
/**
 * Creates an animation object of images passed with no delay (delay =1)
 * @param im array of images that will represent the animation frame
 * {@link #Animation(Image[], int)}
 */
	public Animation(Image[] im) {
		this(im, 1);
	}

	// ##### GET~TERS #####
/**
 * The returned image is based three many factors such as loop, once and isReversed. 
 * <p>As for loop,  if it's true, the images can change(move next or previous)</p>
 * <p>As for once, if it's false, the animation will reset after reaching last frame </p>
 * <p>As for isRevered, if it's true, the animation will move backwards   </p>
 * @return image returns an image of a frame every time it gets called
 */
	public Image getNextFrame() { 
		if (loop)
			timer++;
		
		int nxt = (isReversed)? -1: 1; 
		if (timer == delay[frame]) {
			timer = 0;
			if (frame + nxt == image.length || frame + nxt == -1) {
				if (once) {
					timer--;
					return image[frame];
				}// once
				if(isReversed)
					frame = image.length-1;
				else
				frame = 0;
			}// frame+1
			else
				frame+= nxt;
		}// timer==
		
		return image[frame];
	}
	/**
	 * draws an image using g at x and y 
	 * @param g
	 * @param x
	 * @param y
	 */
	public void drawNextFrame(Graphics g, int x, int y){
		g.drawImage(getNextFrame(), x, y, null);
	}
	/**
	 * 
	 * @param g Graphics 
	 * @param x Integer x position  
	 * @param y Integer y position 
	 * @param w width  of the image 
	 * @param h height of the image
	 */
	public void drawNextFrame(Graphics g, int x, int y, int w, int h){
		g.drawImage(getNextFrame(), x, y,w,h, null);
	}
	 
	
	
/**
 * 
 * @return a copy of the animation object
 */
	public Animation clone() {
	
		Animation ani;
		try {
			ani = new Animation(image, delay);
			ani.setLoop(loop);
			ani.setLoopOnce(once);
			ani.setReversed(isReversed);
			ani.setFrame(frame);
			return ani;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * frame could be any positive or negative value that could exceed the length. 
	 * @param frame  frame number 
	 * @return image frame of that index 
	 */
	public Image getImageAt(int frame) {
		boolean nega = frame<0;
		if(nega)
			frame=0-frame;// >:/
		frame %= length();
			
		if(nega)
			frame = length() - frame;
	
		return image[frame];
	}

	/**
	 * 
	 * @return current image
	 */
	public Image getCurrentImage() {
		return image[frame];
	}
/**
 * 
 * @return number of frames 
 */
	
	public int length() {
		return image.length;
	}

	/**
	 * not tested yet
	 * @return a copy of the frames 
	 */
	public Image[] getImageArray() {
		return image.clone();
	}
	/**
	 * frame could be any positive or negative value that could exceed the length. 
	 * @param frame frame number
	 * @return int value of the frame delay
	 */
	public int getDelayAt(int frame){
		boolean nega = frame<0;
		if(nega)
			frame=0-frame;// >:/
		frame %= length();
			
		if(nega)
			frame = length() - frame;
		
		return delay[frame];
	}
	/**
	 * not tested yet 
	 * @return a copy of frame delays 
	 */
	public int[] getDelayArray(){
		return delay.clone();
	}
	/**
	 * 
	 * @return current frame int value
	 */
	public int getCurrentFrame(){
		return frame; 
	}
/**
 * 
 * @return loop value
 * @see #loop
 */
	public boolean isLooping() {
		return loop;
	}
	/**
	 * 
	 * @return isReversed value 
	 * @see #isReversed
	 */
	public boolean isReversed(){
		return isReversed;
	}
	
	// ##### SET~TERS ######

	/*
	 * just create an new object
	public void setAnimation(Animation ani){
		image = ani.getImageArray();
		delay = ani.getDelayArray();
		frame = ani.getCurrentFrame();
	}
*/
	
	/**
	 * @param im replacement of old images. 
	 * @throws Exception length of the images doesn't match old images 
	 */
	public void setImages(Image[] im) throws Exception {
		if (this.length() == im.length)
			image = im;
		else
			throw new Exception(String.format("Array length doesn't "
					+ "match: input %d != current %d", im.length, image.length));
	}

	
	/**
	 * frame could be any positive or negative value that could exceed the length. 
	 * @param frame frame number  
	 */
	public void setFrame(int frame) {
		boolean nega = frame<0;
		if(nega)
			frame=0-frame;// >:/
		frame %= length();
			
		if(nega)
			frame = length() - frame;
		
		this.frame = frame;
	}
/**
 * @param d delays replacement 
 * @throws Exception new length doesn't match the old one
 */
	public void setDelays(int[] d) throws Exception {
		if (d.length == d.length)
			delay = d;
		else
			throw new Exception(String.format("Array length doesn't "
					+ "match: input %d != current %d", d.length, image.length));

	}
/**
 * sets all delays to a single value 
 * @param d delay value
 */
	public void setDelays(int d) {
		if (d < 0)
			d = 1;
		for (int i = 0; i < delay.length; i++)
			delay[i] = d;
	}
/**
 * sets the value of <code>loop</code>
 * @param b boolean 
 * @see #loop
 */
	public void setLoop(boolean b) {
		loop = b;
	}
/**
 * sets the value of <code>once</code>
 * @param b b(o.o)lean 
 * @see #once
 */
	public void setLoopOnce(boolean b) {
		once = b;
	}
	/**
	 * sets the value of {@link #isReversed}
	 * @param b
	 */
	public void setReversed(boolean b){
		isReversed = b;
	}
	

	// #### Something else
	/**
	 * forced the frame to move to the next frame each call. 
	 */
	public void goNext() {
		if (frame + 1 == image.length)
			frame = 0;
		else
			frame++;
	}
	/**
	 * forces the frame to move to the previous frame each call. 
	 */
	public void goPrevious() {
		if (frame - 1 == -1)
			frame = image.length - 1;
		else
			frame--;
	}
	/**
	 * Adds a new frame and delay to the animation 
	 * @param im image
	 * @param d  delay 
	 */
	public void addImage(Image im, int d){
		Image[] newIm ;
		int[] newDelay;
		
		//if(image[image.length-1]!=null)
		//	newIm = new Image[image.length+1];
		
	   newIm = new Image[image.length+1]; //TODO better resizing
	   newDelay = new int[delay.length+1];
		
	   for(int i=0; i< image.length;i++){
		   newIm[i]= image[i];
		   newDelay[i] = delay[i];
	   }
		newIm[image.length] = im; 
		newDelay[delay.length] = d; 
		
		image = newIm; 
		delay = newDelay;
		
	}
	
	/**
	 * Adds a new images with the same delay as the last frame's delay
	 * @param im image
	 */
	public void addImage(Image im){
		addImage(im, delay[length()-1]);
	}
	

	//TODO resize for both Animation.image and Animation.delay 
	private <T> T[] resize(T[] t){
		if(t[t.length-1]!=null){
			T[] newt = (T[])new Object[t.length];
			for(int i=0; i<t.length;i++){
				newt[i]=t[i];
			}
			return newt;
		}
		return t;
		
	}
	
	

}// end animation class

