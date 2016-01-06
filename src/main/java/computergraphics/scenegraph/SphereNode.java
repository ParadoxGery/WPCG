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

import computergraphics.datastructures.IntersectionResult;
import computergraphics.datastructures.Ray3D;
import computergraphics.math.Vector3;
import javafx.scene.paint.Color;

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
	 * 
	 */
	private Color color = Color.GREEN;

	/**
	 * Constructor.
	 */
	public SphereNode(double radius, int resolution, Vector3 focusPoint) {
		this.radius = radius;
		this.resolution = resolution;
		this.focusPoint.copy(focusPoint);
	}

	public SphereNode(double radius, int resolution, Vector3 focusPoint, Color color) {
		this(radius, resolution, focusPoint);
		this.color = color;
	}

	@Override
	public Vector3 getColor() {
		return new Vector3(color.getRed(), color.getGreen(), color.getBlue());
	}

	@Override
	public IntersectionResult calcIntersection(Node node, Ray3D ray) {
		// pq Formel
		double p = ((ray.getPoint().multiply(ray.getDirection()) * 2) - (focusPoint.multiply(ray.getDirection()) * 2))
				/ (ray.getDirection().multiply(ray.getDirection()));
		double q = ((ray.getPoint().multiply(ray.getPoint())) - (ray.getPoint().multiply(focusPoint) * 2)
				+ (focusPoint.multiply(focusPoint)) - (radius * radius))
				/ (ray.getDirection().multiply(ray.getDirection()));
		// pq Ergebnis 1
		double l1 = (-p / 2) + Math.sqrt(((p * p) / 4) - q);
		// pq Ergebnis 2
		double l2 = (-p / 2) - Math.sqrt(((p * p) / 4) - q);

		double lambda = 0;

		if (l1 < l2 && l1 > 0) {
			lambda = l1;
		} else if (l2 > 0) {
			lambda = l2;
		} else {
			return null;
		}

		Vector3 xs = new Vector3();
		xs.copy(ray.getPoint());
		xs = xs.add(ray.getDirection().multiply(lambda));

		IntersectionResult result = new IntersectionResult();
		result.normal.copy(xs);
		result.normal = result.normal.subtract(focusPoint);
		result.normal.normalize();
		result.object = this;
		result.point.copy(xs);

		return result;

	}

	@Override
	public void drawGl(GL2 gl) {
		// gl.glColor3d(0.75, 0.25, 0.25);
		GLU glu = new GLU();
		// Apply translation
		gl.glPushMatrix();
		// set color
		gl.glColor3d(color.getRed(), color.getGreen(), color.getBlue());
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
