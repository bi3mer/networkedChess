package model;

/**
 * Identifies possibles tiles a piece can move and marks those areas with either move or attack markers 
 * 
 * @author KLD
 */
public class PieceMovement 
{
	/**
	 * True means the same pattern of a move occurs in greater distance 
	 */
	private boolean isContinued; 
	
	/**
	 * A linked list of movement offsets value(s) away from center 
	 */
	private Move move;
	
	/**
	 * @param move movement offset(s) pattern 
	 * @param isCon True means the same pattern of a move occurs in greater distance, false means non-repeated. 
	 * 
	 * @see Move
	 */
	public PieceMovement(Move move, boolean isCon)
	{
		this.move = move;
		isContinued = isCon; 	
	}
	
	/**
	 * Creates a movement with a single pattern 
	 * @param ox offset x pattern 
	 * @param oy offset y pattern 
	 * @param isCon True means the same pattern of a move occurs in greater distance, false means non-repeated. 
	 */
	public PieceMovement(int ox, int oy, boolean isCon)
	{
		this(new Move(ox,oy), isCon); 
	}
	
	/**
	 * Marks a mobility board wit possible moves and attacks using all possible move patterns 
	 * 
	 * @param mboard board to be marked 
	 * @param cboard board to check empty and occupied from 
	 * @param cx center x if piece 
	 * @param cy center y of piece 
	 * @return true if at least one tile is marked, otherwise false 
	 * 
	 * @see #markAvailableMove
	 */
	public boolean markAvailableMovement(MobilityBoard mboard,ChessBoard cboard, int cx, int cy)
	{
		boolean marked = false; 
		
		marked =  marked | markAvailableMove(mboard, cboard, cx, cy, move);
		
		Move pointer = move.next; 
		while(pointer != null)
		{
			marked =  marked | markAvailableMove(mboard, cboard, cx, cy, pointer);
			pointer = pointer.next; 
		}
		
		return marked; 
	}
	
	/**
	 * Marks possible move and attack tiles in a mobility board using a single move pattern in four directions. 
	 * 
	 * @param mboard board to be marked 
	 * @param cboard board to check empty and occupied from 
	 * @param cx center x if piece 
	 * @param cy center y of piece 
	 * @param move move pattern used 
	 * 
	 * @return true if at least one tile is marked, otherwise false 
	 */
	private boolean markAvailableMove(MobilityBoard mboard, ChessBoard cboard, int cx, int cy, Move move)
	{
		boolean didMark = false;  
		
		int DIR = 4;  //directions 
		
		for(int i=0; i < DIR; i++)
		{
			//local x, y
			int lx = move.x; 
			int ly = move.y; 
			double initR = Math.atan((double)ly/lx); 
			double length = Math.sqrt(lx*lx + ly*ly); 
			
			//directed x, y
			int dx =(int) Math.round( length*(Math.cos(initR+(i*Math.PI)/2)) ); 
			int dy =(int) Math.round( length*(Math.sin(initR+(i*Math.PI)/2)) ); 
			
			//pointed
			int px = dx + cx; 
			int py = dy + cy; 
			
			if(cboard.isEmpty(px, py))
			{		
				if(mboard.markMove(px , py))
				{
					didMark = true; 
					if(isContinued)
					{
						recursiveMarking(mboard, cboard, cx, cy, dx, dy); 
					}//end isContinued
				}//end markMove
			}//end isEmpty
			else
			{
				if (mboard.markAttack(px, py, cboard.teamAt(cx, cy)))
						didMark = true; 
				else
				{
					System.out.println("conned: "+ px + " " + py);
					continue; 
				}
			}//end else 
		}//end for i 
		
		return didMark; 
	}
	
	/**
	 * Repeatedly marks movable ares and marks attack when appropriate. 
	 * @param mboard
	 * @param cboard
	 * @param cx centered x of piece 
	 * @param cy centered y of piece 
	 * @param ox pattern's offset x 
	 * @param oy pattern's offset y
	 */
	private void recursiveMarking(MobilityBoard mboard,ChessBoard cboard, int cx, int cy, int ox, int oy)
	{
		int multyplier = 2; 
		
		//recursive x, y 
		int rx = multyplier*ox + cx;
		int ry = multyplier*oy + cy;
		
		while(cboard.isEmpty(rx, ry))
		{
			mboard.markMove(rx,ry); 
			
			multyplier++; 
			rx = multyplier*ox + cx;
			ry = multyplier*oy + cy;
		}//end while
		
		if(cboard.isOccupied(rx, ry))
		{
			mboard.markAttack(rx, ry, cboard.teamAt(cx, cy)); 
		}//end if isOccupied
	}
	
	/**
	 * Creates a new move object 
	 * @param x x offset 
	 * @param y y offset 
	 * @param move linked move if available. Otherwise, null 
	 * 
	 * @return a move pattern object 
	 */
	public static Move createMove(int x, int y, Move move)
	{
		Move m =  new Move(x, y);
		m.next = move; 
		return m; 
	}
	
}

/**
 * A linked list of movement pattern values that appear in four direction. 
 * 
 * @see PieceMovement#createMove(int, int, Move)
 * 
 * @author KLD
 */
class Move
{
	public int x; 
	public int y; 
	
	public Move next; 
	
	public Move(int x, int y)
	{
		this.x = x; 
		this.y = y; 
	}
}
