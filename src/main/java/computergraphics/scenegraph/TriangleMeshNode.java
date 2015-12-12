package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.HalfEdge;
import computergraphics.datastructures.ITriangleMesh;
import computergraphics.datastructures.TriangleFacet;
import computergraphics.datastructures.Vertex;

public class TriangleMeshNode extends Node {

	private ITriangleMesh mesh;
	
	private int listId = 0;

	public TriangleMeshNode(ITriangleMesh mesh) {
		this.mesh = mesh;
		mesh.computeTriangleNormals();
	}

	@Override
	public void drawGl(GL2 gl) {
		if(listId == 0){
			init(gl);
		}else {
			gl.glCallList(listId);
		}
	}
	
	private void init(GL2 gl){
		listId = gl.glGenLists(1);
		gl.glNewList(listId, GL2.GL_COMPILE);
		gl.glBegin(GL2.GL_TRIANGLES);
		for (int i = 0; i < mesh.getNumberOfTriangles(); i++) {
			TriangleFacet facet = mesh.getFacet(i);
			gl.glNormal3d(facet.getNormal().get(0), facet.getNormal().get(1), facet.getNormal().get(2));
			Vertex v = facet.getHalfEdge().getStartVertex();
			gl.glVertex3d(v.getPosition().get(0), v.getPosition().get(1), v.getPosition().get(2));
			v = facet.getHalfEdge().getNext().getStartVertex();
			gl.glVertex3d(v.getPosition().get(0), v.getPosition().get(1), v.getPosition().get(2));
			v = facet.getHalfEdge().getNext().getNext().getStartVertex();
			gl.glVertex3d(v.getPosition().get(0), v.getPosition().get(1), v.getPosition().get(2));
		}
		gl.glEnd();
		gl.glEndList();
	}

}
