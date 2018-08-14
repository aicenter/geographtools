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

import java.util.Collection;
import java.util.List;

public interface GraphStructure<TNode extends Node, TEdge extends Edge> {

	/**
	 * Checks whether the graph contains node.
	 *
	 * @param node <code>TNode</code>
	 * @return <code>true</code>, if graph contains node, otherwise <code>false</code>
	 */
	public boolean containsNode(TNode node);

	/**
	 * Checks whether the graph contains node by specified node id.
	 *
	 * @param nodeId <code>int</code>
	 * @return <code>true</code>, if graph contains node, otherwise <code>false</code>
	 */
	public boolean containsNode(int nodeId);

	/**
	 * Returns the node from graph specified by node id.
	 *
	 * @param nodeId <code>int</code>
	 * @return <code>TNode</code>, if graph contains node, specified by nodeId, null otherwise
	 */
	public TNode getNode(int nodeId);

	/**
	 * Returns true when the graph contains given edge
	 *
	 * @param edge <code>TEdge</code> edge
	 * @return <code>True</code> when the graph contains given edge, otherwise <code>False</code>
	 */
	public boolean containsEdge(TEdge edge);
        
        /**
	 * Returns true when the graph contains edge with given source and goal node
	 *
	 * @param fromNode <code>Node</code> source node
	 * @param toNode   <code>Node</code> goal node
	 * @return <code>True</code> when the graph contains edge with given source and goal node,
	 * otherwise <code>False</code>
	 */
	public boolean containsEdge(Node fromNode, Node toNode);

	/**
	 * Returns edge based on its source and goal node.
	 *
	 * @param fromNode source node
	 * @param toNode goal node
	 * @return <code>TEdge</code> connecting nodes
	 */
	public TEdge getEdge(Node fromNode, Node toNode);

	/**
	 * Returns list of edges incoming to node.
	 *
	 * @param node <code>TNode</code> node
	 * @return <code>List</code> of <code>TEdge</code> incoming to given node
	 */
	public List<TEdge> getInEdges(TNode node);

	/**
	 * Returns list of edges incoming to node specified by node id.
	 *
	 * @param nodeId <code>int</code> id of the node
	 * @return <code>List</code> of <code>TEdge</code> incoming to given node
	 */
	public List<TEdge> getInEdges(int nodeId);

	/**
	 * Returns list of edges outgoing from node specified by node id.
	 *
	 * @param node <code>TNode</code> node
	 * @return <code>List</code> of <code>TEdge</code> outgoing from given node
	 */
	public List<TEdge> getOutEdges(TNode node);

	/**
	 * Returns list of edges outgoing from node.
	 *
	 * @param nodeId <code>int</code> id of the node
	 * @return <code>List</code> of <code>TEdge</code> outgoing from given node
	 */
	public List<TEdge> getOutEdges(int nodeId);

	/**
	 * Returns collection of all nodes in the graph structure
	 *
	 * @return <code>Collection</code> of <code>TNode</code>
	 */
	public Collection<TNode> getAllNodes();

	/**
	 * Returns collection of all edges in the graph structure
	 *
	 * @return <code>Collection</code> of <code>TEdge</code>
	 */
	public Collection<TEdge> getAllEdges();

	public default int numberOfNodes(){
		return getAllNodes().size();
	}

	public default int numberOfEdges(){
		return getAllEdges().size();
	}

}
