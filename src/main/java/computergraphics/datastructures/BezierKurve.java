package computergraphics.datastructures;

import computergraphics.math.MathHelpers;
import computergraphics.math.Vector3;

public class BezierKurve extends AbstractKurve {

	@Override
	public Vector3 computePoint(double t) {
		Vector3 result = new Vector3();
		for(int i = 0; i < getGrad(); i++){
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
		System.err.println(ni*ti*tni);
		return result.multiply(ni * ti * tni);
	}

	@Override
	public Vector3 computePoint_(double t) {
		// TODO Auto-generated method stub
		return null;
	}
}
