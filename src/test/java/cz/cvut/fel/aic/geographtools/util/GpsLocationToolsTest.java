/* This code is owned by Umotional s.r.o. (IN: 03974618). All Rights Reserved. */
package cz.cvut.fel.aic.geographtools.util;

import cz.cvut.fel.aic.geographtools.GPSLocation;
import org.junit.Assert;
import org.junit.Test;

public class GpsLocationToolsTest {

	@Test
	public void testMethodGetGpsLocation() {

		//test for prague
		Transformer transformer = new Transformer(2065);
		double longitude = 14.424638;
		double latitude = 50.077319;

		// lon=x= 742723,6
		// lat=y= 1044163.9

		int elevation = 145;

		GPSLocation pragueGPSLoc = GPSLocationTools.createGPSLocation(latitude, longitude, elevation, transformer);

		Assert.assertEquals(latitude, pragueGPSLoc.getLatitude(), 0.0001);
		Assert.assertEquals(longitude, pragueGPSLoc.getLongitude(), 0.0001);

		Assert.assertEquals(742723.6, pragueGPSLoc.lonProjected, 1);
		Assert.assertEquals(1044163.9, pragueGPSLoc.latProjected, 1);

		Assert.assertEquals(elevation, pragueGPSLoc.elevation);

	}

	@Test
	public void testMethodComputeDistanceAsDouble() {
		Transformer transformer = new Transformer(2065);
		GPSLocation petrinyMetro = GPSLocationTools.createGPSLocation(50.0882367, 14.3448108, 0, transformer);
		GPSLocation karlakCvut = GPSLocationTools.createGPSLocation(50.0762436, 14.4189147, 0, transformer);

		double distanceInMapsCZ = 5452;
		double delta = 0.01;
		Assert.assertEquals(distanceInMapsCZ, GPSLocationTools.computeDistance(petrinyMetro, karlakCvut),
				distanceInMapsCZ * delta);
	}

}
