/*READ ME
*  TimedCommand is an Command that works for t times and then determinates itself .
*  
*  Determinate calls stop() and then removes the object from his parent list if existed. 
*  You can override that method which I spelled wrong : deteminate() and make just so that it stop()
*  the command. 
*
*/
package KLD.cmd;

import java.awt.Graphics;

import KLD.obj.GameObject;

public abstract class TimedCommand extends Command {

	private int cd =0; 

	public TimedCommand(String name, int t, GameObject go) {
		super(name, go);
		this.cd = t;
	}
	
	public TimedCommand(String name, int t) {
		this(name,  t, null);
	}

	
	public abstract void action() ;

	public abstract void draw(Graphics g);
	
	
	@Override 
	public void callAction(){
		if(cd<0)
			this.deteminate();
		
		if(enabled && active)
			action();
		
		
		cd--; 
	}
}
