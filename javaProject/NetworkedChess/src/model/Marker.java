package model;

import java.util.ArrayList;

/**
 * Marks a mobility board using PieceMovement patterns 
 * @author KLD
 *
 */
public class Marker 
{
	private MobilityBoard mboard; 
	
	private ArrayList<Integer> killers; 
	
	public Marker(MobilityBoard mboard)
	{
		this.mboard = mboard;
		killers = new ArrayList<>(); 
 	}
	
	public int markPieceAt(int x, int y)
	{
		PieceMovement mv = mboard.getChessBoard().getPieceMovementAt(x, y);
		if(mv==null)
			return 0; 
		
		int team = mboard.getChessBoard().teamAt(x, y); 
	
		calebrate(team); 
		
		int marks = markMovement(mv,x,y,team);
		
		if(getMobilityBoard().getChessBoard().getPiece(x, y) == Piece.TYPE_KING)
			return marks; 
		
		//test if each move is safe 
		for(int i=0; i<getMobilityBoard().getHeight(); i++)
			for(int j=0; j<getMobilityBoard().getWidth(); j++)
			{
				if(getMobilityBoard().getTileValue(j, i) > MobilityBoard.MARK_INVISIBLE)
				{
					if(!isSafeToMove(x, y, j, i, getMobilityBoard().getChessBoard(), team))
					{
						//if(getMobilityBoard().getTileValue(j,i) > )
						marks--; 
						getMobilityBoard().setTileValue(j, i, MobilityBoard.MARK_INVISIBLE); 
						
					}
					else
					{
						//System.out.printf("Moving from %d %d to %d %d is SAFEe\n", x,y,j,i);
					}
				}
			}
		
		return marks; 
	}
	
	//duh
	public MobilityBoard getMobilityBoard()
	{
		return mboard; 
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
		
		marks += pieceMove.specialMarking(mboard, cx, cy, team); 
		
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
		
		if(mboard.markMove(px , py))
		{
				marks++; 
				if(isCon)
					marks += recursiveMarking(cx, cy, move.x, move.y, team); 
				
		}//end markMove
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
			marks += (mboard.markMove(rx,ry))? 1 : 0 ; 
			 
			rx = ++multyplier*ox + cx;
			ry =   multyplier*oy + cy;
		}//end while
		
		marks += (mboard.markAttack(rx, ry, team))? 1 : 0 ; 
		
		if(team == 0)
			if(mboard.getChessBoard().getPiece(rx, ry) == Piece.TYPE_KING)
			{
				rx = ++multyplier*ox + cx;
				ry = multyplier*oy + cy;
				//
				marks += (mboard.markMove(rx,ry))? 1 : 0 ;
			}
		
