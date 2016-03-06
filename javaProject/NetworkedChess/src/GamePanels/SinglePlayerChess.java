package GamePanels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import KLD.Game;
import KLD.GameFrame;
import KLD.Input;
import KLD.Loader;
import KLD.cmd.Command;
import KLD.cmd.Draw;
import model.ChessBoard;
import model.Marker;
import model.MobilityBoard;
import model.Piece;
import ui.BoardUI;
import ui.PieceImageFactory;

public class SinglePlayerChess extends Game 
{
	private ChessBoard cboard; 
	private MobilityBoard mboard; 
	private Marker marker;  
	
	private int tileWidth;
	private int tileHeight;
	
	private int offsetX;
	private int offsetY;
	
	private int selectedX, selectedY; 
	
	private boolean whiteDead; 
	private boolean blackDead; 
	
	private String whiteMessage; 
	private String blackMessage; 
	
	private Command forfeitCmd; 
	private Command undoCmd; 
	
	private Rectangle undoButton; 
	private Rectangle forfeitButton; 
	
	// 0 false, 7 true 
	/**
	 * Reversing board for player two 
	 */
	private int reverse; 
	
	BoardUI boradui; 
	//PawnPromotionUI promotion; 
	
	int promotionTeam; 
	
	private int undoRequested; 
	private int forfeitRequested; 
	
	
	@Override
	protected void init() 
	{
		name = "SinglePlayerChess"; //not needed 
		whiteMessage = ""; 
		blackMessage = ""; 
		
		cboard = new ChessBoard(); 
		mboard= new MobilityBoard(cboard); 
		marker = new Marker(mboard); 
		
		whiteDead = false; 
		blackDead = false; 
		promotionTeam = 0; 
		
		undoRequested = 0; 
		forfeitRequested = 0; 
		
		placePieces(); 
	
		tileWidth = GameFrame.width/10; 
		tileHeight = GameFrame.height/10; 
		
		offsetX = GameFrame.width/2 - tileWidth*4; 
		offsetY = GameFrame.height/2 - tileHeight*4 - 20; 
		
		//reverse = 0; 
	
		//this will allow the board to be drawn automatically 
		boradui = new BoardUI(mboard, reverse); 
		boradui.setSizes(offsetX, offsetY, tileWidth, tileHeight);
		revereBoard();
		
		undoCmd = new Command("Undo") {
			
			int offsetX = GameFrame.width/2 ;
			int offsetY = GameFrame.height/2 ;
			
			@Override
			protected void draw(Graphics g) 
			{
				Game.setFade(g, .8f);
				g.setColor(Color.black); 
				g.fillRect(0, 0, GameFrame.width, GameFrame.height);
				
				Game.setFade(g, 1f);
				
			
				//TODO replace with == myTeam 
				if(undoRequested != 0)
				{
					g.setColor(Color.gray); 
					g.fillRect(offsetX - 150,  offsetY - 60, 200, 80);
					
					g.setColor(Color.black);
					g.drawString("Awaiting response", offsetX + tileWidth*2 - 80, offsetY + tileHeight/2); 
				}
				else //it's from the other player 
				{
					//g.drawString("Undo is requested", offsetX + tileWidth*2 - 80, offsetY + tileHeight/2); 
					
					Loader load = new Loader();
					
					g.drawImage(load.loadRawImage("g/undo.cond.png"), offsetX - 150,  offsetY - 60,  null); 
					
					g.drawImage(load.loadRawImage("g/accept.png"), offsetX - 100, offsetY, 90, 40, null);
					g.drawImage(load.loadRawImage("g/Decline.png"), offsetX + 20, offsetY, 90, 40, null);
					
				}
			}
			
			@Override
			protected void action() 
			{
				//TODO chnage to if undoRequested equals my team
				if(undoRequested == 0 && input.mouseIsClicked())
				{
					if(input.isIn(new Rectangle(offsetX - 100, offsetY, 90, 40)))
					{
						System.out.println("accept undo");
						//TODO accept undo
						//cboard.untoLastMove(fromX, fromY, toX, toY);
						
						//this.deactivate();
						this.disable();
					}
					else if (input.isIn(new Rectangle(offsetX + 20, offsetY, 90, 40)))
					{
						System.out.println("no undo");
						//decline undo 
						//this.deactivate();
						this.disable();
					}
				
				}
				
			}
		};
		
		forfeitCmd = new Command("Forfiet") {
			
			int offsetX = GameFrame.width/2 ;  
			int offsetY = GameFrame.height/2; 
			
			@Override
			protected void draw(Graphics g) 
			{
				//int offsetX = GameFrame.width/2 - (4*tileWidth)/2; 
				//int offsetY = GameFrame.height/2 + (tileHeight)/2; 
				
				Game.setFade(g, .8f);
				g.setColor(Color.black); 
				g.fillRect(0, 0, GameFrame.width, GameFrame.height);
				
				Game.setFade(g, 1f);
				g.setColor(Color.black);
				
				Loader load = new Loader();
				
				g.drawImage(load.loadRawImage("g/surrenderpop.png"), offsetX - 150,  offsetY - 60,  null); 
					
				g.drawImage(load.loadRawImage("g/Forfeit.png"), offsetX - 100, offsetY, 90, 40, null);
				g.drawImage(load.loadRawImage("g/cancel.png"), offsetX + 20, offsetY, 90, 40, null);
				
			}
			
			@Override
			protected void action() 
			{
				//TODO chnage to if forfeitRequestedRequested equals my team
				if(forfeitRequested == 0 && input.mouseIsClicked())
				{
					if(input.isIn(new Rectangle(offsetX - 100, offsetY, 90, 40)))
					{
						System.out.println("forfeit");
						//TODO accept undo
						//cboard.untoLastMove(fromX, fromY, toX, toY);
						
						//this.deactivate();
						this.disable();
					}
					else if (input.isIn(new Rectangle(offsetX + 20, offsetY, 90, 40)))
					{
						System.out.println("nope");
						//decline undo 
						//this.deactivate();
						this.disable();
					}
				
				}
				
			}
		};
		
		undoButton = new Rectangle(offsetX, GameFrame.height-80 ,90, 40);
		forfeitButton = new Rectangle(offsetX + 100, GameFrame.height-80 ,90, 40);
		
		
		//automate board draw 
		this.addDraw(new Draw("UnforAndForfitDraw") {
			Loader load = new Loader(); 
			@Override
			protected void draw(Graphics g) 
			{
				g.drawImage(load.loadRawImage("g/Forfeit.png"), forfeitButton.x, forfeitButton.y, forfeitButton.width, forfeitButton.height, null);
				g.drawImage(load.loadRawImage("g/Undo.png"), undoButton.x, undoButton.y, undoButton.width, undoButton.height, null);
				
			}
		});
		this.add(boradui);
		this.add(undoCmd);
		this.add(forfeitCmd);

		
		undoCmd.disable();
		forfeitCmd.disable();
	}
	
