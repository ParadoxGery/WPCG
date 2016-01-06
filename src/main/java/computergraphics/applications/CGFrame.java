/**
 * Prof. Philipp Jenke Hochschule für Angewandte Wissenschaften (HAW), Hamburg Base framework for "WP Computergrafik".
 */

package computergraphics.applications;

import javax.swing.JOptionPane;

import computergraphics.datastructures.BezierKurve;
import computergraphics.datastructures.HalfEdgeTriangleMesh;
import computergraphics.datastructures.ITriangleMesh;
import computergraphics.datastructures.ImplicitFunktions;
import computergraphics.datastructures.Kurve;
import computergraphics.datastructures.MonomKurve;
import computergraphics.datastructures.ObjIO;
import computergraphics.framework.AbstractCGFrame;
import computergraphics.math.Vector3;
import computergraphics.projects.raytracing.ImageViewer;
import computergraphics.projects.raytracing.Raytracer;
import computergraphics.scenegraph.ColorNode;
import computergraphics.scenegraph.CurvatureNode;
import computergraphics.scenegraph.DebugNode;
import computergraphics.scenegraph.KurveNode;
import computergraphics.scenegraph.LightSource;
import computergraphics.scenegraph.MarchingCubesNode;
import computergraphics.scenegraph.PlainNode;
import computergraphics.scenegraph.ShaderNode;
import computergraphics.scenegraph.ShaderNode.ShaderType;
import computergraphics.scenegraph.SingleTriangleNode;
import computergraphics.scenegraph.SphereNode;
import computergraphics.scenegraph.TranslationNode;
import computergraphics.scenegraph.TriangleMeshLaPlaceNode;
import computergraphics.scenegraph.TriangleMeshNode;
import javafx.scene.paint.Color;

/**
 * Application for the first exercise.
 * @author Philipp Jenke
 */
public class CGFrame extends AbstractCGFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4257130065274995543L;
	
	private Raytracer r;
	
	/**
	 * Constructor.
	 */
	public CGFrame(int timerInverval) {
		super(timerInverval);
		
		// Shader node does the lighting computation
		//ShaderNode shaderNode = new ShaderNode(ShaderType.PHONG);
		//getRoot().addChild(shaderNode);
		
		LightSource ls = new LightSource(new Vector3(-.1,-1,3.5), new Vector3(1,1,1));
		DebugNode lsNode = new DebugNode(.3, 10, ls.getPosition(),Color.RED);
		getRoot().addChild(lsNode);
		
		LightSource ls2 = new LightSource(new Vector3(.1,-1,3.5), new Vector3(1,1,1));
		DebugNode lsNode2 = new DebugNode(.3, 10, ls2.getPosition(),Color.RED);
		getRoot().addChild(lsNode2);
		
		SphereNode sn = new SphereNode(2, 20, new Vector3(0,4,2),Color.AQUA);
		getRoot().addChild(sn);
		
		SphereNode sn2 = new SphereNode(.2, 20, new Vector3(0,1,2),Color.AQUA);
		getRoot().addChild(sn2);
		
		PlainNode pn = new PlainNode(new Vector3(), new Vector3(-1,2,1), new Vector3(1,2,1), 100,Color.BROWN);
		getRoot().addChild(pn);
		
		r = new Raytracer(getCamera(), getRoot());
		r.addLightsource(ls);
		r.addLightsource(ls2);
		
	}
	
	/*
	 * (nicht-Javadoc)
	 * 
	 * @see computergrafik.framework.ComputergrafikFrame#timerTick()
	 */
	@Override
	protected void timerTick() {
		//System.out.println("Tick");
	}
	
	public void keyPressed(int keyCode) {
		System.out.println("Key pressed: " + (char)keyCode);
		if((char)keyCode == 'T') {
			new ImageViewer(r.render(1024, 768));
		}
	}
	
	/**
	 * Program entry point.
	 */
	public static void main(String[] args) {
		// The timer ticks every 1000 ms.
		new CGFrame(1000);
	}
}
