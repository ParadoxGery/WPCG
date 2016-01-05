package computergraphics.scenegraph;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import computergraphics.datastructures.Ray3D;
import computergraphics.math.Vector3;
import sun.security.provider.VerificationProvider;

public class PlainNodeTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testBerechneSchnittNegativ() {
		PlainNode pn = new PlainNode(new Vector3(), Vector3.X_AXIS, Vector3.Y_AXIS, 10);
		Ray3D ray = new Ray3D(Vector3.Z_AXIS, Vector3.X_AXIS);
		assertNull(pn.berechneSchnitt(ray));
	}

	@Test
	public void testBerechneSchnittPositiv() {
		PlainNode pn = new PlainNode(new Vector3(), Vector3.X_AXIS, Vector3.Y_AXIS, 10);
		Ray3D ray = new Ray3D(Vector3.Z_AXIS, new Vector3(0, 0, -1));
		Vector3 expected = new Vector3();
		assertEquals(expected, pn.berechneSchnitt(ray).point);
	}
	
}
