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
import java.util.*;

/**
 * Structure for converting a sub-list of all edges in the graph to a single list. Does not uses own array,
 * instead marks the interval in the whole list of edges
 */
public class EdgeList<TEdge> extends AbstractList<TEdge> implements RandomAccess, Serializable {

	private static final long serialVersionUID = -4480102272064906541L;

	final int startPosition; // inclusive
	final int endPosition; // exclusive
	private final List<TEdge> edges;

	public EdgeList(int startPosition, int endPosition, List<TEdge> edges) {
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.edges = edges;
	}

	public TEdge get(int index) {
		return edges.get(startPosition + index);
	}

	@Override
	public int size() {
		return endPosition - startPosition;
	}

	public Iterator<TEdge> iterator() {
		return new Itr();
	}

	private class Itr implements Iterator<TEdge> {

		//Index of element to be returned by subsequent call to next.

		int cursor = 0;

		public boolean hasNext() {
			return cursor != size();
		}

		public TEdge next() {
			try {
				TEdge next = get(cursor);
				cursor++;
				return next;
			} catch (IndexOutOfBoundsException e) {
				throw new NoSuchElementException();
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public String toString() {
		return "EdgeList [size=" + this.size() + ", startPosition=" + startPosition + ", endPosition=" + endPosition
				+ "]";
	}
}
