package computergraphics.scenegraph;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import computergraphics.datastructures.Kurve;
import computergraphics.math.Vector3;

public class KurveNode extends Node {

	private Kurve k;
	private double stepSize = 0.1;
	private double tangentenT = -1;
	private int listId = 0;
	
	private boolean updateGlList = false;
	
	public KurveNode(Kurve k){
		this.k = k;
	}
	
	public KurveNode(Kurve k,double stepSize){
		this(k);
		this.stepSize = stepSize;
	}
	
	public KurveNode(Kurve k,double stepSize,double tangente){
		this(k,stepSize);
		this.tangentenT = tangente;
	}
	
	public void setTangentenT(double t){
		this.tangentenT = t;
	}
	
	public void updateGlList(){
		this.updateGlList = true;
	}
	
	@Override
	public void drawGl(GL2 gl) {
		if(listId == 0 || updateGlList){
			init(gl);
		}else {
			gl.glCallList(listId);
		}
	}

	private void init(GL2 gl){
		updateGlList = false;
		listId = gl.glGenLists(1);
		gl.glNewList(listId, GL2.GL_COMPILE);
		gl.glBegin(GL.GL_LINES);
		for (double i = stepSize; i < 1+stepSize ; i += stepSize) {
			Vector3 start = k.computePoint(i-stepSize);
			gl.glVertex3d(start.get(0), start.get(1), start.get(2));
			Vector3 end = k.computePoint(i);
			gl.glVertex3d(end.get(0), end.get(1), end.get(2));
		}
		if(tangentenT>-1){
			k.computeTangentDirection(tangentenT);
			Vector3 start = k.getTangentStart();
			gl.glVertex3d(start.get(0), start.get(1), start.get(2));
			Vector3 end = start.add(k.getTangentDir());
			gl.glVertex3d(end.get(0), end.get(1), end.get(2));
		}
		gl.glEnd();
		gl.glEndList();
	}
}
