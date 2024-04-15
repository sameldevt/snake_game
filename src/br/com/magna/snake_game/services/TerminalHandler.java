package br.com.magna.snake_game.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TerminalHandler {

	public static final String MAIN_MENU = "util/mainmenu.txt";
	public static final String GAME_OVER = "util/gameover.txt";
	public static final String NAME = "util/name.txt";
	public static final String YOU_WIN = "util/youwin.txt";
	public static final String LATER = "util/later.txt";

	public static void print(String path) {
		try (Scanner scan = new Scanner(new File(path))) {
			while (scan.hasNextLine()) {
				System.out.println(scan.nextLine());
				if(path.equals(GAME_OVER) || path.equals(YOU_WIN)) {
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (FileNotFoundException e) {

		}
	}

	public static void clear() {
		for (int i = 0; i < 50; i++)
			System.out.println();
	}
}
