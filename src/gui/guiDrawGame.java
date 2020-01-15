package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.*;
import gameClient.Fruit;
import gameClient.GameAlgo;
import gameClient.Robot;
import oop_dataStructure.OOP_DGraph;
import utils.Point3D;

public class guiDrawGame extends JFrame implements ActionListener, MouseListener, Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID =1892189218921892L;
	private static int WIDTH = 1400;
	private static int HEIGHT = 900;
	private guiGraphPoint graphPoint; 
	private guiFruitPoint fruitPoint; 
	private guiRobotPoint robotPoint;
	private GameAlgo gameAlgo;
	private boolean automatic;
	private double xMax, yMax, xMin,yMin;
	

	public guiDrawGame() {
		this.gameAlgo = new GameAlgo();
		this.graphPoint = new guiGraphPoint();
		this.fruitPoint = new guiFruitPoint();
		this.robotPoint = new guiRobotPoint();
		this.xMax = -99999;
		this.yMax = -99999;
		this.xMin = 99999;
		this.yMin = 99999;

	}
	public guiDrawGame(GameAlgo ga, boolean flag) {
		this();
		this.automatic = flag;
		this.gameAlgo = ga;
		initGUI(WIDTH, HEIGHT);
		this.setVisible(true);
	}
	public guiDrawGame(int num) {
		this();
		this.gameAlgo = new GameAlgo(num);
		initGUI(WIDTH, HEIGHT);
		this.setVisible(true);
	}
	public guiDrawGame(graph gg) {
		this();
		this.gameAlgo.setGraph(gg);
		initGUI(WIDTH, HEIGHT);
		this.setVisible(true);
	}

	/**
	 * Initiate the bounds of the frame by a given width and height and adding menus
	 */
	private void initGUI(int WIDTHT, int HEIGHT) 
	{
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0,0,WIDTH,HEIGHT);
		this.setTitle("Dovies Workshop Welcome"); 

		MenuBar menuBar = new MenuBar(); //menu bar initaite
		Menu menu = new Menu("Menu");
		menuBar.add(menu);
		this.setMenuBar(menuBar);

		MenuItem op_addNode = new MenuItem("play");
		op_addNode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				play();
			}
		});
		menu.add(op_addNode);
		this.setMenuBar(menuBar);
		this.setVisible(true);
		this.addMouseListener(this);
		if (!automatic) {
			this.addMouseListener(this);
		}
	}
	public void paint(Graphics g) {
		BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();

		g2d.setBackground(new Color(240, 240, 240));
		g2d.clearRect(0, 0, WIDTH, HEIGHT);

		paintGraph(g2d);
		setTime(g2d);
		paintFruit(g2d);
		paintRobot(g2d);

		Graphics2D g2dComponent = (Graphics2D) g;
		g2dComponent.drawImage(bufferedImage, null, 0, 0);

	}

	//all the functions of the menu arer written from here until the cases method
	public void play() {
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String action = e.getActionCommand();
		switch (action) {
		case "play" : play();
		}
	}
	private void setTime(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("David", Font.ITALIC, 20));
		g.drawString("Time: " + this.gameAlgo.getGameService().timeToEnd() / 1000, WIDTH - 200, 70);
	}
	public void paintGraph(Graphics g)
	{
		Scale(this.gameAlgo.getGraph());
		super.paint(g);
		for (node_data node : this.gameAlgo.getGraph().getV()){
			int x = (int)this.graphPoint.getPoint(node.getKey()).x();
			int y = (int)this.graphPoint.getPoint(node.getKey()).y();
			g.setColor(Color.BLUE);
			g.fillOval(x - 3, y - 3, 8, 8);
			g.setColor(Color.black);
			g.setFont(new Font("David",Font.ITALIC, 18));
			g.drawString(String.valueOf(node.getKey()), x+3, y + 3);
			if ((this.gameAlgo.getGraph().getE(node.getKey()) != null)) {
				for (edge_data edge :this.gameAlgo.getGraph().getE(node.getKey())) {
					node_data destNode = this.gameAlgo.getGraph().getNode(edge.getDest());
					g.setColor(Color.black);
					int xDest = (int)this.graphPoint.getPoint(destNode.getKey()).x();     
					int yDest = (int)this.graphPoint.getPoint(destNode.getKey()).y();
					g.drawLine(x, y, xDest, yDest);
					g.setColor(Color.orange);
					//to draw the way of the edge iv simply divided 4 times in a row the middle location of the edge.
					int directionX = (((((((x + xDest) /2) + xDest) /2)+ xDest) /2) + xDest)/2  ;
					int directionY = (((((((y + yDest) /2) + yDest) /2)+ yDest) /2) + yDest)/2  ;
					g.fillOval(directionX, directionY, 5, 5);
				}
			}
		}
	}
	private void Scale(graph graph2) {
		Point3D point;

		for (node_data node : graph2.getV()) {
			if (node.getLocation().x() >= xMax) {
				xMax = node.getLocation().x();
			}else if(node.getLocation().x() <= xMin) {
				xMin = node.getLocation().x();
			}
			if (node.getLocation().y() >= yMax) {
				yMax = node.getLocation().y();
			}else if(node.getLocation().y() <= yMin) {
				yMin = node.getLocation().y();
			}
		}
		double x,y;
		for (node_data node : graph2.getV()) {
			x = node.getLocation().x();
			y = node.getLocation().y();
			x = scale(x, xMin, xMax, 100,WIDTH-200);
			y = scale(y, yMin, yMax, 100,HEIGHT-100);
			point = new Point3D(x,y,0);
			this.graphPoint.setPoint(node.getKey(),point);
		}
	}
	/**
	 * @param data to be scaled
	 * @param r_min the minimum of the range of your data
	 * @param r_max the maximum of the range of your data
	 * @param t_min the minimum of the range of your desired target scaling
	 * @param t_max the maximum of the range of your desired target scaling
	 * @return the value after the scale. //by yael landua
	 */
	private double scale(double data, double r_min, double r_max, 
			double t_min, double t_max)
	{
		double res = ((data - r_min) / (r_max-r_min)) * (t_max - t_min) + t_min;
		return res;
	}
	//paint the fruits on their edges
	public void paintFruit(Graphics g) {
		double x, y;
		for (Fruit fruit : this.gameAlgo.getAllFruits()) {
			x = fruit.getLocation().x();
			y = fruit.getLocation().y();
			x = scale(x, xMin, xMax, 100,WIDTH-200);
			y = scale(y, yMin, yMax, 100,HEIGHT-100);
			if (fruit.getType() == 1) {
				g.setColor(Color.RED);
			}else {
				g.setColor(Color.MAGENTA);
			}
			g.fillOval((int)x - 2 , (int)y - 4 , 12, 12);
			g.setColor(Color.blue);
			g.setFont(new Font("David",Font.ITALIC, 18));
			g.drawString(fruit.getValue() + "", (int)x + 2, (int)y + 2);
			this.fruitPoint.setPoint(new Point3D(fruit.getLocation()), new Point3D(x,y)); //add to hashmap of points for future use
			
		}			
	}
	//paint robot
	private void paintRobot(Graphics g) {
		double x, y;
		for (Robot robot : this.gameAlgo.getAllRobots()) {
			x = robot.getLocation().x();
			y = robot.getLocation().y();
			x = scale(x, xMin, xMax, 100,WIDTH-200);
			y = scale(y, yMin, yMax, 100,HEIGHT-100);
			g.setColor(Color.green);
			g.fillOval((int)x - 2 , (int)y - 4 , 15, 15);
			this.robotPoint.setPoint(robot.getId(),new Point3D(x,y));
			}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (!gameAlgo.getGameService().isRunning()) {
			return;
		}
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		int xPress = e.getX();
		int yPress = e.getY();
		int robotID = this.gameAlgo.robotPressed(xPress, yPress, robotPoint);
		if (robotID != -1) {
			try {
			String dest = JOptionPane.showInputDialog("Enter destination for robot number: " + robotID);
			int destination = Integer.parseInt(dest);
			gameAlgo.getGameService().chooseNextEdge(robotID, destination);
			}catch(Exception ex) {
				System.out.println("pressed wrong place");
			}
		}
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	public static void main(String[] args) {

	}
	@Override
	public void run() {
//		while(this.gameAlgo.getGameService().isRunning()) {
//			repaint();
//			
//		}
		
	}
}
