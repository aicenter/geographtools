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

public class Zone<TNode extends Node, TEdge extends Edge> implements Serializable {

	private static final long serialVersionUID = -1912981104095943906L;
	/**
	 * name of the zone
	 */
	public final String name;

	/**
	 * srid used for computing projected coordinates
	 */
	public final int srid;

	/**
	 * graph
	 */
	public final GraphStructure<TNode, TEdge> graph;

	/**
	 * bounding box for given graph
	 */
	public final BoundingBox boundingBox;

	public Zone(String name, int srid, GraphStructure<TNode, TEdge> graph, BoundingBox boundingBox) {
		this.name = name;
		this.srid = srid;
		this.graph = graph;
		this.boundingBox = boundingBox;
	}

	public String getName() {
		return name;
	}

	public int getSrid() {
		return srid;
	}

	public GraphStructure<TNode, TEdge> getGraph() {
		return graph;
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
}
