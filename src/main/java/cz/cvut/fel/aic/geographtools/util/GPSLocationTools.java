/* This code is owned by Umotional s.r.o. (IN: 03974618). All Rights Reserved. */
package cz.cvut.fel.aic.geographtools.util;

//import com.vividsolutions.jts.geom.Coordinate;
import org.locationtech.jts.geom.Coordinate;
import cz.cvut.fel.aic.geographtools.GPSLocation;

/**
 * Tools for working with GPSLocation.
 */
public class GPSLocationTools {

	/**
	 * Converts real lat lon and elevation to GPS Location with projected coordinates computed according to specified
	 * SRID
	 *
	 * @param lat	   latitude in WGS84
	 * @param lon	   longitude in WGS84
	 * @param elevation elevation
	 * @param srid	  srid of the desired projection
	 * @return location with projected coordinates
	 */
	public static GPSLocation createGPSLocation(double lat, double lon, double elevation, int srid) {
		return createGPSLocation(lat, lon, elevation, new Transformer(srid));
	}

	/**
	 * Converts real lat lon and elevation to GPS Location with projected coordinates computed with given Transformer.
	 *
	 * @param lat		 latitude in WGS84
	 * @param lon		 longitude in WGS84
	 * @param elevation   elevation
	 * @param transformer Transform for projected coordinates computation
	 * @return location with projected coordinates
	 */
	public static GPSLocation createGPSLocation(double lat, double lon, double elevation, Transformer transformer) {
		Coordinate coordinate = new Coordinate(lon, lat);
		Coordinate projectedCoordinate = transformer.toProjected(coordinate);

		/**
		 * 1E2 raises the resolution of projected coordinates to cm (0.01 m). So the difference between coordinates
		 * [0,0] and [0,100] is 1 m. The is true if the projected system is metric.
		 **/
		int projectedLatitude = (int) Math.round(projectedCoordinate.y * 1E2);
		int projectedLongitude = (int) Math.round(projectedCoordinate.x * 1E2);

		return new GPSLocation(lat, lon, projectedLatitude, projectedLongitude, (int) Math.round(elevation));
	}

	/**
	 * Converts latProjected and lonProjected coordinates and elevation to GPS Location according to specified
	 * Transformer
	 *
	 * @param projectedLatitude  latitude in WGS84
	 * @param projectedLongitude longitude in WGS84
	 * @param elevation		  elevation
	 * @param srid			   srid of the desired projection
	 * @return location with WGS84 and projected coordinates
	 */
	public static GPSLocation createGPSLocationFromProjected(int projectedLatitude, int projectedLongitude, int elevation, int srid) {
		return createGPSLocationFromProjected(projectedLatitude, projectedLongitude, elevation, new Transformer(srid));
	}

	/**
	 * Converts latProjected and lonProjected coordinates and elevation to GPS Location according to specified
	 * Transformer
	 *
	 * @param projectedLatitude  latitude in WGS84
	 * @param projectedLongitude longitude in WGS84
	 * @param elevation		  elevation
	 * @param transformer		Transform for projected coordinates computation
	 * @return location with WGS84 and projected coordinates
	 */
	public static GPSLocation createGPSLocationFromProjected(int projectedLatitude, int projectedLongitude, int elevation, Transformer transformer) {
		Coordinate projectedCoordinate = new Coordinate(projectedLongitude / 1E2, projectedLatitude / 1E2);
		Coordinate coordinate = transformer.toReal(projectedCoordinate);
		double lat = coordinate.y;
		double lon = coordinate.x;

		return new GPSLocation(lat, lon, projectedLatitude, projectedLongitude, (int) Math.round(elevation));
	}

	/**
	 * Computes distance between two GPSLocation using their projected coordinates
	 *
	 * @param from location
	 * @param to   location
	 * @return Euclidean distance between from and to.
	 */
	public static double computeDistanceAsDouble(GPSLocation from, GPSLocation to) {
		return DistanceUtil.computeEuclideanDistance(from.getLongitudeProjected(), from.getLatitudeProjected(),
				to.getLongitudeProjected(), to.getLatitudeProjected());
	}

	/**
	 * Computes distance between two GPSLocation using their projected coordinates, rounded to meters
	 *
	 * @param from location
	 * @param to   location
	 * @return Euclidean distance between from and to, rounded to nearest integer.
	 */
	public static int computeDistance(GPSLocation from, GPSLocation to) {
		return (int) Math.round(computeDistanceAsDouble(from, to));
	}
}
