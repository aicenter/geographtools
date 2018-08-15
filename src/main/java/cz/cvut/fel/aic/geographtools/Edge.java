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

public class Edge<N extends Node> implements Serializable, Cloneable {

	private static final long serialVersionUID = -4751369758703721049L;

	/**
	 * tail node
	 */
	public final N fromNode;

	/**
	 * goal node
	 */
	public final N toNode;

	/**
	 * length of the edge in meters
	 */
	public final int length;

    public int getLength() {
        return length;
    }
    
    

	public Edge(N fromNode, N toNode, int length) {
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.length = length;
	}

	public Node getFromNode() {
		return fromNode;
	}

	public Node getToNode() {
		return toNode;
	}

   
        public String toWKT() {
            String wkt = "LINESTRING (" + fromNode.latE6+ " " + fromNode.lonE6 + ", " + toNode.latE6 + " " + toNode.lonE6 + " )";
            return wkt;
        }        
        
	@Override
	protected Edge clone() {
		return new Edge(this.fromNode, this.toNode, this.length);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Edge))
			return false;

		Edge edge = (Edge) o;

		if (!fromNode.equals(edge.fromNode))
			return false;
		if (!toNode.equals(edge.toNode))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = fromNode.getId();
		result = 31 * result + toNode.getId();
		return result;
	}

	@Override
	public String toString() {
		return "(" + fromNode.getId() + "," + toNode.getId() + ')';
	}
}
