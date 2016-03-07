package KLD.obj;

import java.awt.Graphics;

public class IdObject {
	
	/**
	 * Gives unique number to each object of it's instance starting from 0   
	 */
	
	private static int counter =0 ;
	/**
	 * A unique generated id
	 */
	public final int id  =  counter++; ; //add one after generating an id 
	/**
	 * name of object
	 */
	public String name = "defultName"; 
	
	/**
	 * name of the object it set to "defultname" followed by the id 
	 */
	public IdObject(){ 
		 name += ""+id;//generate an id automatically 
	}
	
	
	
	public IdObject(String s){
		this();
		name = s+id; 
	}



	
	
	
	
}
