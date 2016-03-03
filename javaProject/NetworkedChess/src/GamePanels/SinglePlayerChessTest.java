package GamePanels;

import static org.junit.Assert.*;

import org.junit.Test;

public class SinglePlayerChessTest {

	@Test
	public void SPtest() {
		SinglePlayerChess chess = new SinglePlayerChess();
		chess.init();
		chess.recieveMovement(0, 0, 1, 1);
		chess.revereBoard(); //base class and function tests
		
		//unitClicking cannot be tested as derived in MultiplayerGame and PlayerController
		
		
	}

}
