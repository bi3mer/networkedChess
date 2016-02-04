package KLDtest;

import model.Piece;

public class MathTest {

	public static void main(String[] args) 
	{
		
		
		//int left = 0; 
		//int right = 1; 
	
		//int tag = 1; 
		//int team = -1; 
		//int piece = 1; 
		
		/*
		int y = 8- (9 - (team))%9;
		   
		System.out.printf("team %d, y: %d\n", team,y);
		   
		team = -1; 
		 y = 8- (9 - (team))%9;
		System.out.printf("team %d, y: %d\n", team,y);
		*/
		
		//convert -1 and 1 >> into >> 0 and 1 
		//team = (team+1)/2; 
		///int k = Piece.TYPE_KING; //1 -> 0 & 1
		//int r = Piece.TYPE_ROOK; //3 -> 2,3  ,4,5
				
		//int index = (team+piece+tag%(piece))%(piece+1);
		//if(didMove(piece, team, tag))
		
		int i=0; 
		int sum=0;
		int[] should = {0,0,1,1,2,3,4,5};
		
		//boolean no = false; 
		
		//-1 up, 1 down
		for(int piece=1; piece<4; piece+=2)
		{
			for(int team=1; team>-2; team-=2)//0 left, 1 right 
			{
				//1 king, 3 rook 
				for(int tag=0; tag<2; tag++)
				{
					int index =(team+piece+(tag%piece))/(1+(piece%3));
					System.out.printf("index: %d->%d, team:%2d, tag: %d, piece: %d\n",index,should[i++],team,tag,piece);
					sum+=index;  
				}
			}
		}
		
		System.out.println("Sum: " + sum); //16 
		
		
		
		
		
		
	}

}
