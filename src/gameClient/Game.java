package gameClient;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;
/**
 * This class represents the elements for the game; robot, fruit, graph
 * and the data from the game service.
 * @author dovie
 *
 */
public class Game {

	private Hashtable<Point3D, Fruit> fruitMap;    // the point which is uniqe in this project represents the key
	private Hashtable<Integer, Robot> robotMap;   // the key will  be the id
	private graph g;
	private game_service gameService;

	public Game() {
		this.fruitMap = new Hashtable<>();
		this.robotMap = new Hashtable<>();
		g = null;
		gameService = null;
	}
	public Game(int scenario) {
		this.fruitMap = new Hashtable<>();
		this.robotMap = new Hashtable<>();
		game_service game = Game_Server.getServer(scenario);
		this.gameService = game;
		String graph = game.getGraph();
		g = new DGraph(graph);
		addFruit(game.getFruits());
		addRobotsToGame();   //before adding to the list you must add robots to the game
		addRobots(game.getRobots());


	}//adds robots to the game sever, uses a method to add to specific node
	public void addRobotsToGame() {
		String s = this.gameService.toString();
		try {
			JSONObject gameServer = new JSONObject(s);
			gameServer = gameServer.getJSONObject("GameServer");
			int numRobot = gameServer.getInt("robots");
			int numFruits = gameServer.getInt("fruits");
			addRobotsByLocation(numRobot,numFruits);   //add according to best location
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}//adds robots by fruis location and fruit quantity
	public void addRobotsByLocation(int numRobot, int numFruits) {
		int largeNode;
		int smallNode;
		for (Fruit fruits : this.getAllFruits()) {	//adds the nearest edge for each fruit
			whichEdge(fruits);
		}
		if (numFruits <= numRobot) {   //if same amount of robots and fruits
			for (Fruit fruits : this.getAllFruits()) { //adds a robot to each fruit by the nearest direction
				if (fruits.getEdge().getSrc() > fruits.getEdge().getDest()) {  //checks which number is bigger src or dest
					largeNode = fruits.getEdge().getSrc();
					smallNode = fruits.getEdge().getDest();
				}else {
					largeNode = fruits.getEdge().getDest();
					smallNode = fruits.getEdge().getSrc();
				}
				if (fruits.getType() == 1) { //if the direction is from small node to larger (by key) add large
					this.gameService.addRobot(largeNode);
				}else {						//if the direction is from large node to small (by key) add small
					this.gameService.addRobot(smallNode);
				}
			}
			//if i need to add more robots randomly (more robots then fruits)
			for (int i = 0; i < numRobot - numFruits; i++) {  ///////////////// only if i want, add one robot in each corner (by i%2 add to size of nodes)
				this.gameService.addRobot(i);
			}
		}else { // add value to highest value of fruit
			for (int i = 0; i < numRobot; i++) {
				double maxValue = maxValueNotAdded();
				int src = -0;
				for (Fruit fruits : this.getAllFruits()) { //checks which fruit thats wasnt used has the max value.
					if (fruits.getValue() == maxValue && fruits.getAdded() == -1) {
						fruits.setAdded(1);
						src = fruits.getEdge().getSrc();   //can add to src or dest, i chose src (will be quicker if i check what type and whos bigger..)
					}
				}
				this.gameService.addRobot(src);   
			}
		}
	}
	//this methos helps to discover whats the max value fruit which wasn't calculated already
	public double maxValueNotAdded() {
		double maxValue = -1;
		for (Fruit fruits : this.getAllFruits()) {
			if (fruits.getValue() > maxValue && fruits.getAdded() == -1) {
				maxValue = fruits.getValue();
			}
		}
		return maxValueNotAdded();
	}
	//returns collection of fruit (helps for use of for each).
	public Collection<Fruit> getAllFruits(){
		return this.fruitMap.values();
	}
	//adds robots to map
	public void addRobots(List<String> robots) {
		for (String robot : robots) {
			Robot r = new Robot(robot);
			robotMap.put(r.getId(), r);
		}
	}
	//adds fruit to map
	public void addFruit(List<String> fruits) {

		for (String fruit : fruits ) {
			Fruit f = new Fruit(fruit);
			fruitMap.put(f.getLocation(), f);
		}
	}
	//which edge does the fruit belong to
	public void whichEdge(Fruit f) {
		double edgeLength;
		double srcToFruit;
		double destToFruit;
		Point3D pFruit = new Point3D(f.getLocation());

		for (node_data node : this.g.getV()) {
			for (edge_data edge : this.g.getE(node.getKey())) {
				Point3D pSrc = new Point3D(this.g.getNode(edge.getSrc()).getLocation());
				Point3D pDest = new Point3D(this.g.getNode(edge.getDest()).getLocation());
				edgeLength = pSrc.distance2D(pDest);
				srcToFruit = pSrc.distance2D(pFruit);
				destToFruit = pDest.distance2D(pFruit);
				if (edgeLength - (srcToFruit + destToFruit) <= Point3D.EPS) {
					f.setEdge(edge);
					return;
				}
			}
		}
	}

}
