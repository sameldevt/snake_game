package br.com.magna.snake_game.entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Table {

	public char[][] matrix = new char[35][160];
	public Snake snake = new Snake();

	public Table() {
		loadTable();
	}
	
	public char[][] getMatrix() {
		return matrix;
	}
	
	public Snake getSnake() {
		return snake;
	}
	
	public void printMatrix() {
		for (int row = 0; row < matrix.length; row++) {
			for (int column = 0; column < matrix[row].length; column++) {
				System.out.print(matrix[row][column]);
			}
			System.out.println();
		}

	}

	private void loadTable() {
		char[][] table = new char[35][160];
		int i = 0;
		try (Scanner s = new Scanner(new File("util/name.txt"))) {
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
}
