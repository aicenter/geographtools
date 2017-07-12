/* This code is owned by Umotional s.r.o. (IN: 03974618). All Rights Reserved. */
package cz.cvut.fel.aic.geographtools.util;

import java.util.ArrayList;
import java.util.List;

public class InterpolationUtil {

	private InterpolationUtil() {
	}

	public static List<double[]> interpolate(double[] fromVector, double[] toVector, int numOfInterpolations) {
		assert fromVector.length == toVector.length : "Dimensions of the vectors must equal!";

		List<double[]> interpolatedVectors = new ArrayList<>();
		double[] distance = new double[fromVector.length];

		for (int i = 0; i < fromVector.length; i++) {
			distance[i] = toVector[i] - fromVector[i];
		}

		for (int i = 0; i < numOfInterpolations; i++) {

			double[] interpolatedVector = new double[fromVector.length];
			double distanceRatio = (((double) (i + 1)) / ((double) (numOfInterpolations + 1)));

			for (int j = 0; j < interpolatedVector.length; j++) {
				double increment = distanceRatio * distance[j];
				interpolatedVector[j] = fromVector[j] + increment;
			}

			interpolatedVectors.add(interpolatedVector);
		}

		return interpolatedVectors;
	}
}
