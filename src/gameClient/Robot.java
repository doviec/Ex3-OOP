package gameClient;

import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

public class Robot {

	private int id;
	private double value;
	private int src;
	private int dest;
	private double speed;
	private Point3D point;

	public Robot() {

	}
	public Robot(String jsonString) {
		try {
			JSONObject robot = new JSONObject(jsonString);
			robot = robot.getJSONObject("Robot");
			int id = robot.getInt("id");
			double value = robot.getDouble("value");
			int src = robot.getInt("src");
			int dest = robot.getInt("dest");
			double speed = robot.getDouble("speed");
		
			this.point = new Point3D(robot.getString("pos"));
			this.id = id;
			this.value = value;
			this.src = src;
			this.dest = dest;
			this.speed = speed;

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getSrc() {
		return src;
	}
	public void setSrc(int src) {
		this.src = src;
	}
	public int getDest() {
		return dest;
	}
	public void setDest(int dest) {
		this.dest = dest;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public Point3D getPoint() {
		return new Point3D(point);
	}
	public void setPoint(Point3D point) {
		this.point = new Point3D(point);
	}

}
