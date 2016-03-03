package KLD;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConsoleTest {

	@Test
	public void constest() {
		Console cons = new Console(30);
		cons.run();
		cons.stop();
		cons.setFPS(25);
		cons.getTimer();
		cons.getRealTime(30);
		cons.showFPS();
		cons.setGame(getClass()); //base game tests mostly getters and setters
								// Class<Game> does not have a constructor
	}

}
