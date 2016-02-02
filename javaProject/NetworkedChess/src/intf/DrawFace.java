package intf;

import java.awt.Graphics;

import KLD.cmd.Draw;

public interface DrawFace {

	public Draw getDraw(); 
	
	public void addDraw(Draw d);
	
	public void removeDraw(int id);
	
	public void createDrawList();
	
	
	public void callAllDraw(Graphics g); 
	
	
}
