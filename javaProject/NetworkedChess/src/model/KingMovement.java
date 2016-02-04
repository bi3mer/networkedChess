package model;

import java.util.ArrayList;

public class KingMovement extends PieceMovement 
{
	
	public KingMovement() 
	{
		super(PieceMovement.createMove(1, 1, PieceMovement.createMove(0, 1, null) ), false);
	}
	
	
	@Override
	public boolean markAvailableMovement(MobilityBoard mboard, ChessBoard cboard, int cx, int cy) 
	{
		boolean didMark = super.markAvailableMovement(mboard, cboard, cx, cy);
		
		int team = cboard.teamAt(cx, cy);
		
		if(mboard.didMove(Piece.TYPE_KING, team, 0))
			return didMark; 
		
		
		//special 
		int y = 8- (9 - (team))%9;
		
		
		//hes at 4, he should move to 1 and rook to 3 
		
		//rook right  
		if(!mboard.didMove(Piece.TYPE_ROOK, team, MobilityBoard.TAG_RIGHT))
		{
			boolean free = true; 
			//check empty
			for(int i=0; i<2; i++)
			{
				free = free && cboard.getTileValue(i+5, y) == ChessBoard.EMPTY ;
			}
			
			if(free)
			{
				didMark = didMark | mboard.markMove(6, y); 
			}
			
		}
		
		//rook left 
		if(!mboard.didMove(Piece.TYPE_ROOK, team, MobilityBoard.TAG_LEFT))
		{
			boolean free = true; 
			//check empty
			for(int i=0; i<3; i++)
			{
				free = free && cboard.getTileValue(i+1, y) == ChessBoard.EMPTY ;
			}
			
			if(free)
			{
				didMark = didMark | mboard.markMove(2, y); 
			}
			
		}
		
		
		return didMark; 
	}

	
	
}
