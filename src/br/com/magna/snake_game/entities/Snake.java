package br.com.magna.snake_game.entities;

public class Snake {
	private int headRow = 3;
	private int headColumn = 3;
	private int tailRow = 3;
	private int tailColumn = 3;
	private int snakeSize = 1;

	public Snake() {
	}
	
	public void updateSnakeHead(int headRow, int headColumn) {
		this.headRow = headRow;
		this.headColumn = headColumn;
		
		System.out.println("size="+snakeSize);
		System.out.println("head="+headRow+":"+headColumn);
		System.out.println("tail="+tailRow+":"+tailColumn);
		
	}

	public void updateSnakeTail(int tailRow, int tailColumn) {
		this.tailRow = tailRow;
		this.tailColumn = tailColumn;		
	}
	
	public void setSnakeSize(int newSnakeSize) {
		snakeSize = newSnakeSize;
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
