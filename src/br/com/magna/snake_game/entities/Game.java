package br.com.magna.snake_game.entities;

public class Game {
	
	public static void start() {
		new Thread(new ControlFrame()).start();
		new Thread(new Table()).start();
	}
}
