package cz.cvut.fel.aic.geographtools;

/**
 * @author Marek Cuchý
 */
public class EdgeId {

	public final int from;
	public final int to;

	public EdgeId(int from, int to) {
		this.from = from;
		this.to = to;
	}

	public int getFrom() {
		return from;
	}

	public int getTo() {
		return to;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		EdgeId edgeId = (EdgeId) o;

		return from == edgeId.from && to == edgeId.to;
	}

	@Override
	public int hashCode() {
		int result = from;
		result = 31 * result + to;
		return result;
	}

	@Override
	public String toString() {
		return "[" + from +
				", " + to +
				']';
	}
}
