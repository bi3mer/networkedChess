package KLD.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class MathTest {

	@Test
	public void BaseMathTest() {
	
		KLDMath math = new KLDMath();
		math.solve("2+2-3*7/2");
		
	}

}
