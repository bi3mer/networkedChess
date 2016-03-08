package GamePanels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import KLD.Game;
import KLD.GameFrame;
import KLD.Input;
import KLD.Loader;
import KLD.cmd.Command;
import KLD.cmd.Draw;
import KLD.cmd.TimedCommand;
import KLD.cmd.TimedDraw;
import intf.MovementTracker;
import menus.MainMenu;
import model.ChessBoard;
import model.ChessPlayerController;
import model.Marker;
import model.MobilityBoard;
import model.Piece;
import ui.BoardUI;
import ui.PieceImageFactory;

public class MultiplayerChessGame extends Game
{
	private ChessBoard cboard; 
	private MobilityBoard mboard; 
	private Marker marker;  
	
	private int tileWidth;
	private int tileHeight;
	
	private int offsetX;
	private int offsetY;
	
	private int selectedX, selectedY; 
	
	private int winner; 
	
	private boolean whiteDead; 
	private boolean blackDead; 
	
	private String whiteMessage; 
	private String blackMessage; 
	
	private Command forfeitCmd; 
	private Command undoCmd; 
	
	private Rectangle undoButton; 
	private Rectangle forfeitButton; 
	
	private float timer; 
	
	// 0 false, 7 true 
	/**
	 * Reversing board for player two 
	 */
	private int reverse; 
	
	BoardUI boradui; 
	//PawnPromotionUI promotion; 
	
	int promotionTeam; 
	
	private int undoRequested; 
	
	private String otherPlayer;
	private int myTeam;
	private String myTurnString;
	private Boolean myTurn;
	private JSONObject previousMove;
	
	private int timeLimit; 
	
	/**
	 * Go to main menu and destroy current game frame
	 */
	private void endGame()
	{		
		
		add(new TimedCommand("Gamed ended", (int)10*GameFrame.FPS) 
		{
			
			@Override
			public void draw(Graphics g)
			{
				g.setColor(Color.black);
				Game.setFade(g, .8f);
				g.fillRect(0, 0, GameFrame.width, GameFrame.height);
				
				Game.setFade(g, 1f);
				
				String outcome = ""; 
				
				if(winner != myTeam)
				{
					outcome = "You lost";
				}
				else
				{
					outcome = "You won!";
				}
				
				g.setColor(Color.white);
				g.drawString(outcome, GameFrame.width/2 - 50, GameFrame.height/2);				
				
			}
			
			@Override
			public void deteminate()
			{
				super.deteminate();
				// Destroy current frame
				GameFrame.destroy();
				
				// Go to main menu
				MainMenu menu = new MainMenu();
				menu.setVisible(true);
				
			}

			@Override
			public void action() 
			{
				if(input.mouseIsClicked())
					deteminate(); 
				
			}
		}); 
		
	}
	
