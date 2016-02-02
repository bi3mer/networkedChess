/* READ ME
 * 
 * Action is a command without draw
 * 
 * 
 */
package KLD.cmd;

import KLD.obj.GameObject;


/**
 * Action is a set of code stored in a function called <code>action()</code>
 * @author KLD
 *
 */
public abstract class Action extends LinkObject {

	protected GameObject go; 
	
	
	protected boolean active = true;  //controls action 
	
	public Action(String name){
		super(name);
		init();
	}
	
	public Action(String name, GameObject go){
		super(name);
		this.go = go; 
		init();
	}
	
	
	
	public void init(){
		
	}

	
	protected abstract void action(); 
	
	
	
	
	public void callAction(){
		
		if(active)
			action();
		
		
	}
	
	
	
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
	
	public void stop(){
		deactivate();
	}
	 
	public void setActivate(boolean b){
		active=b;
	}
	
}
