package KLD.cmd;

import java.awt.Graphics;

public class DrawList extends KLDList {

	public DrawList(String name) {
		super(name);
	}

	public void callAllDraw(Graphics g){
		LinkObject current = first; 
		current = first;     //start at first
		while((current != null)){ //keep going until you reach null 
			Draw d = (Draw) current; 
			d.callDraw(g);    // call the draw 
			current = current.next;  // go to the next one
		}
		
		
	}

	
}
