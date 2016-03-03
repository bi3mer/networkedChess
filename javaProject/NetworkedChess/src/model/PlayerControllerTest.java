package model;

import static org.junit.Assert.*;

import org.junit.Test;

import GamePanels.MultiplayerChessGame;
import KLD.Game;

public class PlayerControllerTest {

	@Test
	public void PlayerControllerTest() {
		MultiplayerChessGame shefy = new MultiplayerChessGame();
		ChessPlayerController cont = new ChessPlayerController(shefy);
		cont.connect();
		try {
			cont.play();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Multiplayer game generation test
		//constructor for Graphics?
		//unitClicking cannot be tested by a unit test unless broken down to components (click checks
		//can only be asserted on live
		//update requires live connection, which a unit test can't be expected to instantiate
		
	}

}
