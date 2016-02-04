package factory;

import java.util.ArrayList;

import intf.ChessFactory;
import model.KingMovement;
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
public class PieceMovementFactory implements ChessFactory<Piece>
{
	private ArrayList<Piece> pieces; 
	
	public PieceMovementFactory() 
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
		PieceMovement m = new KingMovement();//new PieceMovement(PieceMovement.createMove(1, 1, PieceMovement.createMove(0, 1, null) ), false); 
		Piece noobking = new Piece(Piece.TYPE_KING, m);
	
		return noobking;
	}

	@Override
	public Piece factorQueen() 
	{
		PieceMovement m = new PieceMovement(PieceMovement.createMove(1, 1, PieceMovement.createMove(0, 1, null) ), true); 
		Piece queen = new Piece(Piece.TYPE_QUEEN, m);
	
		return queen; 
	}

	@Override
	public Piece factorRook() 
	{
		PieceMovement m = new PieceMovement(PieceMovement.createMove(1, 0, null ), true); 
		Piece rockSolid = new Piece(Piece.TYPE_ROOK, m);
	
		return rockSolid; 
	}

	@Override
	public Piece factorBishop() 
	{
		PieceMovement m = new PieceMovement(PieceMovement.createMove(1, 1, null), true); 
		Piece satan = new Piece(Piece.TYPE_BISHOP, m);
	
		return satan; 
	}

	@Override
	public Piece factorKnight()
	{
		PieceMovement m = new PieceMovement(PieceMovement.createMove(2, 1, PieceMovement.createMove(2, -1, null) ), false); 
		Piece DoNotAssumeIAmAManYouSexist = new Piece(Piece.TYPE_KNIGHT, m);
	
		return DoNotAssumeIAmAManYouSexist; 
	}

	@Override
	public Piece factorPawn() 
	{
		PieceMovement m = new PawnMovement(); //this piece if dick follows no logic 
		Piece dick = new Piece(Piece.TYPE_PAWN, m);
		
		return dick;
	}
}
