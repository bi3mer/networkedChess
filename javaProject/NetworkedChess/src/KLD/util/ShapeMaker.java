package KLD.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;

import KLD.Console;
import KLD.Game;
import KLD.GameFrame;
import KLD.Input;
import KLD.cmd.ButtonCommand;
import KLD.cmd.Command;

public class ShapeMaker extends Game {

	GeneralPath gp ;
	
	boolean showMouseLine = false; 
	
	ButtonCommand newButton; 
	ButtonCommand saveButton; 
	ButtonCommand undoButton; 
	ButtonCommand lineButton; 
	ButtonCommand curvButton; 
	ButtonCommand rectButton; 
	ButtonCommand ovalButton; 
	
	
	@Override
	public void init() {
		name = "ShapeMaker";
		GameFrame.setTitle("ShapeMaker");
		
		gp = new GeneralPath();
		
		//this is the command bar menu for new, save, and stuff
		Command menu = new Command("Menu") {

			@Override
			public void init(){
			
				
			}//end init menu command
			
			
			
			@Override
			public void draw(Graphics g) {
				
				g.setColor(Color.BLACK);
				g.drawRect(0, 0, Console.maxWidth, 16);
				g.setColor(Color.GRAY.brighter());
				g.fillRect(0, 0, Console.maxWidth, 16);
				
				
				     
			}
			
			@Override
			public void action() {
				if(Input.point.y >16){
					if(input.mouseIsClicked()){
						if(Input.point.x >=2 && Input.point.x <=18)
							gp = new GeneralPath();
						else if(input.mouseIsClicked(new Rectangle(42, 2, 36, 16))){
							//save the 
						}
						else if(input.mouseIsClicked(new Rectangle(82, 2, 36, 16))){
							//undo the last step
						}
					
				}
				}//end big if y<16
				
				
				
			}
		};
		
		addCommand(menu);
		
		
		newButton = new ButtonCommand("New",0,0) {
			@Override
			public void init(){
				setButtonColor(Color.WHITE);
				setNextToAnother = true; 
				
			}
			
			@Override
			public void action() {
				System.out.println("YO MAMA");
			}
		};
		
		saveButton = new ButtonCommand("Save",20, 0) {
			@Override
			public void init(){
				setButtonColor(Color.WHITE);
				setNextToAnother = true; 
			}
			
			@Override
			public void action() {
				System.out.println("YO OTHA MAMA");
			}
		};
		
		undoButton = new ButtonCommand("Undo",20, 0) {
			@Override
			public void init(){
				setButtonColor(Color.WHITE);
				setNextToAnother = true; 
			}
			
			@Override
			public void action() {
				System.out.println("UNDO YO MAMA");
			}
		};
		
		curvButton  = new ButtonCommand("Curve",20, 0) {
			
		
			
			@Override
			public void init(){
				setButtonColor(Color.WHITE);
				setNextToAnother = true; 
				isFocused = false; //TODO
			}
			
			@Override
			public void action() {
				System.out.println("CURVE YO MAMA");
			}
			
			@Override
			public void draw(Graphics g) {
				super.draw(g);
				if(isFocused){
					setFade(g, .2f);
					g.setColor(Color.black);
					g.fillRect(x, y, width, height);
					setFade(g, 1f);
				}
			}
		};
		lineButton  = new ButtonCommand("Line",20, 0) {
			
			@Override
			public void init(){
				setButtonColor(Color.WHITE);
				setNextToAnother = true; 
				isFocused = true;//TODO 
			}
			
			@Override
			public void action() {
				System.out.println("CURVE YO MAMA");
			}
			
			@Override
			public void draw(Graphics g) {
				super.draw(g);
				if(isFocused){
					setFade(g, .2f);
					g.setColor(Color.black);
					g.fillRect(x, y, width, height);
					setFade(g, 1f);
				}
			}
		};
		
		rectButton  = new ButtonCommand("Rect",20, 0) {
			public boolean isChoosen = false; 
			@Override
			public void init(){
				setButtonColor(Color.WHITE);
				setNextToAnother = true; 
			}
			
			@Override
			public void action() {
				System.out.println("CURVE YO MAMA");
			}
			
			@Override
			public void draw(Graphics g) {
				super.draw(g);
				if(isChoosen){
					setFade(g, .2f);
					g.setColor(Color.black);
					g.fillRect(x, y, width, height);
					setFade(g, 1f);
				}
			}
		};
		
		ovalButton  = new ButtonCommand("Oval",20, 0) {
			 
			@Override
			public void init(){
				setButtonColor(Color.WHITE);
				setNextToAnother = true; 
			}
			
			@Override
			public void action() {
				System.out.println("CURVE YO MAMA");
			}
			
			@Override
			public void draw(Graphics g) {
				super.draw(g);
				if(isFocused){
					setFade(g, .2f);
					g.setColor(Color.black);
					g.fillRect(x, y, width, height);
					setFade(g, 1f);
				}
			}
		};
		
		
		
		addCommand(newButton);
		addCommand(saveButton);
		addCommand(undoButton);
		addCommand(lineButton);
		addCommand(curvButton);
		addCommand(rectButton);
		addCommand(ovalButton);
		
		
	}

