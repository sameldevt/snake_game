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
			
		if(matrix[headRow][headColumn + 1] == '@') {
			System.exit(0);
		}
		
		if (snakeSize < 20) {
			matrix[headRow][headColumn + 1] = '@';
			snake.setSnakeSize(snakeSize + 1);
			snake.updateSnakeHead(headRow, headColumn + 1);
			return;
		}

		matrix[headRow][headColumn + 1] = '@';
		snake.updateSnakeHead(headRow, headColumn + 1);

		if (matrix[tailRow + 1][tailColumn] == '@') {
			matrix[tailRow + 1][tailColumn] = ' ';
			snake.updateSnakeTail(tailRow + 1, tailColumn);
			return;
		}
		if (matrix[tailRow - 1][tailColumn] == '@') {
			matrix[tailRow - 1][tailColumn] = ' ';
			snake.updateSnakeTail(tailRow - 1, tailColumn);
			return;
		}
		if (matrix[tailRow][tailColumn + 1] == '@') {
			matrix[tailRow][tailColumn + 1] = ' ';
			snake.updateSnakeTail(tailRow, tailColumn + 1);
			return;
		}
		if (matrix[tailRow][tailColumn - 1] == '@') {
			matrix[tailRow][tailColumn - 1] = ' ';
			snake.updateSnakeTail(tailRow, tailColumn - 1);
			return;
		}

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
		
		if(matrix[headRow][headColumn - 1] == '@') {
			System.exit(0);
		}

		if (snakeSize < 20) {
			matrix[headRow][headColumn - 1] = '@';
			snake.setSnakeSize(snakeSize + 1);
			snake.updateSnakeHead(headRow, headColumn - 1);
			return;
		}

		matrix[headRow][headColumn - 1] = '@';
		snake.updateSnakeHead(headRow, headColumn - 1);

		if (matrix[tailRow + 1][tailColumn] == '@') {
			matrix[tailRow + 1][tailColumn] = ' ';
			snake.updateSnakeTail(tailRow + 1, tailColumn);
			return;
		}
		if (matrix[tailRow - 1][tailColumn] == '@') {
			matrix[tailRow - 1][tailColumn] = ' ';
			snake.updateSnakeTail(tailRow - 1, tailColumn);
			return;
		}
		if (matrix[tailRow][tailColumn + 1] == '@') {
			matrix[tailRow][tailColumn + 1] = ' ';
			snake.updateSnakeTail(tailRow, tailColumn + 1);
			return;
		}
		if (matrix[tailRow][tailColumn - 1] == '@') {
			matrix[tailRow][tailColumn - 1] = ' ';
			snake.updateSnakeTail(tailRow, tailColumn - 1);
			return;
		}

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
		
		if(matrix[headRow - 1][headColumn] == '@') {
			System.exit(0);
		}

		if (snakeSize < 20) {
			matrix[headRow - 1][headColumn] = '@';
			snake.setSnakeSize(snakeSize + 1);
			snake.updateSnakeHead(headRow - 1, headColumn);
			return;
		}

		matrix[headRow - 1][headColumn] = '@';
		snake.updateSnakeHead(headRow - 1, headColumn);

		if (matrix[tailRow + 1][tailColumn] == '@') {
			matrix[tailRow + 1][tailColumn] = ' ';
			snake.updateSnakeTail(tailRow + 1, tailColumn);
			return;
		}
		if (matrix[tailRow - 1][tailColumn] == '@') {
			matrix[tailRow - 1][tailColumn] = ' ';
			snake.updateSnakeTail(tailRow - 1, tailColumn);
			return;
		}
		if (matrix[tailRow][tailColumn + 1] == '@') {
			matrix[tailRow][tailColumn + 1] = ' ';
			snake.updateSnakeTail(tailRow, tailColumn + 1);
			return;
		}
		if (matrix[tailRow][tailColumn - 1] == '@') {
			matrix[tailRow][tailColumn - 1] = ' ';
			snake.updateSnakeTail(tailRow, tailColumn - 1);
			return;
		}

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
		
		if(matrix[headRow + 1][headColumn] == '@') {
			System.exit(0);
		}

		if (snakeSize < 20) {
			matrix[headRow + 1][headColumn] = '@';
			snake.setSnakeSize(snakeSize + 1);
			snake.updateSnakeHead(headRow + 1, headColumn);
			return;
		}

		matrix[headRow + 1][headColumn] = '@';
		snake.updateSnakeHead(headRow + 1, headColumn);

		if (matrix[tailRow + 1][tailColumn] == '@') {
			matrix[tailRow + 1][tailColumn] = ' ';
			snake.updateSnakeTail(tailRow + 1, tailColumn);
			return;
		}
		if (matrix[tailRow - 1][tailColumn] == '@') {
			matrix[tailRow - 1][tailColumn] = ' ';
			snake.updateSnakeTail(tailRow - 1, tailColumn);
			return;
		}
		if (matrix[tailRow][tailColumn + 1] == '@') {
			matrix[tailRow][tailColumn + 1] = ' ';
			snake.updateSnakeTail(tailRow, tailColumn + 1);
			return;
		}
		if (matrix[tailRow][tailColumn - 1] == '@') {
			matrix[tailRow][tailColumn - 1] = ' ';
			snake.updateSnakeTail(tailRow, tailColumn - 1);
			return;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
