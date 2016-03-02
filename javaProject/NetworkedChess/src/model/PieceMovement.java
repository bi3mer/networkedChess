package model;

import factory.PieceFactory;

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
	private MovePattern move;
	
	/**
	 * @param move movement offset(s) pattern 
	 * @param isCon True means the same pattern of a move occurs in greater distance, false means non-repeated. 
	 * 
	 * @see Move
	 */
	public PieceMovement(MovePattern move, boolean isCon)
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
		this(new MovePattern(ox,oy), isCon); 
	}
	
	/**
	 * Marks a mobility board wit possible moves and attacks using all possible move patterns 
	 * 
	 * @param mboard board to be marked 
	 * @param cboard board to check empty and occupied from 
	 * @param cx center x if piece 
	 * @param cy center y of piece 
	 * @param team relative team
	 * 
	 * @return number of marks 
	 * 
	 * @see #markAvailableMove
	 */
	public int markAvailableMovement(MobilityBoard mboard,ChessBoard cboard, int cx, int cy, int team)
	{
		int marks = 0; 

		marks +=  markAvailableMove(mboard, cboard, cx, cy, team, move);
		
		MovePattern pointer = move.next; 
		while(pointer != null)
		{
			marks +=  markAvailableMove(mboard, cboard, cx, cy,team, pointer);
			pointer = pointer.next; 
		}

		return marks; 
	
		
	}
	/**
	 * Marks a mobility board wit possible moves and attacks using all possible move patterns 
	 * 
	 * @param mboard board to be marked 
	 * @param cboard board to check empty and occupied from 
	 * @param cx center x if piece 
	 * @param cy center y of piece 
	 * @return number of marks 
	 * 
	 * @see #markAvailableMove
	 */
	public int markAvailableMovement(MobilityBoard mboard,ChessBoard cboard, int cx, int cy)
	{
		return markAvailableMovement(mboard, cboard, cx, cy,cboard.teamAt(cx, cy)); 
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
	 * @return number of marks 
	 */
	public int markAvailableMove(MobilityBoard mboard, ChessBoard cboard, int cx, int cy, int team, MovePattern move)
	{
		int marks = 0;  
		
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
					marks++; 
					if(isContinued)
					{
						marks += recursiveMarking(mboard, cboard, cx, cy, dx, dy, team); 
					}//end isContinued
				}//end markMove
			}//end isEmpty
			else
			{
				if (mboard.markAttack(px, py, team))
					marks++; 
				else
				{
					//System.out.println("conned: "+ px + " " + py); fuckyou 
					continue; 
				}
			}//end else 
		}//end for i 
		
		return marks; 
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
	private int recursiveMarking(MobilityBoard mboard,ChessBoard cboard, int cx, int cy, int ox, int oy, int team)
	{
		int marks = 0 ;
		int multyplier = 2; 
		
		//recursive x, y 
		int rx = multyplier*ox + cx;
		int ry = multyplier*oy + cy;
		
		while(cboard.isEmpty(rx, ry))
		{
			marks += (mboard.markMove(rx,ry))? 1 : 0 ; 
			
			multyplier++; 
			rx = multyplier*ox + cx;
			ry = multyplier*oy + cy;
		}//end while
		
		if(cboard.isOccupied(rx, ry))
		{
			marks += (mboard.markAttack(rx, ry, team))? 1 : 0 ; 
		}//end if isOccupied
		
		
		return marks; 
	}
	
	/**
	 * Creates a new move object 
	 * @param x x offset 
	 * @param y y offset 
	 * @param move linked move if available. Otherwise, null 
	 * 
	 * @return a move pattern object 
	 */
	public static MovePattern createMove(int x, int y, MovePattern move)
	{
		MovePattern m =  new MovePattern(x, y);
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
class MovePattern
{
	public int x; 
	public int y; 
	
	public MovePattern next; 
	
	public MovePattern(int x, int y)
	{
		this.x = x; 
		this.y = y; 
	}
}
