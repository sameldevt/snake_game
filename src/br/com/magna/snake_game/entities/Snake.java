package br.com.magna.snake_game.entities;

import br.com.magna.snake_game.enums.Direction;

public class Snake {
	private int row = 3;
	private int column = 3;
	private Direction direction;
	
	public void move() {
		try {
			direction.move();
		} catch (InterruptedException e) {}
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public Direction getSnakeDirection() {
		return direction;
	}
	public void setSnakeDirection(Direction direction) {
		this.direction = direction;
	}
}
