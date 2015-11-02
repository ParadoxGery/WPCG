package computergraphics.applications;

import java.util.Random;
import computergraphics.framework.AbstractCGFrame;
import computergraphics.math.Vector3;
import computergraphics.objects.Plane;
import computergraphics.objects.Tree;
import computergraphics.scenegraph.ColorNode;
import computergraphics.scenegraph.Node;
import computergraphics.scenegraph.QuadNode;
import computergraphics.scenegraph.RotationNode;
import computergraphics.scenegraph.ShaderNode;
import computergraphics.scenegraph.ShaderNode.ShaderType;
import computergraphics.scenegraph.TranslationNode;

public class Aufgabe3 extends AbstractCGFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9137087903958401474L;
	
	private final int TREECOUNT = 50;
	private final int GROUNDSIZE = 10;
	private final double TREESIZE = .1;
	private final double PLANESIZE = .2;

	private RotationNode planeAnim;
	
	public Aufgabe3(int timerInterval) {
		super(timerInterval);

		// Shader node does the lighting computation
	    ShaderNode shaderNode = new ShaderNode(ShaderType.PHONG);
	    getRoot().addChild(shaderNode);

	    //ground
	    ColorNode groundColor = new ColorNode(.2, .4, 0);
	    shaderNode.addChild(groundColor);
	    QuadNode ground = new QuadNode(GROUNDSIZE,GROUNDSIZE);
	    groundColor.addChild(ground);
    	
	    for(int i = 0; i < TREECOUNT; i++){
	    	shaderNode.addChild(positionTreeAtRandom(GROUNDSIZE,GROUNDSIZE));
	    }
	    
	    planeAnim = new RotationNode(new Vector3(0,0,1), 0);
	    shaderNode.addChild(planeAnim);
	    
	    TranslationNode planePosition = new TranslationNode(new Vector3(0,2,1));
	    planeAnim.addChild(planePosition);
	    
	    planePosition.addChild(new Plane(PLANESIZE));
	}

	@Override
	protected void timerTick() {
		planeAnim.setRotation(planeAnim.getRotation()+2);
	}
	
	public static void main(String[] args) {
		new Aufgabe3(50);
	}
	
	private Node positionTreeAtRandom(int width, int height){
		Random rnd = new Random();
		
		//position
		TranslationNode treePosition = new TranslationNode(new Vector3(rnd.nextInt(width)-width/2,rnd.nextInt(height)-width/2,.1));
		treePosition.addChild(new Tree(TREESIZE));
		return treePosition;
	}
	
}
