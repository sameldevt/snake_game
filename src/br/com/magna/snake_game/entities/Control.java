package br.com.magna.snake_game.entities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import br.com.magna.snake_game.services.LogHandler;
import br.com.magna.snake_game.services.TerminalHandler;

public class Control extends JFrame implements KeyListener, Runnable {

	private static final long serialVersionUID = 1L;

	private static Map<Integer, Method> controls = new HashMap<Integer, Method>();
	private Game gameInstance = null;
	private boolean isMenu = true;

	public Control() {
		gameInstance = Game.getInstance();
	}

	public static Method lookup(int key) {
		return controls.get(key);
	}

	private void loadFrameConfiguration() {
		addKeyListener(this);
		setFocusable(true);
		setVisible(false);
		setAlwaysOnTop(true);
		setUndecorated(true);
		setOpacity(0.01f);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}

	private void action(KeyEvent e) {
		if (isMenu) {
			synchronized (gameInstance) {
				Method action = Control.lookup(e.getKeyCode());
				gameInstance.setAction(action);
				gameInstance.notify();
			}
		}

		Method action = Control.lookup(e.getKeyCode());
		gameInstance.setAction(action);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		action(e);
	}

	@Override
	public void run() {
		loadFrameConfiguration();
		loadMenuControls();
		new Thread(gameInstance).start();
	}

	private void loadMenuControls() {
		if (!controls.isEmpty()) {
			controls.clear();
		}

		try {
			controls.put(KeyEvent.VK_SPACE, Control.class.getDeclaredMethod("moveCursor", Table.class));
			controls.put(KeyEvent.VK_ENTER, Control.class.getDeclaredMethod("selectOption", Table.class));
		} catch (NoSuchMethodException e) {
			LogHandler.error("Couldn't find method " + e.getMessage());
		} catch (NullPointerException e2) {

		}
	}

