package GamePanels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import KLD.Game;
import KLD.GameFrame;
import KLD.Input;
import model.ChessBoard;
import model.MobilityBoard;
import model.Piece;
import ui.UIPiece;

public class ChessGame extends Game 
{
	private ChessBoard board; 
	private MobilityBoard mobilityBoard; 
	
	int tileWidth;
	int tileHeight;
	
	int offsetX;
	int offsetY;
	
	int selectedX, selectedY; 
	
	@Override
	protected void init() 
	{
		board = new ChessBoard(); 
		mobilityBoard= new MobilityBoard(board); 
		
		int[] pieces = {Piece.TYPE_PAWN,Piece.TYPE_ROOK, 
				Piece.TYPE_KNIGHT, Piece.TYPE_BISHOP, Piece.TYPE_KING, Piece.TYPE_QUEEN }; 
		
		
		for(int i=0; i<16; i++)
		{
			int piece = pieces[(i/8)*(  (i-7)+((i/13)*(3-((2*i)%13)))  )%6]; 
			//								14%7= (0)
			
			
			// 1 2 3 4 5 3 2 1
			
			//positive bottom
			board.setTileValue(i%8, 1-i/8, piece); 
		
			//negative top 
			board.setTileValue(i%8, 6+i/8, -piece); 
			
			
		}
		System.out.println(board);
	}

	@Override
	protected void draw(Graphics g) 
	{
		Color[] tileColors = {Color.WHITE, Color.BLACK};
			
		//board background
		for(int i=0; i<board.getHeight(); i++)
		{
			for(int j=0; j<board.getWidth(); j++)
			{
			  g.setColor(tileColors[(i%2+j%2)%2]); 
			  g.fillRect(offsetX+i*tileWidth, offsetY+j*tileHeight, tileWidth, tileHeight);
			  //draw tiles 
			}
		}
		
		//drawing mobility 
		Color[] mobilityColor ={Color.CYAN, Color.RED};
		for(int i=0; i<board.getHeight(); i++)
		{
			for(int j=0; j<board.getWidth(); j++)
			{
				if(mobilityBoard.getTileValue(i, j) != MobilityBoard.MARK_INVISIBLE)
				{
					this.setFade(g, 0.8f);
					
					g.setColor(mobilityColor[mobilityBoard.getTileValue(i, j)-1]);
					 g.fillRect(offsetX+i*tileWidth, offsetY+j*tileHeight, tileWidth, tileHeight);
					 
					 this.setFade(g, 1f);
					
				}
			}
		}
			//draw pieces 
		for(int i=0; i<board.getHeight(); i++)
		{
			for(int j=0; j<board.getWidth(); j++)
			{
			 
			  int pieceIndex = 2*Math.abs(board.getTileValue(i, j)) - (board.getTileValue(i, j) > 0? 1 : 0); 
			  //draw tiles 
			 
			  
			  if(pieceIndex > 0)
				  g.drawImage(UIPiece.Instnece().factor(pieceIndex), offsetX+i*tileWidth, offsetY+j*tileHeight,tileWidth, tileHeight, null); 
			}
		}
		
		
		
		
		
	}

	@Override
	protected void update() 
	{
		
		tileWidth = GameFrame.width/10; 
		tileHeight = GameFrame.height/10; 
		
		offsetX = GameFrame.width/2 - tileWidth*4; 
		offsetY = GameFrame.height/2 - tileHeight*4 - 20; 
		
		//when click within board 
		if(input.mouseIsClicked(new Rectangle(offsetX, offsetY, offsetX+tileWidth*8, offsetY+tileHeight*8)) )
		{
			//get co-ordenate 
			int x = ((Input.point.x - offsetX) /tileWidth); 
			int y = ((Input.point.y - offsetY) /tileHeight); 
			
			
			if(mobilityBoard.getTileValue(x, y) > MobilityBoard.MARK_INVISIBLE)
			{
					//System.out.printf("movin %d %d to %d %d\n", selectedX, selectedY, x, y);
					board.movePiece(selectedX, selectedY, x, y);	
					board.deselect(mobilityBoard);	
			}
			else
			{
				board.deselect(mobilityBoard);	
				board.selectForMark(x, y, mobilityBoard);
			}

			selectedX = x; 
			selectedY = y; 
					
		}
		
	}
	
}//end class
