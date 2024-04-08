package br.com.magna.snake_game.enums;

import java.util.concurrent.ConcurrentHashMap;

import br.com.magna.snake_game.entities.Snake;
import br.com.magna.snake_game.entities.Table;

public enum Direction {
	UP(){
		@Override
		public void move() throws InterruptedException{
			Snake snake = Table.getSnake();
			int snakeRow = snake.getRow();
			int snakeColumn = snake.getColumn();
			int snakeSize = snake.getSnakeSize();
			
			if (snakeRow <= 0 || Table.table[snakeRow - 1][snakeColumn] == '#') {
				System.exit(0);
			}
			
			if(snake.getSnakeSize() == 3) {
				Table.table[snakeRow - 1][snakeColumn] = '#';
				Table.table[snakeRow + snakeSize][snakeColumn] = ' ';
				snake.setRow(--snakeRow);
				Thread.sleep(LEFT_RIGHT_TIME);
				return;
			}
			
			Table.table[snakeRow - 1][snakeColumn] = '#';
			
			
			Thread.sleep(UP_DOWN_TIME);
		}
		
	},
	DOWN{
		@Override
		public void move() throws InterruptedException{
			Snake snake = Table.getSnake();
			int snakeRow = snake.getRow();
			int snakeColumn = snake.getColumn();
			int snakeSize = snake.getSnakeSize();
			
			if (snakeRow + 1 >= Table.table[snakeRow].length || Table.table[snakeRow + 1][snakeColumn] == '#') {
				System.exit(0);
			}

			if(snake.getSnakeSize() == 3) {
				Table.table[snakeRow + 1][snakeColumn] = '#';
				Table.table[snakeRow - snakeSize][snakeColumn] = ' ';
				snake.setRow(++snakeRow);
				Thread.sleep(LEFT_RIGHT_TIME);
				return;
			}
			
			snake.setSnakeSize(snakeSize + 1);
			Table.table[snakeRow + 1][snakeColumn] = '#';
			snake.setRow(++snakeRow);
			
			Thread.sleep(UP_DOWN_TIME);
		}
	}, 
	LEFT{
		@Override
		public void move() throws InterruptedException {	
			Snake snake = Table.getSnake();
			int snakeRow = snake.getRow();
			int snakeColumn = snake.getColumn();
			int snakeSize = snake.getSnakeSize();
			
			if (snakeColumn <= 0 || Table.table[snakeRow][snakeColumn - 1] == '#') {
				System.exit(0);
			}
			
			if(snake.getSnakeSize() == 3) {
				Table.table[snakeRow][snakeColumn - 1] = '#';
				Table.table[snakeRow][snakeColumn + snakeSize] = ' ';
				snake.setColumn(++snakeColumn);
				Thread.sleep(LEFT_RIGHT_TIME);
				return;
			}
			
			snake.setSnakeSize(snakeSize + 1);
			Table.table[snakeRow][snakeColumn - 1] = '#';
			snake.setColumn(--snakeColumn);
			
			Thread.sleep(LEFT_RIGHT_TIME);
		}
	}, 
	RIGHT{
		@Override
		public void move() throws InterruptedException{
			Snake snake = Table.getSnake();
			int snakeRow = snake.getRow();
			int snakeColumn = snake.getColumn();
			int snakeSize = snake.getSnakeSize();
			
			if (snakeColumn >= Table.table[snakeRow].length || Table.table[snakeRow][snakeColumn + 1] == '#') {
				System.exit(0);
			}
			
			if(snake.getSnakeSize() == 3) {
				Table.table[snakeRow][snakeColumn + 1] = '#';
				Table.table[snakeRow][snakeColumn - snakeSize] = ' ';
				snake.setColumn(++snakeColumn);
				Thread.sleep(LEFT_RIGHT_TIME);
				return;
			}
			
			snake.setSnakeSize(snakeSize + 1);
			Table.table[snakeRow][snakeColumn + 1] = '#';
			snake.setColumn(++snakeColumn);
			
			Thread.sleep(LEFT_RIGHT_TIME);
		}
	};
	
	private static final int UP_DOWN_TIME = 100;
	private static final int LEFT_RIGHT_TIME = 100;
	
	public abstract void move() throws InterruptedException;
}
