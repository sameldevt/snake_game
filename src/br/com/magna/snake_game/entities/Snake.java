package br.com.magna.snake_game.entities;

public class Snake {
	private int headRow = 3;
	private int headColumn = 6;
	private int tailRow = 3;
	private int tailColumn = 3;
	private int snakeSize = 3;

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
	
	public void updateSnakeBody(int headRow, int headColumn, int tailRow, int tailColumn) {
		this.headRow = headRow;
		this.headColumn = headColumn;
		this.tailRow = tailRow;
		this.tailColumn = tailColumn;
		
		System.out.println("head row: " + headRow + " column: " + headColumn);
		System.out.println("tail row: " + tailRow + " column: " + tailColumn);
	}
	
	public int getSnakeSize() {
		return snakeSize;
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


}
