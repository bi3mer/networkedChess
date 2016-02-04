package model;

import factory.PieceMovementFactory;

/**
 * Hold piece values 
		
		
 * 
 * @author KLD
 * 
 * @See #Piece
 */
public class ChessBoard extends Board 
{
	private Piece[] pieces; 
	
	/**
	 * Initializes board and creates pieces 
	 * 
	 * TODO set up about with pieces 
	 */
	public ChessBoard() 
	{
		super(8,8);
		
		//define pieces 
		
		PieceMovementFactory pissFactory = new PieceMovementFactory(); 
		
		pieces = new Piece[7];
		
		for(int i=0; i<pieces.length; i++)
		{
			pieces[i] = pissFactory.factor(i+1); 
		}
		
		//set up pieces
	}
	
	public void movePiece(int fromX, int fromY, int toX, int toY)
	{
		setTileValue(toX, toY, getTileValue(fromX, fromY)); 
		setTileValue(fromX, fromY, 0);
		
		//castling 
		if(Math.abs(getTileValue(toX, toY)) == Piece.TYPE_KING && Math.abs(toX-fromX) > 1)
		{
			int y = 8 - (9 - (getTileValue(toX, toY)))%9;
			int delta = toX-fromX;	
			movePiece(((9-delta)%11), y,((delta+2)/2)+3, y);
		}
		
	}

	/**
	 * to be deleted (or set private 
	 */
	public void placePiece(int x, int y, int pieceType)
	{
		setTileValue(x,y,pieceType); 
	}

	/**
	 * @param x coordinate 
	 * @param y coordinate
	 * @return Team value of given piece at position, 0 if position is empty;teamless. 
	 */
	public int teamAt(int x, int y)
	{
		if(getTileValue(x, y) > Board.EMPTY)
			return 1; 
		else if (getTileValue(x, y) < Board.EMPTY)
			return -1; 
		
		return 0; 
	}
	
	/**
	 * Marks a mobility board with given piece 
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @param mboard markable board 
	 */
	public void selectForMark(int x, int y, MobilityBoard mboard)
	{
		int pieceValue = getTileValue(x, y); 
		
		if(pieceValue != Board.EMPTY)
			pieces[Math.abs(pieceValue)-1].movement.markAvailableMovement(mboard, this, x, y); 
	}
	
	/**
	 * Resets mobility board, but not call it directly from itself? Get a life 
	 * @param mboard 
	 */
	public void deselect(MobilityBoard mboard)
	{
		mboard.reset(); 
	}

}
