package br.com.magna.snake_game.entities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import br.com.magna.snake_game.services.LogHandler;

public class Control extends JFrame implements KeyListener, Runnable {

	private static final long serialVersionUID = 1L;

	private static Map<Integer, Method> controls = new HashMap<Integer, Method>();
	private Game gameInstance = null;

	public Control() {
		gameInstance = Game.getInstance();
	}

	public static Method lookup(int key) {
		return controls.get(key);
	}

	private void loadFrameConfiguration() {
		setSize(100, 100);
		addKeyListener(this);
		setFocusable(true);
		setVisible(true);
	}

	private void move(KeyEvent e) {
		Method movingMethod = Control.lookup(e.getKeyCode());
		gameInstance.setMovingMethod(movingMethod);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		move(e);
	}

	@Override
	public void run() {
		new Thread(gameInstance).start();
		loadFrameConfiguration();

		try {
			controls.put(KeyEvent.VK_UP, Control.class.getDeclaredMethod("moveUp", Table.class));
			controls.put(KeyEvent.VK_DOWN, Control.class.getDeclaredMethod("moveDown", Table.class));
			controls.put(KeyEvent.VK_RIGHT, Control.class.getDeclaredMethod("moveRight", Table.class));
			controls.put(KeyEvent.VK_LEFT, Control.class.getDeclaredMethod("moveLeft", Table.class));
		} catch (NoSuchMethodException e) {
			LogHandler.error("Couldn't find method " + e.getMessage());
		} catch (NullPointerException e2) {

		}
	}

	public void moveRight(Table table) {
		char[][] matrix = table.getMatrix();
		Snake snake = table.getSnake();
		int headRow = snake.getHeadRow();
		int headColumn = snake.getHeadColumn();
		int tailRow = snake.getTailRow();
		int tailColumn = snake.getTailColumn();
		int snakeSize = snake.getSnakeSize();

		if (headColumn >= matrix[headRow].length || matrix[headRow][headColumn + 1] == '#') {
			System.exit(0);
		}
		
		matrix[headRow][headColumn + 1] = '#';
		matrix[headRow][headColumn] = ' ';
 		snake.updateSnakeHead(headRow, headColumn + 1);
		snake.updateSnakeTail(headRow, headColumn);
	}

	public void moveLeft(Table table) {
		char[][] matrix = table.getMatrix();
		Snake snake = table.getSnake();
		int headRow = snake.getHeadRow();
		int headColumn = snake.getHeadColumn();
		int tailRow = snake.getTailRow();
		int tailColumn = snake.getTailColumn();
		int snakeSize = snake.getSnakeSize();

		if (headColumn <= 0 || matrix[headRow][headColumn - 1] == '#') {
			System.exit(0);
		}
		
		matrix[headRow][headColumn - 1] = '#';
		matrix[headRow][headColumn] = ' ';
 		snake.updateSnakeHead(headRow, headColumn - 1);
		snake.updateSnakeTail(headRow, headColumn);
	}

	public void moveUp(Table table) {
		char[][] matrix = table.getMatrix();
		Snake snake = table.getSnake();
		int headRow = snake.getHeadRow();
		int headColumn = snake.getHeadColumn();
		int tailRow = snake.getTailRow();
		int tailColumn = snake.getTailColumn();
		int snakeSize = snake.getSnakeSize();
		
		if (headRow <= 0 || matrix[headRow - 1][headColumn] == '#') {
			System.exit(0);
		}

		matrix[headRow - 1][headColumn] = '#';
		matrix[headRow][headColumn] = ' ';
 		snake.updateSnakeHead(headRow - 1, headColumn);
		snake.updateSnakeTail(headRow, headColumn);
	}

	public void moveDown(Table table) {
		char[][] matrix = table.getMatrix();
		Snake snake = table.getSnake();
		int headRow = snake.getHeadRow();
		int headColumn = snake.getHeadColumn();
		int tailRow = snake.getTailRow();
		int tailColumn = snake.getTailColumn();
		int snakeSize = snake.getSnakeSize();

		if (headRow + 1 >= matrix[headRow].length || matrix[headRow + 1][headColumn] == '#') {
			System.exit(0);
		}

		matrix[headRow + 1][headColumn] = '#';
		matrix[headRow][headColumn] = ' ';
 		snake.updateSnakeHead(headRow + 1, headColumn);
		snake.updateSnakeTail(headRow, headColumn);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
