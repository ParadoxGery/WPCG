package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;

public class QuadNode extends Node {
	
	private float width,height;
	
	public QuadNode(float width,float height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void drawGl(GL2 gl) {
		gl.glBegin(GL2.GL_QUADS);
	    gl.glNormal3f(0, 0, 1);
		gl.glVertex3d(-width / 2.0, -height / 2.0, 0);
		gl.glVertex3d(width / 2.0, -height / 2.0, 0);
		gl.glVertex3d(width / 2.0, height / 2.0, 0);
		gl.glVertex3d(-width / 2.0, height / 2.0, 0);
	    gl.glEnd();
	}
	
}
