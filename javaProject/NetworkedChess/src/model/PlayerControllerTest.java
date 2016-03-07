package model;

import static org.junit.Assert.*;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import GamePanels.MultiplayerChessGame;
import KLD.Game;

public class PlayerControllerTest {

	@Test
	public void PlayerControllerTest() {
		
		ChessPlayerController conti = new ChessPlayerController();
		conti.main(null); // main exception test
		conti.getTeam();
		try {
			JSONObject obj = conti.createAccount("Dude", "where's my car");
		} catch (JSONException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//test worked the first time, once an account exists, a new account needs a different name
		//this tes will always fail on the second coverage run
		
		try {
			JSONObject obj = conti.login("Dude", "where's my car");
		} catch (JSONException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//login test for an existing account
		
		try {
			conti.requestUndo();
		} catch (JSONException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			conti.acceptOrDenyUndo(true);
		} catch (JSONException | IOException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conti.acceptOrDenyUndo(false);
		} catch (JSONException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//undo server call tests
		
		try {
			conti.forfeit();
		} catch (JSONException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//forfeit server call test
		
		try {
			conti.leaveQueue();
		} catch (JSONException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Queue resolve test
		
		JSONObject from = new JSONObject();
		JSONObject to = new JSONObject();
		
		try {
			conti.addMove(from, to);
		} catch (JSONException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Movement tracking to server test
	}

}
