package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;
import computergraphics.math.Vector3;

public class PlainNode extends Node {
	
	private Vector3 a = new Vector3(), b = new Vector3(), c = new Vector3();
	
	/**
	 * local point of plain for hessische Normalenform
	 */
	private Vector3 pE = new Vector3();
	
	/**
	 * direction normal of plain for hessische Normalenform
	 */
	private Vector3 nE = new Vector3();
	
	/**
	 * b - a
	 */
	private Vector3 u = new Vector3();
	
	/**
	 *  c-a
	 */
	private Vector3 v = new Vector3();
	
	public PlainNode(Vector3 a,Vector3 b,Vector3 c) {
		this.a.copy(a);
		this.b.copy(b);
		this.c.copy(c);
		
		this.u = b.subtract(a);
		this.v = c.subtract(a);
		
		this.nE = u.cross(v);
	}

	@Override
	public void drawGl(GL2 gl) {
		// TODO Auto-generated method stub
		
	}

}
