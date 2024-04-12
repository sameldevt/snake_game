package br.com.magna.snake_game.entities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import br.com.magna.snake_game.services.LogHandler;
import br.com.magna.snake_game.services.TerminalHandler;

public class Game implements Runnable {

	private static Game instance = null;
	
	private boolean isGameWon = false;
	private Table table = new Table();
	private Method movingMethod = null;

	private Game() {
		try {
			movingMethod = Control.class.getDeclaredMethod("moveRight", Table.class);
		} catch (NoSuchMethodException | SecurityException e) {
			LogHandler.error("Couldn't find method " + e.getMessage());
		}
	}
	
	public static Game getInstance() {
		if(instance == null) {
			instance = new Game();
			return instance;
		}
		
		return instance;
	}

	@Override
	public void run() {
		 gameLoop();
	}
	
	private void gameLoop() {
		while (!isGameWon) {
			try {
				movingMethod.invoke(new Control(), table);
				table.printMatrix();
				Thread.sleep(100);
				TerminalHandler.clear();
			} catch (IllegalAccessException e1) {
				LogHandler.error("Error accessing method from Control:" + e1.getMessage());
			} catch (InvocationTargetException e1) {
				LogHandler.error("Error invocating method from Control:" + e1.getMessage());
			} catch (InterruptedException e3) {
				LogHandler.error("Error handling thread: " + e3.getMessage());
			} catch (NullPointerException e4) {
				
			}
		}
	}

	public void setMovingMethod(Method movingMethod) {
		this.movingMethod = movingMethod;
	}
}
