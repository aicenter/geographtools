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

public class GPSLocation implements Serializable, Cloneable {

	private static final long serialVersionUID = 5990455754768534483L;

	/**
	 * GPS latitude in E6 format, e.g. 42.345762 * 1E6
	 */
	public final int latE6;

	/**
	 * GPS longitude in E6 format, e.g. 143.345762 * 1E6
	 */
	public final int lonE6;

	/**
	 * projected latitude computed using srid stored in zone
	 */
	public final int latProjected;

	/**
	 * projected longitude computed using srid stored in zone
	 */
	public final int lonProjected;

	/**
	 * elevation above sea level
	 */
	public final int elevation;

	public GPSLocation(int latE6, int lonE6, int latProjected, int lonProjected, int elevation) {
		this.latE6 = latE6;
		this.lonE6 = lonE6;
		this.latProjected = latProjected;
		this.lonProjected = lonProjected;
		this.elevation = elevation;
	}

	public GPSLocation(int latE6, int lonE6, int latProjected, int lonProjected) {
		this(latE6, lonE6, latProjected, lonProjected, 0);
	}

	public GPSLocation(double lat, double lon, int latProjected, int lonProjected, int elevation) {
		this((int) (lat * 1E6), (int) (lon * 1E6), latProjected, lonProjected, elevation);
	}

	public GPSLocation(double lat, double lon, int latProjected, int lonProjected) {
		this(lat, lon, latProjected, lonProjected, 0);
	}

	@Override
	protected GPSLocation clone() {
		return new GPSLocation(latE6, lonE6, latProjected, lonProjected, elevation);
	}

	public double getLatitude() {
		return ((double) latE6 / 1E6);
	}

	public double getLongitude() {
		return ((double) lonE6 / 1E6);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		GPSLocation that = (GPSLocation) o;

		if (latE6 != that.latE6)
			return false;
		if (lonE6 != that.lonE6)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = latE6;
		result = 31 * result + lonE6;
		return result;
	}

	@Override
	public String toString() {
		return "GPS(" + "lat=" + getLatitude() + ", lon=" + getLongitude() + ", elevation=" + elevation + ')';
	}
}
