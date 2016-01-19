/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.scenegraph;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.IntersectionResult;
import computergraphics.datastructures.Ray3D;
import computergraphics.math.Vector3;

/**
 * Parent class for all scene graph nodes.
 * 
 * @author Philipp Jenke
 *
 */
public abstract class Node {

	/**
	 * List of child nodes
	 */
	private List<Node> children = new ArrayList<Node>();

	/**
	 * Add a child node.
	 */
	public void addChild(Node child) {
		children.add(child);
	}

	/**
	 * Return the child at the given index.
	 */
	public Node getChildNode(int index) {
		if (index < 0 || index >= getNumberOfChildren()) {
			System.out.println("getChildNode: invalid index.");
			return null;
		}
		return children.get(index);
	}

	/**
	 * Return the number of children
	 */
	public int getNumberOfChildren() {
		return children.size();
	}

	/**
	 * This method is called to draw the node using OpenGL commands. Override in
	 * implementing nodes. Do not forget to call the same method for the
	 * children.
	 */
	public abstract void drawGl(GL2 gl);

	// DEBUGGING
	public IntersectionResult calcIntersection(Node node, Ray3D ray) {
		return null;
	}

	public Vector3 getColor(Vector3 point) {
		return new Vector3(0, 1, 0);
	}

	public IntersectionResult findIntersection(Node object, Ray3D ray) {
		return null;
	}

	/**
	 * gibt eine Liste aller Kinder und Kindeskinder....  zur�ck nicht die debugNode
	 * @return Liste alle Kinder und Kindeskinder
	 */
	public List<Node> getAllChildNodesRec() {
		List<Node> nodes = new ArrayList<>();
		for (int i = 0; i < getNumberOfChildren(); i++) {
			if(getChildNode(i) instanceof DebugNode) continue;
			nodes.add(getChildNode(i));
			nodes.addAll(getChildNode(i).getAllChildNodesRec());
		}
		return nodes;
	}
	
	public double getR(){
		return 0;
	}

	public enum Material {
		NO_REFLEKTION(0),
		REFLEKTION25(0.25),
		ONLY_REFLEKTION(1),
		REFLEKTION75(.75),
		HALF_REFLEKTION(0.5);
		
		private double r = 0;
		Material(double reflektion){
			r = reflektion;
		}
		
		public double getR(){
			return r;
		}
		
	}
}
