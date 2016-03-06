package model;

/**
 * Identifies possibles tiles a piece can move and marks those areas with either move or attack markers 
 * 
 * @author KLD
 */
public class PieceMovement 
{
	/**
	 * True means the same pattern of a move occurs in greater distance 
	 */
	private boolean isContinued; 
	
	/**
	 * A linked list of movement offsets value(s) away from center 
	 */
	private MovePattern move;
	
	/**
	 * @param move movement offset(s) pattern 
	 * @param isCon True means the same pattern of a move occurs in greater distance, false means non-repeated. 
	 * 
	 * @see Move
	 */
	public PieceMovement(MovePattern move, boolean isCon)
	{
		this.move = move;
		isContinued = isCon; 	
	}
	
	/**
	 * Creates a movement with a single pattern 
	 * @param ox offset x pattern 
	 * @param oy offset y pattern 
	 * @param isCon True means the same pattern of a move occurs in greater distance, false means non-repeated. 
	 */
	public PieceMovement(int ox, int oy, boolean isCon)
	{
		this(new MovePattern(ox,oy), isCon); 
	}

	/**
	 * Creates a new move object with 4 directional rotations of offsets 
	 * @param x x offset 
	 * @param y y offset 
	 * @param move linked move if available. Otherwise, null 
	 * 
	 * @return a move pattern object 
	 */
	public static MovePattern createMove(int x, int y)
	{
		MovePattern m =  new MovePattern(x, y);
		int DIR = 4; 
		
		MovePattern point = m; 
		
		for(int i=1; i < DIR; i++)
		{
			//local x, y
			int lx = x; 
			int ly = y; 
			double initR = Math.atan((double)ly/lx); 
			double length = Math.sqrt(lx*lx + ly*ly); 
			
			//directed x, y
			int dx = (int) Math.round(length*(Math.cos(initR+(i*Math.PI)/2)) ); 
			int dy = (int) Math.round(length*(Math.sin(initR+(i*Math.PI)/2)) ); 
			
			point.next = new MovePattern(dx, dy); 
			point = point.next; 
		
		}
		
		return m; 
	}
	
	/**
	 * 
	 * @return movepattern linked list of movepatterns 
	 */
	public MovePattern getMovePattern()
	{
		return this.move; 
	}
	
	public boolean isContinued()
	{
		return this.isContinued; 
	}
	
	public int specialMarking(MobilityBoard mboard, int cx, int cy, int team)
	{
		return 0; 
	}
	
	public int specialAttackMark(Board b, int mark, int cx, int cy, int team)
	{
		return 0; 
	}
	
}

