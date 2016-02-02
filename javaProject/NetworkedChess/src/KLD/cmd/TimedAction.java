/*READ ME
*  TimedAction is an Action command that works for t times and then determinate .
*  
*  Determinate calls stop() and then removes the object from his parent list if existed. 
*  You can override that method which I spelled wrong : deteminate() and make just so that it stop()
*  the command. 
*
*/
package KLD.cmd;

import KLD.obj.GameObject;

public abstract class TimedAction extends Action {

	
	private int cd =0; 

	public TimedAction(String name, int t, GameObject go) {
		super(name, go);
		this.cd = t;
	}
	
	public TimedAction(String name, int t) {
		this(name,  t, null);
	}
	
	

	public abstract void action();
	
	
	
	@Override 
	public void callAction(){
		if(cd<0)
			this.deteminate();
		
		if(active)
			action();
		
		
		cd--; 
	}

	
	public int getTime(){
		return cd; 
	}
	
	public void setTimer(int cd){
		this.cd  = cd; 
	}
	
	
}
