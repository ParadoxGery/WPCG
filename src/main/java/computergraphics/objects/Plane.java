package computergraphics.objects;

import computergraphics.math.Vector3;
import computergraphics.scenegraph.ColorNode;
import computergraphics.scenegraph.CuboidNode;
import computergraphics.scenegraph.GroupNode;
import computergraphics.scenegraph.RotationNode;
import computergraphics.scenegraph.ScaleNode;
import computergraphics.scenegraph.TranslationNode;

public class Plane extends GroupNode {

	public Plane(double scale) {
		ScaleNode planeSize = new ScaleNode(new Vector3(scale,scale,scale));
		
		RotationNode planeRotation = new RotationNode(new Vector3(1,0,0), 90);
		planeSize.addChild(planeRotation);
		
		//trunk
		ColorNode trunkColor = new ColorNode(.1,.1,.1);
		planeRotation.addChild(trunkColor);
		CuboidNode planeTrunk = new CuboidNode(4, 1, 1);
		trunkColor.addChild(planeTrunk);
		
		ColorNode wingColor = new ColorNode(.5, .1, 0);
		planeRotation.addChild(wingColor);
		
		RotationNode wingRotation = new RotationNode(new Vector3(1,0,0), -90);
		wingColor.addChild(wingRotation);
		
		//wing A
		TranslationNode wingAPosition = new TranslationNode(new Vector3(-.5,0,.5));
		wingRotation.addChild(wingAPosition);
		CuboidNode wingA = new CuboidNode(1, 3.5, .1);
		wingAPosition.addChild(wingA);
		
		//wing B
		TranslationNode wingBPosition = new TranslationNode(new Vector3(-.5,0,-.5));
		wingRotation.addChild(wingBPosition);
		CuboidNode wingB = new CuboidNode(1, 3.5, .1);
		wingBPosition.addChild(wingB);
		
		//controll
		TranslationNode controllPosition = new TranslationNode(new Vector3(2.25,.25,0));
		planeRotation.addChild(controllPosition);
		CuboidNode controll = new CuboidNode(.5, 1.5, .1);
		controllPosition.addChild(controll);
		
		//rotor
		TranslationNode rotorPosition = new TranslationNode(new Vector3(-2.25,0,0));
		planeRotation.addChild(rotorPosition);
		RotationNode rotorRotation = new RotationNode(new Vector3(0,1,0), 90);
		rotorPosition.addChild(rotorRotation);
		CuboidNode rotor = new CuboidNode(.2, 1.5, .1);
		rotorRotation.addChild(rotor);
		
		this.addChild(planeSize);
	}
	
}
