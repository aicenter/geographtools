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

public class Edge implements Serializable, Cloneable {

	private static final long serialVersionUID = -4751369758703721049L;

	/**
	 * id of the tail node
	 */
	public final int fromId;

	/**
	 * id of the goal node
	 */
	public final int toId;

	/**
	 * length of the edge in meters
	 */
	public final int length;

    public int getLength() {
        return length;
    }
    
    

	public Edge(int fromId, int toId, int length) {
		this.fromId = fromId;
		this.toId = toId;
		this.length = length;
	}

	@Override
	protected Edge clone() {
		return new Edge(this.fromId, this.toId, this.length);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Edge))
			return false;

		Edge edge = (Edge) o;

		if (fromId != edge.fromId)
			return false;
		if (toId != edge.toId)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = fromId;
		result = 31 * result + toId;
		return result;
	}

	@Override
	public String toString() {
		return "(" + fromId + "," + toId + ')';
	}
}
