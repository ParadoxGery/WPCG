package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.HalfEdge;
import computergraphics.datastructures.ITriangleMesh;
import computergraphics.datastructures.TriangleFacet;
import computergraphics.datastructures.Vertex;

public class TriangleMeshLaPlaceNode extends Node {

	private ITriangleMesh mesh;
	
	private int listId = 0;
	
	private boolean updateGlList = false;

	public TriangleMeshLaPlaceNode(ITriangleMesh mesh) {
		this.mesh = mesh;
		mesh.computeTriangleNormals();
	}
	
	public void setUpdateGlList(boolean update){
		this.updateGlList = update;
	}

	@Override
	public void drawGl(GL2 gl) {
		if(listId == 0 || updateGlList){
			init(gl);
		}else {
			gl.glCallList(listId);
		}
	}
	
	private void init(GL2 gl){
		updateGlList = false;
		listId = gl.glGenLists(1);
		gl.glNewList(listId, GL2.GL_COMPILE);
		gl.glBegin(GL2.GL_TRIANGLES);
		for (int i = 0; i < mesh.getNumberOfTriangles(); i++) {
			TriangleFacet facet = mesh.getFacet(i);
			//gl.glNormal3d(facet.getNormal().get(0), facet.getNormal().get(1), facet.getNormal().get(2));
			Vertex v = facet.getHalfEdge().getStartVertex();
			gl.glNormal3d(v.getNormal().get(0), v.getNormal().get(1), v.getNormal().get(2));
			gl.glVertex3d(v.getPosition().get(0), v.getPosition().get(1), v.getPosition().get(2));
			v = facet.getHalfEdge().getNext().getStartVertex();
			gl.glNormal3d(v.getNormal().get(0), v.getNormal().get(1), v.getNormal().get(2));
			gl.glVertex3d(v.getPosition().get(0), v.getPosition().get(1), v.getPosition().get(2));
			v = facet.getHalfEdge().getNext().getNext().getStartVertex();
			gl.glNormal3d(v.getNormal().get(0), v.getNormal().get(1), v.getNormal().get(2));
			gl.glVertex3d(v.getPosition().get(0), v.getPosition().get(1), v.getPosition().get(2));
		}
		gl.glEnd();
		gl.glEndList();
	}

}
