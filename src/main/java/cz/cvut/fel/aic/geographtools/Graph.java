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

public class Graph<TNode extends Node, TEdge extends Edge> implements GraphStructure<TNode, TEdge>, Serializable {

	private static final long serialVersionUID = -5165475941478397820L;

	/**
	 * Array of nodes indexed by node IDs (size = numberOfNodes).
	 */
	private final ArrayList<TNode> allNodesByNodeId;

	/**
	 * Starting positions of outgoing edges in the edge list (size = numberOfNodes + 1).
	 */
	private final int[] outgoingPositions;

	/**
	 * Array of outgoing edges indexed by starting positions array (size = numberOfEdges).
	 */
	private final ArrayList<TEdge> outgoingEdges;

	/**
	 * Cache of edge lists for outgoing edges (size = numberOfNodes).
	 */
	private final ArrayList<EdgeList<TEdge>> outgoingEdgesCache;

	/**
	 * Cache of edge lists for incoming edges (size = numberOfNodes).
	 */
	private final ArrayList<EdgeList<TEdge>> incomingEdgesCache;

	/**
	 * Number of roadNodes.
	 */
	private final int numberOfNodes;
	/**
	 * Number of edges.
	 */
	private final int numberOfEdges;

	protected Graph(ArrayList<TNode> allNodesByNodeId, int[] outgoingPositions, ArrayList<TEdge> outgoingEdges,
			int[] incomingPositions, ArrayList<TEdge> incomingEdges) {

		this.allNodesByNodeId = allNodesByNodeId;

		this.outgoingPositions = outgoingPositions;
		this.outgoingEdges = outgoingEdges;

		// just derived
		this.numberOfNodes = allNodesByNodeId.size();
		this.numberOfEdges = outgoingEdges.size();

		// create outgoing edges cache
		this.outgoingEdgesCache = new ArrayList<>(this.numberOfNodes);
		for (Node node : allNodesByNodeId) {
			outgoingEdgesCache
					.add(new EdgeList<>(outgoingPositions[node.id], outgoingPositions[node.id + 1], outgoingEdges));
		}

		// create incoming edges cache
		this.incomingEdgesCache = new ArrayList<>(this.numberOfNodes);
		for (Node node : allNodesByNodeId) {
			incomingEdgesCache
					.add(new EdgeList<>(incomingPositions[node.id], incomingPositions[node.id + 1], incomingEdges));
		}
	}

	/**
	 * Creates map from nodes' id to nodes' source id.
	 *
	 * @return <code>HashMap</code>
	 */
	public Map<Integer, Long> createNodeIdToSourceIdMap() {

		Map<Integer, Long> map = new HashMap<>();

		for (TNode TNode : allNodesByNodeId) {
			map.put(TNode.id, TNode.sourceId);
		}

		return map;
	}

	/**
	 * Creates map from nodes' source id to nodes' id.
	 *
	 * @return <code>HashMap</code>
	 */
	public Map<Long, Integer> createSourceIdToNodeIdMap() {

		Map<Long, Integer> map = new HashMap<>();

		for (TNode TNode : allNodesByNodeId) {
			map.put(TNode.sourceId, TNode.id);
		}

		return map;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsNode(int nodeId) {
		return ((0 <= nodeId) && (nodeId < numberOfNodes));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsNode(TNode node) {
		return containsNode(node.id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TNode getNode(int nodeId) {
		return allNodesByNodeId.get(nodeId);
	}

	@Override
	public boolean containsEdge(TEdge edge) {
		return containsEdge(edge.fromId, edge.toId);
	}

	@Override
	public boolean containsEdge(int fromId, int toId) {
		return getEdge(fromId, toId) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TEdge getEdge(int fromNodeId, int toNodeId) {

		// scan edges from outgoingPositions[fromNodeId] to (outgoingPositions[fromNodeId+1]-1)
		// sequential approach still faster than map, operation not used very often

		for (int j = outgoingPositions[fromNodeId]; j < (outgoingPositions[fromNodeId + 1]); j++) {
			if (outgoingEdges.get(j).toId == toNodeId) {
				return outgoingEdges.get(j);
			}
		}
		return null;
	}

	@Override
	public List<TEdge> getInEdges(TNode node) {
		return getInEdges(node.id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TEdge> getInEdges(int nodeId) {
		return incomingEdgesCache.get(nodeId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TEdge> getOutEdges(TNode node) {
		return getOutEdges(node.id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TEdge> getOutEdges(int nodeId) {
		return outgoingEdgesCache.get(nodeId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<TNode> getAllNodes() {
		return Collections.unmodifiableList(allNodesByNodeId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<TEdge> getAllEdges() {
		return Collections.unmodifiableList(outgoingEdges);
	}


	@Override
	public int numberOfNodes() {
		return numberOfNodes;
	}

	@Override
	public int numberOfEdges() {
		return numberOfEdges;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Graph))
			return false;

		Graph graph = (Graph) o;

		if (!allNodesByNodeId.equals(graph.allNodesByNodeId))
			return false;
		if (!new HashSet<>(outgoingEdges).equals(new HashSet<TEdge>(graph.outgoingEdges)))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = allNodesByNodeId.size();
		result = 31 * result + outgoingEdges.size();
		return result;
	}

	@Override
	public String toString() {
		return "Graph [#nodes=" + numberOfNodes + ", #edges=" + numberOfEdges + "]";
	}

}
