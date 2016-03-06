package model;

/**
 * A two dimensional integer grid that can identifies empty and non empty tiles as well as modifying content within bounds. 
 * @author KLD
 */
public class Board 
{
	/**
	 * holds board values 
	 */
	private int[][] board; 
	
	/**
	 * holds width
	 */
	private int width; 
	
	/**
	 *  holds height 
	 */
	private int height; 
	
	/**
	 * empty
	 */
	public final static int EMPTY = 0; 
	
	/**
	 *  Sets board size and initialize values with given value
	 *  
	 *   @param fill filled value 
	 */
	public Board(int width, int height, int fill)
	{
		this.width = width; 
		this.height = height; 
		board = new int[height][width]; 
		
		for(int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				board[i][j]=fill; 
	}
	
	/**
	 *  Sets board size and initialize values with 0 
	 */
	public Board(int width, int height)
	{
		this(width, height, EMPTY); 
	}
	
	/**
	 * Sets value at given position 
	 * @param x coordinate
	 * @param y coordinate
	 * @param value true if position is in bounds, otherwise false
	 */
	public boolean setTileValue(int x, int y, int value)
	{
		if(isOutBounds(x,y))
			return false; 
		
		board[y][x] = value; 
		return true; 
	}
	
	/**
	 *  Returns value of given position 
	 * @param x coordinate
	 * @param y coordinate
	 * @return 0 if position is out of bounds, otherwise stored tile value
	 */
	public int getTileValue(int x, int y)
	{
		if(isInBounds(x,y))
			return board[y][x]; 
		
		return 0; 
	}

	/**
	 * @param x coordinate 
	 * @param y coordinate
	 * @return true if tile at given position is empty
	 */
	public boolean isEmpty(int x, int y)
	{
		if(isInBounds(x, y))
		return (board[y][x] == EMPTY);
		
		return false; 
	}

	/**
	 * @param x coordinate 
	 * @param y coordinate
	 * @return true if tile at given position is not empty
	 */
	public boolean isOccupied(int x, int y)
	{
		if(isInBounds(x, y))
			return (board[y][x] != EMPTY);
	
		return false; 
	}
	
	/**
	 * @return board width 
	 */
	public int getWidth()
	{
		return width; 
	}
	
	/**
	 * @return board height 
	 */
	public int getHeight()
	{
		return height; 
	}

	/**
	 * @param x x coordinate 
	 * @param y y coordinate 
	 * @return true if position is out of board bounds, otherwise false
	 */
	public boolean isOutBounds(int x, int y)
	{
		return (x < 0 || x >= width || y < 0 || y >= height); 
	}
	
	/**
	 * @param x x coordinate 
	 * @param y y coordinate 
	 * @return true if position is within board bounds, otherwise false
	 */
	public boolean isInBounds(int x, int y)
	{
		return !isOutBounds(x, y); 
	}
	
	/**
	 * Fills the board with a number 
	 * @param fill filler 
	 */
	public void fill(int fill)
	{
		for(int i = 0; i < height; i++)
			for(int j = 0; j < width; j++)
				board[i][j] = fill; 	
	}
	
	/**
	 * adds the passed board value to it's value with corresponding position 
	 * @param b
	 */
	public void add(Board b)
	{
		for(int i = 0; i < height; i++)
			for(int j = 0; j < width; j++)
				board[i][j] += b.board[i][j]; 	
	}
	
	/**
	 * multiply the passed board value to it's value with corresponding position 
	 * @param b
	 * @retrurn number of changed values 
	 */
	public int multiply(Board b)
	{
		int changes = 0; 
		for(int i = 0; i < height; i++)
			for(int j = 0; j < width; j++)
			{
				int previous = board[i][j]; 
				board[i][j] *= b.board[i][j]; 	
				
				if(board[i][j]!=previous)
					changes++; 
			}
		return changes; 
	}
	
	/**
	 * @return a string image of the board with integer values
	 * center point being like in real life 
	 */
	public String toString()
	{
		String build = ""; 
		
		for(int i =height-1; i>=0; i--)
		{
			for(int j=0; j<width; j++)
			{
				build+= String.format("%2d ", board[i][j]); 
			}
			build+="\n";
		}
		
		return build; 
	}
	
	/**
	 * Return a cloned object 
	 */
	public Board clone()
	{
		Board clone = new Board(width, height); 
		
		for(int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				clone.board[i][j] = board[i][j]; 
 		
		return clone; 
	}

}
