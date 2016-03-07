package menus;

import static org.junit.Assert.*;

import org.junit.Test;

public class MenusTest {

	@Test
	public void Menaltest() {
		Menus menum = new Menus();
		menum.main(null);
		
		MainMenu mana = new MainMenu();
		mana.main(null);
		
		Queue que = new Queue();
		que.main(null);
		
		Stats stat = new Stats();
		stat.main(null);
		
			/*This test invokes the frame to frame instantiation of each menu, as well as
			 * its own declarative instantiation
			 * button testing is missed from coverage as that can only be done in Acceptance testing
			 */
	}

}
