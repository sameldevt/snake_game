package br.com.magna.snake_game.entities;

public class Snake {
	private int headRow = 3;
	private int headColumn = 3;
	private int tailRow = 3;
	private int tailColumn = 3;
	private int newSnakeSize = 5;
	private int oldSnakeSize = 1;
	private char snakeChar = 'o';

	public Snake() {
	}
	
	public void updateSnakeHead(int headRow, int headColumn) {
		this.headRow = headRow;
		this.headColumn = headColumn;
	}

	public void updateSnakeTail(int tailRow, int tailColumn) {
		this.tailRow = tailRow;
		this.tailColumn = tailColumn;		
	}
	
	public void setNewSnakeSize(int newSnakeSize) {
		this.newSnakeSize = newSnakeSize;
	}
	
	public int getNewSnakeSize() {
		return newSnakeSize;
	}
	
	public void setOldSnakeSize(int oldSnakeSize) {
		this.oldSnakeSize = oldSnakeSize;
	}
	
	public int getOldSnakeSize() {
		return oldSnakeSize;
	}
	
	public int getHeadRow() {
		return headRow;
	}
	
	public int getHeadColumn() {
		return headColumn;
	}
	
	public int getTailRow() {
		return tailRow;
	}
	
	public int getTailColumn() {
		return tailColumn;
	}

	public char getSnakeChar() {
		return snakeChar;
	}


}
