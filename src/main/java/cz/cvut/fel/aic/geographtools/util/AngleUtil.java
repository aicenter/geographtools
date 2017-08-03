/* This code is owned by Umotional s.r.o. (IN: 03974618). All Rights Reserved. */
package cz.cvut.fel.aic.geographtools.util;

import cz.cvut.fel.aic.geographtools.GPSLocation;

/**
 * Class for computing angles
 */
public final class AngleUtil {

	/**
	 * Computes angle between lines (from, center) and (to, center)
	 *
	 * @param from
	 * 		<code>GPSLocation</code>
	 * @param center
	 * 		<code>GPSLocation</code>
	 * @param to
	 * 		<code>GPSLocation</code>
	 *
	 * @return angle in degrees
	 */
	public static double getAngle(GPSLocation from, GPSLocation center, GPSLocation to) {

		double theta1 = computeAngle(from, center);
		double theta2 = computeAngle(to, center);

		double delta = normalizeAngle(theta2 - theta1);

		return Math.toDegrees(delta);
	}

	private static double computeAngle(GPSLocation l1, GPSLocation l2) {
		double angleFromXAxis = Math.atan2(l2.getLatitudeProjected1E6() - l1.getLatitudeProjected1E6(), 
                l2.getLongitudeProjected1E6() - l1.getLongitudeProjected1E6());
		return angleFromXAxis;
	}

	private static double normalizeAngle(double angle) {
		return angle < 0 ? angle + 2 * Math.PI : angle;
	}
}
