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

package cz.cvut.fel.aic;

import cz.cvut.fel.aic.geographtools.Edge;
import cz.cvut.fel.aic.geographtools.Graph;
import cz.cvut.fel.aic.geographtools.GraphBuilder;
import cz.cvut.fel.aic.geographtools.Node;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GraphTest {

	Graph<Node, Edge> graph;
	GraphBuilder<Node, Edge> builder;

	@Before
	public void setUp() throws Exception {
		Node n0 = new Node(0, 10, 50352188, 14412064, 0, 0, 0);
		Node n1 = new Node(1, 11, 50595995, 14536064, 0, 0, 0);
		Node n2 = new Node(2, 12, 50998324, 14382655, 0, 0, 0);
		Node n3 = new Node(3, 13, 49995863, 14063664, 0, 0, 0);
		Node n4 = new Node(4, 14, 49775462, 14732555, 0, 0, 0);
		Node n5 = new Node(5, 15, 50446846, 14565874, 0, 0, 0);
		Node n6 = new Node(6, 16, 50465864, 14987525, 0, 0, 0);
		Node n7 = new Node(7, 17, 50465789, 14579844, 0, 0, 0);

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

		nodes.add(n0);
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

		builder = new GraphBuilder<>();
		builder.addNodes(nodes);
		builder.addEdges(edges);

		graph = builder.dumpCurrentGraph();
	}

	@Test
	public void testGetNode() throws Exception {

		Assert.assertEquals(builder.getNode(1).sourceId, graph.getNode(1).sourceId);
		Assert.assertEquals(builder.getNode(3).sourceId, graph.getNode(3).sourceId);

	}

	@Test
	public void testGetAllNodes() throws Exception {
		Assert.assertEquals(new HashSet<>(builder.getAllNodes()), new HashSet<>(graph.getAllNodes()));
	}

	@Test
	public void testGetEdge() throws Exception {

		for (Node node : graph.getAllNodes()) {
			for (Edge edge : graph.getOutEdges(node)) {
				Assert.assertEquals(graph.getEdge(node, edge.toNode), edge);
			}
		}
	}

	@Test
	public void testGetAllEdges() throws Exception {
		Assert.assertEquals(new HashSet<>(builder.getAllEdges()), new HashSet<>(graph.getAllEdges()));
	}

	@Test
	public void testGetInEdges() throws Exception {

		for (Node node : graph.getAllNodes()) {
			Assert.assertEquals(new HashSet<>(builder.getInEdges(node)), new HashSet<>(graph.getInEdges(node)));
		}
	}

	@Test
	public void testGetOutEdges() throws Exception {
		for (Node node : graph.getAllNodes()) {
			Assert.assertEquals(new HashSet<>(builder.getOutEdges(node)), new HashSet<>(graph.getOutEdges(node)));
		}
	}

	@Test
	public void testGraphEquals() throws Exception {
		Assert.assertTrue(graph.equals(builder.dumpCurrentGraph()));
	}
        
        @Test
        public void testGraphWkt() throws Exception {
            System.out.println(graph.toWKT());
            
            for (Edge edge : graph.getAllEdges()) {
                System.out.println(edge.toWKT());
            }
            
            for (Node node : graph.getAllNodes()) {
                System.out.println(node.toWKT());
            }
        }

}