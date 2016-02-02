package KLD.cmd;

import java.awt.Graphics;

import KLD.obj.GameObject;

public abstract class TimedDraw extends Draw {

	private int cd =0; 

	public TimedDraw(String name, int t, GameObject go) {
		super(name, go);
		this.cd = t;
	}
	
	public TimedDraw(String name, int t) {
		this(name,  t, null);
	}
	

	public abstract void draw(Graphics g) ;
	
	
	@Override
	public void callDraw(Graphics g){

		final Graphics gugu = g; // we love gugu, again <3 
		
		if(cd<0)
			this.deteminate();
		
		if(visible)
			draw(g);
		
		cd--;
		
		g= gugu; // copy pasted YOLO
	}
	
	public int getTime(){
		return cd; 
	}
	
}
