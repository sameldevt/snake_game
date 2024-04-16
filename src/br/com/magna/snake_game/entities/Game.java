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
		if(action == null) {
			return;
		}
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
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException reflectionException) {
					LogHandler.error("Error accessing method from Control:" + reflectionException.getMessage());
				} catch(InterruptedException threadException) {
					LogHandler.error("Error handling thread: " + threadException.getMessage());
				} catch (NullPointerException actionIsNullException) {
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
				wait(action);
				TerminalHandler.clear();
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException reflectionException) {
				LogHandler.error("Error accessing method from Control:" + reflectionException.getMessage());
			} catch(InterruptedException threadException) {
				LogHandler.error("Error handling thread: " + threadException.getMessage());
			} catch (NullPointerException actionIsNullException) {
				
			}
		}
	}
	
	private void wait(Method action) throws NoSuchMethodException, SecurityException, InterruptedException {
		if(action.equals(Control.class.getDeclaredMethod("moveUp", Table.class))
				|| action.equals(Control.class.getDeclaredMethod("moveDown", Table.class))){
			Thread.sleep(150);
		}
		else {
			Thread.sleep(100);
		}
	}
}
