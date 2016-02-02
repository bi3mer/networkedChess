package GameTest;

import java.awt.Graphics;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import KLD.Animation;
import KLD.Console;
import KLD.Game;
import KLD.GameFrame;
import KLD.Input;
import KLD.obj.GameObject;
import KLD.obj.Player;


public class BaderGame extends Game{	
	
	Animation ani; 
	Animation khaled; 
	GameObject go ; 
	GameObject gu ; 
	protected void init() {
		Console.setFPS(60);
		 l.setExtention(".png");
		 l.setFolder("res/ball/ball_");
		ani =  l.loadAnimation(3,"1,2,3,4,5,6,7,8".split(","));
		khaled = ani.clone(); 
		
		go = new GameObject(ani);
		go.setXY(250, 150);
		gu = new GameObject(ani.clone());
		gu.setXY(150, 250);
	}
	protected void draw(Graphics g) {
		
		g.drawString("isLooping " + ani.isLooping(), 20, 20);
		g.drawString("isReversded " + ani.isReversed(), 20, 50);
		
		//g.drawImage(ani.getNextFrame(),150,200,null);
		
		//g.drawImage(khaled.getNextFrame(),200,150,null);
		go.drawMe(g, camera);
		gu.drawMe(g,camera);
		
	}
	
	protected void update() {
		if(input.keyIsClicked(Input.SPACE)){
			ani.setReversed(!ani.isReversed());
		}
		
		if(input.keyIsDown(Input.RIGHT)){
			camera.x++;
		}
		
		if(input.keyIsDown(Input.LEFT)){
			camera.x--;
		}
		
		if(input.keyIsDown(Input.UP)){
			camera.y--;
		}
		
		if(input.keyIsDown(Input.DOWN)){
			camera.y++;
		}
		
	}
	
	


}
