package KLDtest;

import model.ChessBoard;
import model.MobilityBoard;
import model.Piece;

/**
 * Testing piece mobility given other pieces both friendly and enemy. 
 * 
 * @author KLD
 */
public class MovabilityTest //mobility*
{
	/**
	 * Test marking in board ended successfully 
	 */
	public static void main(String[] args) 
	{
		//Variables needed
		ChessBoard chess =  new ChessBoard();
		MobilityBoard mboard = new MobilityBoard(chess); 
		
		int selectedPieceX = 3; 
		int selectedPieceY = 2; 
		int selectedPieceType = Piece.TYPE_PAWN;
		
		int friendlyPieceX =  5; 
		int friendlyPieceY =  5; 
		
		int enemyPieceX =  4; 
		int enemyPieceY =  3; 
		
		//add piece to board
		chess.placePiece(selectedPieceX, selectedPieceY, selectedPieceType);
		
		//printing board and mobility 
		System.out.print("Chess Board (piece index)\n"+chess);
		System.out.printf("King:  %d Queen: %d Rook:%d\nBishop:%d Knight:%d Pawn:%d\n\n", 
				Piece.TYPE_KING, Piece.TYPE_QUEEN, Piece.TYPE_ROOK, Piece.TYPE_BISHOP, Piece.TYPE_KNIGHT, Piece.TYPE_PAWN);
		System.out.println("Mobility: Free Space\n"+mboard);
		
		//select out piece 
		chess.selectForMark(selectedPieceX, selectedPieceY, mboard);
		
		//selecting piece for movement 
		System.out.println("Mobility: Marking Available Movement (with 1)\n"+mboard);
	
		//reset mobility board
		mboard.reset(); 
		
		//adding friendly piece 
		System.out.println("Adding friendly piece at "+ friendlyPieceX + " " + friendlyPieceY);
		chess.placePiece(friendlyPieceX, friendlyPieceY, Piece.TYPE_PAWN); //positive type is friendly 
		
		System.out.println("Seleting our previous for movement");
		chess.selectForMark(selectedPieceX, selectedPieceY, mboard);
		
		System.out.println("Mobility: friendly block\n"+mboard);
		
		//reset mobility board
		mboard.reset(); 
				
		//adding enemy piece 
		System.out.println("Adding enemy piece at " + enemyPieceX + " " + enemyPieceY );
		chess.placePiece(enemyPieceX, enemyPieceY, -Piece.TYPE_PAWN); //negative type is enemy with corresponding positive type 
				
		System.out.println("Seleting our initial piece for movement");
		chess.selectForMark(selectedPieceX, selectedPieceY, mboard);
				
		System.out.println("Mobility: Marking Attack (2 means move & destry) \n"+mboard);
		System.out.println("ChessBoard With all pieces (negative means enemy)\n"+chess);
	}
}
