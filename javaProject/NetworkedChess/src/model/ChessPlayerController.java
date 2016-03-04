package model;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import GamePanels.MultiplayerChessGame;

public class ChessPlayerController
{
	
	private int playerTeam; 
	
	private String host = "localhost:3000";
	
	// TODO: use config file for these in the future
	private String createAccount = "/createAccount";
	private String login = "/login";
	private String addMove = "/addMove";
	private String requestUndo = "/requestUndo";
	private String acceptOrDenyUndo = "/acceptOrDenyUndo";
	private String forfeit = "/forfeit";
	private String ratings = "/ratings";
	private String update = "/getUpdate";
	private String getMatch = "/getMatch";
	private String cancelQueue = "/cancelQueue";
	private String user;

	private MultiplayerChessGame board; 
	
	//create an object of SingleObject
	private static ChessPlayerController instance = null;

   public static ChessPlayerController getInstance() {
      if(instance == null) {
         instance = new ChessPlayerController();
      }
      
      return instance;
   }
	
	public void setBoard(MultiplayerChessGame board)
	{
		this.board = board; 
	}
	
	public int getTeam()
	{
		return playerTeam; 
	}
	
	private Boolean inRange(int status, int min, int max)
	{
		if(status > min && status < max)
		{
			return true;
		}
		return false;
	}
	
	
	public JSONObject createAccount(String pass) throws JSONException, IOException, InterruptedException 
	{
		// Create Object
		JSONObject request = new JSONObject();
		
		// Add user name and password to object
		request.append("user", this.user);
		request.append("pass", pass);
		
		// Set user
		this.user = user;
		
		// Request information from server
		return this.requestFromServer(this.host + this.createAccount, request);
	}
	
	public JSONObject login(String pass) throws JSONException, IOException, InterruptedException
	{
		// Create Object
		JSONObject request = new JSONObject();
		
		// Add user name and password to object
		request.append("user", this.user);
		request.append("pass", pass);
		
		// Set user
		this.user = user;
		
		// Request information from server
		return this.requestFromServer(this.host + this.login, request);
	}
	
	public JSONObject addMove(JSONObject from, JSONObject to) throws JSONException, IOException, InterruptedException
	{
		// Create Object
		JSONObject request = new JSONObject();
		
		// Create move object
		JSONObject move = new JSONObject();
		move.append("from", from);
		move.append("to", to);
		
		// add move and user to request
		request.append("move", move);
		request.append("user", this.user);
		
		// Request information from server
		return this.requestFromServer(this.host + this.addMove, request);
	}
	
	public JSONObject requestUndo() throws JSONException, IOException, InterruptedException
	{
		// Create Object
		JSONObject request = new JSONObject();
		
		// Add user name and password to object
		request.append("user", this.user);
		
		// Request information from server
		return this.requestFromServer(this.host + this.requestUndo, request);
	}
	
	public JSONObject acceptOrDenyUndo(Boolean acceptUndo) throws JSONException, IOException, InterruptedException
	{
		// Create Object
		JSONObject request = new JSONObject();
		
		// Add user name and password to object
		request.append("user", this.user);
		request.append("acceptUndo", acceptUndo);
		
		// Request information from server
		return this.requestFromServer(this.host + this.acceptOrDenyUndo, request);
	}
	
	public JSONObject forfeit(Boolean acceptUndo) throws JSONException, IOException, InterruptedException
	{
		// Create Object
		JSONObject request = new JSONObject();
		
		// Add user name and password to object
		request.append("user", this.user);
		request.append("isPlaying", true);
		
		// Request information from server
		return this.requestFromServer(this.host + this.forfeit, request);
	}
	
	public JSONObject ratings() throws JSONException, IOException, InterruptedException
	{
		// Create Object
		JSONObject request = new JSONObject();
		
		// Add user name and password to object
		request.append("user", this.user);
		
		// Request information from server
		return this.requestFromServer(this.host + this.ratings, request);
	}
	
	public JSONObject getUpdate(Boolean isPlaying) throws JSONException, IOException, InterruptedException
	{
		// Create Object
		JSONObject request = new JSONObject();
		
		// Add user name and password to object
		request.append("user", this.user);
		request.append("isPlaying", isPlaying);
		
		// Request information from server
		return this.requestFromServer(this.host + this.update, request);
	}
	
	public JSONObject getMatchMaking() throws JSONException, IOException, InterruptedException
	{
		// Create Object
		JSONObject request = new JSONObject();
		
		// Add user name and password to object
		request.append("user", this.user);
		
		// Request information from server
		return this.requestFromServer(this.host + this.getMatch, request);
	}
	
	public JSONObject leaveQueue() throws JSONException, IOException, InterruptedException
	{
		// Create Object
		JSONObject request = new JSONObject();
		
		// Add user name and password to object
		request.append("user", this.user);
		
		// Request information from server
		return this.requestFromServer(this.host + this.cancelQueue, request);
	}
	
	private JSONObject requestFromServer(String requestURL, JSONObject requestObj) throws IOException, InterruptedException, JSONException
	{
		// Create URL
		URL url = new URL(requestURL);
		
		// Open connection with URL
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		
		// Set method to POST
		request.setRequestMethod("POST");
		
		// Set content type to JSON
		request.setRequestProperty("Content-Type", "application/json");
		
		// Send over JSON object in req.body as string
		request.setDoOutput(true);
		OutputStream out = new BufferedOutputStream(request.getOutputStream());
		out.write(requestObj.toString().getBytes());
		out.flush();
		
		// Connect to server
		request.connect();
		
		// Character array to store response string
		char[] response = new char[10000];
		
		// Input stream to read in response
		InputStream in;
		
		// Check if status is in (200,300]
		if(this.inRange(request.getResponseCode(),HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_BAD_REQUEST))
		{
			 in = request.getInputStream();
		}
		else 
		{
			in = request.getErrorStream();
		}
		
		InputStreamReader input = new InputStreamReader(in);
		
		input.read(response);
		
		JSONObject json;
		try 
		{
			json = new JSONObject(new String(response));
			System.out.println(json.toString());
		} 
		catch (JSONException e) 
		{	
			// Invalid response, return empty
			json = (JSONObject) JSONObject.NULL;
		}
		
		// add status to object
		json.append("status", request.getResponseCode());
		
		// diconnect connection
		request.disconnect();
		
		// Return Json Object
		return json;
	}
}
