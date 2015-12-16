package computergraphics.datastructures;

import java.util.ArrayList;
import java.util.List;

import computergraphics.math.Vector3;

/**
 * Generische Kurve
 * Created by Gery on 12.12.2015.
 */
public abstract class Kurve implements KurveI {
    private List<Vector3> kontrollpunktList;
    
    private Vector3 tangentStart = new Vector3();
    private Vector3 tangentDir = new Vector3();

    public int getGrad(){
        return kontrollpunktList.size()-1;
    }

    public Kurve(){
        kontrollpunktList = new ArrayList<>();
    }

    public List<Vector3> getKontrollpunktList() {
        return kontrollpunktList;
    }
    
    public void addKontrollpunkt(Vector3 k){
    	kontrollpunktList.add(k);
    }
    
    @Override
    public void computeTangentDirection(double t) {
    	tangentStart = computePoint(t);
    	tangentDir = computePoint_(t).getNormalized();
    }
    
    abstract public Vector3 computePoint_(double t);
    
    public Vector3 getTangentDir() {
		return tangentDir;
	}
    
    public Vector3 getTangentStart() {
		return tangentStart;
	}
}