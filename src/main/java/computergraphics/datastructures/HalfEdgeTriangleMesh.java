package computergraphics.datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gery
 *
 */
public class HalfEdgeTriangleMesh implements ITriangleMesh {
	
	private List<Vertex> vertexList = new ArrayList<Vertex>();
	
	private List<TriangleFacet> facetList = new ArrayList<TriangleFacet>();
	
	private List<HalfEdge> edgeList = new ArrayList<HalfEdge>();
	
	private String textureName;
	
	@Override
	public void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3) {
		TriangleFacet facet = new TriangleFacet();
		HalfEdge halfEdge1 = new HalfEdge();
		HalfEdge halfEdge2 = new HalfEdge();
		HalfEdge halfEdge3 = new HalfEdge();
		
		halfEdge1.setStartVertex(getVertex(vertexIndex1));
		halfEdge2.setStartVertex(getVertex(vertexIndex2));
		halfEdge3.setStartVertex(getVertex(vertexIndex2));
		halfEdge1.setNext(halfEdge2);
		halfEdge2.setNext(halfEdge3);
		//TODO zeigt 3 auf 1 ?
		halfEdge3.setNext(halfEdge1);
		
		facet.setHalfEdge(halfEdge1);
		
		//hinzufügen aller objekte
		edgeList.add(halfEdge1);
		edgeList.add(halfEdge2);
		edgeList.add(halfEdge3);
		facetList.add(facet);
		
		//TODO facet erstellen halfEdge erstellen beide in liste einfügen
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
		// TODO Auto-generated method stub
		
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
