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

package cz.cvut.fel.aic;

import cz.cvut.fel.aic.geographtools.GPSLocation;
import org.junit.Assert;
import org.junit.Test;

public class GPSLocationTest {

	@Test
	public void testE6Conversion() {

		double lat = 50.12345678;
		double lon = 14.12345678;

		GPSLocation loc = new GPSLocation(lat, lon, 0, 0);

		System.out.println(loc.toString());

		Assert.assertEquals((int) (lat * 1E6), loc.latE6);
		Assert.assertEquals((int) (lon * 1E6), loc.lonE6);

		Assert.assertEquals(lat, loc.getLatitude(), 0.000001);
		Assert.assertEquals(lon, loc.getLongitude(), 0.000001);

	}
}
