package computergraphics.scenegraph;

import java.awt.Color;
import com.jogamp.opengl.GL2;

public class ColorNode extends Node {
	
	/**
	 * rot,gr�n,blau
	 */
	private double r, g, b;
	
	public ColorNode(double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public ColorNode(int r, int g, int b) {
		this(r / 255.0, g / 255.0, b / 255.0);
	}
	
	public ColorNode(Color col) {
		this(col.getRed(), col.getGreen(), col.getBlue());
	}
	
	@Override
	public void drawGl(GL2 gl) {
		// Remember current state of the render system
		gl.glPushMatrix();
		
		//set color
		gl.glColor3d(r, g, b);
		
		// Draw all children
		for(int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
			getChildNode(childIndex).drawGl(gl);
		}
		
		// Restore original state
		gl.glPopMatrix();
	}
	
}
