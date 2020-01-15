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
public class guiFruitPoint {

	private Map<Point3D,Point3D> fruitPoint; 


	public guiFruitPoint() {
		fruitPoint = new HashMap<Point3D, Point3D>();
	}
//	public guiFruitPoint(Fruit f) {
//		fruitPoint.put(new Point3D(f.getLocation()), f.getLocation());
//	}
	public void setPoint(Point3D key,Point3D p) {
		fruitPoint.put(key, p);
	}
	public Point3D getPoint(Point3D key) {
		return this.fruitPoint.get(key);
	}
}