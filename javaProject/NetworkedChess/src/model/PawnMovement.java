package model;

import javax.imageio.plugins.bmp.BMPImageWriteParam;

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
		super(null, false);
	}
	
	@Override
	public int specialMarking(MobilityBoard mboard, int cx, int cy, int team) 
	{
		int marks = 0; 
		
		if(team == 0)
		{
			int pieceTeam = mboard.getChessBoard().teamAt(cx, cy);
			return specialAttackMark(mboard, 2, cx, cy, pieceTeam); 
		}
	
		//either 1 or -1
		//int team = mboard.getChessBoard().teamAt(cx, cy); 
				
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
	
	public int specialAttackMark(Board b, int mark, int cx, int cy, int team)
	{
		int marks = 0; ; 
		marks += (b.setTileValue(cx+1, cy+team, mark))? 1 : 0; 
		marks += (b.setTileValue(cx-1, cy+team, mark))? 1 : 0; 
		return marks; 
	}
	
}
