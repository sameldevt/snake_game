package br.com.magna.snake_game.entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import br.com.magna.snake_game.services.TerminalHandler;

public class Table {

	private char[][] matrix = new char[35][160];
	private Snake snake = new Snake();
	private char pointChar = '*';
	private char wallChar = '#';
	private char snakeChar = snake.getSnakeChar();
	private int totalMapPoints = 0;

	public Table() {
		loadMatrix(TerminalHandler.MAIN_MENU);
	}
	
	public char getWallChar() {
		return wallChar;
	}
	
	public char getSnakeChar() {
		return snakeChar;
	}
	
	public char getPointChar() {
		return pointChar;
	}
	
	public char[][] getMatrix() {
		return matrix;
	}
	
	public Snake getSnake() {
		return snake;
	}
	
	public int getTotalMapPoints() {
		return totalMapPoints;
	}
	
	public void printMatrix() {
		for (int row = 0; row < matrix.length; row++) {
			for (int column = 0; column < matrix[row].length; column++) {
				System.out.print(matrix[row][column]);
			}
			System.out.println();
		}
	}
	
	public void loadMatrix(String path) {
		char[][] table = new char[35][160];
		int i = 0;
		try (Scanner s = new Scanner(new File(path))) {
			while (s.hasNextLine()) {
				String line = s.nextLine();

				for (int j = 0; j < line.length(); j++) {
					char c = line.charAt(j);
					table[i][j] = c;
				}

				i++;
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

		matrix = table;
	}
	
	public void loadRandomPoints(char[][] matrix) {
		Random r = new Random();
		int randomPoints = 15;
		while(randomPoints > 0) {
			int randomRow = r.nextInt(0, matrix.length);
			int randomColumn = r.nextInt(0, matrix[0].length);
			
			if(matrix[randomRow][randomColumn] == ' ') {
				matrix[randomRow][randomColumn] = pointChar;
				totalMapPoints++;
			}
			
			randomPoints--;
		}
	}
}
