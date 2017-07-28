/*
 *  Copyright (C) 2016-2017 Umotional s.r.o. (IN: 03974618)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cz.cvut.fel.aic.geographtools;

import java.io.Serializable;

public class BoundingBoxNew implements Serializable {

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

	public BoundingBoxNew(int minLatE6, int minLonE6, int maxLatE6, int maxLonE6) {
		this.minLatE6 = minLatE6;
		this.minLonE6 = minLonE6;
		this.maxLatE6 = maxLatE6;
		this.maxLonE6 = maxLonE6;
	}

	public BoundingBoxNew(double minLat, double minLon, double maxLat, double maxLon) {
		this.minLatE6 = (int) (minLat * 1e6);
		this.minLonE6 = (int) (minLon * 1e6);
		this.maxLatE6 = (int) (maxLat * 1e6);
		this.maxLonE6 = (int) (maxLon * 1e6);
	}

	public double getMinLat() {
		return ((double) minLatE6) / 1e6;
	}

	public double getMinLon() {
		return ((double) minLonE6) / 1e6;
	}

	public double getMaxLat() {
		return ((double) maxLatE6) / 1e6;
	}

	public double getMaxLon() {
		return ((double) maxLonE6) / 1e6;
	}

	/**
	 * Returns whether a point is in given bounding box, or not
	 *
	 * @param lat lat coordinate (gps lat)
	 * @param lon lon coordinate (gps lon)
	 * @return true if point is in given bounding box or on its edge, false otherwise
	 */
	public boolean inside(double lat, double lon) {
		int latE6 = (int) (lat * 1e6);
		int lonE6 = (int) (lon * 1e6);

		return (((lonE6 >= minLonE6) && (lonE6 <= maxLonE6)) && ((latE6 >= minLatE6) && (latE6 <= maxLatE6)));
	}

	/**
	 * Returns whether a point is in given bounding box, or not
	 *
	 * @param latE6 latE6 coordinate (gps lat)
	 * @param lonE6 lonE6 coordinate (gps lon)
	 * @return true if point is in given bounding box or on its edge, false otherwise
	 */
	public boolean inside(int latE6, int lonE6) {
		return (((lonE6 >= minLonE6) && (lonE6 <= maxLonE6)) && ((latE6 >= minLatE6) && (latE6 <= maxLatE6)));
	}
}
