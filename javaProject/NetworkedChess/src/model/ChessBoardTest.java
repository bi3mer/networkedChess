package model;

import static org.junit.Assert.*;

import java.awt.Graphics;

import javax.swing.JFrame;

import org.junit.Test;

import KLD.GameFrame;
import factory.PieceFactory;
import ui.BoardUI;

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
		bordy.fill(1); // fill test
		
		Board bordy2 = new ChessBoard(); // for board opperations testing
		bordy.add(bordy2);
		bordy.multiply(bordy2);
		
		bordy2 = bordy.clone(); //board cloning
		
		bordy.untoLastMove(0, 0, 1, 1);
		
		MobilityBoard bordy_on_wheels = new MobilityBoard(bordy);
		//bordy.selectForMark(2, 2, bordy_on_wheels);  removed -KLD
		//bordy.selectForMark(-1, -1, bordy_on_wheels); removed -KLD
		//bordy.deselect(bordy_on_wheels);  //basic mobility (tests function is removed  - KLD)
		bordy.toString();
		
		bordy_on_wheels.markMove(-6, -6);
		bordy_on_wheels.markAttack(-6, -6,1); // invalid mobility tests
		
		//bordy_on_wheels.markKingMoved(1); removed - KLD
		//bordy_on_wheels.markRookMoved(1, 6); removed -KLD
		bordy_on_wheels.didMove(1, 1, 1);
		bordy_on_wheels.didMove(3, 1,1); //castling attempt tests
		
		PawnMovement pawn = new PawnMovement();
		//pawn.markAvailableMovement(bordy_on_wheels, bordy, 2, 2); removed -KLD
		KingMovement kong = new KingMovement(null);
		//kong.markAvailableMovement(bordy_on_wheels, bordy, 0, 0); //pawn and king movement tests  (removed -KLD)
		Marker mark = new Marker(bordy_on_wheels);
		mark.calebrate(1);
		mark.isThreatened();
		mark.isCheck(1);
		mark.isCheckMate(1);
		mark.didLose(1);		//Loss win, check and checkmate tests
		
		
		
		
		
	}
}
