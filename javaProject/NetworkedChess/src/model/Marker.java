package model;

/**
 * Marks a mobility board using PieceMovement patterns 
 * @author KLD
 *
 */
public class Marker 
{
	private MobilityBoard mboard; 
	
	public Marker(MobilityBoard mboard)
	{
		this.mboard = mboard; 
 	}
	
	public int markPieceAt(int x, int y)
	{
		PieceMovement mv = mboard.getChessBoard().getPieceMovementAt(x, y);
		if(mv==null)
			return 0; 
		
		int team = mboard.getChessBoard().teamAt(x, y); 
	
		return markMovement(mv,x,y,team); 
	}
	
	/**
	 * Marks a mobility board with possible moves and attacks using all possible move patterns 
	 * 
	 * @param pieceMove movement to be marked 
	 * @param cx center x if piece 
	 * @param cy center y of piece 
	 * @param team relative team
	 * 
	 * @return number of marks 
	 * 
	 * @see #markAvailableMove
	 */
	public int markMovement(PieceMovement pieceMove, int cx, int cy, int team)
	{
		int marks = 0; 
		MovePattern pointer = pieceMove.getMovePattern(); 

		while(pointer != null)
		{
			marks +=  markMove(pointer, pieceMove.isContinued(),cx, cy, team);
			pointer = pointer.next; 
		}

		marks += pieceMove.specialMarking(mboard, cx, cy); 
		
		return marks; 
	}
	
	/**
	 * @param move
	 * @param cx
	 * @param cy
	 * @param team
	 * @return
	 */
	public int markMove(MovePattern move, boolean isCon, int cx, int cy, int team)
	{
		int marks = 0;  
		
		int px = move.x + cx; 
		int py = move.y + cy; 
		
		if(mboard.getChessBoard().isEmpty(px, py))
		{		
			if(mboard.markMove(px , py))
			{
				marks++; 
				if(isCon)
				{
					marks += recursiveMarking(cx, cy, move.x, move.y, team); 
				}//end isContinued
			}//end markMove
		}//end isEmpty
		else if (mboard.markAttack(px, py, team))
			marks++; 
		
		return marks; 
	}
	
	/**
	 * Repeatedly marks movable areas and marks attack when appropriate. 
	 * @param cx center x 
	 * @param cy center y 
	 * @param ox offset x 
	 * @param oy offset y 
	 * @param team team 
	 * @return number of marked (including attack) 
	 */ //PSYCH! it's not even recursive! 
	private int recursiveMarking(int cx, int cy, int ox, int oy, int team)
	{
		//
		int marks = 0 ;
		int multyplier = 2; 
		
		//recursive x, y 
		int rx = multyplier*ox + cx;
		int ry = multyplier*oy + cy;
		//System.out.printf("marked: r:%d %d  o:%d %d c:%d %d m:%d\n",rx,ry, ox,oy, cx,cy, multyplier);

		
		while(mboard.getChessBoard().isEmpty(rx, ry))
		{
			//System.out.printf("marked: r:  o:%d %d c:%d %d m:%d\n",rx,ry, ox,oy, cx,cy, multyplier);

			marks += (mboard.markMove(rx,ry))? 1 : 0 ; 
			
			//multyplier++; 
			rx = ++multyplier*ox + cx;
			ry = multyplier*oy + cy;
		}//end while
		
		//
		
		if(team == 0)
		{
			if(mboard.getChessBoard().getPiece(rx, ry) == Piece.TYPE_KING)
			{
				marks += (mboard.markMove(rx,ry))? 1 : 0 ;
				
				//
				rx = ++multyplier*ox + cx;
				ry = multyplier*oy + cy;
				//
				marks += (mboard.markMove(rx,ry))? 1 : 0 ;
				
				
			}
		}
		else
		{
				
				marks += (mboard.markAttack(rx, ry, team))? 1 : 0 ; 
		}//end else
		
		return marks; 
	}
	
	/**
	 * 
	 */
	public int forceMarkPieceAt(int x, int y, int ix, int iy)
	{
		PieceMovement mv = mboard.getChessBoard().getPieceMovementAt(x, y);
		if(mv == null)
			return 0; 
		
		//int team = mboard.getChessBoard().teamAt(x, y); 
	
		return forceMarkMovement(mv, x, y, ix, iy); 
	}
	
	/**
	 * 
	 * @param pieceMove
	 * @param x
	 * @param y
	 * @param ix
	 * @param iy
	 * @return
	 */
	public int forceMarkMovement(PieceMovement pieceMove, int x, int y, int ix, int iy)
	{
		int marks = 0; 
		MovePattern pointer = pieceMove.getMovePattern(); 

		while(pointer != null)
		{
			marks +=  forceMarkMove(pointer, pieceMove.isContinued(),x, y, ix, iy);
			pointer = pointer.next; 
		}

		marks += pieceMove.specialMarking(mboard, x, y); 
		
		return marks; 
	}

	/**
	 * 
	 * @param move
	 * @param continued
	 * @param x
	 * @param y
	 * @return
	 */
	private int forceMarkMove(MovePattern move, boolean isCon, int x, int y, int ix, int iy) 
	{
		int marks = 0;  
		
		int px = move.x + x; 
		int py = move.y + y; 
		
		if(mboard.getChessBoard().isEmpty(px, py) || (ix==x && iy==y))
		{		
			if(mboard.markMove(px , py))
			{
				marks++; 
				if(isCon)
				{
					marks += forceRecursiveMarking(x, y, move.x, move.y); 
				}//end isContinued
			}//end markMove
		}//end isEmpty
		else if (mboard.markAttack(px, py, 0))
			marks++; 
		
		return marks; 
	}
	
	private int forceRecursiveMarking(int cx, int cy, int ox, int oy)
	{
		//
		int marks = 0 ;
		int multyplier = 2; 
		
		//recursive x, y 
		int rx = multyplier*ox + cx;
		int ry = multyplier*oy + cy;
		//System.out.printf("marked: r:%d %d  o:%d %d c:%d %d m:%d\n",rx,ry, ox,oy, cx,cy, multyplier);

		
		while(mboard.getChessBoard().isEmpty(rx, ry))
		{
			//System.out.printf("marked: r:  o:%d %d c:%d %d m:%d\n",rx,ry, ox,oy, cx,cy, multyplier);

			marks += (mboard.markMove(rx,ry))? 1 : 0 ; 
			
			multyplier++; 
			rx = multyplier*ox + cx;
			ry = multyplier*oy + cy;
		}//end while
		
		if(mboard.getChessBoard().getTileValue(rx, ry)==Piece.TYPE_KING)
		{
			marks += (mboard.markMove(rx,ry))? 1 : 0 ;
			multyplier++; 
			rx = multyplier*ox + cx;
			ry = multyplier*oy + cy;
			marks += (mboard.markMove(rx,ry))? 1 : 0 ;
		}
		
		
		
		return marks; 
	}
	
}
