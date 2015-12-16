package computergraphics.datastructures.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import computergraphics.datastructures.MonomKurve;
import computergraphics.math.Vector3;

public class MonomKurveTest {
	private MonomKurve mk;
	
	@Before
	public void setUp() throws Exception {
		mk = new MonomKurve();
		mk.addPunkt(new Vector3(0,0,0));
		mk.addPunkt(new Vector3(1,1,0));
		mk.addPunkt(new Vector3(1,1,1));
		mk.interpolate();
	}

	@Test
	public void test() {
		assertEquals(new Vector3(0,0,0), mk.computePoint(0));
		assertEquals(new Vector3(1,1,0), mk.computePoint(0.5));
		assertEquals(new Vector3(1,1,1), mk.computePoint(1));
	}

}
