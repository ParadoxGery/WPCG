package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.IntersectionResult;
import computergraphics.datastructures.Ray3D;
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
	
	private javafx.scene.paint.Color color = Color.GREEN;

	public PlainNode(Vector3 a, Vector3 b, Vector3 c, double scale) {
		this.a.copy(a);
		this.b.copy(b);
		this.c.copy(c);

		this.u = b.subtract(a);
		this.v = c.subtract(a);

		this.u = this.u.multiply(scale);
		this.v = this.v.multiply(scale);

		this.nE = u.cross(v).getNormalized();
		this.pE = this.pE.add(a);
	}
	
	public PlainNode(Vector3 a, Vector3 b, Vector3 c, double scale, javafx.scene.paint.Color color) {
		this(a,b,c,scale);
		this.color = color;
	}

	@Override
	public Vector3 getColor(){
		return new Vector3(color.getRed(),color.getGreen(),color.getBlue());
	}
	
	@Override
	public IntersectionResult calcIntersection(Node node,Ray3D ray) {
		double nepe = nE.multiply(pE);
		double neps = nE.multiply(ray.getPoint());
		double nevs = nE.multiply(ray.getDirection());

		double lambda = (nepe - neps) / nevs;

		if (lambda <= 0) {
			Vector3 xs = new Vector3();
			xs.copy(ray.getPoint());
			xs = xs.add(ray.getDirection().multiply(lambda));

			IntersectionResult result = new IntersectionResult();
			result.normal.copy(nE);
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
		//set color
		gl.glColor3d(color.getRed(),color.getGreen(),color.getBlue());
		gl.glNormal3d(nE.get(0), nE.get(1), nE.get(2));
		gl.glVertex3d(u.get(0), u.get(1), u.get(2));
		gl.glVertex3d(v.get(0), v.get(1), v.get(2));
		gl.glVertex3d(-1 * u.get(0), -1 * u.get(1), -1 * u.get(2));
		gl.glVertex3d(-1 * v.get(0), -1 * v.get(1), -1 * v.get(2));
		gl.glEnd();
		gl.glPopMatrix();
	}

}
