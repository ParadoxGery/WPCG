package computergraphics.datastructures;

import computergraphics.math.MathHelpers;
import computergraphics.math.Vector3;

public class BezierKurve extends Kurve {

	@Override
	public Vector3 computePoint(double t) {
		Vector3 result = new Vector3();
		for(int i = 0; i < getKontrollpunktList().size(); i++){
			result = result.add(computeBasis(i, t));
		}
		return result;
	}

	@Override
	public Vector3 computeBasis(int kontrollIndex, double t) {
		Vector3 result = getKontrollpunktList().get(kontrollIndex);
		double ni = MathHelpers.over(getGrad(), kontrollIndex);
		double ti = Math.pow(t, kontrollIndex);
		double tni = Math.pow((1 - t), (getGrad() - kontrollIndex));
		
		//System.err.println(ni*ti*tni);
		result = result.multiply(ni * ti * tni);
		return result;
	}

	@Override
	public Vector3 computePoint_(double t) {
		Vector3 result = new Vector3();
		for(int i = 0; i < getGrad(); i++){
			Vector3 nminus1 = getKontrollpunktList().get(i+1).subtract(getKontrollpunktList().get(i));
			double ni = MathHelpers.over(getGrad()-1, i);
			double ti = Math.pow(t, i);
			double tni = Math.pow((1 - t), (getGrad()-1 - i));
			nminus1 = nminus1.multiply(ni * ti * tni);
			result = result.add(nminus1);
		}
		return result;
	}
}
