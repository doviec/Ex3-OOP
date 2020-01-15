package gui;

import java.util.HashMap;
import java.util.Map;

import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;
/**
 * this class represnets the new points that will be used to fit the frame painting
 * key is node id, point will be the new point after scaling it.
 * @author dovie
 *
 */
public class guiGraphPoint {

	private Map<Integer,Point3D> graphPoint; 
	

	public guiGraphPoint() {
		graphPoint = new HashMap<Integer, Point3D>();
		
	}
	public guiGraphPoint(graph graph) {
		for (node_data node : graph.getV()) {
			graphPoint.put(node.getKey(), node.getLocation());
		}
	}
	public void setPoint(int key,Point3D p) {
		graphPoint.put(key, p);
	}
	public Point3D getPoint(int key) {
		return this.graphPoint.get(key);
	}
}
