package model;

/**
 * Special needs 
 * 
 * @author KLD
 */
public class PawnMovement extends PieceMovement 
{
	/**
	 * creates an idiot pawn 
	 */
	public PawnMovement() 
	{
		super(0, 0, false);
	}
	
	@Override
	public boolean markAvailableMovement(MobilityBoard mboard, ChessBoard cboard, int cx, int cy) 
	{
		boolean didMark = false; 
	
		//either 1 or -1
		int team = cboard.teamAt(cx, cy); 
				
		didMark = didMark | mboard.markMove(cx, cy + team); 
		
		didMark = didMark | mboard.markAttack(cx+1, cy+team, team); 
		didMark = didMark | mboard.markAttack(cx-1, cy+team, team); 
		

		if(!didMark)
			return false; 
		
		
		//initial move proven mathematically 
			if(team == cy || team+7 == cy)
			{
				didMark = didMark | mboard.markMove(cx, cy + 2*team); 
			}
			
			
		
		return didMark; 
	}

}
