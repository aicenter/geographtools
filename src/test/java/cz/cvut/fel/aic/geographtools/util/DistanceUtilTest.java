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
                // distances are from https://www.cqsrg.org/tools/GCDistance/
                double d1lat, d1lon ,d2lat ,d2lot ,distance;
                double delta = 0.005; // 0.5 percent
                
                d1lat = 50.0882367; d1lon = 14.3448108;
                d2lat = 50.0762436; d2lot = 14.4189147;
                
                distance = 5469.054;		

		Assert.assertEquals(distance, DistanceUtil.computeGreatCircleDistance(d1lat,
				d1lon, d2lat, d2lot), delta * distance);
                
                d1lat = 50.080128; d1lon = 14.395388;
                d2lat = 50.079301; d2lot = 14.373770;
                
                distance = 1550.086;		

		Assert.assertEquals(distance, DistanceUtil.computeGreatCircleDistance(d1lat,
				d1lon, d2lat, d2lot), delta * distance);
                
                d1lat = 50.075911; d1lon = 14.419055;
                d2lat = 50.075821; d2lot = 14.413369;
                
                distance = 407.143;		

		Assert.assertEquals(distance, DistanceUtil.computeGreatCircleDistance(d1lat,
				d1lon, d2lat, d2lot), delta * distance);

	}
}
