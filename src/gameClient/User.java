package gameClient;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;
import Server.game_service;
import gui.guiDrawGame;

public class User extends Thread{

	private guiDrawGame gui;
	private GameAlgo gameAlgo;
	private static int scenario = 0;
	private static boolean automatic = true;

	public User() {
		this.gameAlgo = new GameAlgo(scenario);
		this.gui = new guiDrawGame(gameAlgo, automatic);
		if (automatic) {
			///here will be the automatic class;
		}
	}
	@Override
	public void run() {
		try {
			game_service gs = this.gameAlgo.getGameService();
			gs.startGame();
			while (gs.isRunning()) {
				Thread.sleep(600);
				gs.move();
				this.gameAlgo.update();
				gui.repaint();				
			}
		}
		catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		double grade = getGrade();
		int moves = getMoves();
		JOptionPane.showMessageDialog(gui, "Game Over\nYour Score: " + grade + " \nmoves: " + moves);
		gui.setVisible(false);
		System .exit(1); //exit(system call) terminates the currently running Java
		//virtual machine by initiating its shutdown sequence. The argument serves 
		//as a status code. By convention, a nonzero status code indicates abnormal
		//termination. On Unix and Linux systems, 0 for successful executions and 1 
		//or higher for failed executions
	}
	private int getMoves() {
		int moves = -1;
		try {
			JSONObject serverObj = new JSONObject(this.gameAlgo.getGameService().toString()).getJSONObject("GameServer");
			moves = serverObj.getInt("moves");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return moves;
	}
	private double getGrade() {
		int grade = -1;
		try {
			JSONObject serverObj = new JSONObject(this.gameAlgo.getGameService().toString()).getJSONObject("GameServer");
			grade = serverObj.getInt("grade");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return grade;
	}
	private static void init() {
		JFrame frame = new JFrame();
		frame.setBounds(300, 50, 400, 400);
		try {
			String[] modes = {"Manuel", "Automatic"};
			String level = JOptionPane.showInputDialog(frame,"Enter a Level you want to PLAY 0-23");
			int mode = JOptionPane.showOptionDialog(frame, "Choose Option", "The Maze of Waze",
					JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, null, modes, modes[1]);
			scenario = Integer.parseInt(level);
			if (scenario < 0 || scenario > 23) {
				JOptionPane.showMessageDialog(frame, "Please choose level between 0 - 23");
				throw new RuntimeException("Wrong Input");
			}
			if (mode == 0) {
				automatic = false;
			}else {
				automatic = true;
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(frame, "Playing levle 0 because of your valid input");
			scenario = 0;
			automatic = true;
		}
	}
	public static void main(String[] args) {
		init();
		User user = new User();
		user.start();
	}
}
