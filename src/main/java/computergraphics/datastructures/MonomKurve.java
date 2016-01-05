package computergraphics.datastructures;

import java.util.ArrayList;
import java.util.List;

import computergraphics.math.Vector3;

public class MonomKurve extends AbstractKurve {

	private List<Vector3> punkte = new ArrayList<>();
	
	@Override
	public Vector3 computePoint(double t) {
		Vector3 result = new Vector3();
		for (int i = 0; i < getGrad(); i++) {
			result = result.add(computeBasis(i, t));
		}
		return result;
	}

	@Override
	public Vector3 computeBasis(int kontrollIndex, double t) {
		return getKontrollpunktList().get(kontrollIndex).multiply(Math.pow(t, kontrollIndex));
	}
	
	@Override
	public Vector3 computePoint_ (double t){
		Vector3 result = new Vector3();
		for (int i = 1; i < getGrad(); i++) {
			Vector3 tmp = getKontrollpunktList().get(i).multiply(Math.pow(t, i-1));
			result = result.add(tmp.multiply(i));
		}
		return result;
	}
	
	public void addPunkt(Vector3 v){
		punkte.add(v);
	}
	
	public void interpolate(){
		if(punkte.size() == 2){
			addKontrollpunkt(punkte.get(0));
			addKontrollpunkt(punkte.get(1));
		}else if (punkte.size() == 3){
			Vector3 c0 = punkte.get(0);
			//Vector3 p1 = c0.add(punkte.get(1).multiply(.5)).add(punkte.get(2).multiply(.25));
			//Vector3 p2 = c0.add(punkte.get(1)).add(punkte.get(2));
			
			
			Vector3 c1 = punkte.get(1).multiply(4).subtract(c0.multiply(3)).subtract(punkte.get(2));
			Vector3 c2 = punkte.get(2).subtract(c0).subtract(c1);
			
			addKontrollpunkt(c0);
			addKontrollpunkt(c1);
			addKontrollpunkt(c2);
		}
	}
	
	public List<Vector3> getPunkte(){
		return punkte;
	}
}
