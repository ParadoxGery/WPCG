package computergraphics.applications;

import computergraphics.framework.AbstractCGFrame;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.ColorNode;
import computergraphics.scenegraph.ScaleNode;
import computergraphics.scenegraph.ShaderNode;
import computergraphics.scenegraph.SingleTriangleNode;
import computergraphics.scenegraph.SphereNode;
import computergraphics.scenegraph.ShaderNode.ShaderType;

public class Aufgabe1 extends AbstractCGFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8275568545431965641L;

	public Aufgabe1(int timerInterval) {
		super(timerInterval);
		
	    // Shader node does the lighting computation
	    ShaderNode shaderNode = new ShaderNode(ShaderType.PHONG);
	    getRoot().addChild(shaderNode);

	    //triangle color
	    ColorNode triangleColorNode = new ColorNode(0, 0, 1);
	    shaderNode.addChild(triangleColorNode);
	    
	    //triangle scale
	    ScaleNode triangleScaleNode = new ScaleNode(new Vector3(2,2,2));
	    triangleColorNode.addChild(triangleScaleNode);
	    
	    // Simple triangle
	    SingleTriangleNode triangleNode = new SingleTriangleNode();
	    triangleScaleNode.addChild(triangleNode);

	    //sphere color
	    ColorNode sphereColorNode = new ColorNode(0, 1, 0);
	    shaderNode.addChild(sphereColorNode);
	    
	    // Sphere
	    SphereNode sphereNode = new SphereNode(0.25, 20);
	    sphereColorNode.addChild(sphereNode);
	}

	@Override
	protected void timerTick() {
		
	}
	
	public static void main(String[] args) {
		new Aufgabe1(1000);
	}
	
}
