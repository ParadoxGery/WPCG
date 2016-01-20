package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.IntersectionResult;
import computergraphics.datastructures.Ray3D;
import computergraphics.math.MathHelpers;
import computergraphics.math.Vector3;
import javafx.scene.paint.Color;

public class PlainNode extends Node {

	private Vector3 a = new Vector3(), b = new Vector3(), c = new Vector3();

	/**
	 * direction normal of plain for hessische Normalenform
	 */
	private Vector3 nE = new Vector3();

	/**
	 * mittelpunkt der Ebene
	 */
	private Vector3 pE = new Vector3();

	/**
	 * b - a
	 */
	private Vector3 u = new Vector3();

	/**
	 * c-a
	 */
	private Vector3 v = new Vector3();

	private Vector3 tu = new Vector3();
	private Vector3 tv = new Vector3();

	/**
	 * Farbe der Ebene
	 */
	private javafx.scene.paint.Color color = Color.GREEN;

	private Material mat;
	
	public PlainNode(Vector3 a, Vector3 b, Vector3 c, double scale) {
		this.a.copy(a);
		this.b.copy(b);
		this.c.copy(c);

		this.v = b.subtract(a);
		this.u = c.subtract(a);

		this.nE = u.cross(v).getNormalized();
		// Mittelpunkt der Ebene pE
		this.pE = this.pE.add(a);

		tu.copy(Vector3.X_AXIS);
		tu = tu.cross(nE);
		tv = tu.cross(nE).getNormalized();
		tu.normalize();
	}

	public PlainNode(Vector3 a, Vector3 b, Vector3 c, double scale, javafx.scene.paint.Color color) {
		this(a, b, c, scale);
		this.color = color;
	}
	
	public PlainNode(Vector3 a, Vector3 b, Vector3 c, double scale, javafx.scene.paint.Color color,Material mat) {
		this(a, b, c, scale,color);
		this.mat = mat;
	}
	
	public double getR(){
		return mat.getR();
	}

	@Override
	public Vector3 getColor(Vector3 point) {
		// return new Vector3(color.getRed(),color.getGreen(),color.getBlue());
		double u, v;
		u = tu.multiply(point);
		v = tv.multiply(point);
		u = u > 0 ? u : -u + 1;
		v = v > 0 ? v : -v + 1;
		if((int)u%2 == (int)v%2) return new Vector3();
		return new Vector3(1,1,1);
	}

	@Override
	public IntersectionResult calcIntersection(Node node, Ray3D ray) {
		double nepe = nE.multiply(pE); // vector nE * vector pE
		double neps = nE.multiply(ray.getPoint()); // vector nE * vector pS
		double nevs = nE.multiply(ray.getDirection()); // vector nE * vector vS

		double lambda = (nepe - neps) / nevs;

		// wenn lambda > 0 dann gibt es ein ergebnis
		if (lambda >= MathHelpers.EPSILON) {
			Vector3 xs = new Vector3();
			xs.copy(ray.getPoint());
			xs = xs.add(ray.getDirection().multiply(lambda));

			IntersectionResult result = new IntersectionResult();
			result.normal.copy(nE); // jeder punkt der ebene hat die selbe
									// Normale
			result.object = this;
			result.point.copy(xs);

			return result;
		}

		return null;
	}

	@Override
	public void drawGl(GL2 gl) {
		gl.glBegin(GL2.GL_QUADS);
		gl.glPushMatrix();
		// set color
		gl.glColor3d(color.getRed(), color.getGreen(), color.getBlue());
		gl.glNormal3d(nE.get(0), nE.get(1), nE.get(2));
		gl.glVertex3d(u.get(0), u.get(1), u.get(2));
		gl.glVertex3d(v.get(0), v.get(1), v.get(2));
		gl.glVertex3d(-1 * u.get(0), -1 * u.get(1), -1 * u.get(2));
		gl.glVertex3d(-1 * v.get(0), -1 * v.get(1), -1 * v.get(2));
		gl.glEnd();
		gl.glPopMatrix();

		gl.glBegin(GL2.GL_LINES);
		gl.glVertex3d(a.get(0), a.get(1), a.get(2));
		Vector3 normalEnd = a.add(nE);
		gl.glVertex3d(normalEnd.get(0), normalEnd.get(1), normalEnd.get(2));
		gl.glEnd();
	}

}
