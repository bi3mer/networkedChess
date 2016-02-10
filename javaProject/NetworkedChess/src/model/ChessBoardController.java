package model;

/**
 * Allows two player to play 
 * 
 * 
 * @author KLD 
 */
public class ChessBoardController 
{
	private ChessBoard cboard; 
	private MobilityBoard mboard; 
	
	public final static int PLAYER_ONE = 1; 
	public final static int PLAYER_TWO = -1;
	 

	public ChessBoardController(int player)
	{
		cboard = new ChessBoard(); 
		mboard = new MobilityBoard(cboard);
		//this.player = player; 
		
		//piece order 
		int[] pieces = {Piece.TYPE_PAWN,Piece.TYPE_ROOK, 
				Piece.TYPE_KNIGHT, Piece.TYPE_BISHOP, Piece.TYPE_KING, Piece.TYPE_QUEEN }; 
		
		for(int i=0; i<16; i++)
		{
			//math 
			int piece = pieces[(i/8)*( (i-7)+((i/13)*(3-((2*i)%13))) )%6]; 
			
			//positive 
			cboard.setTileValue(i%8, 1-i/8, piece); 
		
			//negative top 
			cboard.setTileValue(i%8, 6+i/8, -piece); 
		}//end placing pieces of chess
		
	}
	
	public void sentTurn(int fromX, int fromY, int toX, int toY)
	{
		
	}
	
	public void recieveTurn(int fromX, int fromY, int toX, int toY)
	{
		
	}
	
	public boolean isMoveValid(int fromX, int fromY, int toX, int toY)
	{
		mboard.reset();
		
		return true; 
	}

	
}
