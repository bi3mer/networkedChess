package model;

import factory.PieceFactory;

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
	
	private int lastKilled; 
	
	public ChessBoard() 
	{
		super(8,8);
		
		//define pieces 
		
		PieceFactory pissFactory = PieceFactory.instence(); 
		
		pieces = new Piece[7];
		
		for(int i=0; i<pieces.length; i++)
		{
			pieces[i] = pissFactory.factor(i+1); 
		}
		
		//set up pieces
	}
	
	public void movePiece(int fromX, int fromY, int toX, int toY)
	{
		lastKilled = getTileValue(toX, toY); 
		
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
	 * Undo's last killed piece 
	 * @param fromX last piece moved from X co-od 
	 * @param fromY last piece moved from  Y co-od 
	 * @param toX last piece killed at X co-od 
	 * @param toY last piece killed at Y co-od 
	 */
	public void untoLastMove(int fromX, int fromY, int toX, int toY)
	{
		setTileValue(fromX, fromX, getTileValue(toX, toY)); 
		setTileValue(toX, toY, lastKilled);
	}

	/**
	 * to be deleted (or set private) Do not use 
	 * 
	 * @see #movePiece(int, int, int, int)
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
	public int selectForMark(int x, int y, MobilityBoard mboard)
	{
		int pieceValue = getPiece(x, y); 
		
		if(pieceValue != Board.EMPTY)
			return pieces[pieceValue-1].movement.markAvailableMovement(mboard, this, x, y); 
		
		return 0; 
	}
	
	/**
	 * Resets mobility board, but not call it directly from itself? Get a life 
	 * @param mboard 
	 */
	public void deselect(MobilityBoard mboard)
	{
		mboard.reset(); 
	}
	
	/**
	 * return piece values 
	 * @param x x co-ord 
	 * @param y y co-ord 
	 * @return 0 if no piece, otherwise piece value 
	 * 
	 * @see Piece
	 */
	public int getPiece(int x, int y)
	{
		return Math.abs(getTileValue(x, y));
	}

}
