package KLD.cmd;

import java.awt.Graphics;

public class CommandList extends KLDList {

	public CommandList(String name){
		super(name);
	}
	
	//calls all behaviors' calls (LoL)
	public void callAllAction(){
		LinkObject current = first; 

		while(current != null){ //keep going until you reach null 
			Command c = (Command) current; 
			c.callAction();
			
			current = current.next;  // go to the next one
			
		}
		}
		
		
	
	
	public void callAllDraw(Graphics g){
		LinkObject current = first; 
		current = first;     //start at first
		while((current != null)){ //keep going until you reach null 
			Command c = (Command) current; 
			c.callDraw(g);    // call draw duh 
			current = current.next;  // go to the next one
		}
		
		
	}
}
