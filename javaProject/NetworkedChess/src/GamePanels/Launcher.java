/* KLD Game Library  1.0
 * 
 * Luncher gets called in the main method before Console is created. 
 * 
 * Luncher is meant to set the starting (or default) game.
 * 
 * Here you could set the FPS, width and height of the GameFrame. 
 * 
 */

package GamePanels;

import GameTest.*;
import KLD.*;
import KLD.obj.GameObject;
import KLD.util.ShapeMaker;

 
/**
 * Launches a game into a the console (basically main method) 
 */
public class Launcher 
{
	public Launcher()
	{
		//setting the main panel. You could also use GameMenuPanel.setGame(class).
		GameFrame.mainGame = ChessGame.class; 
		
		//TODO read json: config
		
		
		GameFrame.width = 800; 
		GameFrame.height = 800;
		//frame size is set by default 500x500 
		//adding a title to the game frame. 
		GameFrame.setTitle("default lame title!!");
		GameFrame.setResizable(true);
		GameFrame.FPS = 10; 
		
	}
}