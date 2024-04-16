package br.com.magna.snake_game.entities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import br.com.magna.snake_game.services.LogHandler;
import br.com.magna.snake_game.services.TerminalHandler;

public class Game implements Runnable {

	private static Game instance = new Game();

	private boolean isGameWon = false;
	private Table table = new Table();
	private int pointsToWin = 0;
	private int points = 0;
	private Method action = null;

	private Game() {

	}

	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
			return instance;
		}

		return instance;
	}

	public void setAction(Method action) {
		if (action == null) {
			return;
		}
		this.action = action;
	}

	@Override
	public void run() {
		if (!menuLoop()) {
			update();
			gameLoop();
		}
	}

	private boolean menuLoop() {
		boolean isOptionPicked = false;
		synchronized (instance) {
			while (!isOptionPicked) {
				try {
					if (action != null) {
						action.invoke(new Control(), table);

						if (action.getName().equals("moveRight")) {
							isOptionPicked = true;
							break;
						}
					}

					table.printMatrix();
					instance.wait();
					TerminalHandler.clear();
				} catch (IllegalAccessException | InvocationTargetException reflectionException) {
					LogHandler.error("Error accessing method from Control:" + reflectionException.getMessage());
				} catch (InterruptedException threadException) {
					LogHandler.error("Error handling thread: " + threadException.getMessage());
				} catch (NullPointerException actionIsNullException) {
				}
			}

			return false;
		}
	}

	private void update() {
		table.loadMatrix(TerminalHandler.NAME);
		table.loadRandomPoints(table.getMatrix());
		pointsToWin = table.getTotalMapPoints();
	}
	
	private void gameLoop() {
		while (!isGameWon) {
			try {
				checkWin();
				moveSnake();
				table.printMatrix();
				waitThread();
				TerminalHandler.clear();
			} catch (NullPointerException actionIsNullException) {

			}
		}

		TerminalHandler.print(TerminalHandler.YOU_WIN);
		System.exit(0);
	}

	private void increasePoints() {
		points += 1;
		Snake snake = table.getSnake();
		resize(snake);
	}

	private void waitThread() {
		try {
			switch (action.getName()) {
			case "moveUp":
			case "moveDown":
				Thread.sleep(150);
				return;
			case "moveRight":
			case "moveLeft":
				Thread.sleep(100);
				return;
			}
		} catch (InterruptedException threadException) {
			LogHandler.error("Error handling thread: " + threadException.getMessage());
		}
	}

	private boolean isCollision() {
		return hitsWall() || hitsItself();
	}

	private boolean hitsWall() {
		char[][] matrix = table.getMatrix();
		Snake snake = table.getSnake();
		char wallChar = table.getWallChar();
		int headRow = snake.getHeadRow();
		int headColumn = snake.getHeadColumn();
		
		switch (action.getName()) {
		case "moveUp":
			return headRow <= matrix.length - matrix.length || matrix[headRow - 1][headColumn] == wallChar;
		case "moveDown":
			return headRow + 1 >= matrix[headRow].length || matrix[headRow + 1][headColumn] == wallChar;
		case "moveRight":
			return headColumn >= matrix[headRow].length || matrix[headRow][headColumn + 1] == wallChar;
		case "moveLeft":
			return headColumn <= matrix.length - matrix.length  || matrix[headRow][headColumn - 1] == wallChar;
		}

		return false;
	}

	private boolean hitsItself() {
		char[][] matrix = table.getMatrix();
		Snake snake = table.getSnake();
		char snakeChar = table.getSnakeChar();
		int headRow = snake.getHeadRow();
		int headColumn = snake.getHeadColumn();
		
		switch (action.getName()) {
		case "moveUp":
			return matrix[headRow - 1][headColumn] == snakeChar;
		case "moveDown":
			return matrix[headRow + 1][headColumn] == snakeChar;
		case "moveRight":
			return matrix[headRow][headColumn + 1] == snakeChar;
		case "moveLeft":
			return matrix[headRow][headColumn - 1] == snakeChar;
		}

		return false;
	}

	private void resize(Snake snake) {
		int oldSnakeSize = snake.getOldSnakeSize();
		snake.setNewSnakeSize(oldSnakeSize + points);
	}

	public boolean isFruitPicked() {
		char[][] matrix = table.getMatrix();
		Snake snake = table.getSnake();
		char pointChar = table.getPointChar();
		int headRow = snake.getHeadRow();
		int headColumn = snake.getHeadColumn();

		switch (action.getName()) {
		case "moveUp":
			return matrix[headRow - 1][headColumn] == pointChar;
		case "moveDown":
			return matrix[headRow + 1][headColumn] == pointChar;
		case "moveRight":
			return matrix[headRow][headColumn + 1] == pointChar;
		case "moveLeft":
			return matrix[headRow][headColumn - 1] == pointChar;
		}
		return false;
	}

	private void checkWin() {
		if (points == pointsToWin) {
			isGameWon = true;
		}
	}

	private void moveSnake() {
		try {
			if (isCollision()) {
				TerminalHandler.print(TerminalHandler.GAME_OVER);
				System.exit(0);
			}

			if (isFruitPicked()) {
				increasePoints();
			}
			action.invoke(new Control(), table);
		} catch (IllegalAccessException | InvocationTargetException reflectionException) {
			LogHandler.error("Error accessing method from Control:" + reflectionException.getMessage());
		}
	}
}
