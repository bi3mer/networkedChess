package KLDtest;

import factory.PieceFactory;
import model.Piece;

public class MovePatternTest {

	public static void main(String[] args) 
	{
		PieceFactory fact = PieceFactory.instence(); 

		
		//System.out.println("" , fact.factorBishop().movement.getMovePattern());

		int[] piece = {Piece.TYPE_KING, Piece.TYPE_QUEEN, Piece.TYPE_BISHOP, Piece.TYPE_KNIGHT, Piece.TYPE_PAWN, Piece.TYPE_ROOK}; 
		String[] names = "king,queen,bish,night,rekt,rock".split(",");
		
		for(int i=0; i<piece.length; i++)
		{
			System.out.println(names[i]+"- " + fact.factor(piece[i]).movement.getMovePattern());
		}
	}

}
