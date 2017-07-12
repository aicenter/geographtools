/* This code is owned by Umotional s.r.o. (IN: 03974618). All Rights Reserved. */
package cz.cvut.fel.aic.geographtools.util;

import org.junit.Assert;
import org.junit.Test;

public class DistanceUtilTest {

	@Test
	public void testMethodComputeEuclideanDistance() {
		Assert.assertEquals(5, DistanceUtil.computeEuclideanDistance(0, 0, 4, 3), 0.001);
	}

	@Test
	public void testMethodComputeGreatCircleDistance() {

		double latPetrinyMetro = 50.0882367;
		double lonPetrinyMetro = 14.3448108;
		double latKarlakCvut = 50.0762436;
		double lonKarlakCvut = 14.4189147;

		double distanceInMapsCZ = 5452;
		double delta = 0.01;

		Assert.assertEquals(distanceInMapsCZ, DistanceUtil.computeGreatCircleDistance(latPetrinyMetro,
				lonPetrinyMetro, latKarlakCvut, lonKarlakCvut), distanceInMapsCZ * delta);

	}
}
