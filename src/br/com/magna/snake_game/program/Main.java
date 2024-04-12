package br.com.magna.snake_game.program;

import br.com.magna.snake_game.entities.Control;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		new Thread(new Control()).start();
	}
}
