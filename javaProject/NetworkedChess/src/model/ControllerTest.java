package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ControllerTest {

	@Test
	public void contest() {
	
		ChessBoardController ruler = new ChessBoardController(2);
		ruler.isMoveValid(0, 0, 1, 1);
		ruler.sentTurn(0, 0, 1, 1);
		ruler.recieveTurn(0, 0, 1, 1); //Board Controller functionality tests
		
	}

}
