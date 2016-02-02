/* READ ME
 *  Command: objects of this class is used to either draw or take action (or both at the same item)
 *  
 *   Before you create an object, you have to write cmd(), but note that you call the command using call() 
 *   Commands can be added to a GamePanel or a Player and they are called after action() and draw(g) are executed.
 *   
 *   You can simple use: (GamePanel).addCommand(Command cmd)  
 * 
 * 	 For Player, Commands are not all the same. You either add a draw command ( addDraw(cmd) ) or action command ( addAction(cmd) )
 * 
 */
package KLD.cmd;


//TODO better link this somehow to both action and draw.
import java.awt.Graphics;

import KLD.obj.GameObject;

public abstract class Command extends LinkObject{

	protected GameObject go; 
	
	protected boolean enabled = true;  //controls the whole thing
	
	protected boolean visible = true; //controls draw
	protected boolean active = true;  //controls action 
	
	
	public Command(String name, GameObject go){
		this(name);
		this.go = go; 
	}
	
	public Command(String name){
		super(name);
		init();
	}
	
	public void init(){
		
	}

	
	protected abstract void action(); 
	
	protected abstract void draw(Graphics g); 
	
	
	
	public void callAction(){
		
		if(enabled && active)
			action();
		
		
	}
	
	public void callDraw(Graphics g){
		//back-up g 
		Graphics gugu = g.create(); // we love gugu <3 
		
		if(enabled && visible)
			draw(g);
		
		g= gugu; 
	}
	
	
	
	//command part 
	public void disable(){
		enabled = false; 
	}
	
	public void enable(){
		enabled = true; 
	}

	public boolean isDisabled() {
		return !enabled;
	}
	public boolean isEnabled() {
		return enabled;
	}
	
	//draw part
	public void toVisibile(){
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
	
	//action part
	public void activate(){
		active = true; 
	}
	
	public void deactivate(){
		active = false; 
	}

	public boolean isActivated() {
		return active;
	}
	public boolean isDeactivated() {
		return !active;
	}
	
	public void setActivate(boolean b){
		active=b;
	}
	
	public void stop(){
		disable();
	}
}