	private void loadGameControls() {
		if (!controls.isEmpty()) {
			controls.clear();
		}

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

	public void moveCursor(Table table) {
		char[][] matrix = table.getMatrix();
		int cursorRow = 18;
		int cursorColumn = 64;

		if (matrix[cursorRow][cursorColumn] == '>') {
			matrix[cursorRow][cursorColumn] = ' ';
			matrix[cursorRow + 1][cursorColumn] = '>';
		} else {
			matrix[cursorRow + 1][cursorColumn] = ' ';
			matrix[cursorRow][cursorColumn] = '>';
		}
	}

	public void selectOption(Table table) {
		char[][] matrix = table.getMatrix();
		int cursorRow = 18;
		int cursorColumn = 64;

		if (matrix[cursorRow][cursorColumn] == '>') {
			loadGameControls();
			Method action = Control.lookup(KeyEvent.VK_RIGHT);
			gameInstance.setAction(action);
			isMenu = false;
		} else if (matrix[cursorRow + 1][cursorColumn] == '>') {
			TerminalHandler.print(TerminalHandler.LATER);
			System.exit(0);
		}
	}

	public void moveRight(Table table) {
		char[][] matrix = table.getMatrix();
		Snake snake = table.getSnake();
		int headRow = snake.getHeadRow();
		int headColumn = snake.getHeadColumn();
		int oldSnakeSize = snake.getOldSnakeSize();
		int newSnakeSize = snake.getNewSnakeSize();

		if (headColumn >= matrix[headRow].length || matrix[headRow][headColumn + 1] == '#') {
			TerminalHandler.print(TerminalHandler.GAME_OVER);
			System.exit(0);
		}

		if (matrix[headRow][headColumn + 1] == 'O') {
			TerminalHandler.print(TerminalHandler.GAME_OVER);
			System.exit(0);
		}

		if (oldSnakeSize < newSnakeSize) {
			matrix[headRow][headColumn + 1] = 'O';
			snake.setOldSnakeSize(oldSnakeSize + 1);
			snake.updateSnakeHead(headRow, headColumn + 1);
			return;
		}

		if (matrix[headRow][headColumn + 1] == '*') {
			int snakePoints = snake.getPoints();
			snake.setPoints(snakePoints + 1);

			if (snake.getPoints() == 7) {
				TerminalHandler.print(TerminalHandler.YOU_WIN);
				System.exit(0);
			}

			snake.setNewSnakeSize(oldSnakeSize + 5);
			matrix[headRow][headColumn + 1] = 'O';
			snake.updateSnakeHead(headRow, headColumn + 1);
			return;
		}

		matrix[headRow][headColumn + 1] = 'O';
		snake.updateSnakeHead(headRow, headColumn + 1);

		checkTail(snake, matrix);

	}

	public void moveLeft(Table table) {
		char[][] matrix = table.getMatrix();
		Snake snake = table.getSnake();
		int headRow = snake.getHeadRow();
		int headColumn = snake.getHeadColumn();
		int oldSnakeSize = snake.getOldSnakeSize();
		int newSnakeSize = snake.getNewSnakeSize();

		if (headColumn <= 0 || matrix[headRow][headColumn - 1] == '#') {
			TerminalHandler.print(TerminalHandler.GAME_OVER);
			System.exit(0);
		}

		if (matrix[headRow][headColumn - 1] == 'O') {
			TerminalHandler.print(TerminalHandler.GAME_OVER);
			System.exit(0);
		}

		if (oldSnakeSize < newSnakeSize) {
			matrix[headRow][headColumn - 1] = 'O';
			snake.setOldSnakeSize(oldSnakeSize + 1);
			snake.updateSnakeHead(headRow, headColumn - 1);
			return;
		}

		if (matrix[headRow][headColumn - 1] == '*') {
			int snakePoints = snake.getPoints();
			snake.setPoints(snakePoints + 1);

			if (snake.getPoints() == 7) {
				TerminalHandler.print(TerminalHandler.YOU_WIN);
				System.exit(0);
			}

			snake.setNewSnakeSize(oldSnakeSize + 5);
			matrix[headRow][headColumn - 1] = 'O';
			snake.updateSnakeHead(headRow, headColumn - 1);
			return;
		}

		matrix[headRow][headColumn - 1] = 'O';
		snake.updateSnakeHead(headRow, headColumn - 1);

		checkTail(snake, matrix);

	}

	public void moveUp(Table table) {
		char[][] matrix = table.getMatrix();
		Snake snake = table.getSnake();
		int headRow = snake.getHeadRow();
		int headColumn = snake.getHeadColumn();
		int oldSnakeSize = snake.getOldSnakeSize();
		int newSnakeSize = snake.getNewSnakeSize();

		if (headRow <= 0 || matrix[headRow - 1][headColumn] == '#') {
			TerminalHandler.print(TerminalHandler.GAME_OVER);
			System.exit(0);
		}

		if (matrix[headRow - 1][headColumn] == 'O') {
			TerminalHandler.print(TerminalHandler.GAME_OVER);
			System.exit(0);
		}

		if (oldSnakeSize < newSnakeSize) {
			matrix[headRow - 1][headColumn] = 'O';
			snake.setOldSnakeSize(oldSnakeSize + 1);
			snake.updateSnakeHead(headRow - 1, headColumn);
			return;
		}

		if (matrix[headRow - 1][headColumn] == '*') {
			int snakePoints = snake.getPoints();
			snake.setPoints(snakePoints + 1);

			if (snake.getPoints() == 7) {
				TerminalHandler.print(TerminalHandler.YOU_WIN);
				System.exit(0);
			}

			snake.setNewSnakeSize(oldSnakeSize + 5);
			matrix[headRow - 1][headColumn] = 'O';
			snake.updateSnakeHead(headRow - 1, headColumn);
			return;
		}

		matrix[headRow - 1][headColumn] = 'O';
		snake.updateSnakeHead(headRow - 1, headColumn);

		checkTail(snake, matrix);

	}

	public void moveDown(Table table) {
		char[][] matrix = table.getMatrix();
		Snake snake = table.getSnake();
		int headRow = snake.getHeadRow();
		int headColumn = snake.getHeadColumn();
		int oldSnakeSize = snake.getOldSnakeSize();
		int newSnakeSize = snake.getNewSnakeSize();

		if (headRow + 1 >= matrix[headRow].length || matrix[headRow + 1][headColumn] == '#') {
			TerminalHandler.print(TerminalHandler.GAME_OVER);
			System.exit(0);
		}

		if (matrix[headRow + 1][headColumn] == 'O') {
			TerminalHandler.print(TerminalHandler.GAME_OVER);
			System.exit(0);
		}

		if (oldSnakeSize < newSnakeSize) {
			matrix[headRow + 1][headColumn] = 'O';
			snake.setOldSnakeSize(oldSnakeSize + 1);
			snake.updateSnakeHead(headRow + 1, headColumn);
			return;
		}

		if (matrix[headRow + 1][headColumn] == '*') {
			int snakePoints = snake.getPoints();
			snake.setPoints(snakePoints + 1);

			if (snake.getPoints() == 7) {
				TerminalHandler.print(TerminalHandler.YOU_WIN);
				System.exit(0);
			}

			snake.setNewSnakeSize(oldSnakeSize + 5);
			matrix[headRow + 1][headColumn] = 'O';
			snake.updateSnakeHead(headRow + 1, headColumn);
			return;
		}

		matrix[headRow + 1][headColumn] = 'O';
		snake.updateSnakeHead(headRow + 1, headColumn);

		checkTail(snake, matrix);
	}

	private void checkTail(Snake snake, char[][] matrix) {
		int tailRow = snake.getTailRow();
		int tailColumn = snake.getTailColumn();

		if (matrix[tailRow + 1][tailColumn] == 'O') {
			matrix[tailRow + 1][tailColumn] = ' ';
			snake.updateSnakeTail(tailRow + 1, tailColumn);
			return;
		}
		if (matrix[tailRow - 1][tailColumn] == 'O') {
			matrix[tailRow - 1][tailColumn] = ' ';
			snake.updateSnakeTail(tailRow - 1, tailColumn);
			return;
		}
		if (matrix[tailRow][tailColumn + 1] == 'O') {
			matrix[tailRow][tailColumn + 1] = ' ';
			snake.updateSnakeTail(tailRow, tailColumn + 1);
			return;
		}
		if (matrix[tailRow][tailColumn - 1] == 'O') {
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
