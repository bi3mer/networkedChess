//Get out of here!
package KLD;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;

import GamePanels.Launcher;

public class GameFrame {

	public static JFrame frame = new JFrame();
	public static int width = 500; 
	public static int height = 500; 
	public static int FPS = 20; 
	public static Class mainGame; 
	
	private static Console conole;
	
	/**
	 * Launches game calling launcher 
	 * @throws Exception
	 */
	public static void launch() throws Exception 
	{
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) 
			{
				width = frame.getWidth();  
				height = frame.getHeight(); 
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		new Launcher(); 
		
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		
		conole = new Console(mainGame,FPS);
		
		frame.add(conole);
		conole.run();
		
		frame.setVisible(true);
		
		
		
	}

	
	public static void setSize(int w, int h)
	{
		frame.setSize(w, h);
		height = h; 
		width = w ; 
		frame.pack();
		Console.maxWidth = frame.getContentPane().getWidth();
		Console.maxHeight = frame.getContentPane().getHeight();
	}
	
	public static void setResizable(boolean b){
		frame.setResizable(b);
	}
	
	
	public static void setTitle(String s){
		frame.setTitle(s);
	}
	
	public static void destroy()
	{
		if(frame==null)
			return; 
		frame.setVisible(false); //you can't see me!
		frame.dispose();
	}
	
}
