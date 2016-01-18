package model;

/**
 * Chess piece each with it's movement set
 * @author KLD
 *
 */
public class Piece 
{
	//0 is reserved for empty tile
	public final static int TYPE_KING = 1; 
	public final static int TYPE_QUEEN = 2; 
	public final static int TYPE_ROOK = 3; 
	public final static int TYPE_BISHOP = 4; 
	public final static int TYPE_KNIGHT = 5; 
	public final static int TYPE_PAWN = 6; 
	
	public final static int TYPE_UNKNOWN = 7; 

	//TODO useless value
	private int type; 
	
	/**
	 * movement set
	 */
	public PieceMovement movement; 

	/**
	 * @param movement piece movement set 
	 */
	public Piece(PieceMovement movement)
	{
		this.movement = movement; 
		this.type = TYPE_UNKNOWN; //TODO useless 
	}
	
	/**
	 * 
	 * @param type useless 
	 * @param movement movement set
	 */
	public Piece(int type, PieceMovement movement)
	{
		this(movement); 
		this.type = type;  //TODO very useless
	}
	
	//TODO useless function
	public int getType()
	{
		return type; 
	}
	
}