	/**
	 * For fit and then go to main menu
	 */
	private void forfeit()
	{
		try 
		{
			ChessPlayerController.getInstance().forfeit();
		} 
		catch (JSONException | IOException | InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.endGame();
	}
	
	private Boolean isPrevMove(JSONObject previousMove)
	{
		Boolean isPrevMove = true;
		
		try 
		{
			if(((JSONObject) previousMove.get("to")).getInt("x") == ((JSONObject) this.previousMove.get("to")).getInt("x"))
			{
				isPrevMove = false;
			}
			else if(((JSONObject) previousMove.get("to")).getInt("y") == ((JSONObject) this.previousMove.get("to")).getInt("y"))
			{
				isPrevMove = false;
			}
			else if(((JSONObject) previousMove.get("from")).getInt("x") == ((JSONObject) this.previousMove.get("from")).getInt("x"))
			{
				isPrevMove = false;
			}
			else if(((JSONObject) previousMove.get("from")).getInt("y") == ((JSONObject) this.previousMove.get("from")).getInt("y"))
			{
				isPrevMove = false;
			}
		} 
		catch (JSONException e) 
		{
			// pass
		}
		
		return isPrevMove;
	}
	
	private void opponentMove(JSONObject move)
	{
		// Add move to board
		try 
		{
			// Get form and to
			JSONObject from = (JSONObject) move.get("from");
			JSONObject to   = (JSONObject) move.get("to");
			
			System.out.println("from: " + from.toString());
			System.out.println("to:   " + to.toString());
			
			cboard.movePiece(from.getInt("x"), from.getInt("y"), to.getInt("x"), to.getInt("y"));
			
			// Check for checkmate and check and handle
			this.checkMate(to.getInt("x"), to.getInt("y"));
			
			// Opponent has made move, so I can now make a move
			this.myTurn = true;
		} 
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void init() 
	{	
		// Set string of other player
		this.otherPlayer = ChessPlayerController.getInstance().otherPlayer;
	
		whiteMessage = ""; 
		blackMessage = ""; 
		myTurnString = "";
		
		cboard = new ChessBoard(); 
		mboard= new MobilityBoard(cboard); 
		marker = new Marker(mboard); 
		
		whiteDead = false; 
		blackDead = false; 
		promotionTeam = 0; 
		
		undoRequested = 0; 
		timer = 0; 
		timeLimit = 100; 
		
		placePieces(); 
	
		tileWidth = GameFrame.width/10; 
		tileHeight = GameFrame.height/10; 
		
		offsetX = GameFrame.width/2 - tileWidth*4; 
		offsetY = GameFrame.height/2 - tileHeight*4 - 20; 
	
		//this will allow the board to be drawn automatically 
		boradui = new BoardUI(mboard, reverse); 
		boradui.setSizes(offsetX, offsetY, tileWidth, tileHeight);
		
		// TODO: add other player field
		
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
				
			
				// Refrence: replace with == myTeam (undoRequested != 0)
				if(undoRequested == myTeam)
				{
					g.setColor(Color.gray); 
					g.fillRect(offsetX - 150,  offsetY - 60, 200, 80);
					
					g.setColor(Color.white);
					g.drawString("Awaiting response", offsetX + 150 - 80, offsetY + 60); 
				}
				else //it's from the other player 
				{
					Loader load = new Loader();
					
					g.drawImage(load.loadRawImage("g/undo.cond.png"), offsetX - 150,  offsetY - 60,  null); 
					
					g.drawImage(load.loadRawImage("g/accept.png"), offsetX - 100, offsetY, 90, 40, null);
					g.drawImage(load.loadRawImage("g/Decline.png"), offsetX + 20, offsetY, 90, 40, null);
				}
			}
			
			@Override
			protected void action() 
			{
				if(undoRequested == -myTeam && input.mouseIsClicked())
				{
					Boolean inBounds = false;
					Boolean acceptUndoRequest = true;
					
					if(input.isIn(new Rectangle(offsetX - 100, offsetY, 90, 40)))
					{
						System.out.println("accept undo");
						//TODO accept undo
						inBounds = true;
						
						try 
						{
							JSONObject from = previousMove.getJSONObject("from");
							JSONObject to = previousMove.getJSONObject("to");
							cboard.untoLastMove(from.getInt("x"), from.getInt("y"), to.getInt("x"), to.getInt("y"));

							myTurn = !myTurn;
						} 
						catch (JSONException e) 
						{
							// pass
						}
					}
					else if (input.isIn(new Rectangle(offsetX + 20, offsetY, 90, 40)))
					{
						System.out.println("no undo");
						inBounds = true;
						acceptUndoRequest = false;
					}
					
					if(inBounds)
					{
						this.disable();
						
						try 
						{
							ChessPlayerController.getInstance().acceptOrDenyUndo(acceptUndoRequest);
						} 
						catch (JSONException | IOException | InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
				if(input.mouseIsClicked())
				{
					if(input.isIn(new Rectangle(offsetX - 100, offsetY, 90, 40)))
					{
						this.disable();

						// Forfeit
						forfeit();
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
		
		// Reverse board and set team
		if(ChessPlayerController.getInstance().isWhite)
		{
			this.myTurn = true;
			this.myTeam = 1;
			
			// also reverse the board cause player is on black pieces
			this.revereBoard();
		}
		else
		{
			this.myTurn = false;
			this.myTeam = -1;
		}
				
		// Add components to UI
		this.add(boradui);
		this.add(undoCmd);
		this.add(forfeitCmd);
		
		// Set pervious move
		this.previousMove = new JSONObject();
		
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
		
		g.drawString(myTurn? "My turn" : "His turn", 500, 30);
		
		if(myTurn)
			g.drawString((String.format("%2d:%2d", (timeLimit-(int)timer)/60,  (timeLimit-(int)timer)%60)).replaceAll(" ", "0"), 410,30);
		
	 
		if(promotionTeam !=0)
		{
			drawPromotionMenu(g);
		}
	}//end color 
	
	@Override
	protected void update() 
	{	
		if(myTurn)
		{
			if((timer+= 1f/GameFrame.FPS) >= timeLimit)
			{
				forfeit();	
			}
		}
		else 
		{
			timer = 0;
		}
		
		try 
		{
			// Get Updates from server
			JSONArray updates = ChessPlayerController.getInstance().getUpdate(true).getJSONArray("updates");
			
			// Loop through updates
			for(int i = 0; i < updates.length(); ++i)
			{
				// Get Object
				JSONObject update = (JSONObject) updates.get(i);
				
				// Accept or decline undo request
				if(update.has("undo"))
				{
					System.out.println("Undo Called!");
					// set request
					this.undoRequested = 0;
					
					// disable undo
					this.undoCmd.disable();
					
					// check if accepted
					if(update.getBoolean("undo"))
					{
						// accepted
						JSONObject move = update.getJSONObject("move");
						JSONObject from = move.getJSONObject("from");
						JSONObject to = move.getJSONObject("to");
//						this.cboard.untoLastMove(to.getInt("x"), to.getInt("y"), from.getInt("x"), from.getInt("y"));
						this.cboard.untoLastMove(from.getInt("x"), from.getInt("y"), to.getInt("x"), to.getInt("y"));

						System.out.println(from.toString() + ", " + to.toString());
						myTurn = !myTurn;
					}
					else
					{
						// Denied
					}
				}
				else if(update.has("undoRequest")) 
				{
					System.out.println("TODO: update to show undo request");
					this.undoRequested = -this.myTeam;
					this.undoCmd.enable();
				}
				else if(update.has("forfeit"))
				{
					winner = myTeam;
					this.endGame();
				}
				else if(update.has("move"))
				{
					this.previousMove = update.getJSONObject("move");
					this.opponentMove(update.getJSONObject("move"));
				}
				else if(update.has("previousMove"))
				{
					// get move
					JSONObject prevMove = update.getJSONObject("previousMove");
					
					// Check move
					if(this.previousMove != null && this.isPrevMove(prevMove))
					{
						this.previousMove = prevMove;
						this.opponentMove(prevMove);
					}
				}
			}
		} 
		catch (JSONException | IOException | InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!input.mouseIsClicked())
			return; 
		
		if(promotionTeam == this.myTeam)
		{
			int offsetX = GameFrame.width/2 - (4*tileWidth)/2; 
			int offsetY = GameFrame.height/2 - (4*tileHeight)/2 + tileHeight; 
				
			if (input.isIn(new Rectangle(offsetX, offsetY, tileWidth*4, tileHeight)))
			{
				int[] pieces = {Piece.TYPE_ROOK, Piece.TYPE_BISHOP, Piece.TYPE_KNIGHT, Piece.TYPE_QUEEN};
				
				int x = ((Input.point.x - offsetX) / tileWidth); 
				
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
		else if(input.isIn(undoButton) && !this.myTurn)
		{
			try 
			{
				ChessPlayerController.getInstance().requestUndo();
			} 
			catch (JSONException | IOException | InterruptedException e) 
			{
				// pass
			}
			
			undoRequested = this.myTeam;
			undoCmd.enable(); 
		}
		else if(input.mouseIsClicked())
		{
			mboard.reset(); 
		}
		
	}
	
	public void undoRequest()
	{
		
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
	}
	
	public void placePieces()
	{
		int[] pieces = {Piece.TYPE_PAWN, Piece.TYPE_ROOK, Piece.TYPE_KNIGHT, Piece.TYPE_BISHOP, Piece.TYPE_QUEEN, Piece.TYPE_KING }; 
	
		for(int i=0; i<16; i++)
		{
			int piece = pieces[(i/8)*(  (i-7)+((i/13)*(3-((2*i)%13)))  )%6]; 
			
			//positive bottom
			cboard.setTileValue(i%8, 1-i/8, piece); 
		
			//negative top 
			cboard.setTileValue(i%8, 6+i/8, -piece); 	
		}
	}
	
	public void checkMate(int x, int y)
	{
		// TODO: Khaled is this what you wanted?????????
		// Get king for my team
		marker.calebrate(this.myTeam);
		
		//update check and check mate 
		if((whiteDead = marker.isThreatened()))
			if(marker.isCheck(1))
			{
				blackMessage = "Check...";
			}
			else
				blackMessage = "Check.."; 
		else
			blackMessage = ""; 
		
		
		marker.calebrate(-1);
		
		if((blackDead = marker.isThreatened()))
			if(marker.isCheck(-1))
			{
				whiteMessage = "Check..";
			}
			else 
				whiteMessage = "Check.."; 
		else
			whiteMessage = ""; 
		
		
		// calibrate the other team 
		
		if(marker.didLose(1))
		{
			winner = -1; 
			if(this.myTeam == 1)
			{
				forfeit(); 
			}
		}
		
		marker.calebrate(-1); 
		
		if(marker.didLose(-1))
		{
			winner = 1;
			
			if(this.myTeam == -1)
			{
				forfeit(); 
			}
		}
	}
	
	public void boardClicked(int x, int y)
	{
		if(!this.myTurn)
		{
			return;
		}
		
		y = Math.abs(reverse-y);
		
		// Check tile
		if(mboard.getTileValue(x, y) > MobilityBoard.MARK_INVISIBLE && this.myTeam == cboard.teamAt(selectedX, selectedY))
		{
			
			// I've made move, so I have to wait for opponent
			this.myTurn = false;
			
			//rook or king moved notifications 
			int notification =	cboard.movePiece(selectedX, selectedY, x, y);

			// Add move to board
			JSONObject from = new JSONObject();
			JSONObject to = new JSONObject();
			try 
			{
				// create from object
				from.put("x", selectedX);
				from.put("y", selectedY);
				
				// Create to object
				to.put("x", x);
				to.put("y", y);
				
				// Create and set new prev move
				this.previousMove = new JSONObject();
				this.previousMove.put("from", from);
				this.previousMove.put("to", to);

				// Send object to server
				JSONObject status = ChessPlayerController.getInstance().addMove(from, to);
				System.out.println(status.toString());
			} 
			catch (JSONException | IOException | InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
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
			
			// Check for checkmate and check and handle
			this.checkMate(x, y);
			
			mboard.reset();	
		}//end mobility 
		else
		{
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
}