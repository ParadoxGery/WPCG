package computergraphics.datastructures.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import computergraphics.datastructures.HalfEdgeTriangleMesh;
import computergraphics.datastructures.ImplicitFunktions;
import computergraphics.datastructures.MarchingCube;
import computergraphics.datastructures.TriangleFacet;
import computergraphics.math.Vector3;

public class HalfEdgeTriangleMeshMarchingCubesTest {

	private List<MarchingCube> cubes;
	private HalfEdgeTriangleMesh mesh;
	
	@Before
	public void setUp() throws Exception {
		mesh = new HalfEdgeTriangleMesh();
		double minRange = -7;
		double maxRange = 7;
		int resolution = 50;
		ImplicitFunktions function = ImplicitFunktions.TORUS;
		cubes = new ArrayList<>();
		double step = (Math.abs(minRange) + Math.abs(maxRange)) / resolution;
		for(float x = (float)minRange; x < maxRange; x += step) {
			for(float y = (float)minRange; y < maxRange; y += step) {
				for(float z = (float)minRange; z < maxRange; z += step) {
					List<Vector3> points = new ArrayList<>();
					points.add(new Vector3(x, y, z + step));
					points.add(new Vector3(x + step, y, z + step));
					points.add(new Vector3(x + step, y + step, z + step));
					points.add(new Vector3(x, y + step, z + step));
					points.add(new Vector3(x, y, z));
					points.add(new Vector3(x + step, y, z));
					points.add(new Vector3(x + step, y + step, z));
					points.add(new Vector3(x, y + step, z));
					cubes.add(new MarchingCube(points, function));
				}
			}
		}
		this.mesh.doMarchingCube(cubes, function);
	}

	@Test
	public void testOppositeEdges() {
		int countMissing = 0;
		for(int i = 0; i < mesh.getNumberOfTriangles(); i++){
			TriangleFacet facet = mesh.getFacet(i);
			if(facet.getHalfEdge().getOpposite()==null) countMissing++;
			if(facet.getHalfEdge().getNext().getOpposite()==null) countMissing++;
			if(facet.getHalfEdge().getNext().getOpposite()==null) countMissing++;
		}
		assertEquals(0, countMissing);
	}
}

