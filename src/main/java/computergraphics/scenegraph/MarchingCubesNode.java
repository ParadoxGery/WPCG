package computergraphics.scenegraph;

import java.util.ArrayList;
import java.util.List;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import computergraphics.datastructures.HalfEdgeTriangleMesh;
import computergraphics.datastructures.ImplicitFunktions;
import computergraphics.datastructures.MarchingCube;
import computergraphics.datastructures.TriangleFacet;
import computergraphics.datastructures.Vertex;
import computergraphics.math.Vector3;

public class MarchingCubesNode extends Node {
	
	private static final boolean DEBUG = false;
	
	private int listId = 0;
	
	private boolean updateGlList = false;
	private HalfEdgeTriangleMesh mesh;
	private List<MarchingCube> cubes;
	
	public MarchingCubesNode(int resolution, double minRange, double maxRange, HalfEdgeTriangleMesh mesh,
			ImplicitFunktions function) {
		cubes = new ArrayList<>();
		this.mesh = mesh;
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
	
	public void setUpdateGlList(boolean update) {
		this.updateGlList = update;
	}
	
	@Override
	public void drawGl(GL2 gl) {
		if(listId == 0 || updateGlList) {
			init(gl);
		} else {
			gl.glCallList(listId);
		}
		if(DEBUG) {
			gl.glBegin(GL.GL_LINES);
			for(MarchingCube c : cubes) {
				gl.glVertex3d(c.getPoints().get(0).get(0), c.getPoints().get(0).get(1), c.getPoints().get(0).get(2));
				gl.glVertex3d(c.getPoints().get(1).get(0), c.getPoints().get(1).get(1), c.getPoints().get(1).get(2));
				
				gl.glVertex3d(c.getPoints().get(1).get(0), c.getPoints().get(1).get(1), c.getPoints().get(1).get(2));
				gl.glVertex3d(c.getPoints().get(2).get(0), c.getPoints().get(2).get(1), c.getPoints().get(2).get(2));
				
				gl.glVertex3d(c.getPoints().get(2).get(0), c.getPoints().get(2).get(1), c.getPoints().get(2).get(2));
				gl.glVertex3d(c.getPoints().get(3).get(0), c.getPoints().get(3).get(1), c.getPoints().get(3).get(2));
				
				gl.glVertex3d(c.getPoints().get(3).get(0), c.getPoints().get(3).get(1), c.getPoints().get(3).get(2));
				gl.glVertex3d(c.getPoints().get(1).get(0), c.getPoints().get(1).get(1), c.getPoints().get(1).get(2));
				
				gl.glVertex3d(c.getPoints().get(1).get(0), c.getPoints().get(1).get(1), c.getPoints().get(1).get(2));
				gl.glVertex3d(c.getPoints().get(5).get(0), c.getPoints().get(5).get(1), c.getPoints().get(5).get(2));
				
				gl.glVertex3d(c.getPoints().get(2).get(0), c.getPoints().get(2).get(1), c.getPoints().get(2).get(2));
				gl.glVertex3d(c.getPoints().get(6).get(0), c.getPoints().get(6).get(1), c.getPoints().get(6).get(2));
				
				gl.glVertex3d(c.getPoints().get(5).get(0), c.getPoints().get(5).get(1), c.getPoints().get(5).get(2));
				gl.glVertex3d(c.getPoints().get(6).get(0), c.getPoints().get(6).get(1), c.getPoints().get(6).get(2));
				
				gl.glVertex3d(c.getPoints().get(6).get(0), c.getPoints().get(6).get(1), c.getPoints().get(6).get(2));
				gl.glVertex3d(c.getPoints().get(7).get(0), c.getPoints().get(7).get(1), c.getPoints().get(7).get(2));
				
				gl.glVertex3d(c.getPoints().get(5).get(0), c.getPoints().get(5).get(1), c.getPoints().get(5).get(2));
				gl.glVertex3d(c.getPoints().get(4).get(0), c.getPoints().get(4).get(1), c.getPoints().get(4).get(2));
				
				gl.glVertex3d(c.getPoints().get(4).get(0), c.getPoints().get(4).get(1), c.getPoints().get(4).get(2));
				gl.glVertex3d(c.getPoints().get(7).get(0), c.getPoints().get(7).get(1), c.getPoints().get(7).get(2));
				
				gl.glVertex3d(c.getPoints().get(4).get(0), c.getPoints().get(4).get(1), c.getPoints().get(4).get(2));
				gl.glVertex3d(c.getPoints().get(0).get(0), c.getPoints().get(0).get(1), c.getPoints().get(0).get(2));
				
				gl.glVertex3d(c.getPoints().get(7).get(0), c.getPoints().get(7).get(1), c.getPoints().get(7).get(2));
				gl.glVertex3d(c.getPoints().get(3).get(0), c.getPoints().get(3).get(1), c.getPoints().get(3).get(2));
			}
			gl.glEnd();
		}
	}
	
	private void init(GL2 gl) {
		updateGlList = false;
		listId = gl.glGenLists(1);
		gl.glNewList(listId, GL2.GL_COMPILE);
		gl.glBegin(GL2.GL_TRIANGLES);
		for(int i = 0; i < mesh.getNumberOfTriangles(); i++) {
			TriangleFacet facet = mesh.getFacet(i);
			Vertex v = facet.getHalfEdge().getStartVertex();
			//gl.glColor3d(v.getColor().get(0), v.getColor().get(1), v.getColor().get(2));
			gl.glNormal3d(v.getNormal().get(0), v.getNormal().get(1), v.getNormal().get(2));
			gl.glVertex3d(v.getPosition().get(0), v.getPosition().get(1), v.getPosition().get(2));
			v = facet.getHalfEdge().getNext().getStartVertex();
			//gl.glColor3d(v.getColor().get(0), v.getColor().get(1), v.getColor().get(2));
			gl.glNormal3d(v.getNormal().get(0), v.getNormal().get(1), v.getNormal().get(2));
			gl.glVertex3d(v.getPosition().get(0), v.getPosition().get(1), v.getPosition().get(2));
			v = facet.getHalfEdge().getNext().getNext().getStartVertex();
			//gl.glColor3d(v.getColor().get(0), v.getColor().get(1), v.getColor().get(2));
			gl.glNormal3d(v.getNormal().get(0), v.getNormal().get(1), v.getNormal().get(2));
			gl.glVertex3d(v.getPosition().get(0), v.getPosition().get(1), v.getPosition().get(2));
		}
		gl.glEnd();
		gl.glEndList();
	}
	
}
