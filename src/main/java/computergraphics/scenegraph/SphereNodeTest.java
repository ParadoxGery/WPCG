package computergraphics.scenegraph;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import computergraphics.datastructures.Ray3D;
import computergraphics.math.Vector3;

public class SphereNodeTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testBerechneSchnittNegativ() {
		SphereNode sn = new SphereNode(.5, 10, new Vector3());
		Ray3D ray = new Ray3D(Vector3.Z_AXIS, Vector3.X_AXIS);
		assertNull(sn.calcIntersection(sn,ray));
	}

	@Test
	public void testBerechneSchnittPositiv() {
		SphereNode sn = new SphereNode(.5, 10, new Vector3());
		Ray3D ray = new Ray3D(Vector3.Z_AXIS, new Vector3(0,0,-1));
		Vector3 expected = new Vector3(0,0,.5);
		Vector3 expectedNormal = Vector3.Z_AXIS;
		assertEquals(expected, sn.calcIntersection(sn,ray).point);
		assertEquals(expectedNormal, sn.calcIntersection(sn,ray).normal);
	}
}
