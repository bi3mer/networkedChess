package KLD;

import static org.junit.Assert.*;

import java.awt.Graphics;

import javax.swing.JFrame;

import org.junit.Test;

import GamePanels.MultiplayerChessGame;

public class GameFrameTest {

	@Test
	public void Frametest() {
		GameFrame fram = new GameFrame();
		try {
			fram.main(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fram.setSize(1, 1);
		
		
		//Frame generation tests, remaining methods are stubs
	}

}
