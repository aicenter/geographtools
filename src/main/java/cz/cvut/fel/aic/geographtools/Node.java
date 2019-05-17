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

package cz.cvut.fel.aic.geographtools;

import java.io.Serializable;
import cz.cvut.fel.aic.geographtools.util.Transformer;

public class Node extends GPSLocation implements Serializable, Cloneable {

	private static final long serialVersionUID = 3983630937165589188L;

	/**
	 * id of the node, used for referencing, should be [0..(numOfNodes-1)]
	 */
	public final int id;
		
	/**
	 * id of the node from the data source, mostly corresponds with osm maps id
	 */
	public final long sourceId;
		
	
	
	
	/**
	 * Constructor using coordinates in WGS84 as integer representing fixed point
	 * real number with 6 decimal places.
	 * 
	 * @param id
	 *			Node internal id - generated.
	 * @param sourceId
	 *			Node id inferred from the source data
	 * @param latE6
	 *			latitude in WGS84 as integer representing fixed point real number
	 *			with 6 decimal places
	 * @param lonE6
	 *			longitude in WGS84 as integer representing fixed point real number
	 *			with 6 decimal places
	 * @param latProjected
	 *			projected latitude as integer representing fixed point real number
	 *			with 2 decimal places
	 * @param lonProjected
	 *			projected longitude as integer representing fixed point real
	 *			number with 2 decimal places
	 * @param elevation
	 *			elevation
	 */
	public Node(int id, long sourceId, int latE6, int lonE6, int latProjected, int lonProjected, int elevation) {
		super(latE6, lonE6, latProjected, lonProjected, elevation);
		this.id = id;
		this.sourceId = sourceId;
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *			Node internal id - generated.
	 * @param sourceId
	 *			Node id inferred from the source data
	 * @param lat
	 *			latitude in WGS84
	 * @param lon
	 *			longitude in WGS84
	 * @param latProjected
	 *			projected latitude as integer representing fixed point real number
	 *			with 2 decimal places
	 * @param lonProjected
	 *			projected longitude as integer representing fixed point real
	 *			number with 2 decimal places
	 * @param elevation
	 *			elevation
	 */
	public Node(int id, long sourceId, double lat, double lon, int latProjected, int lonProjected, int elevation) {
		super(lat, lon, latProjected, lonProjected, elevation);
		this.id = id;
		this.sourceId = sourceId;
	}
		
	/**
	 * Constructor from existing GPSLocation.
	 * 
	 * @param id
	 *			Node internal id - generated.
	 * @param sourceId
	 *			Node id inferred from the source data
	 * @param location
	 *			GPS location.
	 */
	public Node(int id, long sourceId, GPSLocation location) {
		super(location.latE6, location.lonE6, location.latProjected, location.lonProjected, location.elevation);
		this.id = id;
		this.sourceId = sourceId;
	}
	
	/**
	 * Constructor from coordinates and projection. Projected coordinates are computed from projection.
	 * @param id Node internal id - generated.
	 * @param sourceId Node id inferred from the source data
	 * @param latitude latitude in WGS84
	 * @param longitude longitude in WGS84
	 * @param elevation elevation
	 * @param transformer projection definition
	 */
	public Node(int id, long sourceId, double latitude, double longitude, int elevation, 
			Transformer transformer){
		super(latitude, longitude, elevation, transformer);
		this.id = id;
		this.sourceId = sourceId;
	}
	
	/**
	 * Constructor from projected coordinates and projection. GPS coordinates are computed from projection.
	 * @param id Node internal id - generated.
	 * @param sourceId Node id inferred from the source data
	 * @param projectedLatitude projected latitude as integer representing fixed point real number
	 *			with 2 decimal places
	 * @param projectedLongitude projected longitude as integer representing fixed point real
	 *			number with 2 decimal places
	 * @param elevation elevation
	 * @param transformer projection definition
	 */
	public Node(int id, long sourceId, int projectedLatitude, int projectedLongitude, int elevation, 
			Transformer transformer){
		super(projectedLatitude, projectedLongitude, elevation, transformer);
		this.id = id;
		this.sourceId = sourceId;
	}
			

	
	
	public int getId() {
		return id;
	}

	/**
	 * Get id of the node from the data source, mostly corresponds with osm maps id
	 * 
	 * @return
	 */
	public long getSourceId() {
		return sourceId;
	}
  
		public String toWKT() {
			return "POINT ( " + (lonE6 / 1E6 ) + " " + (latE6 / 1E6 ) + " )";
		}
		
	@Override
	protected Node clone() {
		return new Node(id, sourceId, latE6, lonE6, latProjected, lonProjected, elevation);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Node))
			return false;
		if (!super.equals(o))
			return false;

		Node node = (Node) o;

		if (id != node.id)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public String toString() {
		return "{N(#" + id + ") : sourceId=" + sourceId + ", " + "GPS( " + "lat=" + (double) (latE6) / 1E6 + ", lon="
				+ (double) (lonE6) / 1E6 + ", elevation=" + elevation + ')' + '}';
	}
}