package GamePanels;

import static org.junit.Assert.*;

import org.junit.Test;

import intf.MovementTracker;

public class MultiplayerChessGameTest {

	@Test
	public void MCGtest() {
		MultiplayerChessGame chess = new MultiplayerChessGame();
		chess.init();
		chess.markRead();
		chess.recieveMovement(1, 1, 1, 3);
		MovementTracker mt = null;
		chess.setMovementTraker(mt);
		chess.revereBoard(); //bse function and class tests
		
		//Need graphic declaration
	}

}
