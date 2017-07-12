/* This code is owned by Umotional s.r.o. (IN: 03974618). All Rights Reserved. */
package cz.cvut.fel.aic.geographtools.util;

/**
 * Class for computing distances
 */
public class DistanceUtil {

	private DistanceUtil() {
	}

	/**
	 * Computes euclidean distance in plane
	 */
	public static double computeEuclideanDistance(double fromX, double fromY, double toX, double toY) {
		return Math.sqrt(((fromX - toX) * (fromX - toX)) + ((fromY - toY) * (fromY - toY)));
	}

	/**
	 * Computes distance between two gps locations
	 */
	public static double computeGreatCircleDistance(double fromLat, double fromLon, double toLat, double toLon) {

		//based on http://stackoverflow.com/questions/837872/calculate-distance-in-meters-when-you-know-longitude-and
		// -latitude-in-java

		double earthRadius = 6371000; //meters
		double dLat = Math.toRadians(toLat - fromLat);
		double dLng = Math.toRadians(toLon - fromLon);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(fromLat)) * Math.cos(Math
				.toRadians(toLat)) *
				Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		float dist = (float) (earthRadius * c);

		return dist;
	}

}
