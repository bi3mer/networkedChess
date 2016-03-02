/* READ ME
 * 
 * 
 * Draw is a command without action. 
 * 
 * 
 * 
 */
package KLD.cmd;

import java.awt.Graphics;

import KLD.obj.GameObject;

public abstract class Draw extends LinkObject{

	protected GameObject go; 
	protected boolean visible = true; //controls draw
	
		
	public Draw(String name){
		super(name);
		init();
	}
	
	public Draw(String name, GameObject go){
		this(name);
		this.go = go; 
	}
	
	public void init(){
		
	}

	

	
	protected abstract void draw(Graphics g); 

	
	public void callDraw(Graphics g){
		
		final Graphics gugu = g; // we love gugu, again <3 
		
		if(visible)
			draw(g);
		
		g= gugu; 
	}
	
	
	
	public void totVisibile(){
		visible = true; 
	}
	
	public void toInvisible(){
		visible = false; 
	}

	public boolean isVisible() {
		return visible;
	}
	public boolean isInvisible() {
		return !visible;
	}
	
	public void setVisibile(boolean b){
		visible = b;
	}
	
	public void stop(){
		toInvisible();
	}
	
	
}

	

