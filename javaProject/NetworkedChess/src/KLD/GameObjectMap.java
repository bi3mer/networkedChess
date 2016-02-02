package KLD;

import java.awt.Graphics;

import KLD.obj.GameObject;
import KLD.obj.LinkGameObject;
/**
 * under construction! 
 * @author KLD
 *
 */
public class GameObjectMap {

	/**
	 * holds all game object is the map 
	 */
	LinkGameObject[][] map; 
	
	/**
	 * length of objects 
	 */
	int length;
	
	/**
	 * cell's width 
	 */
	int width;
	/**
	 * cell's height 
	 */
	int height;
	
	int maxWidth;
	
	int maxHeight;
	
	
	
	public void add(GameObject go){
		int x = go.x(); 
		int y = go.y();
		
		LinkGameObject p = map[y][x];
		while(p!=null){
			p = p.next; 
		}
		p.next = new LinkGameObject(go);
	}
	
	public void remove(GameObject go){
		int x = go.x(), y = go.y();
		LinkGameObject p = map[y][x];
		LinkGameObject f = map[y][x];
		
		if(p==null)
			return; 
		else
			p=p.next;
		while(p!=null){
			
			if(p.go.id == go.id){
				//first object 
				if(f==p){
					map[y][x] = map[y][x].next;
				return;
				}
				if(p.hasNext())
					
					
				return; 
			}
			p = p.next;
			f = f.next;
			
		}//end while
	}
	
	
	public void update(){
		for(int i=0; i<map.length;i++){
			for(int j=0;i< map[i].length;j++){
				
				
				
			}//j
			
		}//i 
	}
	
	
	public void DrawGuildLine(Graphics g){
		for(int i=0; i<length;i++){
			
			g.drawLine(0, i*height,maxWidth,0);
			
			g.drawLine(i*width, 0,0,maxHeight);
			
		}//i 
	}
	
}