	@Override
	protected void draw(Graphics g) 
	{
		g.setColor(Color.black);
		
		String s = (whiteDead)? "White team is under threat"  : "White safe"; 
		g.drawString(s, 20, 20);
		String b = (blackDead)? "Black team is under threat"  : "Black safe"; 
		g.drawString(b, 20, 40);

		g.drawString(whiteMessage, 200, 20);
		g.drawString(blackMessage, 200, 40);

		
		
		if(promotionTeam !=0)
		{
			drawPromotionMenu(g);
		}
		
		
		
	}//end color 
	
	@Override
	protected void update() 
	{
		/*if(!myTurn)
			return; 
		*/
		//TODO change != 0 to == myTeam. Player can only promote his pawns 
		
		if(!input.mouseIsClicked())
			return; 
		
		if(promotionTeam != 0)
		{
			int offsetX = GameFrame.width/2 - (4*tileWidth)/2; 
			int offsetY = GameFrame.height/2 - (4*tileHeight)/2 + tileHeight; 
				
			if (input.isIn(new Rectangle(offsetX, offsetY, tileWidth*4, tileHeight)))
			{
				int[] pieces = {Piece.TYPE_ROOK, Piece.TYPE_BISHOP, Piece.TYPE_KNIGHT, Piece.TYPE_QUEEN};
				
				int x = ((Input.point.x - offsetX) / tileWidth); 
				//int y = ((Input.point.y - offsetY) / tileHeight); 
				
				cboard.setTileValue(selectedX, selectedY, pieces[x]); 
				
				marker.calebrate(cboard.teamAt(selectedX, selectedY)); 
				
				//TODO server sent pieces[x] to finalize promotion 
				
				promotionTeam = 0; 
			}
		}
		else if(input.isIn(new Rectangle(offsetX, offsetY, tileWidth*8, tileHeight*8)) )
		{
			int x = ((Input.point.x - offsetX) / tileWidth); 
			int y = ((Input.point.y - offsetY) / tileHeight); 
			
			boardClicked(x,y);  
		}
		else if(input.isIn(forfeitButton))
		{
			forfeitCmd.enable(); 
		}
		else if(input.isIn(undoButton))
		{
			undoCmd.enable(); 
		}
		else if(input.mouseIsClicked())
		{
			mboard.reset(); 
		}
		
	}
	
	public void forfitRequest()
	{
		
	}
	
	public void undoRequest()
	{
		
	}
	
	
	public boolean recieveMovement(int fromX, int fromY, int toX, int toY)
	{
		
		cboard.movePiece(fromX, fromY, toX, toY);
		return true; 
	}
	
