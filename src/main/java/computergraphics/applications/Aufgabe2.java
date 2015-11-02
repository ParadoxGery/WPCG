package computergraphics.applications;

import computergraphics.framework.AbstractCGFrame;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.ColorNode;
import computergraphics.scenegraph.RotationNode;
import computergraphics.scenegraph.ShaderNode;
import computergraphics.scenegraph.SingleTriangleNode;
import computergraphics.scenegraph.SphereNode;
import computergraphics.scenegraph.TranslationNode;
import computergraphics.scenegraph.ShaderNode.ShaderType;

public class Aufgabe2 extends AbstractCGFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5916160721020479450L;

	public Aufgabe2(int timerInterval) {
		super(timerInterval);
		
		// Shader node does the lighting computation
	    ShaderNode shaderNode = new ShaderNode(ShaderType.PHONG);
	    getRoot().addChild(shaderNode);

	    //triangle color
	    ColorNode triangleColorNode = new ColorNode(0, 0, 1);
	    shaderNode.addChild(triangleColorNode);
	    
	    //triangle rotation
	    RotationNode triangleRotationNode = new RotationNode(new Vector3(1,0,0), 90f);
	    triangleColorNode.addChild(triangleRotationNode);
	    
	    // Simple triangle
	    SingleTriangleNode triangleNode = new SingleTriangleNode();
	    triangleRotationNode.addChild(triangleNode);

	    //sphere color
	    ColorNode sphereColorNode = new ColorNode(0, 1, 0);
	    shaderNode.addChild(sphereColorNode);
	    
	    //sphere translation
	    TranslationNode sphereTranslationNode = new TranslationNode(new Vector3(1,1,0));
	    sphereColorNode.addChild(sphereTranslationNode);
	    
	    // Sphere
	    SphereNode sphereNode = new SphereNode(0.25, 20);
	    sphereTranslationNode.addChild(sphereNode);
	}

	@Override
	protected void timerTick() {
		
	}
	
	public static void main(String[] args) {
		new Aufgabe2(1000);
	}
	
}
