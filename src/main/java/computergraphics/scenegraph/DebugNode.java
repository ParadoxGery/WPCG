package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import computergraphics.math.Vector3;
import javafx.scene.paint.Color;

public class DebugNode extends Node {

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
	 * 
	 */
	private Color color = Color.GREEN;
	
	/**
	 * Constructor.
	 */
	public DebugNode(double radius, int resolution, Vector3 focusPoint) {
		this.radius = radius;
		this.resolution = resolution;
		this.focusPoint.copy(focusPoint);
	}
	
	public DebugNode(double radius, int resolution, Vector3 focusPoint, Color color){
		this(radius,resolution,focusPoint);
		this.color = color;
	}
	
	@Override
	public void drawGl(GL2 gl) {
		GLU glu = new GLU();
		// Apply translation
		gl.glPushMatrix();
		//set color
		gl.glColor3d(color.getRed(),color.getGreen(),color.getBlue());
		gl.glTranslatef((float) focusPoint.get(0), (float) focusPoint.get(1), (float) focusPoint.get(2));
		GLUquadric earth = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
		glu.gluQuadricNormals(earth, GLU.GLU_SMOOTH);
		glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
		final int slices = resolution;
		final int stacks = resolution;
		glu.gluSphere(earth, radius, slices, stacks);

		gl.glPopMatrix();
	}

}
