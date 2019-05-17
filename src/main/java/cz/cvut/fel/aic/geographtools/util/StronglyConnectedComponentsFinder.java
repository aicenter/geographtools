/* This code is owned by Umotional s.r.o. (IN: 03974618). All Rights Reserved. */
package cz.cvut.fel.aic.geographtools.util;

import cz.cvut.fel.aic.geographtools.Edge;
import cz.cvut.fel.aic.geographtools.GraphStructure;
import cz.cvut.fel.aic.geographtools.Node;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for computing the strongly connected components using Kosaraju's algorithm
 */
public class StronglyConnectedComponentsFinder {

	/**
	 * Computes strongly connected components.
	 *
	 * @param nodeIds
	 * 		Ids of nodes
	 * @param edges
	 * 		Directed edges in the graph represented as adjacency lists. Stored in Map sourceNode > set of successors
	 *
	 * @return List of sets of ids, each set corresponds to one strongly connected component. The sets are sorted
	 * according to their size
	 */
	public static List<HashSet<Integer>> getStronglyConnectedComponentsSortedBySize(Set<Integer> nodeIds,
																					Map<Integer, Set<Integer>> edges) {
		return new KosarajuSCCComputer(nodeIds, edges).getAllStronglyConnectedComponentsSortedBySize();
	}

	/**
	 * Computes strongly connected components for given graph.
	 *
	 * @return List of sets of ids, each set corresponds to one strongly connected component. The sets are sorted
	 * according to their size
	 */
	public static List<HashSet<Integer>> getStronglyConnectedComponentsSortedBySize(
			GraphStructure<? extends Node, ? extends Edge> graph) {

		Set<Integer> nodeIds = graph.getAllNodes().stream().map(node -> node.id).collect(Collectors.toSet());

		Map<Integer, Set<Integer>> outgoing = new HashMap<>();

		for (Edge edge : graph.getAllEdges()) {
			addElementToSetInMapValue(outgoing, edge.fromNode.getId(), edge.toNode.getId());
		}

		return new KosarajuSCCComputer(nodeIds, outgoing).getAllStronglyConnectedComponentsSortedBySize();
	}

	/**
	 * Computes strongly connected components.
	 *
	 * @param nodes
	 * 		Nodes
	 * @param edges
	 * 		Edges
	 *
	 * @return List of sets of ids, each set corresponds to one strongly connected component. The sets are sorted
	 * according to their size
	 */
	public static List<HashSet<Integer>> getStronglyConnectedComponentsSortedBySize(Collection<? extends Node> nodes,
																					Collection<? extends Edge> edges) {

		Set<Integer> nodeIds = nodes.stream().map(node -> node.id).collect(Collectors.toSet());

		Map<Integer, Set<Integer>> outgoing = new HashMap<>();

		for (Edge edge : edges) {
			addElementToSetInMapValue(outgoing, edge.fromNode.getId(), edge.toNode.getId());
		}

		return new KosarajuSCCComputer(nodeIds, outgoing).getAllStronglyConnectedComponentsSortedBySize();
	}

	/**
	 * Computes strongly connected components. Nodes: ((in-degree = 0) && (out-degree = 0)), are not present in any
	 * component, because we have no evidence about them from the set of edges
	 *
	 * @param edges
	 * 		Edges
	 *
	 * @return List of sets of ids, each set corresponds to one strongly connected component. The sets are sorted
	 * according to their size
	 */
	public static List<HashSet<Integer>> getStronglyConnectedComponentsSortedBySize(Collection<? extends Edge> edges) {

		HashSet<Integer> nodeIds = new HashSet<>();

		Map<Integer, Set<Integer>> outgoing = new HashMap<>();

		for (Edge edge : edges) {
			nodeIds.add(edge.fromNode.getId());
			nodeIds.add(edge.toNode.getId());
			addElementToSetInMapValue(outgoing, edge.fromNode.getId(), edge.toNode.getId());
		}

		return new KosarajuSCCComputer(nodeIds, outgoing).getAllStronglyConnectedComponentsSortedBySize();
	}

	private static <K, V> void addElementToSetInMapValue(Map<K, Set<V>> map, K key, V element) {

		if (!map.containsKey(key)) {
			map.put(key, new HashSet<>());
		}

		map.get(key).add(element);
	}

