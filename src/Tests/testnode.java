package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dataStructure.Node;
import dataStructure.node_data;
import utils.Point3D;

class testnode {

	@Test
	void testToString() {
	
		node_data node1 = new Node(0,new Point3D(4,6),12,"",0);
		node_data node2 = new Node(1,new Point3D(2,45),6,"",1);
		assertEquals("key: 0 location: 4.0,6.0,0.0 weight: 12.0 info:  tag: 0", node1.toString());
		assertEquals("key: 1 location: 2.0,45.0,0.0 weight: 6.0 info:  tag: 1", node2.toString());
	}
	@Test
	void testGettersSetters() {
		node_data node1 = new Node(0,new Point3D(5,0,0),6,"",0);

		assertEquals(0,node1.getKey() );
		assertEquals(new Point3D(5,0,0),node1.getLocation());
		assertEquals(6,node1.getWeight());
		assertEquals("",node1.getInfo());
		assertEquals(0,node1.getTag());

		node_data node2 = new Node(1,new Point3D(2,45),6,"test",1);

		assertEquals(1,node2.getKey() );
		assertEquals(new Point3D(2,45,0),node2.getLocation());
		assertEquals(6,node2.getWeight());
		assertEquals("test",node2.getInfo());
		assertEquals(1,node2.getTag());

		node2.setInfo("set");
		node2.setLocation(new Point3D (4,6));
		node2.setTag(0);
		node2.setWeight(10);

		assertEquals(new Point3D(4,6),node2.getLocation());
		assertEquals(10,node2.getWeight());
		assertEquals("set",node2.getInfo());
		assertEquals(0,node2.getTag());
	}
}


