/*
 * Copyright (C) 2017 Czech Technical University in Prague
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package cz.cvut.fel.aic.geographtools.util;

/**
 *
 * @author fido
 */
public class NearestElementUtilPair<Coordinate, TElement>{
		
	private final Coordinate value0;

	private final TElement value1;

	public NearestElementUtilPair(Coordinate value0, TElement value1) {
		this.value0 = value0;
		this.value1 = value1;
	}


	public Coordinate getValue0() {
		return value0;
	}

	public TElement getValue1() {
		return value1;
	}

}