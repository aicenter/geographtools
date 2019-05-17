/* This code is owned by Umotional s.r.o. (IN: 03974618). All Rights Reserved. */
package cz.cvut.fel.aic.geographtools.util;

import cz.cvut.fel.aic.geographtools.GPSLocation;

/**
 * Implementation of <code>KDTreeResolver</code> for GPSLocation and its subclasses.
 */
public class GPSLocationKDTreeResolver<T extends GPSLocation> implements KDTreeResolver<T> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double computeDistance(T v1, double[] coords) {
		return DistanceUtil.computeEuclideanDistance(v1.getLatitudeProjected(), v1.getLongitudeProjected(), coords[1], 
				coords[0]);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double[] getCoordinates(T v1) {
		return getCoords(v1);
	}

	/**
	 * Static version of getCoordinates to be used without class initialization.
	 * @param v1 location
	 * @return coordinates of the location as array
	 */
	private static double[] getCoords(GPSLocation v1) {
		return new double[]{v1.getLongitudeProjected(), v1.getLatitudeProjected()};
	}
}
