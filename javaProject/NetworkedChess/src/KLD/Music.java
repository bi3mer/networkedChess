package KLD;

import javax.sound.sampled.Clip;



public class Music {

	Clip clip; 
	public Music(Clip c){
		clip = c;
		
	}//end music  
	
	public void play(){
		if(clip!=null)
			clip.start(); 
	}
	
	public void stop(){
		if(clip!=null)
			clip.stop();
	}
	
	public void setFramePosition(int frames){
		clip.setFramePosition(frames);
	}
	
	public void restart(){
		if(clip!=null)
			clip.setFramePosition(0);
	}
	
	public void loop(){
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public int getFramePosition(){
		return clip.getFramePosition();
	}
	
	
	
	
	
	
}//end 
