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
		
		//set up pieces. <-- Not my job
	}
	
	public int movePiece(int fromX, int fromY, int toX, int toY)
	{
		lastKilled = getTileValue(toX, toY); 
		
		int piece = getPiece(fromX, fromY); 
		
		int dyingTeam = teamAt(toX, toY); 
		int dyingPiece = getPiece(toX, toY); 
		
		setTileValue(toX, toY, piece*teamAt(fromX,fromY)); 
		setTileValue(fromX, fromY, 0);
		
		//castling 
		if(piece == Piece.TYPE_KING)
		{
			if(Math.abs(toX-fromX) > 1)
			{
				//math for figuring out witch of four castles to move
				//NOTE that marking will only happen if it's possible 
				int y = 8 - (9 - (getTileValue(toX, toY)))%9;
				int delta = toX-fromX;	
				movePiece(((9-delta)%11), y,((delta+2)/2)+3, y);
			}
			//mobilityBoard.markKingMoved(teamAt(fromX, fromY)); 
			return teamAt(toX, toY) == 1? 2 : 1; 
		}
		
		
		if(dyingPiece != Piece.TYPE_ROOK)
			if((toY==0 || toY==7))
			{
					if(toX == 0)
					{
						return dyingTeam == 1? 5 : 3; //mobilityBoard.markRookMoved(teamAt(fromX, fromY), MobilityBoard.TAG_LEFT); 
					}
					else if(toX == 7)
					{
						return dyingTeam == 1? 6 : 4; //mobilityBoard.markRookMoved(teamAt(fromX, fromY), MobilityBoard.TAG_RIGHT); 
					}	
			}
		
		//TODO mark if cast,  I can't remember what this meant 
		if(piece == Piece.TYPE_ROOK)	
			if(fromY==0 || fromY==7)
			{
					if(fromX == 0)
					{
						return teamAt(toX, toY) == 1? 5 : 3; //mobilityBoard.markRookMoved(teamAt(fromX, fromY), MobilityBoard.TAG_LEFT); 
					}
					else if(fromX == 7)
					{
						return teamAt(toX, toY) == 1? 6 : 4; //mobilityBoard.markRookMoved(teamAt(fromX, fromY), MobilityBoard.TAG_RIGHT); 
					}	
			}
		
			return 0; 
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
		System.out.println("From: " + fromX + ", " + fromY);
		System.out.println("to: " + toX + ", " + toY);
		
		System.out.println("killed: " + lastKilled);
		
		setTileValue(fromX, fromY, getTileValue(toX, toY)); 
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
	public int selectForMark(int x, int y, Marker marker)
	{
		//int pieceValue = getPiece(x, y); 
		
		if(isOccupied(x, y))
		{
			int marks = marker.markPieceAt(x, y); 
			return marks; 
		}
		
		return 0; 
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

	public PieceMovement getPieceMovementAt(int x, int y)
	{
		if(getPiece(x,y) == 0)
			return null; 
		return pieces[getPiece(x,y)-1].movement; 
	}
}
