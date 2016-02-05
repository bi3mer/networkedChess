package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ChessBoardTest {


	@Test
	public void BoardTest(){
		ChessBoard bordy = new ChessBoard();
		
		bordy.placePiece(6, 0, 1); // place test
		bordy.placePiece(-5, -5, 2); //out-bound error check
		
		
		bordy.movePiece(6, 0, 1, 1); //castling test
		
		bordy.teamAt(1,1); //team test
		bordy.placePiece(2, 2, -2); // black queen @ 2 2
		bordy.teamAt(2, 2); // check for the queen team
		bordy.teamAt(0, 0); // check empty
		
		MobilityBoard bordy_on_wheels = new MobilityBoard(bordy);
		bordy.selectForMark(2, 2, bordy_on_wheels);
		bordy.deselect(bordy_on_wheels);
	}
}
