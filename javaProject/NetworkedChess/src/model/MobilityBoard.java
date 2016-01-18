package model;

/**
 * A board that highlights available moment (including attack) places on a chess board 
 * 
 * @author KLD
 */
public class MobilityBoard extends Board 
{
	public final static int MARK_INVISIBLE = 0; 
	public final static int MARK_MOVE = 1; 
	public final static int MARK_ATTACK = 2; 

	/**
	 * Dependency 
	 */
	private ChessBoard cboard;  
	
	/**
	 * Initializes size and keeps a pointed to ChessBoard 
	 * @param cboard 
	 */
	public MobilityBoard(ChessBoard cboard)
	{
		super(cboard.getWidth(), cboard.getHeight(), MARK_INVISIBLE); 
		this.cboard = cboard; 
	}

	/**
	 * Mark a given area using given mark 
	 * @param markType mark 
	 * @param x coordinate 
	 * @param y coordinate
	 * @return true if value is inbounds otherwise false. 
	 * 
	 * @see Board#setTileValue
	 */
	public boolean mark(int markType, int x, int y)
	{
		return setTileValue(x, y, markType);
	}

	/**
	 * Marks tile with move marker 
	 * @param x coordinate
	 * @param y coordinate
	 * @return true if tile is marked, otherwise false
	 * 
	 * @see #mark
	 */
	public boolean markMove(int x, int y)
	{
		if(cboard.isEmpty(x, y))
			return mark(MARK_MOVE, x, y);
		
		return false; 
	}
	
	/**
	 * Marks tile with attack marker 
	 * @param x coordinate
	 * @param y coordinate
	 * @return true if tile is marked, otherwise false
	 * 
	 * @see #mark
	 */
	public boolean markAttack(int x, int y, int team)
	{
		if(cboard.isOccupied(x, y) && cboard.teamAt(x,y) != team)
			return mark(MARK_ATTACK, x,y);
		return false; 
	}
	
	/**
	 * Marks tile with invisible marker 
	 * @param x coordinate
	 * @param y coordinate
	 * @return true if tile is marked, otherwise false
	 * 
	 * @see #mark
	 */
	public boolean unMark(int x, int y)
	{
		return mark(MARK_INVISIBLE, x, y);
	}

	/**
	 * Unmarks every tile with invisible marker
	 */
	public void reset()
	{
		for(int i=0; i<getHeight(); i++)
			for(int j=0; j<getWidth(); j++)
				unMark(j, i); 
	}
	
}