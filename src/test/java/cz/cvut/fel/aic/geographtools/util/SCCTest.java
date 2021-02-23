/* This code is owned by Umotional s.r.o. (IN: 03974618). All Rights Reserved. */
package cz.cvut.fel.aic.geographtools.util;

import cz.cvut.fel.aic.geographtools.Edge;
import cz.cvut.fel.aic.geographtools.Node;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;

public class SCCTest {

	@Test
	public void testSCCTriangle() {
			
				Node n1 = new Node(1, 1, 0, 0, 0, 0, 0);
		Node n2 = new Node(2, 1, 0, 0, 0, 0, 0);
		Node n3 = new Node(3, 1, 0, 0, 0, 0, 0);
			
		Edge e1 = new Edge(n1, n2, 100);
		Edge e2 = new Edge(n2, n3, 100);
		Edge e3 = new Edge(n3, n1, 100);

		List<Edge> test1 = new ArrayList<>();
		test1.add(e1);
		test1.add(e2);
		test1.add(e3);

		Assert.assertEquals(3, StronglyConnectedComponentsFinder.getStronglyConnectedComponentsSortedBySize(test1).get
				(0).size());

		List<Edge> test2 = new ArrayList<>();
		test2.add(e1);
		test2.add(e2);

		Assert.assertEquals(3, StronglyConnectedComponentsFinder.getStronglyConnectedComponentsSortedBySize(test2)
				.size());
	}

	@Test
	public void testLargerGraphComponent() {

		Node n1 = new Node(1, 1, 0, 0, 0, 0, 0);
		Node n2 = new Node(2, 1, 0, 0, 0, 0, 0);
		Node n3 = new Node(3, 1, 0, 0, 0, 0, 0);
		Node n4 = new Node(4, 1, 0, 0, 0, 0, 0);
		Node n5 = new Node(5, 1, 0, 0, 0, 0, 0);
		Node n6 = new Node(6, 1, 0, 0, 0, 0, 0);
		Node n7 = new Node(7, 1, 0, 0, 0, 0, 0);

		Edge e1 = new Edge(n1, n2, 100);
		Edge e2 = new Edge(n2, n3, 100);
		Edge e3 = new Edge(n3, n1, 100);
		Edge e4 = new Edge(n3, n4, 100);
		Edge e5 = new Edge(n3, n5, 100);
		Edge e6 = new Edge(n4, n5, 100);
		Edge e7 = new Edge(n5, n4, 100);
		Edge e8 = new Edge(n5, n6, 100);

		List<Node> nodes = new ArrayList<>();
		List<Edge> edges = new ArrayList<>();

		nodes.add(n1);
		nodes.add(n2);
		nodes.add(n3);
		nodes.add(n4);
		nodes.add(n5);
		nodes.add(n6);
		nodes.add(n7);

		edges.add(e1);
		edges.add(e2);
		edges.add(e3);
		edges.add(e4);
		edges.add(e5);
		edges.add(e6);
		edges.add(e7);
		edges.add(e8);

		List<HashSet<Integer>> allCompo = StronglyConnectedComponentsFinder.getStronglyConnectedComponentsSortedBySize
				(nodes, edges);
		List<HashSet<Integer>> edgeCompo = StronglyConnectedComponentsFinder
				.getStronglyConnectedComponentsSortedBySize(edges);

		for (int i = 0; i < edgeCompo.size(); i++) {
			Assert.assertEquals(allCompo.get(i).size(), edgeCompo.get(i).size());
		}

		System.out.println(allCompo.size());
		Assert.assertEquals(3, allCompo.get(0).size());
		Assert.assertEquals(2, allCompo.get(1).size());
		Assert.assertEquals(1, allCompo.get(2).size());
		Assert.assertEquals(1, allCompo.get(3).size());

	}
}
