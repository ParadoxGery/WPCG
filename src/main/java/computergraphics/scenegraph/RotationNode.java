package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;
import computergraphics.math.Vector3;

public class RotationNode extends Node {
	
	private Vector3 rotationAxis = new Vector3();
	private float rotation;
	
	/**
	 * @return the rotation
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * @param rotation the rotation to set
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public RotationNode(Vector3 rotationAxis, float rotation) {
		this.rotationAxis.copy(rotationAxis);
		this.rotation = rotation;
	}

	@Override
	public void drawGl(GL2 gl) {
		// Remember current state of the render system
	    gl.glPushMatrix();

	    // Apply rotation
	    gl.glRotatef(rotation, (float)rotationAxis.get(0), (float)rotationAxis.get(1), (float)rotationAxis.get(2));
	    
	    // Draw all children
	    for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
	      getChildNode(childIndex).drawGl(gl);
	    }

	    // Restore original state
	    gl.glPopMatrix();
	}
	
}
