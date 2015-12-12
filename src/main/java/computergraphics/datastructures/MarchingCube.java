package computergraphics.datastructures;

import java.util.ArrayList;
import java.util.List;

import computergraphics.math.Vector3;

public class MarchingCube {
	private List<Vector3> points;
	private List<Double> values;
	
	public MarchingCube(List<Vector3> p, ImplicitFunktions f) {
		points = new ArrayList<>(p);
		values = new ArrayList<>();
		for(Vector3 v : points){
			values.add(f.f(v));
		}
	}
	
	public List<Double> getValues() {
		return values;
	}
	
	public List<Vector3> getPoints() {
		return points;
	}
}
