package KLD.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class MathTest {

	@Test
	public void BaseMathTest() {
	
		KLDMath math = new KLDMath();
		math.solve("2+2-3*7/2");
		char []c = new char [3];
		c[0] = 'T';
		c[1] = 'U';
		c[2] = 1;
		math.matchNumber(c);
		
	}

}
