package computergraphics.datastructures;

import computergraphics.math.MathHelpers;
import computergraphics.math.Vector3;

public enum ImplicitFunktions {
	KUGEL() {
		@Override
		public double f(Vector3 position) {
			double r = 1;
			return (Math.pow(position.get(0), 2) + Math.pow(position.get(1), 2) + Math.pow(position.get(2), 2)
					- Math.pow(r, 2))*-1;
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
			return ((position.get(0) * position.get(0) + position.get(1) * position.get(1)
					+ position.get(2) * position.get(2) + outerRadSquare - innerRadSquare)
					* (position.get(0) * position.get(0) + position.get(1) * position.get(1)
							+ position.get(2) * position.get(2) + outerRadSquare - innerRadSquare)
					- 4 * outerRadSquare * (position.get(0) * position.get(0) + position.get(1) * position.get(1)))*-1;
		}
		
		@Override
		public double getIso() {
			return 0;
		}
	},
	ELLIPTISCHER_KEGEL() {
		@Override
		public double f(Vector3 position) {
			double alpha = MathHelpers.degree2radiens(70);
			double beta = MathHelpers.degree2radiens(70);
			double alphaSquare = alpha*alpha;
			double betaquare = beta*beta;
			return ((position.get(0)*position.get(0)/alphaSquare)+(position.get(1)*position.get(1)/betaquare)-(position.get(2)*position.get(2)))*-1;
		}

		@Override
		public double getIso() {
			return 0;
		}
	},
	HYPERBOLISCHES_PARABOLOID() {
		@Override
		public double f(Vector3 position) {
			double alpha = MathHelpers.degree2radiens(220);
			double beta = MathHelpers.degree2radiens(70);
			double alphaSquare = alpha*alpha;
			double betaquare = beta*beta;
			return ((position.get(1)*position.get(1)/betaquare)-(position.get(0)*position.get(0)/alphaSquare)-position.get(2))*-1;
		}

		@Override
		public double getIso() {
			return 0;
		}
	},
	SUPERQUADER() {
		@Override
		public double f(Vector3 position) {
			Vector3 m = new Vector3(0,0,0);
			Vector3 a = new Vector3(.5, .5, .5);
			double e1 = .1;
			double e2 = .1;
			double t1 = Math.pow((position.get(0)-m.get(0))/a.get(0), 2/e2);
			double t2 = Math.pow((position.get(1)-m.get(1))/a.get(1), 2/e2);
			double t3 = Math.pow((position.get(2)-m.get(2))/a.get(2), 2/e1);
			return Math.pow(t1+t2, e2/e1)+t3;
		}

		@Override
		public double getIso() {
			return 0;
		}
	},
	BOYSCHE_FLAECHE() {
		@Override
		public double f(Vector3 position) {
			double x = position.get(0);
			double y = position.get(1);
			double z = position.get(2);
			double x2 = x*x;
			double y2 = y*y;
			double z2 = z*z;
			double z3 = z2*z;
			double z4 = z3*z;
			double emz2 = (1-z)*(1-z);
			double r2 = Math.sqrt(2);
			return (((64*((1-z)*emz2))*z3) -
					(48*emz2*z2*(3*x2+3*y2+2*z2)) +
					12*(1-z)*z *
					((27*((x2+y2)*(x2+y2)))-(24*z2*(x2+y2))+(36*r2*y*z*(y2-3*x2))+4*z4) +
					(9*x2+9*y2-2*z2)*(-81*((x2+y2)*(x2+y2))-72*z2*(x2+y2)+108*r2*x*z*(x2-3*y2)+4*z4));
		}

		@Override
		public double getIso() {
			return 0;
		}
	};
	
	public abstract double f(Vector3 position);
	
	public abstract double getIso();
}
