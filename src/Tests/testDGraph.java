package Tests;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import dataStructure.DGraph;
import dataStructure.Edge;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.node_data;
import utils.Point3D;

public class testDGraph {


	@Test
	void testGettersSetters() {

		DGraph graph = new DGraph();
		for (int i = 0; i < 4; i++) {
			node_data node = new Node(i,new Point3D(0,0,0), 0,"", 0);
			graph.addNode(node);
		}
		graph.connect(0, 1, 2);
		graph.connect(1, 2, 3);
		graph.connect(2, 3, 4);
		graph.connect(3, 0, 1);
		graph.connect(0, 2, 2);
		Point3D point = new Point3D(4,4);
		graph.getNode(3).setLocation(new Point3D(4,4));

		assertEquals(1, graph.getNode(1).getKey());
		assertEquals(point, graph.getNode(3).getLocation());
		assertNull(graph.getNode(5));

		assertEquals(4, graph.getEdge(2,3).getWeight());
		assertEquals(2, graph.getEdge(0,2).getWeight());
		assertNull(graph.getEdge(0, 4));

	}
	@Test
	void testCollection() {

		DGraph graph = new DGraph();
		for (int i = 0; i < 4; i++) {
			node_data node = new Node(i,new Point3D(0,0,0), 0,"", 0);
			graph.addNode(node);
		}

		graph.connect(0, 1, 2);
		graph.connect(1, 2, 3);
		graph.connect(2, 3, 4);
		graph.connect(3, 0, 1);
		graph.connect(0, 2, 2);
		graph.connect(0, 3, 2);

		Collection<node_data> nodes = graph.getV();
		assertEquals(graph.nodeSize(), nodes.size());

		Collection<edge_data> edges0 = graph.getE(0);
		assertEquals(3,edges0.size() );	
		Collection<edge_data> edges1 = graph.getE(1);
		assertEquals(1,edges1.size() );
		assertEquals(6, graph.edgeSize());
	}
	@Test
	void testRemoveNode() {
		DGraph graph = new DGraph();
		for (int i = 0; i < 4; i++) {
			node_data node = new Node(i,new Point3D(0,0,0), 0,"", 0);
			graph.addNode(node);
		}	
		graph.connect(0, 1, 2);
		graph.connect(1, 2, 3);
		graph.connect(2, 3, 4);
		graph.connect(3, 0, 1);
		graph.connect(0, 2, 2);
		graph.connect(0, 3, 2);

		graph.removeNode(0);
		assertEquals(null, graph.getNode(0));
		assertEquals(null, graph.getEdge(0, 1));
		assertEquals(null, graph.getEdge(3, 0));
		assertEquals(2, graph.edgeSize());		
	}
	@Test
	void testRemoveEdge() {
		DGraph graph = new DGraph();
		for (int i = 0; i < 4; i++) {
			node_data node = new Node(i,new Point3D(0,0,0), 0,"", 0);
			graph.addNode(node);
		}	
		graph.connect(0, 1, 2);
		graph.connect(1, 2, 3);
		graph.connect(2, 3, 4);
		graph.connect(3, 0, 1);
		graph.connect(0, 2, 2);
		graph.connect(0, 3, 2);

		graph.removeEdge(2, 3);
		graph.removeEdge(0, 1);
		assertEquals(null, graph.getEdge(2, 3));
		assertEquals(null, graph.getEdge(0, 1));
		boolean check = false;
		try {
			graph.removeEdge(2, 4);
			fail("There is no node 4");
		}
		catch (Exception e) {
			check = true;
		}
		assertTrue(check);
	}
	@Test
	void testSize() {
		DGraph graph = new DGraph();
		for (int i = 0; i < 6; i++) {
			node_data node = new Node(i,new Point3D(0,0,0), 0,"", 0);
			graph.addNode(node);
		}	
		graph.connect(0, 1, 2);
		graph.connect(1, 2, 3);
		graph.connect(2, 3, 4);
		graph.connect(3, 0, 1);
		graph.connect(0, 2, 2);
		graph.connect(0, 3, 2);
		
		assertEquals(6, graph.nodeSize());
		assertEquals(6, graph.edgeSize());
	}




}
