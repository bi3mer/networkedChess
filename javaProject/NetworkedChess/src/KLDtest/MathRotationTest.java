package KLDtest;

/**
 * Testing math rotation for a given point (x,y) in four directions 
 * 
 * @author KLD
 */
public class MathRotationTest 
{
	/**
	 * sucksess 
	 */
	public static void main(String[] args) 
	{
		//point 
		int x = 0; 
		int y = 1; 
		
		//initial rotation 
		double initR = Math.atan((double)y/x); 
		
		//r 
		double length = Math.sqrt(x*x + y*y); 
		
		//rotate counter clockwise
		for(int i=0;i<4; i++)
		{
			//math 
			int px =(int) Math.round( length*(Math.cos(initR+(i*Math.PI)/2)) ); 
			int py =(int) Math.round( length*(Math.sin(initR+(i*Math.PI)/2)) ); 
		
			System.out.printf("%d %d\n", px,py);
		}

	}

}
