package computergraphics.objects;

import computergraphics.math.Vector3;
import computergraphics.scenegraph.ColorNode;
import computergraphics.scenegraph.CuboidNode;
import computergraphics.scenegraph.GroupNode;
import computergraphics.scenegraph.RotationNode;
import computergraphics.scenegraph.ScaleNode;
import computergraphics.scenegraph.SphereNode;
import computergraphics.scenegraph.TranslationNode;

public class Tree extends GroupNode {

	public Tree(double scale) {
		//tree size
		ScaleNode treeSize = new ScaleNode(new Vector3(scale,scale,scale));
		
		//tree rotation
		RotationNode treeRotation = new RotationNode(new Vector3(1,0,0), 90);
		treeSize.addChild(treeRotation);
		
		//simple tree
	    GroupNode tree = new GroupNode();
	    treeRotation.addChild(tree);
	    
	    //trunk
	    ColorNode trunkColor = new ColorNode(.5, .25, 0);
	    tree.addChild(trunkColor);
	    CuboidNode trunkNode = new CuboidNode(1, 2, 1);
	    trunkColor.addChild(trunkNode);
	    
	    //crown 
	    TranslationNode crownTranslation = new TranslationNode(new Vector3(0,2,0));
	    tree.addChild(crownTranslation);
	    ColorNode crownColor = new ColorNode(0, 1, 0);
	    crownTranslation.addChild(crownColor);
	    SphereNode crownNode = new SphereNode(2, 16,new Vector3(0,0,0));
	    crownColor.addChild(crownNode);
	    
	    this.addChild(treeSize);
	}
}
