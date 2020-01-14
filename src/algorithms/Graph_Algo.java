package algorithms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;

/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms{

	private DGraph graph;

	public Graph_Algo() {

	}
	public Graph_Algo(graph gra) {
		init(gra);
	}
	@Override
	public void init(graph g) {
		this.graph = (DGraph) g;

	}

	@Override
	public void init(String file_name) {
		Graph_Algo saveGraph = new Graph_Algo();
		saveGraph = null;
		try {
			FileInputStream file = new FileInputStream(file_name);
			ObjectInputStream input = new ObjectInputStream(file);
			saveGraph = (Graph_Algo) input.readObject();
			this.graph = saveGraph.graph;
			input.close();
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save(String file_name) {
		try { 
			FileOutputStream file = new FileOutputStream(file_name);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(graph);
			out.close();
			file.close();
		} catch (IOException e) {
			System.err.println("**save file error**");
		}

	}

	@Override
	public boolean isConnected() {
		boolean flag;
		int src;
		for (node_data node : graph.getV()) {   //checks through every node if its connected to all others.
			src = node.getKey();
			flag = true;
			for (node_data differentNode : graph.getV()) {   
				HashSet<Integer> keySet = new HashSet<>(); 
				if ( differentNode.getKey() != node.getKey()) {   //if both keys arnet the same check if the node in the prior for connects to this one.
					flag = isSrcConnected(src, differentNode.getKey(),keySet);   //returns true if connectes.
				}
				if (!(flag)) {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * This recursive method returns true if a src node is connected to a dest or if one
	 * of its edges is connected to the dest and so on.
	 * @param src
	 * @param key
	 * @param keySet
	 * @return true if src connected to dest (not only direct)
	 */
	public boolean isSrcConnected(int src, int key, HashSet keySet) {

		if (graph.getE(src) == null || graph.getE(src).isEmpty()) {   //checks if src has any destinations (has edges)
			return false;
		}
		Collection<edge_data> edges = graph.getE(src);
		for (edge_data dest : edges) {
			if (dest.getDest() == key) {
				return true;
			}
		}
		boolean flag = false;
		boolean temp;
		for (edge_data dest : edges) {

			if ( !( keySet.contains(dest.getDest()))) {          //checks if we've been to this destination.
				keySet.add(dest.getDest());
				temp = isSrcConnected(dest.getDest(), key, keySet);  // if not then we recursively check if this dest is connected to our key;
				flag = flag || temp;
				if (temp == true) {
					return true;
				}
			}
		}
		return flag; 
	}

	@Override
	public double shortestPathDist(int src, int dest) {        //info: visited, weight: cost
		if (graph.getNode(src) == null || graph.getNode(dest) == null || src == dest) {
			throw new RuntimeException("Please use valid nodes for this method");
		}
		ArrayList<node_data> listNotVisited = new ArrayList<node_data>();  //list of all unvisited nodes
		node_data currentNode = null;
		for (node_data node : graph.getV()) {     //initiate all weights to infinity except our current node.
			node.setTag(0);      //tag 0 means not visited and 1 is visited
			if (node.getKey() == src) {
				node.setWeight(0);                
				currentNode = node;
			}else {
				node.setWeight(Double.POSITIVE_INFINITY);   
			}
		}
		node_data nextNode = null;
		node_data destNode;
		double currentNodeWeight,checkWeight,nodeWeight;
		listNotVisited.add(currentNode);

		while((!listNotVisited.isEmpty()) || this.graph.getNode(dest).getTag() == 0) {
			nextNode = listNotVisited.get(0);   //incase the current node has no edges
			currentNodeWeight = currentNode.getWeight();
			currentNode.setTag(1);
			listNotVisited.remove(currentNode);
			double minWeight = Double.POSITIVE_INFINITY;
			if (graph.getE(currentNode.getKey()) != null) {
				for (edge_data neighbour : graph.getE(currentNode.getKey()) ) {
					destNode = this.graph.getNode(neighbour.getDest());    //the destination node of the edge
					if (destNode.getTag() == 0) { //if we didn't visit this node insert it to the list
						listNotVisited.add(destNode);
					}
					checkWeight = currentNodeWeight + neighbour.getWeight();    //the cost of getting to the neighbor
					if (checkWeight < destNode.getWeight()) {
						destNode.setWeight(checkWeight);
						destNode.setInfo(currentNode.getInfo() + ">" + currentNode.getKey() + ""); //saves the whole path;
					}
					if (destNode.getWeight() < minWeight && destNode.getTag() == 0) {
						nextNode = destNode;
						minWeight = destNode.getWeight();
					}
				}currentNode = nextNode;
			}else {
				if (!listNotVisited.isEmpty()) {
					currentNode = listNotVisited.get(0);
				}
			}
		}

		return graph.getNode(dest).getWeight();
	}
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		List<node_data> path = new ArrayList<node_data>();
		shortestPathDist(src, dest);
		String stringPath = graph.getNode(dest).getInfo();
		int lengthPath = graph.getNode(dest).getInfo().length(); //length of the path
		int key = 0;
		for (int i = 1; i < lengthPath; i++) {  
			if (stringPath.charAt(i) == '>') {  
				node_data node = new Node((Node) graph.getNode(key));
				path.add(node);
				key = 0;
			}else {
				key = key*10 + Character.getNumericValue(stringPath.charAt(i)); //key will be equal to the node in the string before the '>' 
			}
		}
		path.add(graph.getNode(key));
		path.add(graph.getNode(dest));
		return path;
	}

	@Override
	public List<node_data> TSP(List<Integer> targets) {
		if (targets.size() <= 1) {
			System.out.println("**TSP** Please enter at least two nodes");
			return null;
		}
		if (!this.isConnected()) {
			if (!firmlyConnected(targets)) {   //checks if the 'nodes'represented by the list are firmly connected 
				System.out.println("this list does not represent a connected graph");
				return null;
			}
		}
		try {
			List<node_data> path = new ArrayList<node_data>();
			List<node_data> temp;
			int src; 
			int dest = 0; 
			String nodePath;
			String target;
			while (targets.size() > 1) {
				src = targets.get(0);
				dest = targets.get(1);
				targets.remove(0);
				temp = shortestPath(src, dest);
				path.addAll(temp);
				nodePath = this.graph.getNode(dest).getInfo();  //the path to dest Node
				for (int i = 1; i < targets.size(); i++) {     //checks for all the 
					target = String.valueOf(targets.get(i));
					if (contains(nodePath, target)) {
						targets.remove(i);
						i--;
					}
				}
			}
			String [] trimedInfo = this.graph.getNode(dest).getInfo().split(">");
			int pathSize = path.size();
			for (int i=0;i<pathSize-trimedInfo.length;i++) {
				path.remove(0);
			}

			return path;
		} catch (Exception e) {
			throw new RuntimeException("Cant reach a node invalid list!!");
		}
	}
	/**
	 * this method checks if the list containing int whom represents nodes represent a connected graph
	 * @param targets
	 * @return true if connected
	 */
	private boolean firmlyConnected(List<Integer> targets) {
		graph tempGraph = new DGraph();
		int src, dest;
		double weight;
		for (int index : targets) {   //add nodes to new graph
			tempGraph.addNode(graph.getNode(index));
		}
		for (int index : targets) {
			if (this.graph.getE(index) != null) {
				for (edge_data edge : graph.getE(index)) {
					src =  edge.getSrc();
					dest = edge.getDest();
					weight = edge.getWeight();
					if (targets.contains(src) && targets.contains(dest))
					tempGraph.connect(src, dest, weight);
				}				
			}
		}
		Graph_Algo temp = new Graph_Algo();
		temp.init(tempGraph);
		if (temp.isConnected()) {
			return true;
		}
		return false;
	}
	/**
	 * checks if string s1 contains string s2
	 * @param s1
	 * @param s2
	 * @return true if positive answer
	 */
	public boolean contains(String s1, String s2) {
		String [] str = s1.split(">");
		for (int i = 0; i < str.length; i++) {
			if (str[i].equals(s2)) {
				return true;
			}
		}return false;
	}

	@Override
	public graph copy() {
		return new DGraph(this.graph);

	}

}
