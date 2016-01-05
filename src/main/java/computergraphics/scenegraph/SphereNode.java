/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import computergraphics.math.Vector3;

/**
 * Geometry of a simple sphere.
 * 
 * @author Philipp Jenke
 */
public class SphereNode extends Node {

	/**
	 * Sphere radius.
	 */
	private double radius;

	/**
	 * Resolution (in one dimension) of the mesh.
	 */
	private int resolution;
	
	/**
	 * Focuspoint of the sphere
	 */
	private Vector3 focusPoint = new Vector3();

	/**
	 * Constructor.
	 */
	public SphereNode(double radius, int resolution,Vector3 focusPoint) {
		this.radius = radius;
		this.resolution = resolution;
		this.focusPoint.copy(focusPoint);
	}

	@Override
	public void drawGl(GL2 gl) {
		//gl.glColor3d(0.75, 0.25, 0.25);
		GLU glu = new GLU();
		 // Apply translation
	    gl.glTranslatef((float)focusPoint.get(0), (float)focusPoint.get(1), (float)focusPoint.get(2));
		GLUquadric earth = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
		glu.gluQuadricNormals(earth, GLU.GLU_SMOOTH);
		glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
		final int slices = resolution;
		final int stacks = resolution;
		glu.gluSphere(earth, radius, slices, stacks);
	}

}
