package ui;

import java.awt.Color;
import java.awt.Graphics;

import model.ChessBoard;
import model.MobilityBoard;

import KLD.cmd.Draw;
import KLD.Game; 

public class BoardUI extends Draw 
{
	private int offsetX; 
	private int offsetY; 
	
	private int cellWidth; 
	private int cellHeight; 
	
	private ChessBoard board; 
	private MobilityBoard mboard; 
	private int reverse; 
	
	
	public BoardUI(ChessBoard board, MobilityBoard mboard, int reverse) 
	{
		super("BoardUI");
		
		offsetX = 0; 
		offsetY = 0; 
		
		cellWidth = 0; 
		cellHeight = 0; 
		
		this.board = board; 
		this.mboard = mboard; 
		this.reverse = reverse; 
	}
	
	public void setSizes(int ox, int oy, int cx, int cy)
	{
		offsetX = ox; 
		offsetY = oy; 
		
		cellWidth = cx; 
		cellHeight = cy; 
	}
	
	public void setReverse(int r)
	{
		reverse = r; 
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
				int y = Math.abs(reverse-i); 
				
				g.setColor(tileColors[(j%2+i%2)%2]); 
				g.fillRect(offsetX+j*cellWidth, offsetY+y*cellHeight, cellWidth, cellHeight);
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
				
				if(mboard.getTileValue(j, i) != MobilityBoard.MARK_INVISIBLE)
				{
					
					Game.setFade(g, 0.8f);
					
					g.setColor(mobilityColor[Math.abs(mboard.getTileValue(j, i))-1]);
					g.fillRect(offsetX+j*cellWidth, offsetY+y*cellHeight, cellWidth, cellHeight);
					 
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
				  g.drawImage(PieceImageFactory.Instnece().factor(pieceIndex), offsetX+j*cellWidth, offsetY+y*cellHeight,cellWidth, cellHeight, null); 
				  //g.setColor(Color.red);
				  //g.drawString(""+pieceIndex, offsetX+j*tileWidth, offsetY+y*tileHeight);
			  }
			}
		}
	}

	
	
}
