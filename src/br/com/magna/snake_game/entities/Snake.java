package br.com.magna.snake_game.entities;

import br.com.magna.snake_game.enums.Direction;

public class Snake {
	private int row = 3;
	private int column = 3;
	private int snakeSize = 1;
	private int head = column;
	private int tail = column;
	private Direction direction;
	
	public void move() {
		try {
			direction.move();
		} catch (InterruptedException e) {}
	}
	
	public int getHead() {
		return head;
	}
	
	public int getTail() {
		return tail;
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
	public int getSnakeSize() {
		return snakeSize;
	}
	
	public void setSnakeSize(int newSize) {
		snakeSize = newSize;
	}
}
