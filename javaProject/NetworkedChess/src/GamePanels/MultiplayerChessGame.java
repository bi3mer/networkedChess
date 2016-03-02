package GamePanels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import KLD.Game;
import KLD.GameFrame;
import KLD.Input;
import intf.MovementTracker;
import model.BoardUI;
import model.ChessBoard;
import model.ChessPlayerController;
import model.MobilityBoard;
import model.Piece;

public class MultiplayerChessGame extends Game
{
	private ChessBoard board; 
	private MobilityBoard mobilityBoard; 
	
	private int tileWidth;
	private int tileHeight;
	
	private int offsetX;
	private int offsetY;
	
	private int selectedX, selectedY; 
	
	private boolean turnReady; 
	
	private int waitingTime; 
	
	// 0 false, 7 true 
	/**
	 * Reversing board for player two 
	 */
	private int reverse; 
	
	
	private MovementTracker traker; 
	
	ChessPlayerController player; 
	
	BoardUI boradui; 
	
	@Override
	protected void init() 
	{
		player = new ChessPlayerController(this); 
		
		
		board = new ChessBoard(); 
		mobilityBoard= new MobilityBoard(board); 
			
		//int[] pieces = {Piece.TYPE_PAWN, Piece.TYPE_ROOK, 
				//Piece.TYPE_KNIGHT, Piece.TYPE_BISHOP, Piece.TYPE_QUEEN, Piece.TYPE_KING }; 
				
		int[] pieces = {0, Piece.TYPE_ROOK, 
				Piece.TYPE_KNIGHT, Piece.TYPE_BISHOP, Piece.TYPE_QUEEN, Piece.TYPE_KING }; 
					
		
		for(int i=0; i<16; i++)
		{
			int piece = pieces[(i/8)*(  (i-7)+((i/13)*(3-((2*i)%13)))  )%6]; 
			
			//positive bottom
			board.setTileValue(i%8, 1-i/8, piece); 
		
			//negative top 
			board.setTileValue(i%8, 6+i/8, -piece); 	
		}
		
		System.out.println(board);
		
		tileWidth = GameFrame.width/10; 
		tileHeight = GameFrame.height/10; 
		
		offsetX = GameFrame.width/2 - tileWidth*4; 
		offsetY = GameFrame.height/2 - tileHeight*4 - 20; 
		
		turnReady = false; 
		waitingTime = 0; 
		reverse = player.getTeam(); 
		//System.out.println(board);
		
		
		//this will allow the board to be drawn automatically 
		boradui = new BoardUI(board, mobilityBoard, reverse); 
		boradui.setSizes(offsetX, offsetY, tileWidth, tileHeight); 
		this.addDraw(boradui);

	}

	@Override
	protected void draw(Graphics g) 
	{
		//g.drawRect(50, 59, 100, 100);
		
		
		/*Color[] tileColors = {Color.WHITE, Color.BLACK};
			
		//board background
		for(int i=0; i<board.getHeight(); i++)
		{
			for(int j=0; j<board.getWidth(); j++)
			{
				int y = Math.abs(reverse-i); 
				
				g.setColor(tileColors[(j%2+i%2)%2]); 
				g.fillRect(offsetX+j*tileWidth, offsetY+y*tileHeight, tileWidth, tileHeight);
			  //draw tiles 
			}
		}
		
		//drawing mobility 
		Color[] mobilityColor ={Color.CYAN, Color.RED};
		for(int i=0; i<board.getHeight(); i++)
		{
			for(int j=0; j<board.getWidth(); j++)
			{
				int y = Math.abs(reverse-i); 
				
				if(mobilityBoard.getTileValue(j, i) != MobilityBoard.MARK_INVISIBLE)
				{
					
					Game.setFade(g, 0.8f);
					
					g.setColor(mobilityColor[Math.abs(mobilityBoard.getTileValue(j, i))-1]);
					g.fillRect(offsetX+j*tileWidth, offsetY+y*tileHeight, tileWidth, tileHeight);
					 
					Game.setFade(g, 1f);
					
				}
			}
		}
		
		
			//draw pieces 
		for(int i=0; i<board.getHeight(); i++)
		{
			for(int j=0; j<board.getWidth(); j++)
			{
				int y = Math.abs(reverse-i); 
				
				
			  int pieceIndex = 2*Math.abs(board.getTileValue(j, i)) - (board.getTileValue(j, i) > 0? 0 : 1); 
			  //draw tiles 
			 
			  
			  if(pieceIndex > 0)
			  {
				  g.drawImage(PieceImageFactory.Instnece().factor(pieceIndex), offsetX+j*tileWidth, offsetY+y*tileHeight,tileWidth, tileHeight, null); 
				  //g.setColor(Color.red);
				  //g.drawString(""+pieceIndex, offsetX+j*tileWidth, offsetY+y*tileHeight);
			  }
			}
		}
		*/
		if(!turnReady)
		{
			Game.setFade(g, .7f);
			
			g.setColor(Color.black);
			
			g.fillRect(0, 0, GameFrame.width, GameFrame.height);
			
			String build = ""; 
			
			for(int i=0; i<waitingTime%3; i++)
				build+="."; 
			
			g.drawString("waiting for other player" + build, GameFrame.width/2-50, offsetY/2);
			
			Game.setFade(g, 1f);
		}else
		{
			g.setColor(Color.black);
			g.drawString("You turn dumbdumb", GameFrame.width/2-50, offsetY/2);
		}
		
		
		
	}//end color 

