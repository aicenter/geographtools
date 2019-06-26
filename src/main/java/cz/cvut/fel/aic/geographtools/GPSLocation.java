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

//import com.vividsolutions.jts.geom.Coordinate;
import org.locationtech.jts.geom.Coordinate;
import cz.cvut.fel.aic.geographtools.util.Transformer;
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
	 * Projected latitude computed using srid stored in zone. Location is saved as a
	 * fix point real number with two digits after the radix point ie scaled by
	 * 10^2.
	 */
	protected final int latProjected;

	/**
	 * Projected longitude computed using srid stored in zone. Location is saved as
	 * a fix point real number with two digits after the radix point ie scaled by
	 * 10^2.
	 */
	protected final int lonProjected;

	/**
	 * elevation above sea level
	 */
	public final int elevation;
	
	
	
	
	/**
	 * Integer getter for latitude.
	 * @return latitude in E6 format, e.g. 42345762
	 */
	public int getLatE6() {
		return latE6;
	}

	/**
	 * Integer getter for longitude.
	 * @return longitude in E6 format, e.g. 42345762
	 */
	public int getLonE6() {
		return lonE6;
	}
	
	/**
	 * Float getter for latitude.
	 * @return latitude as float number, e. g. 42.345762
	 */
	public double getLatitude() {
		return ((double) latE6 / 1E6);
	}

	/**
	 * Float getter for longitude.
	 * @return longitude as float number, e. g. 42.345762
	 */
	public double getLongitude() {
		return ((double) lonE6 / 1E6);
	}

	/**
	 * @return elevation above sea level.
	 */
	public int getElevation() {
		return elevation;
	}

	/**
	 * Returns the projected latitude as integer representing a fixed point real
	 * number with 2 decimal places.
	 * 
	 * @return projected latitude as integer representing a fixed point real number
	 *		 with 2 decimal places
	 */
	public int getLatitudeProjected1E2() {
		return latProjected;
	}

	/**
	 * Returns the projected latitude as double.
	 * 
	 * @return projected latitude
	 */
	public double getLatitudeProjected() {
		return (double) latProjected / 1E2;
	}

	/**
	 * Returns the projected latitude as integer, (2) decimal places are truncated.
	 * 
	 * @return projected latitude as rounded integer
	 */
	public int getLatitudeProjectedRounded() {
		return (int) Math.round(latProjected / 1E2);
	}

	/**
	 * Returns the projected longitude as integer representing a fixed point real
	 * number with 2 decimal places.
	 * 
	 * @return projected longitude as integer representing a fixed point real number
	 *		 with 2 decimal places
	 */
	public int getLongitudeProjected1E2() {
		return lonProjected;
	}

	/**
	 * Returns the projected longitude as double.
	 * 
	 * @return projected longitude
	 */
	public double getLongitudeProjected() {
		return (double) lonProjected / 1E2;
	}

	/**
	 * Returns the projected longitude as integer, (2) decimal places are truncated.
	 * 
	 * @return projected longitude as rounded integer
	 */
	public int getLongitudeProjectedRounded() {
		return (int) Math.round(lonProjected / 1E2);
	}

	
	
	
	/**
	 * Constructor
	 * 
	 * @param latE6
	 *			latitude in WGS84 as integer representing fixed point real number
	 *			with 6 decimal places
	 * @param lonE6
	 *			longitude in WGS84 as integer representing fixed point real number
	 *			with 6 decimal places
	 * @param latProjected
	 *			projected latitude as integer representing fixed point real number
	 *			with 2 decimal places
	 * @param lonProjected
	 *			projected longitude as integer representing fixed point real
	 *			number with 2 decimal places
	 * @param elevation
	 *			elevation
	 */
	public GPSLocation(int latE6, int lonE6, int latProjected, int lonProjected, int elevation) {
		this.latE6 = latE6;
		this.lonE6 = lonE6;
		this.latProjected = latProjected;
		this.lonProjected = lonProjected;
		this.elevation = elevation;
	}
		
	/**
	 * Constructor with zero elevation.
	 * 
	 * @param latE6
	 *			latitude in WGS84 as integer representing fixed point real number
	 *			with 6 decimal places
	 * @param lonE6
	 *			longitude in WGS84 as integer representing fixed point real number
	 *			with 6 decimal places
	 * @param latProjected
	 *			projected latitude as integer representing fixed point real number
	 *			with 2 decimal places
	 * @param lonProjected
	 *			projected longitude as integer representing fixed point real
	 *			number with 2 decimal places
	 */
	public GPSLocation(int latE6, int lonE6, int latProjected, int lonProjected) {
		this(latE6, lonE6, latProjected, lonProjected, 0);
	}
		
	/**
	 * Constructor with latitude and longitude as double.
	 * 
	 * @param lat
	 *			latitude in WGS84
	 * @param lon
	 *			longitude in WGS84
	 * @param latProjected
	 *			projected latitude as integer representing fixed point real number
	 *			with 2 decimal places
	 * @param lonProjected
	 *			projected longitude as integer representing fixed point real
	 *			number with 2 decimal places
	 * @param elevation
	 *			elevation
	 */
	public GPSLocation(double lat, double lon, int latProjected, int lonProjected, int elevation) {
		this((int) (lat * 1E6), (int) (lon * 1E6), latProjected, lonProjected, elevation);
	}

	/**
	 * Constructor with latitude and longitude as double with zero elevation.
	 * 
	 * @param lat
	 *			latitude in WGS84
	 * @param lon
	 *			longitude in WGS84
	 * @param latProjected
	 *			projected latitude as integer representing fixed point real number
	 *			with 2 decimal places
	 * @param lonProjected
	 *			projected longitude as integer representing fixed point real
	 *			number with 2 decimal places
	 */
	public GPSLocation(double lat, double lon, int latProjected, int lonProjected) {
		this(lat, lon, latProjected, lonProjected, 0);
	}

	/**
	 * Constructor for creating GPSLocation using coordinates and projection. Projected coordinates are 
	 * computed from projection.
	 * @param latitude latitude in WGS84
	 * @param longitude longitude in WGS84
	 * @param elevation elevation
	 * @param transformer projection definition
	 */
	public GPSLocation(double latitude, double longitude, int elevation, Transformer transformer){
		latE6 = (int) Math.round(latitude * 1E6);
		lonE6 = (int) Math.round(longitude * 1E6);
		this.elevation = elevation;
		Coordinate coordinate = new Coordinate(longitude, latitude);
		Coordinate projectedCoordinate = transformer.toProjected(coordinate);

		/**
		 * 1E2 raises the resolution of projected coordinates to cm (0.01 m). So the difference between coordinates
		 * [0,0] and [0,100] is 1 m. The is true if the projected system is metric.
		 **/
		latProjected = (int) Math.round(projectedCoordinate.y * 1E2);
		lonProjected = (int) Math.round(projectedCoordinate.x * 1E2);
	}
	
	/**
	 * Constructor for creating GPSLocation using projected coordinates and projection. GPS coordinates are 
	 * computed from projection.
	 * @param projectedLatitude projected latitude as integer representing fixed point real number
	 *			with 2 decimal places
	 * @param projectedLongitude projected longitude as integer representing fixed point real
	 *			number with 2 decimal places
	 * @param elevation elevation
	 * @param transformer projection definition
	 */
	public GPSLocation(int projectedLatitude, int projectedLongitude, int elevation, Transformer transformer){
		latProjected = projectedLatitude;
		lonProjected = projectedLongitude;
		this.elevation = elevation;
		Coordinate projectedCoordinate = new Coordinate(projectedLongitude / 1E2, projectedLatitude / 1E2);
		Coordinate coordinate = transformer.toReal(projectedCoordinate);
		latE6 = (int) Math.round(coordinate.y * 1E6);
		lonE6 = (int) Math.round(coordinate.x * 1E6);
	}
	
		
	
	
	@Override
	protected GPSLocation clone() {
		return new GPSLocation(latE6, lonE6, latProjected, lonProjected, elevation);
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
