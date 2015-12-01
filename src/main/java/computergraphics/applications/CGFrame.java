/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */

package computergraphics.applications;

import computergraphics.datastructures.HalfEdgeTriangleMesh;
import computergraphics.datastructures.ITriangleMesh;
import computergraphics.datastructures.ImplicitFunktions;
import computergraphics.datastructures.ObjIO;
import computergraphics.framework.AbstractCGFrame;
import computergraphics.scenegraph.ColorNode;
import computergraphics.scenegraph.CurvatureNode;
import computergraphics.scenegraph.MarchingCubesNode;
import computergraphics.scenegraph.ShaderNode;
import computergraphics.scenegraph.ShaderNode.ShaderType;
import computergraphics.scenegraph.SingleTriangleNode;
import computergraphics.scenegraph.SphereNode;
import computergraphics.scenegraph.TriangleMeshLaPlaceNode;
import computergraphics.scenegraph.TriangleMeshNode;
import javafx.scene.paint.Color;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 */
public class CGFrame extends AbstractCGFrame {

  /**
   * 
   */
  private static final long serialVersionUID = 4257130065274995543L;

  private TriangleMeshLaPlaceNode laPlace; 
  private CurvatureNode curveNode;
  private HalfEdgeTriangleMesh mesh;
  
  private double a = .5;
  /**
   * Constructor.
   */
  public CGFrame(int timerInverval) {
    super(timerInverval);

    // Shader node does the lighting computation
    ShaderNode shaderNode = new ShaderNode(ShaderType.PHONG);
    getRoot().addChild(shaderNode);

    ColorNode objColor = new ColorNode(Color.MEDIUMPURPLE.darker());//114,84,154
    shaderNode.addChild(objColor);
    
    mesh = new HalfEdgeTriangleMesh();

    MarchingCubesNode cubeNode = new MarchingCubesNode(25,-2, 2, mesh, ImplicitFunktions.KUGEL);
    
    shaderNode.addChild(cubeNode);
    
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
	  System.out.println("Key pressed: " + (char) keyCode);
	    if((char)keyCode == 'S'){
    		mesh.doLaplaceSmoothing(a);
	    	curveNode.setUpdateGlList(true);
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
