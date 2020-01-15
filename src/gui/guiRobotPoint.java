package gui;

import java.util.HashMap;
import java.util.Map;

import utils.Point3D;
/**
 * this class represnets the new points that will be used to fit the frame painting
 * key is node id, point will be the new point after scaling it.
 * @author dovie
 *
 */
public class guiRobotPoint {

	private Map<Integer,Point3D> robotPoint; 


	public guiRobotPoint() {
		robotPoint = new HashMap<Integer, Point3D>();
	}
	public void setPoint(int key,Point3D p) {
		robotPoint.put(key, p);
	}
	public Point3D getPoint(int key) {
		return this.robotPoint.get(key);
	}
}
