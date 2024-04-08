package br.com.magna.snake_game.entities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import br.com.magna.snake_game.enums.Direction;

public class ControlFrame extends JFrame implements KeyListener, Runnable {

	private static final long serialVersionUID = 1L;
	private Map<Integer, Direction> directions;
	
	public ControlFrame() {
			directions = new HashMap<Integer, Direction>();
			
			directions.put(KeyEvent.VK_UP, Direction.UP);
			directions.put(KeyEvent.VK_DOWN, Direction.DOWN);
			directions.put(KeyEvent.VK_RIGHT, Direction.RIGHT);
			directions.put(KeyEvent.VK_LEFT, Direction.LEFT);
		
        setSize(100, 100);
		addKeyListener(this);
		setFocusable(true);
		setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			Table.getSnake().setSnakeDirection(directions.get(e.getKeyCode()));
		}
		catch(NullPointerException npe) {}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void run() {}
}