	public boolean recievePromotion(int x, int y, int piece)
	{
		int team = cboard.teamAt(x, y); 
		cboard.setTileValue(x, y, team*piece); 
		return true; 
	}
	
	public void revereBoard()
	{
		reverse = 7; 
		boradui.setReverse(reverse);
		//
	
	}
	
	public void placePieces()
	{
		int[] pieces = {Piece.TYPE_PAWN, Piece.TYPE_ROOK, Piece.TYPE_KNIGHT, Piece.TYPE_BISHOP, Piece.TYPE_QUEEN, Piece.TYPE_KING }; 
			
		//int[] pieces = {0, Piece.TYPE_ROOK, 0,0, 0, Piece.TYPE_KING }; 
				
	
		for(int i=0; i<16; i++)
		{
			int piece = pieces[(i/8)*(  (i-7)+((i/13)*(3-((2*i)%13)))  )%6]; 
			
			//positive bottom
			cboard.setTileValue(i%8, 1-i/8, piece); 
		
			//negative top 
			cboard.setTileValue(i%8, 6+i/8, -piece); 	
		}
		
		//board.setTileValue(4, 4, Piece.TYPE_PAWN);
		
		//board.setTileValue(4, 2, -Piece.TYPE_PAWN);
		
		
		
	
	}//end place 
	
	public void boardClicked(int x, int y)
	{
		y = Math.abs(reverse-y); 
		
		//TODO if cboard.teamAt(x,y) doesn't match my team, I cannot move the unit 
		if(mboard.getTileValue(x, y) > MobilityBoard.MARK_INVISIBLE)
		{
			//rook or king moved notifications 
			int notification =	cboard.movePiece(selectedX, selectedY, x, y);
			
			//TODO server send coordinates 
				
			if(cboard.getPiece(x, y) == Piece.TYPE_PAWN && y%7==0)
			{
				promotionTeam = cboard.teamAt(x, y); 
			}
		
			//notify mobility to adjust castling 
			if (notification > 0)
			{
				notification--; 
				mboard.setMoved(notification); 
			
			}//end if notification 
			
			marker.calebrate(1);
			
			//update check and check mate 
			if((whiteDead = marker.isThreatened()))
				if(marker.isCheckMate(1))
					blackMessage = "Checkmate!";
				else
					blackMessage = "Check.."; 
			else
				blackMessage = ""; 
			
			
			marker.calebrate(-1);
			
			if((blackDead = marker.isThreatened()))
				if(marker.isCheckMate(-1))
					whiteMessage = "Checkmate!";
				else 
					whiteMessage = "Check.."; 
			else
				whiteMessage = ""; 
			
			
			//calibrate the other team 
			int team = cboard.teamAt(x, y); 
			if(marker.didLose(team))
			{
				if(team==1)
				{
					whiteMessage = "Lost!"; 
					blackMessage = "Won!";
				}	
				else
				{
					whiteMessage = "Won!"; 
					blackMessage = "Lost!";
				}
			}
			mboard.reset();	
		}//end mobility 
		else
		{
			//System.out.println("selected");
			mboard.reset();	
			marker.markPieceAt(x, y); 		
		}
		
	
			selectedX = x; 
			selectedY = y; 
					
	}
		
	private void drawPromotionMenu(Graphics g)
	{
		int[] pieces = {Piece.TYPE_ROOK, Piece.TYPE_BISHOP, Piece.TYPE_KNIGHT, Piece.TYPE_QUEEN};
		
		g.setColor(Color.BLACK);
		Game.setFade(g, .8f);
		
		g.fillRect(0, 0, GameFrame.width, GameFrame.height);
		
		int offsetX = GameFrame.width/2 - (pieces.length*tileWidth)/2; 
		int offsetY = GameFrame.height/2 - (pieces.length*tileHeight)/2 + tileHeight; 
		
		Game.setFade(g, 1f);
		g.setColor(Color.gray); 
		g.fillRect(offsetX, offsetY, tileWidth*4, tileHeight);
		
		//TODO chnage to == myTeam
		if(promotionTeam == 1)
			for(int i=0; i<pieces.length; i++)
			{
				//g.drawImage(PieceImageFactory.Instnece().factor(pieces[i]), offsetX + i*tileWidth, offsetY + tileHeight, null); 
				int pieceIndex = 2*pieces[i] - (cboard.teamAt(selectedX, selectedY) > 0? 0 : 1); 
				//draw tiles
				 g.drawImage(PieceImageFactory.Instnece().factor(pieceIndex), offsetX+i*tileWidth, offsetY,tileWidth, tileHeight, null); 
			}
		else
		{
			g.setColor(Color.black);
			g.drawString("Enemy is picking promotion", offsetX + tileWidth*2 - 80, offsetY + tileHeight/2);
		}
	}


}//end class

