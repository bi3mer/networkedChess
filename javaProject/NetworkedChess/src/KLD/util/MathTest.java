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
		c[1] = '1';
		c[2] = 1;
		math.matchNumber(c); // Math class tests
		
		math.matchNumberRev(c);
		
		//possible digit tracking issues
		
	}

}
