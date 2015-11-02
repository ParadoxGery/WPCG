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
	
	private List<HalfEdge> halfEdgeList = new ArrayList<HalfEdge>();
	
	private String textureName;
	
	@Override
	public void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3) {
		TriangleFacet facet = new TriangleFacet();
		HalfEdge halfEdge = new HalfEdge();
		halfEdge.setStartVertex(getVertex(vertexIndex1));
		
		facet.setHalfEdge(halfEdge);
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
		halfEdgeList.clear();
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
