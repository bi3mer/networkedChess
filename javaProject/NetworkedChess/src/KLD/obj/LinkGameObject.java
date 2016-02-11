package KLD.obj;

import java.awt.Image;

import KLD.Animation;
//TODO  create this for your map!  wtf is this?
public class LinkGameObject {

	public LinkGameObject next; 
	public GameObject go ;
	
	public LinkGameObject(GameObject go){
		this.go = go;
	}
	
	
	public boolean hasNext(){
		return next!=null;
	}
	
	public boolean hasObject(){
		return go!=null;
	}
	
}
