package intf;

import java.awt.Graphics;

import KLD.cmd.Command;

public interface CommandFace {
/**
 * 
 * @return <Command> object that represents the classe's action and draw 
 */
	public Command getCommand(); 
	public void addCommand(Command c);
	
	public void removeCommand(int id);
	
	public void createCommandList();

	
	public void callAllAction();
	public void callAllDraw(Graphics g);
	
}
