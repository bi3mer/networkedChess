/* READ ME
 * 
 *  KLD is a class for static objects. 
 * 
 * 
 */

package GameTest;

import java.awt.Color;
import java.awt.Graphics;

import KLD.Console;
import KLD.Input;
import KLD.cmd.Command;
import KLD.cmd.Draw;

/**
 * Holds constants 
 * @author KLD
 */
public class K 
{

	public static Draw drawTest = new Draw( "Blue circle") 
	{
		@Override
		public void draw(Graphics g) {
			g.setColor(Color.BLUE);
			g.fillOval(50, 50, 60, 60);
		}
	};

	public static Draw drawTest2 = new Draw("Red empty circle") 
	{
		@Override
		public void draw(Graphics g) 
		{
			g.setColor(Color.RED);
			g.drawOval(220, 140, 380, 260);
		}
	};

	
	public static Command whatToDo = new Command("List") 
	{
		@Override
		public void action() 
		{
			if( Console.input.keyIsClicked(Input.RIGHT))
				Console.setGame(RightGame.class);
			else if(Console.input.keyIsClicked(Input.DOWN))
				Console.setGame(TestGame.class);
			else if(Console.input.keyIsClicked(Input.UP))
				Console.setGame(AwesomeGame.class);
		}

		@Override
		public void draw(Graphics g) 
		{
			g.drawString("Press the right arrow to go to RightGame ", 50, 50);
			g.drawString("Press the down to go to the first Game ", 40, 70);
			g.drawString("Press the up to go to the Awesome Game ", 60, 90);
		}
	};
	
	
	
}
