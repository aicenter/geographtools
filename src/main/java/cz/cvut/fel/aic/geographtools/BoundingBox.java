/* This code is owned by Umotional s.r.o. (IN: 03974618). All Rights Reserved. */
package cz.cvut.fel.aic.geographtools;

import java.io.Serializable;

public class BoundingBox implements Serializable {

	private static final long serialVersionUID = 3232930949850936975L;

	/**
	 * minimal X value (x = gps lon)
	 */
	public final int minLonE6;

	/**
	 * minimal Y value (y = gps lat)
	 */
	public final int minLatE6;

	/**
	 * max X value (x = gps lon)
	 */
	public final int maxLonE6;

	/**
	 * max Y value (y = gps lat)
	 */
	public final int maxLatE6;

	public BoundingBox(int minLonE6, int minLatE6, int maxLonE6, int maxLatE6) {
		this.minLonE6 = minLonE6;
		this.minLatE6 = minLatE6;
		this.maxLonE6 = maxLonE6;
		this.maxLatE6 = maxLatE6;
	}

	/**
	 * returns wether point is in given bounding box, or not
	 *
	 * @param lonE6 lonE6 coordinate (gps lon)
	 * @param latE6 latE6 coordinate (gps lat)
	 * @return true if point is in given bounding box or on its edge, false otherwise
	 */
	public boolean inside(int lonE6, int latE6) {
		return (((lonE6 >= minLonE6) && (lonE6 <= maxLonE6)) && ((latE6 >= minLatE6) && (latE6 <= maxLatE6)));
	}

	public int getMinLonE6() {
		return minLonE6;
	}

	public int getMinLatE6() {
		return minLatE6;
	}

	public int getMaxLonE6() {
		return maxLonE6;
	}

	public int getMaxLatE6() {
		return maxLatE6;
	}
}
