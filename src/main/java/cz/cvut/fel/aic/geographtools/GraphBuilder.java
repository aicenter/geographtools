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

import java.util.*;

public class GraphBuilder<TNode extends Node, TEdge extends Edge> implements GraphStructure<TNode, TEdge> {

	private Map<Long, Integer> longIdToIntId = new HashMap<>();

	private Map<Integer, TNode> nodesByNodeId = new LinkedHashMap<>();
	private Map<EdgeId, TEdge> edgeByFromToNodeIds = new LinkedHashMap<>();
	private Map<Integer, List<TEdge>> nodeOutcomingEdges = new HashMap<>();
	private Map<Integer, List<TEdge>> nodeIncomingEdges = new HashMap<>();

	public GraphBuilder() {
	}

	/**
	 * Return internal <code>int</code> id for the node specified by source id
	 *
	 * @param sourceId <code>long</code>
	 * @return <code>int</code> id for given node
	 */
	public int getIntIdForSourceId(long sourceId) {
		return longIdToIntId.get(sourceId);
	}

	/**
	 * Add a collection of roadNodes to the graph
	 *
	 * @param nodes Nodes
	 */
	public void addNodes(Collection<TNode> nodes) {
		for (TNode node : nodes) {
			addNode(node);
		}
	}

	/**
	 * Adds node to graph.
	 *
	 * @param node <code>TNode</code>
	 */
	public void addNode(TNode node) {

		if (nodesByNodeId.containsKey(node.id)) {
			throw new IllegalArgumentException("Node with this int id already present! Can not import node");
		}

		nodesByNodeId.put(node.id, node);
		nodeOutcomingEdges.put(node.id, new ArrayList<>());
		nodeIncomingEdges.put(node.id, new ArrayList<>());

		longIdToIntId.put(node.sourceId, node.id);

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
	public boolean containsNode(int nodeId) {
		return getNode(nodeId) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TNode getNode(int nodeId) {
		return nodesByNodeId.get(nodeId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<TNode> getAllNodes() {
		return nodesByNodeId.values();
	}

	/**
	 * Add a collection of edges to the graph
	 *
	 * @param edges Edges
	 */
	public void addEdges(Collection<TEdge> edges) {
		for (TEdge edge : edges) {
			if (!containsEdge(edge.fromId, edge.toId)) {
				addEdge(edge);
			}
		}
	}

	/**
	 * Adds edge to graph.
	 *
	 * @param edge <code>TEdge</code>
	 */
	public void addEdge(TEdge edge) { // we may think about "creating edges" inside graph builder because of ids

		assert nodesByNodeId.get(edge.fromId) != null
				&& nodesByNodeId.get(edge.toId) != null : "Node has to be in graph builder before inserting edge";

		EdgeId edgeId = new EdgeId(edge.fromId, edge.toId);

		assert !edgeByFromToNodeIds.containsKey(edgeId) : "Edge has not to exist yet";

		List<TEdge> outcomingEdgesFromNode = nodeOutcomingEdges.get(edge.fromId);
		List<TEdge> incomingEdgesToNode = nodeIncomingEdges.get(edge.toId);

		outcomingEdgesFromNode.add(edge);
		incomingEdgesToNode.add(edge);

		edgeByFromToNodeIds.put(edgeId, edge);
		nodeOutcomingEdges.put(edge.fromId, outcomingEdgesFromNode);
		nodeIncomingEdges.put(edge.toId, incomingEdgesToNode);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsEdge(TEdge edge) {
		return containsEdge(edge.fromId, edge.toId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsEdge(int fromId, int toId) {
		return getEdge(fromId, toId) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TEdge getEdge(int fromId, int toId) {
		EdgeId edgeId = new EdgeId(fromId, toId);
		return edgeByFromToNodeIds.get(edgeId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TEdge> getInEdges(TNode node) {
		return getInEdges(node.id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TEdge> getInEdges(int nodeId) {
		return nodeIncomingEdges.get(nodeId);
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
		return nodeOutcomingEdges.get(nodeId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<TEdge> getAllEdges() {
		return edgeByFromToNodeIds.values();
	}

	/**
	 * Creates a final graph and clears the builder structures.
	 *
	 * @return Final <code>Graph</code>
	 */
	public Graph<TNode, TEdge> createGraph() {

		Graph<TNode, TEdge> graph = dumpCurrentGraph();

		this.nodesByNodeId = new HashMap<>();
		this.edgeByFromToNodeIds = new HashMap<>();
		this.nodeOutcomingEdges = new HashMap<>();
		this.nodeIncomingEdges = new HashMap<>();
		this.longIdToIntId = new HashMap<>();

		return graph;
	}

	/**
	 * Creates a final graph and keeps the structures ready for further building of the graph.
	 *
	 * @return current state of the <code>Graph</code>
	 */
	public Graph<TNode, TEdge> dumpCurrentGraph() {

		// check node ids sequence
		for (int i = 0; i < nodesByNodeId.keySet().size(); i++) {

			TNode node = nodesByNodeId.get(i);
			if (node == null) {
				throw new NoSuchElementException(" Node with id " + i
						+ " not present! The sequence of ndoe id must start with 0 and end with 'numOfNodes-1'");
			}
		}

		ArrayList<TNode> nodesByNodeIdList = new ArrayList<>(nodesByNodeId.keySet().size());
		for (int i = 0; i < nodesByNodeId.keySet().size(); i++) {
			nodesByNodeIdList.add(nodesByNodeId.get(i));
		}

		int[] outgoingPositions = new int[nodesByNodeId.keySet().size() + 1];
		ArrayList<TEdge> outgoingEdges = new ArrayList<>(nodesByNodeId.keySet().size());
		int[] incomingPositions = new int[nodesByNodeId.keySet().size() + 1];
		ArrayList<TEdge> incomingEdges = new ArrayList<>(nodesByNodeId.keySet().size());

		// OUTGOING EDGES
		// iterate over all roadNodes
		int j = 0; // outgoing edges id
		for (int k = 0; k < nodesByNodeIdList.size(); k++) {

			// log.debug("---------");
			// log.debug("NODE: " + allNodesByNodeId[k]);

			// assign outgoing position
			outgoingPositions[k] = j;

			// iterate over outgoing edges
			for (TEdge edge : nodeOutcomingEdges.get(k)) {

				outgoingEdges.add(edge);
				j++;
			}
		}
		// set numberOfNodes+1 of outgoing positions to current edge id as indentation
		outgoingPositions[nodesByNodeId.keySet().size()] = j;

		// INCOMING EDGES
		// iterate over all roadNodes
		int l = 0; // outgoing edges id
		for (int k = 0; k < nodesByNodeIdList.size(); k++) {

			// assign incoming position
			incomingPositions[k] = l;

			// iterate over incoming edges
			for (TEdge edge : nodeIncomingEdges.get(k)) {

				incomingEdges.add(edge);
				l++;
			}
		}
		// set numberOfNodes+1 of incoming positions to current edge id as indentation
		incomingPositions[nodesByNodeId.keySet().size()] = l;

		return new Graph<>(nodesByNodeIdList, outgoingPositions, outgoingEdges, incomingPositions, incomingEdges);

	}

	private class EdgeId {
		private static final long serialVersionUID = 4716865102995519001L;

		public final long fromNodeId;
		public final long toNodeId;

		public EdgeId(long fromNodeId, long toNodeId) {
			super();
			this.fromNodeId = fromNodeId;
			this.toNodeId = toNodeId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (fromNodeId ^ (fromNodeId >>> 32));
			result = prime * result + (int) (toNodeId ^ (toNodeId >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			EdgeId other = (EdgeId) obj;
			if (fromNodeId != other.fromNodeId)
				return false;
			return toNodeId == other.toNodeId;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return String.format("%d-->%d", this.fromNodeId, this.toNodeId);
		}

		public long getFromNodeId() {
			return fromNodeId;
		}

		public long getToNodeId() {
			return toNodeId;
		}
	}
}
