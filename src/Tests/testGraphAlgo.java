package Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

public class testGraphAlgo {

	@Test
	void testSave() {
		graph graph = new DGraph();
		for (int i = 0; i < 7; i++) {
			node_data node = new Node(i,new Point3D(0,0,0), 0,"", 0);
			graph.addNode(node);
		}	
		graph.connect(0, 1, 2);
		graph.connect(1, 2, 3);
		graph.connect(2, 3, 4);
		graph.connect(2, 0, 4);
		graph.connect(3, 4, 4);
		graph.connect(4, 0, 4);
		Graph_Algo algo = new Graph_Algo();
		algo.init(graph);
		algo.save("save file.txt");
	}
	@Test
	void testIsConeccted() {
		graph graph = new DGraph();
		for (int i = 0; i < 7; i++) {
			node_data node = new Node(i,new Point3D(0,0,0), 0,"", 0);
			graph.addNode(node);
		}	
		graph.connect(0, 1, 2);
		graph.connect(1, 2, 3);
		graph.connect(2, 3, 4);
		graph.connect(2, 0, 4);
		graph.connect(3, 4, 4);
		graph.connect(4, 0, 4);
		graph.connect(4, 6, 4);
		graph.connect(6, 2, 4);
		graph.connect(4, 0, 4);
		graph.connect(5, 6, 4);
		graph.connect(2, 5, 4);

		Graph_Algo algo = new Graph_Algo();
		algo.init(graph);
		assertTrue(algo.isConnected());
	}	@Test
	void testIsNotConeccted() {
		graph graph1 = new DGraph();
		for (int i = 0; i < 6; i++) {
			node_data node = new Node(i,new Point3D(0,0,0), 0,"", 0);
			graph1.addNode(node);
		}	
		graph1.connect(0, 1, 2);
		graph1.connect(1, 2, 3);
		graph1.connect(2, 3, 4);
		graph1.connect(3, 0, 1);
		graph1.connect(0, 3, 4);
		graph1.connect(2, 0, 1);
		graph1.connect(3, 4, 3);
		graph1.connect(4, 5, 4);
		graph1.connect(2, 4, 1);

		Graph_Algo algo1 = new Graph_Algo();
		algo1.init(graph1);
		assertFalse(algo1.isConnected());		
	}
	@Test
	void testShortestPathDist() {

		graph graph = new DGraph();
		for (int i = 0; i < 8; i++) {
			node_data node = new Node(i,new Point3D(0,0,0), 0,"", 0);
			graph.addNode(node);
		}	
		graph.connect(0, 7, 2);
		graph.connect(0, 1, 2);
		graph.connect(1, 2, 3);
		graph.connect(2, 3, 4);
		graph.connect(3, 0, 1);
		graph.connect(0, 3, 4);
		graph.connect(2, 0, 1);
		graph.connect(3, 4, 3);
		graph.connect(4, 5, 4);
		graph.connect(5, 0, 1);
		graph.connect(2, 4, 1);

		Graph_Algo algo = new Graph_Algo();
		algo.init(graph);
		assertEquals(6,algo.shortestPathDist(0, 4));
	}
	@Test
	void testShortestPathList() {
		graph graph = new DGraph();
		for (int i = 0; i < 15; i++) {
			node_data node = new Node(i,new Point3D(0,0,0), 0,"", 0);
			graph.addNode(node);
		}	
		graph.connect(1, 2, 3);
		graph.connect(2, 3, 4);
		graph.connect(3, 4, 1);
		graph.connect(8, 9, 1);
		graph.connect(9, 10, 1);
		graph.connect(10, 11, 4);
		graph.connect(11, 12, 3);
		graph.connect(12, 13, 4);
		graph.connect(13, 14, 1);
		graph.connect(10, 13, 2);
		graph.connect(3, 11, 6);

		Graph_Algo algo = new Graph_Algo();
		algo.init(graph);
		List<node_data> path = algo.shortestPath(1, 13);
		String string = "";
		for (int i = 0; i < path.size()-1 ;i++) {
			string += (path.get(i).getKey()+"" + " > ");
		}
		string += (path.get(path.size()-1).getKey() + "");
		assertEquals("1 > 2 > 3 > 11 > 12 > 13", string);
		System.out.println(string);
	}
	@Test
	void testTSP() {
		graph graph = new DGraph();
		for (int i = 0; i < 9; i++) {
			node_data node = new Node(i,new Point3D(0,0,0), 0,"", 0);
			graph.addNode(node);
		}	
		graph.connect(0, 1, 2);
		graph.connect(1, 2, 3);
		graph.connect(2, 3, 4);
		graph.connect(3, 4, 4);
		graph.connect(4, 5, 2);
		graph.connect(5, 6, 3);
		graph.connect(6, 0, 4);
		graph.connect(4, 7, 1);
		
		Graph_Algo algo = new Graph_Algo();
		algo.init(graph);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(3);
		targets.add(4);
		targets.add(2);
		targets.add(1);
		targets.add(6);
		targets.add(5);
		targets.add(0);
		List<node_data> path = algo.TSP(targets);
		String string = "";
		for (int i = 0; i < path.size()-1 ;i++) {
			string += (path.get(i).getKey()+"" + " > ");
		}
		string += (path.get(path.size()-1).getKey() + "");
		assertEquals("3 > 4 > 5 > 6 > 0 > 1 > 2", string);
	}
	@Test
	void testTSPNotConnected() {
		graph graph = new DGraph();
		for (int i = 0; i < 5; i++) {
			node_data node = new Node(i,new Point3D(0,0,0), 0,"", 0);
			graph.addNode(node);
		}	
		graph.connect(0, 1, 10);
		graph.connect(1, 2, 10);
		graph.connect(2, 4, 10);
		graph.connect(3, 4, 10);
		graph.connect(4, 2, 10);

		Graph_Algo algo = new Graph_Algo();
		algo.init(graph);
		List<Integer> targets = new ArrayList<Integer>();
		targets.add(1);
		targets.add(0);
		targets.add(4);
		targets.add(3);
		List<node_data> path = algo.TSP(targets);
		assertNull(path);
	}
}
