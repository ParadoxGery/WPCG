/**
 * Prof. Philipp Jenke Hochschule für Angewandte Wissenschaften (HAW), Hamburg Base framework for "WP Computergrafik".
 */

package computergraphics.applications;

import javax.swing.JOptionPane;

import computergraphics.datastructures.BezierKurve;
import computergraphics.datastructures.HalfEdgeTriangleMesh;
import computergraphics.datastructures.ITriangleMesh;
import computergraphics.datastructures.ImplicitFunktions;
import computergraphics.datastructures.KurveI;
import computergraphics.datastructures.MonomKurve;
import computergraphics.datastructures.ObjIO;
import computergraphics.framework.AbstractCGFrame;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.ColorNode;
import computergraphics.scenegraph.CurvatureNode;
import computergraphics.scenegraph.KurveNode;
import computergraphics.scenegraph.MarchingCubesNode;
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
	
	private KurveNode bkn,mkn,mk2n;
	
	
	private double a = .5;
	
	/**
	 * Constructor.
	 */
	public CGFrame(int timerInverval) {
		super(timerInverval);
		
		// Shader node does the lighting computation
		ShaderNode shaderNode = new ShaderNode(ShaderType.PHONG);
		getRoot().addChild(shaderNode);
		
		ColorNode kontrollColor = new ColorNode(Color.MEDIUMPURPLE.darker());//114,84,154
		shaderNode.addChild(kontrollColor);
		
		ColorNode punktColor = new ColorNode(Color.BISQUE);
		shaderNode.addChild(punktColor);
		
		BezierKurve k = new BezierKurve();
		k.addKontrollpunkt(new Vector3(-2, 0, 0));
		k.addKontrollpunkt(new Vector3(-1, 1, 0));
		k.addKontrollpunkt(new Vector3(0, -2, 0));
		k.addKontrollpunkt(new Vector3(1, 1, 0));
		k.addKontrollpunkt(new Vector3(2, 0, 0));
		
		MonomKurve mk = new MonomKurve();
		mk.addPunkt(new Vector3(0,0,0));
		mk.addPunkt(new Vector3(1,1,0));
		mk.addPunkt(new Vector3(1,1,1));
		mk.interpolate();
		
		for(Vector3 p : mk.getPunkte()){
			TranslationNode t = new TranslationNode(p);
			SphereNode s = new SphereNode(.1, 10);
			t.addChild(s);
			punktColor.addChild(t);
		}
		
		MonomKurve mk2 = new MonomKurve();
		mk2.addKontrollpunkt(new Vector3(0,1,0));
		mk2.addKontrollpunkt(new Vector3(-1,0,0));
		mk2.addKontrollpunkt(new Vector3(-1,1,0));
		
		for(int i = 0; i< mk.getGrad(); i++){
			TranslationNode t = new TranslationNode(mk.getKontrollpunktList().get(i));
			SphereNode s = new SphereNode(.05, 10);
			t.addChild(s);
			kontrollColor.addChild(t);
		}
		
		for(int i = 0; i< mk2.getGrad(); i++){
			TranslationNode t = new TranslationNode(mk2.getKontrollpunktList().get(i));
			SphereNode s = new SphereNode(.05, 10);
			t.addChild(s);
			kontrollColor.addChild(t);
		}
				
		for(int i = 0; i< k.getGrad(); i++){
			TranslationNode t = new TranslationNode(k.getKontrollpunktList().get(i));
			SphereNode s = new SphereNode(.05, 10);
			t.addChild(s);
			kontrollColor.addChild(t);
		}
		
		ColorNode kurveColor = new ColorNode(Color.BLACK.darker());
		shaderNode.addChild(kurveColor);
		
		bkn = new KurveNode(k,.001,.5);
		kurveColor.addChild(bkn);
		
		mkn = new KurveNode(mk,.001,.5);
		kurveColor.addChild(mkn);	
		
		mk2n = new KurveNode(mk2,.001,.5);
		kurveColor.addChild(mk2n);	
		
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
			String tString = JOptionPane.showInputDialog(null,"Insert t parameter for tangente");
			double t = Double.parseDouble(tString);
			bkn.setTangentenT(t);
			bkn.updateGlList();
			mkn.setTangentenT(t);
			mkn.updateGlList();
			mk2n.setTangentenT(t);
			mk2n.updateGlList();
		}
		if((char)keyCode == '1'){
			bkn.toggle();
		}
		if((char)keyCode == '2'){
			mkn.toggle();
		}
		if((char)keyCode == '3'){
			mk2n.toggle();
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
