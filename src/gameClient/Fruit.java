package gameClient;

import org.json.JSONException;
import org.json.JSONObject;

import dataStructure.edge_data;
import utils.Point3D;

public class Fruit {

	private double value;
	private int type;
	private Point3D location;
	private Point3D guiLocation;
	private edge_data edge;
	private int added; //-1 will represent no and 1 will be for yes

	public Fruit() {

	}
	public Fruit(String jsonString) {
		try {
			JSONObject fruit = new JSONObject(jsonString);
			fruit = fruit.getJSONObject("Fruit");
			double value = fruit.getDouble("value");
			int type = fruit.getInt("type");
			Point3D point = new Point3D(fruit.getString("pos"));
			this.value = value;
			this.type = type;
			this.location = point;
			this.added = -1;
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public int getType() {
		return this.type;
	}
	public void setType(int t) {
		this.type = t;
	}
	public double getValue() {
		return this.value;
	}
	public void setValue(double v) {
		this.value = v;
	}
	public Point3D getLocation() {
		return new Point3D(this.location);
	}
	public void setLocation(Point3D p) {
		this.location = new Point3D(p);
	}
	public Point3D getguiLocation() {
		return new Point3D(this.guiLocation);
	}
	public void setguiLocation(Point3D p) {
		this.guiLocation = new Point3D(p);
	}
	public void setEdge(edge_data edge) {
		this.edge = edge;
	}
	public edge_data getEdge() {
		return this.edge;
	}
	public void setAdded(int a) {
		this.added = a;
	}
	public int getAdded() {
		return this.added;
	}
}
