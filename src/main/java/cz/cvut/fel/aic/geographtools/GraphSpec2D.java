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
package cz.cvut.fel.aic.geographtools;

/**
 *
 * @author fido
 */
public class GraphSpec2D {
	public final double minLat;
	
	public final double minLon;
	
	public final double maxLat;
	
	public final double maxLon;
	
	public final GPSLocation centroid;

	public GraphSpec2D(double minLat, double minLon, double maxLat, double maxLon, GPSLocation centroid) {
		this.minLat = minLat;
		this.minLon = minLon;
		this.maxLat = maxLat;
		this.maxLon = maxLon;
		this.centroid = centroid;
	}
	
	public int getWidth(){
		return (int) Math.round(maxLon - minLon);
	}
	
	public int getHeight(){
		return (int) Math.round(maxLat - minLat);
	}
	
}
