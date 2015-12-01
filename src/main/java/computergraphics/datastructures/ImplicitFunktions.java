package computergraphics.datastructures;

import computergraphics.math.Vector3;

public enum ImplicitFunktions {
	KUGEL() {
		@Override
		public double f(Vector3 position) {
			double r = 1;
			return Math.pow(position.get(0), 2) + Math.pow(position.get(1), 2) + Math.pow(position.get(2), 2)
					- Math.pow(r, 2);
		}
	},
	TORUS() {
		@Override
		public double f(Vector3 position) {
			// TODO Auto-generated method stub
			return 1;
		}
	};

	public abstract double f(Vector3 position);
}
