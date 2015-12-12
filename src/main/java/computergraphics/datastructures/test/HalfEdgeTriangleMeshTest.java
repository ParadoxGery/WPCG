package computergraphics.datastructures.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import computergraphics.datastructures.HalfEdgeTriangleMesh;
import computergraphics.datastructures.ObjIO;
import computergraphics.datastructures.TriangleFacet;
import computergraphics.scenegraph.TriangleMeshNode;

public class HalfEdgeTriangleMeshTest {
	
	private HalfEdgeTriangleMesh mesh;
	
	@Before
	public void setUp() throws Exception {
		ObjIO objio = new ObjIO();
		mesh = new HalfEdgeTriangleMesh();
		objio.einlesen("meshes/cow.obj", mesh);
	}

	@Test
	public void testOppositeEdges() {
		for(int i = 0; i < mesh.getNumberOfTriangles(); i++){
			TriangleFacet facet = mesh.getFacet(i);
			assertNotNull(facet.getHalfEdge().getOpposite());
			assertNotNull(facet.getHalfEdge().getNext().getOpposite());
			assertNotNull(facet.getHalfEdge().getNext().getOpposite());
		}
	}
	
	

}
