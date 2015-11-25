package computergraphics.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import computergraphics.math.Vector3;
import javafx.scene.paint.Color;

/**
 * @author Gery
 */
public class HalfEdgeTriangleMesh implements ITriangleMesh {

	/**
	 * Liste aller Punkte im Mesh
	 */
	private List<Vertex> vertexList = new ArrayList<Vertex>();

	/**
	 * Liste aller Dreiecke im Mesh
	 */
	private List<TriangleFacet> facetList = new ArrayList<TriangleFacet>();

	/**
	 * Liste aller Halbkanten im Mesh
	 */
	private List<HalfEdge> edgeList = new ArrayList<HalfEdge>();

	/**
	 * Map aller ausgehenden Kanten an den Punkten
	 */
	private Map<Vertex, Set<HalfEdge>> edgesOnPoint = new HashMap<Vertex, Set<HalfEdge>>();

	private String textureName;

	@Override
	public void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3) {
		TriangleFacet facet = new TriangleFacet();
		HalfEdge halfEdge1 = new HalfEdge();
		HalfEdge halfEdge2 = new HalfEdge();
		HalfEdge halfEdge3 = new HalfEdge();

		halfEdge1.setStartVertex(getVertex(vertexIndex1));
		halfEdge2.setStartVertex(getVertex(vertexIndex2));
		halfEdge3.setStartVertex(getVertex(vertexIndex3));

		halfEdge1.setNext(halfEdge2);
		halfEdge2.setNext(halfEdge3);
		halfEdge3.setNext(halfEdge1);

		halfEdge1.setOpposite(findOpposite(halfEdge1));
		halfEdge2.setOpposite(findOpposite(halfEdge2));
		halfEdge3.setOpposite(findOpposite(halfEdge3));

		getVertex(vertexIndex1).setHalfEgde(halfEdge1);
		getVertex(vertexIndex2).setHalfEgde(halfEdge2);
		getVertex(vertexIndex3).setHalfEgde(halfEdge3);

		halfEdge1.setFacet(facet);
		halfEdge2.setFacet(facet);
		halfEdge3.setFacet(facet);

		facet.setHalfEdge(halfEdge1);

		// speichern aller objekte
		edgeList.add(halfEdge1);
		edgeList.add(halfEdge2);
		edgeList.add(halfEdge3);
		facetList.add(facet);

		// map zum halbkanten finden füllen
		saveEdgesOnPoint(getVertex(vertexIndex1), halfEdge1);
		saveEdgesOnPoint(getVertex(vertexIndex2), halfEdge2);
		saveEdgesOnPoint(getVertex(vertexIndex3), halfEdge3);

	}

	private void createTriangles(List<Vector3> points, List<Double> values) {
		double tau = 0.5;
		int caseIndex = 0;
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i) > tau) {
				caseIndex += Math.pow(2, i);
			}
		}
	}

	private List<Integer> getCaseEdgeList(int caseIndex) {
		List<Integer> result = new ArrayList<>();
		for (int i = (caseIndex * 15); i < (caseIndex + 1) * (15 - 1); i++) {
			if(CaseDictionary.faces[i]!= -1){
				result.add(CaseDictionary.faces[i]);
			}
		}
		return result;
	}

	/**
	 * Dies Methode findet eine Kante von Ziel zu Start der gegebenen Kante
	 * falls vorhanden
	 * 
	 * @param halfEdge
	 *            die Kante deren gegenueber gesucht wird
	 * @return eine Kante die von Ziel zu Start der gegebenen Kante zeigt oder
	 *         null
	 */
	private HalfEdge findOpposite(HalfEdge halfEdge) {
		Vertex start = halfEdge.getStartVertex();
		Vertex ziel = halfEdge.getNext().getStartVertex();
		// an Ziel Vertex liegen Kanten an
		if (edgesOnPoint.containsKey(ziel)) {
			// prüfen ob eine der Halbkanten zu Start zeigt
			for (HalfEdge edge : edgesOnPoint.get(ziel)) {
				if (edge.getNext().getStartVertex().equals(start)) {
					edge.setOpposite(halfEdge);
					return edge;
				}
			}
		}
		return null;
	}

	/**
	 * Diese Methode speichert ausgehende Kanten eines Vertex
	 * 
	 * @param v
	 *            der Vertex von dem die Kante ausgeht
	 * @param h
	 *            die ausgehende Kante
	 */
	private void saveEdgesOnPoint(Vertex v, HalfEdge h) {
		// wenn noch keine Kante am Vertex existiert muss der Eintrag erzeugt
		// werden
		if (!edgesOnPoint.containsKey(v)) {
			Set<HalfEdge> newSet = new HashSet<HalfEdge>();
			newSet.add(h);
			edgesOnPoint.put(v, newSet);
		}
		// wenn ein eintrag existiert muss das Set erweitert werden
		else {
			edgesOnPoint.get(v).add(h);
		}
	}

	/**
	 * Berechne Vertexnormalen anhand der umliegenden Faceten
	 */
	private void computeVertexNormals() {
		for (Vertex vertex : vertexList) {
			Set<HalfEdge> halfEdgeSet = edgesOnPoint.get(vertex);
			Vector3 vertexNormal = new Vector3(0, 0, 0);
			for (HalfEdge halfEdge : halfEdgeSet) {
				vertexNormal = vertexNormal.add(halfEdge.getFacet().getNormal());
			}
			vertexNormal.normalize();
			vertex.setNormal(vertexNormal);
		}
	}

	/**
	 * Fuehrt eine Laplace Glaettung durch
	 * 
	 * @param a
	 *            Laplace Faktor
	 */
	public void doLaplaceSmoothing(double a) {
		Map<Vertex, Vector3> vertexFocus = new HashMap<Vertex, Vector3>();
		// Speichere Gewichtungen pro Vertex ab
		for (Vertex vertex : vertexList) {
			Vector3 neighbourFocus = new Vector3();
			for (HalfEdge halfEdge : edgesOnPoint.get(vertex)) {
				neighbourFocus = neighbourFocus.add(halfEdge.getOpposite().getStartVertex().getPosition());
			}
			// 1/N(Vertices-Neighbours)
			neighbourFocus = neighbourFocus.multiply(1.0 / edgesOnPoint.get(vertex).size());
			vertexFocus.put(vertex, neighbourFocus);
		}
		// Bewege Vertex
		for (Vertex vertex : vertexList) {
			// p=a*p+(1-a)*c

			Vector3 position = new Vector3(vertex.getPosition()).multiply(a);
			Vector3 newPosition = vertexFocus.get(vertex).multiply(1 - a);
			position = position.add(newPosition);
			vertex.setPosition(position);
		}
		computeTriangleNormals();
	}

	/**
	 * berechnet Kr�mmung und setzt Farbe an Vertex
	 */
	public void doComputeCurvature() {
		Map<Vertex, Double> curvatureMap = new HashMap<Vertex, Double>();
		double minCurvature = Double.MAX_VALUE, maxCurvature = 0;
		for (Vertex vertex : vertexList) {
			double avAng = 0;
			double areaSum = 0;
			for (HalfEdge halfEdge : edgesOnPoint.get(vertex)) {
				areaSum += halfEdge.getFacet().getArea();
				double normals = vertex.getNormal().multiply(halfEdge.getFacet().getNormal());
				double length = vertex.getNormal().getNorm() * halfEdge.getFacet().getNormal().getNorm();
				avAng += Math.acos(normals / length);
			}
			avAng = avAng / edgesOnPoint.get(vertex).size();
			double curvature = avAng / areaSum;
			if (curvature > maxCurvature) {
				maxCurvature = curvature;
			}
			if (minCurvature >= curvature) {
				minCurvature = curvature;
			}
			curvatureMap.put(vertex, curvature);
		}
		Color min = Color.MEDIUMPURPLE.darker();// milka
		Color max = Color.CHARTREUSE;
		Vector3 minColor = new Vector3(min.getRed(), min.getGreen(), min.getBlue());
		Vector3 maxColor = new Vector3(max.getRed(), max.getGreen(), max.getBlue());

		for (Vertex v : vertexList) {
			double m = (curvatureMap.get(v) - minCurvature) / (maxCurvature - minCurvature);
			Vector3 color = new Vector3();
			color = color.add(maxColor.multiply(m * 100));
			color = color.add(minColor).multiply(100 - m * 100);
			color.normalize();
			v.setColor(color);
		}
	}

	@Override
	public int addVertex(Vertex v) {
		vertexList.add(v);
		return vertexList.indexOf(v);
	}

	@Override
	public int getNumberOfTriangles() {
		return facetList.size();
	}

	@Override
	public int getNumberOfVertices() {
		return vertexList.size();
	}

	@Override
	public Vertex getVertex(int index) {
		return vertexList.get(index);
	}

	@Override
	public TriangleFacet getFacet(int facetIndex) {
		return facetList.get(facetIndex);
	}

	@Override
	public void clear() {
		vertexList.clear();
		facetList.clear();
		edgeList.clear();
	}

	@Override
	public void computeTriangleNormals() {
		for (TriangleFacet triangle : facetList) {
			Vertex vertex1 = triangle.getHalfEdge().getStartVertex();
			Vertex vertex2 = triangle.getHalfEdge().getNext().getStartVertex();
			Vertex vertex3 = triangle.getHalfEdge().getNext().getNext().getStartVertex();
			Vector3 v12 = new Vector3(vertex2.getPosition().subtract(vertex1.getPosition()));
			Vector3 v13 = new Vector3(vertex3.getPosition().subtract(vertex1.getPosition()));
			Vector3 normal = new Vector3(v12.cross(v13).getNormalized());
			triangle.setNormal(normal);
		}
		computeVertexNormals();
		doComputeCurvature();
	}

	@Override
	public void setTextureFilename(String filename) {
		textureName = filename;
	}

	@Override
	public String getTextureFilename() {
		return textureName;
	}

}
