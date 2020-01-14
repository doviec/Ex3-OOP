package gui;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.*;
import oop_dataStructure.OOP_DGraph;
import utils.Point3D;

public class guiGraph extends JFrame implements ActionListener, MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID =28022802280228021L;
	private graph graph;
	private Graph_Algo algo;
	private int MC;
	private int nodeCounter;
	private guiPoint guiPoint = new guiPoint();


	public guiGraph() {
		this.graph = new DGraph();
		algo = new Graph_Algo();
		algo.init(graph);
		this.MC = graph.getMC();
		initGUI(1100, 1000);
		nodeCounter = graph.nodeSize();
	}
	public guiGraph(graph gra) {
		this.MC = gra.getMC();
		this.graph = gra;
		algo = new Graph_Algo();
		algo.init(gra);
		initGUI(1100, 1000);
		this.setVisible(true);
		nodeCounter = gra.nodeSize();
	}
	public guiGraph(graph gra, int width, int height)
	{
		this.MC = gra.getMC();
		this.graph = gra;
		algo.init(gra);
		initGUI(1100, 1000);
		initGUI(width, height);
		nodeCounter = gra.nodeSize();
		this.setVisible(true);
	}
	/**
	 * Initiate the bounds of the frame by a given width and height and adding menus
	 */
	private void initGUI(int width, int heigt) 
	{
		this.setSize(width, heigt);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0,0,width,heigt);
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
		this.addMouseListener(this);
		//incase of changes of MC the graph will be repainted
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					synchronized (this) {
						if (graph.getMC() != MC) {
							repaint();
							MC = graph.getMC();
						}
					}
				}
			}
		});
		thread.start();
		this.setMenuBar(menuBar);
		this.setVisible(true);
		this.addMouseListener(this);
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
	public void paint(Graphics g)
	{
		Scale(this.graph);
		super.paint(g);
		for (node_data node : graph.getV()) {
			int x = (int)this.guiPoint.getPoint(node.getKey()).x();
			int y = (int)this.guiPoint.getPoint(node.getKey()).y();
			g.setColor(Color.BLUE);
			g.fillOval(x - 3, y - 3, 8, 8);
			g.setColor(Color.black);
			g.setFont(new Font("David",Font.ITALIC, 18));
			g.drawString(String.valueOf(node.getKey()), x+3, y + 3);
			if ((graph.getE(node.getKey()) != null)) {
				for (edge_data edge : graph.getE(node.getKey())) {
					node_data destNode = graph.getNode(edge.getDest());
					g.setColor(Color.black);
					int xDest = (int)this.guiPoint.getPoint(destNode.getKey()).x();     
					int yDest = (int)this.guiPoint.getPoint(destNode.getKey()).y();
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
		double xMax = -99999;
		double yMax = -99999;
		double xMin = 99999;
		double yMin = 99999;
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
			x = scale(x, xMin, xMax, 100,900);
			y = scale(y, yMin, yMax, 100,800);
			point = new Point3D(x,y,0);
			this.guiPoint.setPoint(node.getKey(),point);
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
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("mouseClicked");
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("mouseReleased");
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("mouseEntered");
	}
	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("mouseExited");
	}
	public static void main(String[] args) {

	}
}
