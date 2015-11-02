package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;

public class ColorNode extends Node {
	
	/**
	 * rot,grün,blau
	 */
	private double r,g,b;
	
	public ColorNode(double r,double g,double b) {
		this.r=r;
		this.g=g;
		this.b=b;
	}
	
	@Override
	public void drawGl(GL2 gl) {
		// Remember current state of the render system
	    gl.glPushMatrix();
		
	    //set color
		gl.glColor3d(r, g, b);
		
		// Draw all children
	    for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
	      getChildNode(childIndex).drawGl(gl);
	    }
	    
    	// Restore original state
	    gl.glPopMatrix();
	}
	
}
