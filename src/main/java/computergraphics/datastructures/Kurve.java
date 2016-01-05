package computergraphics.datastructures;

import computergraphics.math.Vector3;

public interface Kurve {
	public Vector3 computePoint(double t);
    public Vector3 computeBasis(int kontrollIndex,double t);
    public void computeTangentDirection(double t);
    public Vector3 getTangentStart();
    public Vector3 getTangentDir();
}