	private static final class KosarajuSCCComputer {

		private Set<Integer> nodeIds;
		private Map<Integer, Set<Integer>> outgoingEdges;
		private Map<Integer, Set<Integer>> incomingEdges;
		private HashSet<Integer> alreadyInOrdering;
		private Stack<Integer> ordering;
		private HashSet<Integer> open;

		private KosarajuSCCComputer(Set<Integer> nodeIds, Map<Integer, Set<Integer>> edgesAsAdjacencyLists) {
			this.nodeIds = nodeIds;
			this.outgoingEdges = edgesAsAdjacencyLists;
			this.incomingEdges = buildIncomingEdges(edgesAsAdjacencyLists);

			open = new HashSet<>();
			alreadyInOrdering = new HashSet<>();
			ordering = new Stack<>();

		}

		private Map<Integer, Set<Integer>> buildIncomingEdges(Map<Integer, Set<Integer>> edgesAsAdjacencyLists) {

			Map<Integer, Set<Integer>> incomingEdges = new HashMap<>();

			for (Map.Entry<Integer, Set<Integer>> entry : edgesAsAdjacencyLists.entrySet()) {
				Integer fromId = entry.getKey();
				for (Integer toId : entry.getValue()) {
					addElementToSetInMapValue(incomingEdges, toId, fromId);
				}
			}

			return incomingEdges;
		}

		private ArrayList<HashSet<Integer>> getAllStronglyConnectedComponentsSortedBySize() {

			ArrayList<Integer> points = new ArrayList<>(nodeIds);

			int index = 0;
			while (alreadyInOrdering.size() < points.size()) {

				while (index < points.size() && (alreadyInOrdering.contains(points.get(index)))) {
					index++;
				}
				if (index == points.size()) {
					break;
				}
				DFSextwalk(points.get(index), outgoingEdges, incomingEdges);
			}

			HashSet<Integer> alreadyInSomeRegion = new HashSet<>();
			Stack<Integer> stack2 = new Stack<>();
			open = new HashSet<>();

			ArrayList<HashSet<Integer>> components = new ArrayList<>();

			HashSet<Integer> component;

			while (!ordering.isEmpty()) {

				boolean empty = false;
				Integer currentStarting = null;
				do {
					if (ordering.isEmpty()) {
						empty = true;
						break;
					} else {
						currentStarting = ordering.pop();
					}

					/**/
				} while (alreadyInSomeRegion.contains(currentStarting));
				if (empty) {
					break;
				}
				component = new HashSet<>();
				components.add(component);

				stack2.add(currentStarting);

				while (!stack2.isEmpty()) {
					Integer current = stack2.pop();
					component.add(current);
					alreadyInSomeRegion.add(current);

					ArrayList<Integer> succ = new ArrayList<>();
					if (incomingEdges.get(current) != null) {
						succ.addAll(incomingEdges.get(current));
					}

					for (int i = 0; i < succ.size(); i++) {
						if (!alreadyInSomeRegion.contains(succ.get(i)) && !open.contains(succ.get(i))) {
							stack2.push(succ.get(i));
							open.add(succ.get(i));
						}
					}
				}
			}

			Collections.sort(components, (o1, o2) -> o2.size() - o1.size());

			return components;

		}

		private void DFSextwalk(Integer root, Map<Integer, Set<Integer>> outgoing,
								Map<Integer, Set<Integer>> incomming) {
			//System.out.println("current point: "+current.id);
			open.add(root);
			//System.out.println("starting walk with: "+root.id);

			Stack<Integer> stack = new Stack<>();
			HashMap<Integer, ArrayList<Integer>> successors = new HashMap<>();

			stack.add(root);

			while (!stack.isEmpty()) {

				Integer current = stack.peek();
				open.add(current);

				if (!successors.containsKey(current)) {

					ArrayList<Integer> succ = new ArrayList<>();

					if (outgoing.get(current) != null) {
						succ.addAll(outgoing.get(current));
					}
					successors.put(current, succ);

				}

				if (!successors.get(current).isEmpty()) {
					Integer next = successors.get(current).remove(0);
					if (!open.contains(next)) {
						stack.push(next);
					}

				} else {
					stack.pop();
					ordering.push(current);
					alreadyInOrdering.add(current);

				}
			}
		}
	}

}
