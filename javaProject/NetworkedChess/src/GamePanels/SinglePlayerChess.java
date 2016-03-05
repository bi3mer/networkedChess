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
		
		board = new ChessBoard(); 
		mobilityBoard= new MobilityBoard(board); 
		marker = new Marker(mobilityBoard); 
			
		placePieces(); 
	
	tileWidth = GameFrame.width/10; 
	tileHeight = GameFrame.height/10; 
	
	offsetX = GameFrame.width/2 - tileWidth*4; 
	offsetY = GameFrame.height/2 - tileHeight*4 - 20; 
	
	//reverse = 0; 
	waitingTime = 0; 
	
	
	
	System.out.println(board);
	
	
	//this will allow the board to be drawn automatically 
		boradui = new BoardUI(board, mobilityBoard, reverse); 
		boradui.setSizes(offsetX, offsetY, tileWidth, tileHeight);
		revereBoard();
		
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
		//System.out.println("reserve");
	
	}
	
	public void placePieces()
	{
		//int[] pieces = {Piece.TYPE_PAWN, Piece.TYPE_ROOK, Piece.TYPE_KNIGHT, Piece.TYPE_BISHOP, Piece.TYPE_QUEEN, Piece.TYPE_KING }; 
			
		int[] pieces = {0, Piece.TYPE_ROOK, 0,0, 0, Piece.TYPE_KING }; 
				
	
		for(int i=0; i<16; i++)
		{
			int piece = pieces[(i/8)*(  (i-7)+((i/13)*(3-((2*i)%13)))  )%6]; 
			
			//positive bottom
			board.setTileValue(i%8, 1-i/8, piece); 
		
			//negative top 
			board.setTileValue(i%8, 6+i/8, -piece); 	
		}
	
	}//end place 
	
	public void unitClicking()
	{
		if(input.mouseIsClicked(new Rectangle(offsetX, offsetY, offsetX+tileWidth*7, offsetY+tileHeight*7)) )
		{
			//get co-ordenate 
		int x = ((Input.point.x - offsetX) /tileWidth); 
		int y = ((Input.point.y - offsetY) /tileHeight); 
		
		//System.out.printf("clicked %d %d\n", x, y);
		
		y = Math.abs(reverse-y); 
		
		if(mobilityBoard.getTileValue(x, y) > MobilityBoard.MARK_INVISIBLE)
		{
				//System.out.printf("movin %d %d to %d %d\n", selectedX, selectedY, x, y);
			//rook or king moved notifications 
			int notification=	board.movePiece(selectedX, selectedY, x, y);
				
			//notify mobility to adjust castling 
			if (notification > 0)
			{
				notification--; 
				mobilityBoard.setMoved(notification); 
				//int team = board.teamAt(x, y); 
				
				
				/*if(notification/2 == 0) 
					mobilityBoard.markKingMoved(team);
				else
				{
					int side =  notification/2 -1; 
					mobilityBoard.markRookMoved(team, side);
				}*/
				
			}//endn if notification 
			
				
			//System.out.println(board);
				
			mobilityBoard.reset();	
		}//end mobility 
		else
		{
			mobilityBoard.reset();	
			
			
				//System.out.println("marking " + x + "-" + y);
				int marked = marker.markPieceAt(x, y); 
				
				//System.out.println("Marked " + marked);
					
				}
		
	
			selectedX = x; 
			selectedY = y; 
					
		}else if(input.mouseIsClicked())
		{
			mobilityBoard.reset();	
		}
		
	}//end click 
	



}//end class

