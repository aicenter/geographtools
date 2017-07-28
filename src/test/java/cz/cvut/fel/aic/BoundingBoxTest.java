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

import cz.cvut.fel.aic.geographtools.BoundingBoxNew;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoundingBoxTest {

	private BoundingBoxNew boundingBox;

	@Test
	public void testMinLat() throws Exception {
		boundingBox = callIntegerConstructor();

		assertEquals(50062400, boundingBox.minLatE6);
		assertEquals(50.062400, boundingBox.getMinLat(), 0.);

		boundingBox = callDoubleConstructor();

		assertEquals(50062400, boundingBox.minLatE6);
		assertEquals(50.062400, boundingBox.getMinLat(), 0.);
	}

	@Test
	public void testMinLon() throws Exception {
		boundingBox = callIntegerConstructor();

		assertEquals(14349900, boundingBox.minLonE6);
		assertEquals(14.349900, boundingBox.getMinLon(), 0.);

		boundingBox = callDoubleConstructor();

		assertEquals(14349900, boundingBox.minLonE6);
		assertEquals(14.349900, boundingBox.getMinLon(), 0.);
	}

	@Test
	public void testMaxLat() throws Exception {
		boundingBox = callIntegerConstructor();

		assertEquals(50112800, boundingBox.maxLatE6);
		assertEquals(50.112800, boundingBox.getMaxLat(), 0.);

		boundingBox = callDoubleConstructor();

		assertEquals(50112800, boundingBox.maxLatE6);
		assertEquals(50.112800, boundingBox.getMaxLat(), 0.);
	}

	@Test
	public void testMaxLon() throws Exception {
		boundingBox = callIntegerConstructor();

		assertEquals(14484600, boundingBox.maxLonE6);
		assertEquals(14.484600, boundingBox.getMaxLon(), 0.);

		boundingBox = callDoubleConstructor();

		assertEquals(14484600, boundingBox.maxLonE6);
		assertEquals(14.484600, boundingBox.getMaxLon(), 0.);
	}

	@Test
	public void testInside() throws Exception {
		boundingBox = callIntegerConstructor();

		assertTrue(boundingBox.inside(50075538, 14437800));
		assertFalse(boundingBox.inside(49759977, 15222244));

		boundingBox = callDoubleConstructor();

		assertTrue(boundingBox.inside(50075538, 14437800));
		assertFalse(boundingBox.inside(49759977, 15222244));
	}

	@Test
	public void testInside1() throws Exception {
		boundingBox = callIntegerConstructor();

		assertTrue(boundingBox.inside(50.075538, 14.4378));
		assertFalse(boundingBox.inside(49.759977, 15.222244));

		boundingBox = callDoubleConstructor();

		assertTrue(boundingBox.inside(50.075538, 14.4378));
		assertFalse(boundingBox.inside(49.759977, 15.222244));
	}

	private BoundingBoxNew callIntegerConstructor() {
		return new BoundingBoxNew(50062400, 14349900, 50112800, 14484600);
	}

	private BoundingBoxNew callDoubleConstructor() {
		return new BoundingBoxNew(50.062400, 14.349900, 50.112800, 14.484600);
	}
}