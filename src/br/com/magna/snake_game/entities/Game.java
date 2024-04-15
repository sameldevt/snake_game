package br.com.magna.snake_game.entities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import br.com.magna.snake_game.services.LogHandler;
import br.com.magna.snake_game.services.TerminalHandler;

public class Game implements Runnable {

	private static Game instance = new Game();

	private boolean isGameWon = false;
	private Table table = new Table();
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
		this.action = action;
	}

	@Override
	public void run() {
		if (!menuLoop()) {
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
						
						if (action.equals(Control.class.getDeclaredMethod("moveRight", Table.class))) {
							isOptionPicked = true;
							break;
						}
					}

					table.printMatrix();
					instance.wait();
					TerminalHandler.clear();
				} catch (IllegalAccessException e1) {
					LogHandler.error("Error accessing method from Control:" + e1.getMessage());
				} catch (InvocationTargetException e2) {
					LogHandler.error("Error invocating method from Control:" + e2.getMessage());
				} catch (InterruptedException e3) {
					LogHandler.error("Error handling thread: " + e3.getMessage());
				} catch (NullPointerException e4) {
				} catch (NoSuchMethodException e5) {
					LogHandler.error("Error accessing method from Control:" + e5.getMessage());
				} catch (SecurityException e6) {
					LogHandler.error("Error handling thread: " + e6.getMessage());
				}
			}
			
			return false;
		}
	}

	private void gameLoop() {
		table.loadMatrix(TerminalHandler.NAME);
		
		while (!isGameWon) {
			try {
				action.invoke(new Control(), table);
				table.printMatrix();
				
				setGameSpeed(action);
				
				TerminalHandler.clear();
			} catch (IllegalAccessException e1) {
				LogHandler.error("Error accessing method from Control:" + e1.getMessage());
			} catch (InvocationTargetException e2) {
				LogHandler.error("Error invocating method from Control:" + e2.getMessage());
			} catch (InterruptedException e3) {
				LogHandler.error("Error handling thread: " + e3.getMessage());
			} catch (NullPointerException e4) {

			} catch (NoSuchMethodException e5) {
				LogHandler.error("Error accessing method from Control:" + e5.getMessage());
			} catch (SecurityException e6) {
				LogHandler.error("Error accessing method from Control:" + e6.getMessage());
			}
		}
	}
	
	private void setGameSpeed(Method action) throws NoSuchMethodException, SecurityException, InterruptedException {
		if(action.equals(Control.class.getDeclaredMethod("moveUp", Table.class))
				|| action.equals(Control.class.getDeclaredMethod("moveDown", Table.class))){
			Thread.sleep(110);
		}
		else {
			Thread.sleep(90);
		}
	}
}
