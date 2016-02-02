package KLD.cmd;

import KLD.obj.IdObject;

public abstract class LinkObject extends IdObject {

	public LinkObject next;
	public LinkObject previous;

	protected KLDList owner; 
	
	public LinkObject(String s){
		super(s); 
	
	}
	
	
	
	public abstract void stop();
	
	public void deteminate(){
		stop(); 
		if(owner!=null)
			owner.removeLinkObject(id);
	}
	
	
	
}