	@Override
	public void draw(Graphics g) {
	Graphics2D g2 = (Graphics2D)g;
	
	if(showMouseLine && gp.getCurrentPoint() != null){
		
		int x = (int)gp.getCurrentPoint().getX();
		int y = (int)gp.getCurrentPoint().getY();
		int px = Input.point.x; 
		int py = Input.point.y;
		if(lineButton.isFocused)
		g2.drawLine(x, y, px, py);
		else if(curvButton.isFocused){//TODO
			Point p = new Point((int)gp.getCurrentPoint().getX(), (int)gp.getCurrentPoint().getY());
			curveFromTopTo(g2, p,  Input.point, 180);
			//g2.drawArc(x- Math.abs(x-px), y - Math.abs(y-py)/2, Math.abs(x-px), Math.abs(y-py), 0, 90);
			//g2.drawRect(x, y, Math.abs(x-px), Math.abs(y-py));
			
		}
	
	
	}
	
	g2.draw(gp);
	
	
	}

	@Override
	public void update() {
		if(input.keyIsClicked(Input.S))
			showMouseLine  =  !showMouseLine;
		
		if(input.keyIsClicked(Input.SPACE))
			gp.closePath();
		
		
		if(Input.point.y > 20){
			
			
		 if(input.mouseIsClicked()){
			 if(gp.getCurrentPoint()==null)
				 gp.moveTo(Input.point.x, Input.point.y);
			  else 
				 gp.lineTo(Input.point.x, Input.point.y);
			 }
		if(input.keyIsClicked(Input.SHIFT)){
			//remove the last step
		}
			 
		}
		
	}//end big if
	
	
	private void curveFromTopTo(Graphics2D g, Point start, Point end, double arcAngel){
		//curveTo( g,  start,  end,  arcAngel/2, arcAngel/50);	
		
		
		
		
		double dy = end.y-start.y; 
		double dx = end.x-start.x; 
		double theta = Math.atan(Math.abs(dy/dx));
		theta *= (180/Math.PI);
		double base = Math.sqrt((dy*dy)+(dx*dx));
		double a = ((base*Math.PI));
		
		//if(dy>0.000000)
		//	theta = -theta;
		
		g.drawArc((int) (start.x), (int)(start.y- dy/2) , (int)(dx), (int)(dy),180, (int)(180-theta));
		g.drawRect(start.x, start.y, (int)dx, (int)dy);
		
		//g.drawArc(x, y, width, height, startAngle, arcAngle)
		
	}
	
	
	private void curveTo(Graphics2D g, Point start, Point end, double arcAngel, double dec){
		double dy = end.y-start.y; 
		double dx = end.x-start.x; 
		double theta = Math.atan(Math.abs(dy/dx));
		
		double base = Math.sqrt((dy*dy)+(dx*dx));
		
		
		double a = ((base*Math.PI)/50);
		
		if(arcAngel<0.0000000){
			return;
			//g.drawLine(start.x, start.y, end.x, end.y);
		}
		else {
			Point nxt = new Point();
			if(dy>0.000000)
				theta = -theta;
			nxt.setLocation(start.x+a*Math.sin(arcAngel+theta),start.y+a*Math.cos(arcAngel+theta));
			
			g.drawLine(start.x, start.y, nxt.x, nxt.y);
			
			curveTo(g, nxt, end, arcAngel-dec, dec);
		}
	}

}//end class
