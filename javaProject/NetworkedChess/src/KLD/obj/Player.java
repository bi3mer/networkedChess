package KLD.obj;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;

import KLD.Animation;
import KLD.Console;
import KLD.Input;

public class Player extends GameObject{
	
	public boolean isJumping = false; 
	public boolean isFalling = false;
	
	protected Input input = Console.input; 
	
	private Player(){
		super();
		x=0;
		y=0;
	}
	public Player(int x, int y, int w, int h){
		super(w,h);
		this.x = x; 
		this.y = y; 
		
	}
	public Player(int x, int y,Image im){
		super(im);
		this.x = x; 
		this.y = y; 
	}
	public Player(int x, int y,Animation ani){
		super(ani);
		this.x = x; 
		this.y = y; 
	}
	

	
	
	// ##### GET.TERS #####
	
	public Image getAnimationImage(){

		return animation.getNextFrame();
	}
	
	public Ellipse2D.Double getEllipse2DDouble(){
		return new Ellipse2D.Double(x, y, width, height);
	}
	
	
	
	// ##### applying action ######
	
	public void applyMovingLeft(int keyCode, int delta, int limit){
		if(input.keyIsDown(keyCode) && x > limit){
			if(x - delta > limit)
				x -= delta;
			else 
				x = limit;		
		}
	}
	public void applyMovingLeft(int keyCode, int delta){
		applyMovingLeft(keyCode, delta ,Integer.MIN_VALUE);
	}
	public void applyMovingLeft(int keyCode){
		applyMovingLeft(keyCode, 1);
	}
	
 	public void applyMovingRight(int keyCode, int delta, int limit){
		
 		if(input.keyIsDown(keyCode) && x < limit){
			if(x + delta < limit)
				x += delta;
			else 
				x = limit;	
		}
	}
	public void applyMovingRight(int keyCode, int delta){
		applyMovingRight(keyCode, delta,  Integer.MAX_VALUE);
	}
	
	public void applyMovingRight(int keyCode) {
		applyMovingRight(keyCode, 1);
	}
	public void applyMovingUp(int keyCode, int delta, int limit){
		if(input.keyIsDown(keyCode) && y > limit){
			if(y - delta > limit)
				y -= delta; 
			else
				y = limit; 
		}
	}
	public void applyMovingUp(int keyCode, int delta){
		applyMovingUp(keyCode, delta, Integer.MIN_VALUE);
	}
	
	public void applyMovingDown(int keyCode, int delta, int limit){
		if(input.keyIsDown(keyCode) && y < limit){
			if(y + delta < limit)
				y += delta; 
			else
				y = limit;  
		}
	}
	public void applyMovingDown(int keyCode, int delta){
		applyMovingDown(keyCode, delta, Integer.MAX_VALUE);
	}
	
	
	public void applyArrowKeys(int upSpeed, int rightSpeed, int downSpeed, int leftSpeed){	
		applyMovingUp(KeyEvent.VK_UP, upSpeed);
		applyMovingRight(KeyEvent.VK_RIGHT, rightSpeed);
		applyMovingDown(KeyEvent.VK_DOWN, downSpeed);
		applyMovingLeft(KeyEvent.VK_LEFT, leftSpeed);
	}
	public void applyArrowKeys(int speed){	
		applyArrowKeys(speed,speed,speed,speed);
	}
	public void applyArrowKeys(){		
		applyArrowKeys(1,1,1,1);
	}
	
	
	public void applyWASD(int upSpeed, int rightSpeed, int downSpeed, int leftSpeed){	
		applyMovingUp(KeyEvent.VK_W, upSpeed);
		applyMovingRight(KeyEvent.VK_D, rightSpeed);
		applyMovingDown(KeyEvent.VK_S, downSpeed);
		applyMovingLeft(KeyEvent.VK_A, leftSpeed);
	}
	public void applyWASD(int speed){
		applyWASD(speed, speed,speed,speed);
	}
	
	public void applyWASD(){
		applyWASD(1,1,1,1);
	}
	
	public void applyJumping(int delta, int limit){

		if(isJumping){
			if(y < limit)
				if( y - delta > limit)
					y -= delta;	
				
				else{
					y = limit; 
					isJumping = false; 
				}
	
		}
	}//end applyJ
	
	public void applyFalling(int delta, int limit){
		
		if(isFalling){
			if(y > limit)
				if( y + delta < limit)
					y += delta;	
				
				else{
					y = limit; 
					isFalling = false; 
				}
	
		}
	}//end applyJ
	
	public void applyJumpingAndFalling(int keyCode,int delta, int max, int min){
       if(input.keyIsClicked(keyCode) && !isJumping && !isFalling)
			isJumping = true;
		
		if(isJumping){
			if( y - delta > max){
				y -= delta;
			}
			else {
				y = max;
				isJumping = false; 
				isFalling = true; 
			}
		}//end jump
		
		if(isFalling){
			if(y+delta < min )
				y += delta;
			else{
				y = min;
				isFalling = false; 
			}
		}//end falling

	}

	public void applyGravityRight(int delta, int limit){
		if(x < limit){
			if(x+delta < limit)
				x+=delta;
			else
				x = limit;
				
		}
	}
	public void applyGravityRight(int delta){
		applyGravityRight(delta, Integer.MAX_VALUE);
	}
	public void applyGravityLeft(int delta, int limit){
		if(x > limit){
			if(x-delta > limit)
				x-=delta;
			else
				x = limit;	
		}
	}
	public void applyGravityLeft(int delta){
		applyGravityLeft(delta, Integer.MIN_VALUE);
	}
	
	public void applyGravityDown(int delta, int limit){
		if(y < limit){
			if(y+delta < limit)
				y+=delta;
			else
				y = limit;
		}
	}
	public void applyGravityDown(int delta){
		applyGravityDown( delta,  Integer.MAX_VALUE);
	}
	public void applyGravityUp(int delta, int limit){
		if(y > limit){
			if(y-delta > limit)
				y-=delta;
			else
				y = limit;	
		}
	}
	public void applyGravityUp(int delta){
		applyGravityUp(delta, Integer.MIN_VALUE);
	}
	
	public void applyJumpAndMove(int keyCode, int deltaJump, int deltaMove, int max, int min){
		if(input.keyIsClicked(keyCode) && !isJumping && !isFalling)
			isJumping = true;
		
		if(isJumping){
			x += deltaMove;
			if( y - deltaJump > max)
				y -= deltaJump; 
			else {
				y = max;
				isJumping = false; 
				isFalling = true; 
			}
		}//end jump
		
		if(isFalling){
			x += deltaMove;
			if(y+deltaJump < min )
				y += deltaJump;
			else{
				y = min;
				isFalling = false; 
			}
		}//end falling
	}
	
	
	
	
	
	
	
	
	
}//end Player class
