package computergraphics.datastructures;

import computergraphics.math.Vector3;

public enum ImplicitFunktions {
	KUGEL() {
		@Override
		public double f(Vector3 position) {
			// TODO Auto-generated method stub
			return 0.5;
		}
	},
	TORUS() {
		@Override
		public double f(Vector3 position) {
			// TODO Auto-generated method stub
			return 0.5;
		}
	};
	
	public abstract double f(Vector3 position);
}
