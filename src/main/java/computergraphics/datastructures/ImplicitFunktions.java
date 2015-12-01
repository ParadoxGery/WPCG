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
		
		@Override
		public double getIso() {
			return 0;
		}
	},
	TORUS() {
		@Override
		public double f(Vector3 position) {
			double outerR = 1;
			double innerR = .5;
			double outerRadSquare = outerR * outerR;
			double innerRadSquare = innerR * innerR;
			return (position.get(0) * position.get(0) + position.get(1) * position.get(1)
					+ position.get(2) * position.get(2) + outerRadSquare - innerRadSquare)
					* (position.get(0) * position.get(0) + position.get(1) * position.get(1)
							+ position.get(2) * position.get(2) + outerRadSquare - innerRadSquare)
					- 4 * outerRadSquare * (position.get(0) * position.get(0) + position.get(1) * position.get(1));
		}
		
		@Override
		public double getIso() {
			return 0;
		}
	};
	
	public abstract double f(Vector3 position);
	
	public abstract double getIso();
}
