package factory;

import java.util.ArrayList;

import intf.ChessFactory;
import model.KingMovement;
import model.MovePattern;
import model.PawnMovement;
import model.Piece;
import model.PieceMovement;

/** Responsible of creating pieces either by index (see Piece.java) or by function. 
 * 
 * @author KLD
 * @see Piece
 * 
 * @TODO singleton and document 
 */
public class PieceFactory implements ChessFactory<Piece>
{
	//singleton 
	private static PieceFactory insentce; 
	
	public static PieceFactory instence()
	{
		if(insentce==null)
			insentce = new PieceFactory(); 
		
		return insentce; 
	}
	//end singleton 

	/**
	 * memorize created pieces by index  
	 */
	private ArrayList<Piece> pieces; 
	
	private PieceFactory() 
	{
		pieces = new ArrayList<>(); 
	}
	
	@Override
	public Piece factor(int index) 
	{
		while(pieces.size() <= index )
			pieces.add(null); 
		
		if(pieces.get(index) == null)
			initlize(index); 
	
		return pieces.get(index);
	}

	private void initlize(int index) 
	{
		if(index == Piece.TYPE_BISHOP)
			pieces.set(index, factorBishop()); 
		else if(index == Piece.TYPE_KING)
			pieces.set(index, factorKing()); 
		else if (index == Piece.TYPE_KNIGHT)
			pieces.set(index, factorKnight()); 
		else if (index == Piece.TYPE_QUEEN)
			pieces.set(index, factorQueen()); 
		else if (index == Piece.TYPE_PAWN)
			pieces.set(index, factorPawn()); 
		else if (index == Piece.TYPE_ROOK)
			pieces.set(index, factorRook()); 
	}

	@Override
	public Piece factorKing() 
	{
		MovePattern rookLike = PieceMovement.createMove(1, 0); 
		MovePattern bishipLike = PieceMovement.createMove(1, 1); 
	
		//attach
		rookLike.next.next.next.next = bishipLike; 
		
		PieceMovement m = new KingMovement(rookLike);
		Piece noobking = new Piece(Piece.TYPE_KING, m);
	
		return noobking;
	}

	@Override
	public Piece factorQueen() 
	{
		MovePattern rookLike = PieceMovement.createMove(1, 0); 
		MovePattern bishipLike = PieceMovement.createMove(1, 1); 
	
		//attach
		rookLike.next.next.next.next = bishipLike; 
	
		
		PieceMovement m = new PieceMovement(rookLike, true); 
		Piece queen = new Piece(Piece.TYPE_QUEEN, m);
	
		return queen; 
	}

	@Override
	public Piece factorRook() 
	{
		PieceMovement m = new PieceMovement(PieceMovement.createMove(1, 0), true); 
	
		Piece rockSolid = new Piece(Piece.TYPE_ROOK, m);
	
		return rockSolid; 
	}

	@Override
	public Piece factorBishop() 
	{
		PieceMovement m = new PieceMovement(PieceMovement.createMove(1, 1), true); 
		Piece satan = new Piece(Piece.TYPE_BISHOP, m);
	
		return satan; 
	}

	@Override
	public Piece factorKnight()
	{
		MovePattern horse1 = PieceMovement.createMove(1, 2); 
		MovePattern horse2 = PieceMovement.createMove(1, -2); 
	
		//attach
		horse1.next.next.next.next = horse2; 
			
				
		PieceMovement m = new PieceMovement(horse1, false); 
		Piece DoNotAssumeIAmAManYouSexist = new Piece(Piece.TYPE_KNIGHT, m);
	
		return DoNotAssumeIAmAManYouSexist; 
	}

	@Override
	public Piece factorPawn() 
	{
		PieceMovement m = new PawnMovement(); //this piece of dick follows no logic 
		Piece dick = new Piece(Piece.TYPE_PAWN, m);
		
		return dick;
	}
}
