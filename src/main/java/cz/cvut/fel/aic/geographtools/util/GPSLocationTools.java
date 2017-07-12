/* This code is owned by Umotional s.r.o. (IN: 03974618). All Rights Reserved. */
package cz.cvut.fel.aic.geographtools.util;

import com.vividsolutions.jts.geom.Coordinate;
import cz.agents.basestructures.GPSLocation;

/**
 * Tools for working with GPSLocation.
 */
public class GPSLocationTools {

	private GPSLocationTools() {
	}

	/**
	 * Converts real lat lon and elevation to GPS Location with projected coordinates computed according to specified
	 * SRID
	 */
	public static GPSLocation createGPSLocation(double lat, double lon, double elevation, int srid) {
		return createGPSLocation(lat, lon, elevation, new Transformer(srid));
	}

	/**
	 * Converts real lat lon and elevation to GPS Location with projected coordinates computed with given Transformer
	 */
	public static GPSLocation createGPSLocation(double lat, double lon, double elevation, Transformer transformer) {

		Coordinate coordinate = new Coordinate(lon, lat);
		Coordinate projectedCoordinate = transformer.toProjected(coordinate);

		return new GPSLocation(lat, lon, (int) projectedCoordinate.y, (int) projectedCoordinate.x, (int) elevation);
	}

	/**
	 * Computes distance between two GPSLocation using their projected coordinates
	 */
	public static double computeDistanceAsDouble(GPSLocation from, GPSLocation to) {
		return DistanceUtil.computeEuclideanDistance(from.lonProjected, from.latProjected, to.lonProjected, to
				.latProjected);
	}

	/**
	 * Computes distance between two GPSLocation using their projected coordinates, rounded to meters
	 */
	public static int computeDistance(GPSLocation from, GPSLocation to) {
		return (int) computeDistanceAsDouble(from, to);
	}

}
