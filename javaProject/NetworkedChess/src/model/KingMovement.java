package model;

import factory.PieceFactory;

public class KingMovement extends PieceMovement 
{
	/**
	 * I hate this 
	 */
	private static boolean isUnmarking = false;
	
	public KingMovement(MovePattern pattern) 
	{
		super(pattern, false);
	}
	
	
	@Override
	public int specialMarking(MobilityBoard mboard, int cx, int cy) 
	{
		
		int marks = super.specialMarking(mboard, cx, cy);
		
		ChessBoard cboard = mboard.getChessBoard(); 
		
		int team = cboard.teamAt(cx, cy);
		
		//casteling 
		if(!mboard.didMove(Piece.TYPE_KING, team, 0))
		{	
			marks += markCastles(mboard, team); 
			
		}//did not move 
		
		if(isUnmarking)
			return 0; 
		
		//TODO "for the king" unmark threatened ares 
		if(cboard.getPiece(cx, cy) == Piece.TYPE_KING)
		{
			isUnmarking = true; 
			
			MobilityBoard threat = new MobilityBoard(cboard); 
			//remove threatened areas 
			int _team = cboard.teamAt(cx, cy); 
				
			//loop through map and find enemy pieces 
			for(int i=0 ; i<cboard.getHeight(); i++)
				for(int j=0; j<cboard.getWidth(); j++)
						if(cboard.teamAt(j, i) + _team == 0)
						{
							//System.out.printf("enemy unit %s %s\n", j, i);
							int piece = cboard.getPiece(j, i); 
							
							if(piece > 0)
							{
								//PieceFactory.instence().factor(piece).movement.markAvailableMovement(threat, cboard, j, i, 0); 
								Marker threatMarker = new Marker(threat); 
								threatMarker.markMovement(PieceFactory.instence().factor(piece).movement, j, i, 0); 
							}		
							//cboard.selectForMark(j, i, threat); 
						}
			
				
			//match { threat with mboard
			//
			for(int i = 0; i < cboard.getHeight(); i++)
				for(int j = 0; j < cboard.getWidth(); j++)
				{
					if(mboard.getTileValue(j, i) > MobilityBoard.MARK_INVISIBLE && threat.getTileValue(j, i) > MobilityBoard.MARK_INVISIBLE)
					{
						//System.out.printf("unmarking unit %s %s\n", j, i);
						mboard.unMark(j, i); 
						marks--; 
					}
				}
					
			//isUnmarking = false; 
			}//end unmarking threat ares 

		isUnmarking = false; 
		return marks; 
	}

	private int markCastles(MobilityBoard mboard, int team)
	{
		int marks =0 ; 
		
		ChessBoard cboard = mboard.getChessBoard(); 
		//special 
		int y = 8- (9 - (team))%9;
		
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
				marks += (mboard.markMove(6, y))? 1: 0; 
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
				marks += (mboard.markMove(2, y))? 1 : 0; 
			}
			
		}
		
		return marks; 
	}
	
	
}
