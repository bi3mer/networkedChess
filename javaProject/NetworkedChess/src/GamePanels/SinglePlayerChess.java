package GamePanels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import KLD.Game;
import KLD.GameFrame;
import KLD.Input;
import model.BoardUI;
import model.ChessBoard;
import model.Marker;
import model.MobilityBoard;
import model.Piece;

public class SinglePlayerChess extends Game 
{
	private ChessBoard board; 
	private MobilityBoard mobilityBoard; 
	private Marker marker;  
	
	
	private int tileWidth;
	private int tileHeight;
	
	private int offsetX;
	private int offsetY;
	
	private int selectedX, selectedY; 
	
	private int waitingTime; 
	
	boolean whiteDead; 
	boolean blackDead; 
	
	String whiteMessage; 
	String blackMessage; 
	
	// 0 false, 7 true 
	/**
	 * Reversing board for player two 
	 */
	private int reverse; 
	
	
	BoardUI boradui; 
	
	
	@Override
	protected void init() 
	{
		name = "SinglePlayerChess"; //not needed 
		whiteMessage = ""; 
		blackMessage = ""; 
		
		board = new ChessBoard(); 
		mobilityBoard= new MobilityBoard(board); 
		marker = new Marker(mobilityBoard); 
		
		whiteDead = false; 
		blackDead = false; 
		
		placePieces(); 
	
		tileWidth = GameFrame.width/10; 
		tileHeight = GameFrame.height/10; 
		
		offsetX = GameFrame.width/2 - tileWidth*4; 
		offsetY = GameFrame.height/2 - tileHeight*4 - 20; 
		
		//reverse = 0; 
		waitingTime = 0; 
		
	
		//this will allow the board to be drawn automatically 
		boradui = new BoardUI(board, mobilityBoard, reverse); 
		boradui.setSizes(offsetX, offsetY, tileWidth, tileHeight);
		revereBoard();
		
		this.addDraw(boradui);
	
	}
	
	@Override
	protected void draw(Graphics g) 
	{
		
		
		g.setColor(Color.black);
		
		//Threat threat = new Threat(marker); 
		
		String s = (whiteDead)? "White team is under threat"  : "White safe"; 
		
		g.drawString(s, 20, 20);
		
		String b = (blackDead)? "Black team is under threat"  : "Black safe"; 
		
		g.drawString(b, 20, 40);
		
		g.drawString(whiteMessage, 200, 20);
		g.drawString(blackMessage, 200, 40);

		
		
	}//end color 
	
	@Override
	protected void update() 
	{
	
	//check for check 
	
	//check for checkmate
	
	
	
	//can request undo 
	//TODO
	
	//can forfeit 
	//TODO
	
	//player not allowed further interaction  
	
	//when click within board 
		unitClicking(); 
		
	}
	
	
	public boolean recieveMovement(int fromX, int fromY, int toX, int toY)
	{
		
		board.movePiece(fromX, fromY, toX, toY);
		return true; 
	}
	
	public void revereBoard()
	{
		reverse = 7; 
		boradui.setReverse(reverse);
		//
	
	}
	
	public void placePieces()
	{
		int[] pieces = {Piece.TYPE_PAWN, Piece.TYPE_ROOK, Piece.TYPE_KNIGHT, Piece.TYPE_BISHOP, Piece.TYPE_QUEEN, Piece.TYPE_KING }; 
			
		//int[] pieces = {0, Piece.TYPE_ROOK, 0,0, 0, Piece.TYPE_KING }; 
				
	
		for(int i=0; i<16; i++)
		{
			int piece = pieces[(i/8)*(  (i-7)+((i/13)*(3-((2*i)%13)))  )%6]; 
			
			//positive bottom
			board.setTileValue(i%8, 1-i/8, piece); 
		
			//negative top 
			board.setTileValue(i%8, 6+i/8, -piece); 	
		}
		
		//board.setTileValue(4, 4, Piece.TYPE_PAWN);
		
		//board.setTileValue(4, 2, -Piece.TYPE_PAWN);
		
		
		
	
	}//end place 
	
	public void unitClicking()
	{
		if(input.mouseIsClicked(new Rectangle(offsetX, offsetY, offsetX+tileWidth*7, offsetY+tileHeight*7)) )
		{
			//System.out.println(board);
			//System.out.println("in");
				//get co-ordenate 
			int x = ((Input.point.x - offsetX) / tileWidth); 
			int y = ((Input.point.y - offsetY) / tileHeight); 
			
			//System.out.printf("clicked %d %d\n", x, y);
			
			y = Math.abs(reverse-y); 
			
			if(mobilityBoard.getTileValue(x, y) > MobilityBoard.MARK_INVISIBLE)
			{
					//System.out.printf("movin %d %d to %d %d\n", selectedX, selectedY, x, y);
				//rook or king moved notifications 
				int notification =	board.movePiece(selectedX, selectedY, x, y);
					
			
				//notify mobility to adjust castling 
				if (notification > 0)
				{
					notification--; 
					mobilityBoard.setMoved(notification); 
				
				}//end if notification 
				
				
				//marker.calebrate(1); 
				//whiteDead = threat.isUnderTHreat(); 
				//marker.calebrate(-1); 
				//blackDead = threat.isUnderTHreat(); 
				
				//calibrate the other team 
				int team = board.teamAt(x, y); 
				
				marker.calebrate(1);
				
				//update check and check mate 
				if((whiteDead = marker.isThreatened()))
				{
					if(marker.isCheckMate(1))
						blackMessage = "Checkmate!";
					else
						blackMessage = "Check.."; 
				}
				else
				{
					blackMessage = ""; 
				}
				
				marker.calebrate(-1);
				
				if((blackDead = marker.isThreatened()))
				{
					if(marker.isCheckMate(-1))
						whiteMessage = "Checkmate!";
					else 
						whiteMessage = "Check.."; 
				}
				else
				{
					whiteMessage = ""; 
				}
				
				
				
				if(marker.didLose(team))
				{
					if(team==1)
					{
						whiteMessage = "Lost!"; 
						blackMessage = "Won!";
					}	
					else
					{
						whiteMessage = "Won!"; 
						blackMessage = "Lost!";
					}
				}
				
					
				//
					
				mobilityBoard.reset();	
			}//end mobility 
			else
			{
				mobilityBoard.reset();	
				
				
					int team = board.teamAt(x, y); 
					
					if((team==1 && whiteDead) || (team==-1 && blackDead))
					{
						//threat.calebrate(team); //extra cuz in this mode both teams are the same 
						//threat.markPieceAt(x, y); 
					}
					else 
					{
						//marker.markPieceAt(x, y); 
					}
					
					/*if(team == 1)
						marker.calebrate(1); 
					else
						marker.calebrate(-1); 
					*/
					int marks = marker.markPieceAt(x, y); 
					
					//System.out.println("marked " + marks + " at " + x +"-"+ y);
					//
						
					}
			
		
				selectedX = x; 
				selectedY = y; 
						
			}else if(input.mouseIsClicked())
			{
				mobilityBoard.reset();	
			}
			
	}//end click 
	
	


}//end class