		return marks; 
	}
	
	
	private boolean isSafeToMove(int fromX, int fromY, int toX, int toY, ChessBoard cboard, int team)
	{
		int pieceFrom = cboard.getTileValue(fromX, fromY); 
		int pieceTo = cboard.getTileValue(toX, toY); 
		
		//move piece 
		cboard.setTileValue(toX, toY, pieceFrom); 
		cboard.setTileValue(fromX, fromY, 0); 
		
		//boolean isThreatened = false ; // = calebrate(team).size() > 0 ; 
		
		int pos = findKing(team);
		int kx = pos%getMobilityBoard().getWidth(); 
		int ky = pos/getMobilityBoard().getHeight(); 
		
		
		for(int i=0; i < getMobilityBoard().getHeight(); i++)
			for(int j=0; j < getMobilityBoard().getWidth(); j++)
				if(cboard.teamAt(j, i) + team == 0)
					if(canPieceKillKing(cboard, j, i, kx, ky))
					{
						cboard.setTileValue(toX, toY, pieceTo); 
						cboard.setTileValue(fromX, fromY, pieceFrom); 
						return false; 
					}
		
		
		//reverse movement 
		cboard.setTileValue(toX, toY, pieceTo); 
		cboard.setTileValue(fromX, fromY, pieceFrom); 
		
		return true; 
	}
	
	private boolean canPieceKillKing(ChessBoard cboard, int cx, int cy, int kx, int ky)
	{
		MovePattern pointer = cboard.getPieceMovementAt(cx, cy).getMovePattern(); 
		
		//it's a fking pawn
		if(pointer == null)
		{
			Board b = new MobilityBoard(cboard);
			
			cboard.getPieceMovementAt(cx, cy).specialAttackMark(b, 1, cx, cy, 0);
			
			return b.getTileValue(kx, ky) > 0; 
		}
		
		
		while(pointer != null)
		{
			//track++; 
			int itt = 0; 
			int ox ;
			int oy ;
	
			do{
				itt++; 
				ox = cx + pointer.x * itt; 
				oy = cy + pointer.y * itt; 
				
				//System.out.printf("o:%d %d k:%d %d\n",ox,oy, kingX, kingY);
				
				if(ox == kx && oy == ky)
					return true; 
				
			}while(cboard.getPieceMovementAt(cx, cy).isContinued() && cboard.isEmpty(ox, oy)); 
			
			//if(mboard.getChessBoard().teamAt(ox, oy)+team == 0)
			//mark all points

			//see if king is marked 
			pointer = pointer.next; 
		}
		
		return false; 
	}
	
	/**
	 * Not gonna correct the spelling at this point. This will locate kings of given team and find piece if enemy team that could possibly kill king. 
	 * 
	 * The killers are used later while marking movement. 
	 * @param team
	 * @return list of killers 
	 * 
	 * 
	 */
	public ArrayList<Integer> calebrate(int team)
	{
		MobilityBoard threat = new MobilityBoard(mboard.getChessBoard()); 
		Marker marker = new Marker(threat); 
		
		ArrayList<Integer> killers = new ArrayList<Integer>();
		
		int pos = findKing(team);
		int kingX = pos%getMobilityBoard().getWidth(); 
		int kingY = pos/getMobilityBoard().getHeight(); 
		
		
		//find king 
		for(int i=0; i<threat.getHeight(); i++)
			for(int j=0; j<threat.getWidth(); j++)
			{
				
				if(threat.getChessBoard().teamAt(j, i) == -team)
				{
					marker.markMovement(threat.getChessBoard().getPieceMovementAt(j, i), j, i, -team);  
				}
				if(threat.getTileValue(kingX, kingY) > 0)
				{
					killers.add(i*8 + j); // i/8 j%8 don't forget 
					threat.setTileValue(kingX, kingY, 0);  
				}
			}
		
		if(killers.size() > 0)
			threat.setTileValue(kingX, kingY, 1);  
		
		this.killers = killers; 
		
		return killers; 
	}
	
	public boolean isThreatened()
	{
		return killers.size() > 0; 
	}
	
	private int findKing(int team)
	{
		for(int i=0; i<getMobilityBoard().getHeight(); i++)
			for(int j=0; j<getMobilityBoard().getWidth(); j++)
			{
				if(getMobilityBoard().getChessBoard().getPiece(j, i) == Piece.TYPE_KING && getMobilityBoard().getChessBoard().teamAt(j, i) == team)
				{
					return i*getMobilityBoard().getHeight() + j; 
				}
			}
		return -1; 
	}
	
	/**
	 * 
	 * 
	 * NOTE: Check after move is made 
	 * @param team team to check for 
	 * @return
	 */
	public boolean isCheck(int team)
	{
		MobilityBoard mb = new MobilityBoard(getMobilityBoard().getChessBoard()); 
		Marker m = new Marker(mb); 
		m.calebrate(team); 
		
		return  m.isThreatened();
	}
	
	/**
	 * 
	 * 
	 * NOTE: Check after move is made 
	 * @param team team to check for 
	 * @return
	 */
	public boolean isCheckMate(int team)
	{
		int pos  = findKing(team); 
		int x = pos%getMobilityBoard().getWidth(); 
		int y = pos/getMobilityBoard().getHeight(); 
		
		MobilityBoard mb = new MobilityBoard(getMobilityBoard().getChessBoard()); 
		Marker m = new Marker(mb); 
		m.calebrate(team); 
		
		return m.markPieceAt(x, y)==0 && m.isThreatened(); 
		
	}
	
	public boolean didLose(int team)
	{
		getMobilityBoard().reset();	
		
		int sum =0; 
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
			{
				if(getMobilityBoard().getChessBoard().teamAt(j, i) == team)
				{
					int pieceMove = markPieceAt(j, i); 
					
					//System.out.println("Piece at " + j + i + " has " + pieceMove);
					
					sum += pieceMove; 
					getMobilityBoard().reset();	
				}
			}
		
		return sum == 0; 
	}
}
