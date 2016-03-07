package GamePanels;

import static org.junit.Assert.*;

import java.awt.Graphics;

import javax.swing.JFrame;

import org.junit.Test;

import KLD.GameFrame;

public class SinglePlayerChessTest {

	@Test
	public void SPtest() {
		SinglePlayerChess chess = new SinglePlayerChess();
		chess.init();
		chess.recieveMovement(0, 0, 1, 1);
		chess.revereBoard(); //base class and function tests
		
		chess.undoRequest();
		chess.recievePromotion(1, 1, 1);
		chess.boardClicked(0, 0);
		chess.boardClicked(-1,2);
		
		
		//unitClicking cannot be tested as derived in MultiplayerGame and PlayerController
		
		
	}

}
