package model;

/**
 * A linked list of movement pattern values that appear in four direction. 
 * 
 * @see PieceMovement#createMove(int, int, Move)
 * 
 * @author KLD
 */
public class MovePattern
{
	public int x; 
	public int y; 
	
	public MovePattern next; 
	
	public MovePattern(int x, int y)
	{
		this.x = x; 
		this.y = y; 
	}
	
	public String toString()
	{
		String build= "{" + String.format("(%d,%d)", x,y); 
		
		MovePattern p = next; 
		
		while(p!=null)
		{
			build += String.format(",(%d,%d)", p.x,p.y); 
			 
			p = p.next;
		}
		
		return build + "}"; 
	}
}