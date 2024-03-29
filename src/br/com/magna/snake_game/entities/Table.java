package br.com.magna.snake_game.entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import br.com.magna.snake_game.enums.Direction;

public class Table implements Runnable{

	public static char[][] table =  new char[35][160];
	public static Snake snake = new Snake();
	
	public Table() {
		table = fillTable();
		snake.setSnakeDirection(Direction.RIGHT);
	}
	
	@Override
	public void run() {
		while(true) {
			printTable();
			snake.move();
			clear();
		}
	}
	
	public static Snake getSnake() {
		return snake;
	}
	
	private static void clear() {
		for (int i = 0; i < 50; i++)
			System.out.println();
	}

	private void printTable() {
		for (char[] row : table) {
			for (int column = 0; column < row.length; column++) {
				System.out.print(row[column]);
			}
			System.out.println();
		}
	}
	
	private char[][] fillTable(){
		char[][] table = new char[35][160];
		int i = 0;
		try(Scanner s = new Scanner(new File("util/name.txt"))){
			while(s.hasNextLine()) {
				String line = s.nextLine();

				for(int j = 0; j < line.length(); j++) {
					char c = line.charAt(j);
					table[i][j] = c;
				}

				i++;
			}
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return table;
	}
}
