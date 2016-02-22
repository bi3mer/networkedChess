package model;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import GamePanels.MultiplayerChessGame;
import intf.MovementTracker;

public class ChessPlayerController
{
	
	private int playerTeam; 
	
	private String host = "localhost";
	private int port = 5000; 
	
	private ServerSocket server; 
	private Socket suckit; 
	
	private MultiplayerChessGame board; 
	
	private boolean isConnecting; 
	
	Thread networkThread; 
	
	public ChessPlayerController(MultiplayerChessGame board)
	{
		this.board = board; 
		this.host = "localhost"; //TODO 
		this.port = 5000; 
		isConnecting = false; 
		
	}
	
	public void connect()
	{
		if(isConnecting)
		return; 
		
		if(networkThread == null)
		{
			networkThread = new Thread(new Runnable() {
				
				@Override
				public void run() 
				{
					isConnecting = true; 
					
					try {
						
						suckit = new Socket(host, port);
						
						System.out.println("Found a hosting server");
						
						playerTeam = -1; 
						
					} catch (IOException e) 
					{
						
						try {
				
							System.out.println("Establishing server");
							playerTeam = 1; 
							board.revereBoard(); 
							server = new ServerSocket(port); 
							
							suckit = server.accept(); 
							
							System.out.println("found connection");
							
						} catch (IOException e1) {
						
							e1.printStackTrace();
						} 
						
						
					} 
					try
					{
						play(); 
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}//end run 
			}); 
			
			networkThread.start();
		}
		
		
		
		
	}//end connect 
	
	public void play() throws Exception
	{
		
		final PrintStream out = new PrintStream(suckit.getOutputStream());
	
	
		board.setMovementTraker(new MovementTracker() {
			
			
			@Override
			public void sendMovment(int fromX, int fromY, int toX, int toY) 
			{
				
					System.out.printf("sending %d%d%d%d\n", fromX,fromY,toX,toY);
					out.printf("%d%d%d%d\n", fromX,fromY,toX,toY);	
			
			}
		});
		
		
		if(playerTeam == 1)
		{
			board.markRead(); 
		}
		
		
		try {
			
			Scanner in = new Scanner(suckit.getInputStream());
			
			while(true)//insert game end condition 
			{
				System.out.println("Waiting for movement");
				
				while(!in.hasNext())
				{
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
			
				String input = in.nextLine(); 
				
				System.out.println("Recieveed Movement: "+ input);
			
				int fromX = Integer.parseInt(""+input.charAt(0)); 
				int fromY = Integer.parseInt(""+input.charAt(1));
				int toX = Integer.parseInt(""+input.charAt(2));
				int toY = Integer.parseInt(""+input.charAt(3));
			
				board.recieveMovement(fromX, fromY, toX, toY); 
				
				board.markRead(); 
			
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
		
		
	}
	
	public int getTeam()
	{
		return playerTeam; 
	}
	
}
