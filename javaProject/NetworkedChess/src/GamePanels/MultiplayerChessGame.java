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
import intf.MovementTracker;
import model.BoardUI;
import model.ChessBoard;
import model.ChessPlayerController;
import model.Marker;
import model.MobilityBoard;
import model.Piece;

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
	
	private boolean turnReady; 
	
	private int waitingTime; 
	
	// 0 false, 7 true 
	/**
	 * Reversing board for player two 
	 */
	private int reverse; 
	
	
	private MovementTracker traker; 
	
	ChessPlayerController player; 
	
	BoardUI boradui; 
	
	@Override
	protected void init() 
	{
		// Add player to static controller
		ChessPlayerController.getInstance().setBoard(this);
		
		// Create board
		cboard = new ChessBoard(); 
		mboard = new MobilityBoard(cboard); 
		
		marker = new Marker(mboard); 
				
		int[] pieces = {0, Piece.TYPE_ROOK, 
				Piece.TYPE_KNIGHT, Piece.TYPE_BISHOP, Piece.TYPE_QUEEN, Piece.TYPE_KING }; 
					
		
		for(int i=0; i<16; ++i)
		{
			int piece = pieces[(i/8)*(  (i-7)+((i/13)*(3-((2*i)%13)))  )%6]; 
			
			//positive bottom
			cboard.setTileValue(i%8, 1-i/8, piece); 
		
			//negative top 
			cboard.setTileValue(i%8, 6+i/8, -piece); 	
		}
		
		tileWidth = GameFrame.width/10; 
		tileHeight = GameFrame.height/10; 
		
		offsetX = GameFrame.width/2 - tileWidth*4; 
		offsetY = GameFrame.height/2 - tileHeight*4 - 20; 
		
		turnReady = false; 
		waitingTime = 0; 
		reverse = player.getTeam(); 
		
		//this will allow the board to be drawn automatically 
		boradui = new BoardUI(cboard, mboard, reverse); 
		boradui.setSizes(offsetX, offsetY, tileWidth, tileHeight); 
		this.addDraw(boradui);
	}

	@Override
	protected void draw(Graphics g) 
	{
		if(!turnReady)
		{
			Game.setFade(g, .7f);
			
			g.setColor(Color.black);
			
			g.fillRect(0, 0, GameFrame.width, GameFrame.height);
			
			String build = ""; 
			
			for(int i=0; i<waitingTime%3; i++)
				build+="."; 
			
			g.drawString("waiting for other player" + build, GameFrame.width/2-50, offsetY/2);
			
			Game.setFade(g, 1f);
		}else
		{
			g.setColor(Color.black);
			g.drawString("You turn dumbdumb", GameFrame.width/2-50, offsetY/2);
		}
	}//end color 

	@Override
	protected void update() 
	{	
		//check for check 
		
		//check for checkmate
		
		// Get Update from Server
		JSONArray updates;
		try 
		{
			// Get updates
			updates = ChessPlayerController.getInstance().getUpdate(true).getJSONArray("updates");
			
			// Loop through array
			for(int i = 0; i < updates.length(); ++i)
			{
				// Get update object
				JSONObject update = updates.getJSONObject(i);
				
				if(update.has("forfeit"))
				{
					System.out.println("TODO: Game is over, end game");
				}
				else if(update.has("from"))
				{
					// Get form and to
					JSONObject from = (JSONObject) update.get("from");
					JSONObject to   = (JSONObject) update.get("to");
					
					// Add move to board
					cboard.movePiece(from.getInt("x"), from.getInt("y"), to.getInt("x"), to.getInt("y"));
				}
				else if(update.has("undoRequest")) 
				{
					System.out.println("TODO: update to show undo request");
				}
				else if(update.has("undo"))
				{
					if(update.getBoolean("undo"))
					{
						System.out.println("TODO: undo request was accepted");
					}
					else
					{
						System.out.println("TODO: undo request was denyed");
					}
				}
			}
		}
		catch (JSONException | IOException | InterruptedException e) 
		{
			// Pass, call again next frame
		}
		
		//can request undo 
		//TODO
		
		//can forfeit 
		//TODO
	
		//player not allowed further interaction  
		if(turnReady)
		{
			//when click within board 
			unitClicking(); 
		}
	}
	
	public void markRead()
	{
		turnReady = true; 
	}
	
	// TODO: is this needed? 
	public boolean recieveMovement(int fromX, int fromY, int toX, int toY)
	{
		cboard.movePiece(fromX, fromY, toX, toY);
		
		return true;
	}
	
	public void setMovementTraker(MovementTracker mt)
	{
		traker = mt;
	}
	
	public void revereBoard()
	{
		reverse = 7; 
		boradui.setReverse(reverse);
		System.out.println("reserve");
	}
	
	public void unitClicking()
	{
		if(input.mouseIsClicked(new Rectangle(offsetX, offsetY, offsetX+tileWidth*7, offsetY+tileHeight*7)) )
		{
			//get coordinates 
			int x = ((Input.point.x - offsetX) / tileWidth); 
			int y = ((Input.point.y - offsetY) / tileHeight); 
			
			System.out.printf("clicked %d %d\n", x, y);
			
			y = Math.abs(reverse-y); 
			
			if(mboard.getTileValue(x, y) > MobilityBoard.MARK_INVISIBLE)
			{
				
				// Update board
				turnReady = false; 
				
				//rook or king moved notifications 
				int notification = cboard.movePiece(selectedX, selectedY, x, y);
				
				//notify mobility to adjust castling 
				if (notification > 0)
				{
					notification--; 
					
					mboard.setMoved(notification); 
					
					/*if(notification/2 == 0) 
						mboard.markKingMoved(player.getTeam());
					else
					{
						int side =  notification/2 -1; 
						mboard.markRookMoved(player.getTeam(), side);
					}*/
					
				}//endn if notification 
				
				// Update tracker
				traker.sendMovment(selectedX, selectedY, x, y);
				
				
				JSONObject from = new JSONObject();
				JSONObject to = new JSONObject();
				
				try 
				{
					// Create from object
					from.append("x", selectedX);
					from.append("y", selectedY);
					
					// Create to object
					to.append("x", x);
					to.append("y", y);
					
					// Update server with from and to object
					ChessPlayerController.getInstance().addMove(from, to);
				} 
				catch (JSONException | IOException | InterruptedException e) 
				{
					System.out.println("Error pushing to server");
				}
				
				mboard.reset();	
			}//end mobility 
			else
			{
				mboard.reset();	
				
				if(cboard.teamAt(x, y) == player.getTeam())
				{
					System.out.println("marking " + x + "-" + y);
					int marked = marker.markPieceAt(x, y); 
					
					System.out.println("Marked " + marked);
				}
			}

			selectedX = x; 
			selectedY = y; 
					
		}
		else if(input.mouseIsClicked())
		{
			mboard.reset();	
		}
	}	
}