	@Override
	protected void update() 
	{
		//keeps player connected 
		player.connect();
		
		//check for check 
		
		//check for checkmate
		
		
		
		//can request undo 
		//TODO
		
		//can forfeit 
		//TODO
	
		//player not allowed further interaction  
		if(!turnReady)
			return; 

		//when click within board 
		unitClicking(); 
		
		
	
		
	}
	
	public void markRead()
	{
		turnReady = true; 
	}
	
	public boolean recieveMovement(int fromX, int fromY, int toX, int toY)
	{
		
		board.movePiece(fromX, fromY, toX, toY);
		return true; 
	}
	
	public void setMovementTraker(MovementTracker mt)
	{
		traker = mt; 
	}
	
	public void revereBoard()
	{
		reverse = 7; 
		boradui.setReverse(reverse);
		System.out.println("reserve");
		
	}
	
	public void unitClicking()
	{
		if(input.mouseIsClicked(new Rectangle(offsetX, offsetY, offsetX+tileWidth*7, offsetY+tileHeight*7)) )
		{
			//get co-ordenate 
			int x = ((Input.point.x - offsetX) /tileWidth); 
			int y = ((Input.point.y - offsetY) /tileHeight); 
			
			System.out.printf("clicked %d %d\n", x, y);
			
			y = Math.abs(reverse-y); 
			
			if(mobilityBoard.getTileValue(x, y) > MobilityBoard.MARK_INVISIBLE)
			{
					//System.out.printf("movin %d %d to %d %d\n", selectedX, selectedY, x, y);
					
					int piece = Math.abs(board.getTileValue(selectedX, selectedY)) ; 
					
					if(piece == Piece.TYPE_KING ||piece == Piece.TYPE_ROOK)
					{
						//System.out.println("Moving a king or rook");
						//int tag = 0; 
						if(piece == Piece.TYPE_ROOK && (selectedY == 0 || selectedY == 7))
						{
							//System.out.println("Moving rook");
							if(selectedX == 0)
							{
								mobilityBoard.markRookMoved(board.teamAt(selectedX, selectedY),MobilityBoard.TAG_LEFT); 
								//System.out.println("Left Rook Moved!");
							}
							else if(selectedX == 7)
							{
								mobilityBoard.markRookMoved(board.teamAt(selectedX, selectedY),MobilityBoard.TAG_RIGHT); 
								//System.out.println("Right Rook Moved!");
							}
							
							
						}
						else if(piece == Piece.TYPE_KING)
						{
							mobilityBoard.markKingMoved(board.teamAt(selectedX, selectedY)); 
							//System.out.println("King Moved!");
						}
							
					}
					
					turnReady = false; 
					board.movePiece(selectedX, selectedY, x, y);
					
					traker.sendMovment(selectedX, selectedY, x, y);
					
					board.deselect(mobilityBoard);	
			}//end mobility 
			else
			{
				board.deselect(mobilityBoard);	
				
				if(board.teamAt(x, y) == player.getTeam())
				{
					System.out.println("marking " + x + "-" + y);
					int marked = board.selectForMark(x, y, mobilityBoard);
					
					System.out.println("Marked " + marked);
					
				}
			}

			selectedX = x; 
			selectedY = y; 
					
		}else if(input.mouseIsClicked())
		{
			board.deselect(mobilityBoard);	
		}
	}
	
	
	
}//end class
	
