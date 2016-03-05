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
	public int specialMarking(MobilityBoard mboard, int cx, int cy) 
	{
		int marks = 0; 
	
		//either 1 or -1
		int team = mboard.getChessBoard().teamAt(cx, cy); 
				
		marks += (mboard.markMove(cx, cy + team))? 1 : 0; 
		
		marks += (mboard.markAttack(cx+1, cy+team, team))? 1 : 0; 
		
		marks += (mboard.markAttack(cx-1, cy+team, team))? 1 : 0; 
		
		if(marks==0)
			return 0; 
		
		//initial move proven mathematically 
			if(team == cy || team+7 == cy)
				marks += (mboard.markMove(cx, cy + 2*team))? 1 : 0;  
				
		return marks; 
	}

}
