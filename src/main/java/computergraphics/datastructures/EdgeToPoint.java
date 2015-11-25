package computergraphics.datastructures;

import java.util.ArrayList;
import java.util.List;

public enum EdgeToPoint {
	E0 {
		@Override
		public List<Integer> getPointIndexes() {
			List<Integer> points = new ArrayList<>();
			points.add(0);
			points.add(1);
			return points;
		}
	},
	E1 {
		@Override
		public List<Integer> getPointIndexes() {
			List<Integer> points = new ArrayList<>();
			points.add(1);
			points.add(2);
			return points;
		}
	},
	E2 {
		@Override
		public List<Integer> getPointIndexes() {
			List<Integer> points = new ArrayList<>();
			points.add(2);
			points.add(3);
			return points;
		}
	},
	E3 {
		@Override
		public List<Integer> getPointIndexes() {
			List<Integer> points = new ArrayList<>();
			points.add(0);
			points.add(3);
			return points;
		}
	},
	E4 {
		@Override
		public List<Integer> getPointIndexes() {
			List<Integer> points = new ArrayList<>();
			points.add(4);
			points.add(5);
			return points;
		}
	},
	E5 {
		@Override
		public List<Integer> getPointIndexes() {
			List<Integer> points = new ArrayList<>();
			points.add(5);
			points.add(6);
			return points;
		}
	},
	E6 {
		@Override
		public List<Integer> getPointIndexes() {
			List<Integer> points = new ArrayList<>();
			points.add(6);
			points.add(7);
			return points;
		}
	},
	E7 {
		@Override
		public List<Integer> getPointIndexes() {
			List<Integer> points = new ArrayList<>();
			points.add(7);
			points.add(4);
			return points;
		}
	},
	E8 {
		@Override
		public List<Integer> getPointIndexes() {
			List<Integer> points = new ArrayList<>();
			points.add(4);
			points.add(0);
			return points;
		}
	},
	E9 {
		@Override
		public List<Integer> getPointIndexes() {
			List<Integer> points = new ArrayList<>();
			points.add(5);
			points.add(1);
			return points;
		}
	},
	E10 {
		@Override
		public List<Integer> getPointIndexes() {
			List<Integer> points = new ArrayList<>();
			points.add(7);
			points.add(3);
			return points;
		}
	},
	E11 {
		@Override
		public List<Integer> getPointIndexes() {
			List<Integer> points = new ArrayList<>();
			points.add(6);
			points.add(3);
			return points;
		}
	};
	
	abstract public List<Integer> getPointIndexes();
}
