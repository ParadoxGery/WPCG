package computergraphics.projects.raytracing;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import computergraphics.datastructures.IntersectionResult;
import computergraphics.datastructures.Ray3D;
import computergraphics.framework.Camera;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.ColorNode;
import computergraphics.scenegraph.LightSource;
import computergraphics.scenegraph.Node;
import computergraphics.scenegraph.SphereNode;

/**
 * Creates a raytraced image of the current scene.
 */
public class Raytracer {

	private static final int DEFAULTMATERIALPARAMETER = 20;
	
	/**
	 * List of Lightsources
	 */
	private List<LightSource> lightsources = new ArrayList();
	
	/**
	 * Reference to the current camera.
	 */
	private final Camera camera;

	/**
	 * Reference to the current root node.
	 */
	private final Node rootNode;

	/**
	 * Constructor.
	 * 
	 * @param camera
	 *            Scene camera.
	 * @param rootNode
	 *            Root node of the scenegraph.
	 */
	public Raytracer(Camera camera, Node rootNode) {
		this.camera = camera;
		this.rootNode = rootNode;
	}

	/**
	 * Creates a raytraced image for the current view with the provided
	 * resolution. The opening angle in x-direction is grabbed from the camera,
	 * the opening angle in y-direction is computed accordingly.
	 * 
	 * @param resolutionX
	 *            X-Resolution of the created image.
	 * 
	 * @param resolutionX
	 *            Y-Resolution of the created image.
	 */
	public Image render(int resolutionX, int resolutionY) {
		BufferedImage image = new BufferedImage(resolutionX, resolutionY, BufferedImage.TYPE_INT_RGB);

		Vector3 viewDirection = camera.getRef().subtract(camera.getEye()).getNormalized();
		Vector3 xDirection = viewDirection.cross(camera.getUp()).getNormalized();
		Vector3 yDirection = viewDirection.cross(xDirection).getNormalized();
		double openingAngleYScale = Math.sin(camera.getOpeningAngle() * Math.PI / 180.0);
		double openingAngleXScale = openingAngleYScale * (double) resolutionX / (double) resolutionY;

		for (int i = 0; i < resolutionX; i++) {
			double alpha = (double) i / (double) (resolutionX + 1) - 0.5;
			for (int j = 0; j < resolutionY; j++) {
				double beta = (double) j / (double) (resolutionY + 1) - 0.5;
				Vector3 rayDirection = viewDirection.add(xDirection.multiply(alpha * openingAngleXScale))
						.add(yDirection.multiply(beta * openingAngleYScale)).getNormalized();
				Ray3D ray = new Ray3D(camera.getEye(), rayDirection);

				Vector3 color = trace(ray, 0);

				// Adjust color boundaries
				for (int index = 0; index < 3; index++) {
					color.set(index, Math.max(0, Math.min(1, color.get(index))));
				}

				image.setRGB(i, j,
						new Color((int) (255 * color.get(0)), (int) (255 * color.get(1)), (int) (255 * color.get(2)))
								.getRGB());
			}
		}

		return image;
	}

	/**
	 * Compute a color from tracing the ray into the scene.
	 * 
	 * @param ray
	 *            Ray which needs to be traced.
	 * @param recursion
	 *            Current recursion depth. Initial recursion depth of the rays
	 *            through the image plane is 0. This parameter is used to abort
	 *            the recursion.
	 * 
	 * @return Color in RGB. All values are in [0,1];
	 */
	private Vector3 trace(Ray3D ray, int recursion) {
		Color c = Color.white;
		IntersectionResult result = null;
		List<Node> nodes = new ArrayList<>(rootNode.getAllChildNodesRec());
		for(Node n : nodes){
			IntersectionResult newInterseciton = n.calcIntersection(n, ray);
			if(newInterseciton != null){
				if(result == null){
					result = newInterseciton;
				}else if(newInterseciton.point.getNormBetween(ray.getPoint())<result.point.getNormBetween(ray.getPoint())){
					result = newInterseciton;
				}
			}
		}
		// Your task
		if(result == null) return new Vector3(0,0,0);
		
		Vector3 e = camera.getEye().subtract(result.point).getNormalized();
		
		Vector3 colorDiff = new Vector3();
		Vector3 colorSpec = new Vector3();
		
		for(LightSource ls : lightsources){
			
			Vector3 l = ls.getPosition().subtract(result.point).getNormalized();
			double nl = result.normal.multiply(l);
			if(nl>0){
				colorDiff = result.object.getColor().multiply(nl);
			}
			
			Vector3 r = l.subtract(result.normal.multiply(l.multiply(result.normal)*2));
			Vector3 minusVs = ray.getDirection().multiply(-1);
			double r_vs = r.multiply(minusVs);
			if(r_vs>0){
				colorSpec = new Vector3(1,1,1);
				colorSpec = colorSpec.multiply(Math.pow(r.multiply(minusVs), DEFAULTMATERIALPARAMETER));
			}
		}
		//Ray3D shadowRay = new Ray3D(result.point, lightsources.get(0).getPosition().subtract(result.point).getNormalized());
		//IntersectionResult shadowResult = result.object.calcIntersection(result.object, shadowRay);
		//if(shadowResult != null){
			//return new Vector3(0,0,0);
		//}
		return result.object.getColor().multiply(.3).add(colorSpec.multiply(.3)).add(colorDiff.multiply(.3));
	}
	
	public void addLightsource(LightSource ls){
		lightsources.add(ls);
	}

}