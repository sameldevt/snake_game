package br.com.magna.snake_game.services;

import java.util.logging.Logger;

public class LogHandler {

	private static final Logger LOGGER = Logger.getLogger(LogHandler.class.getName());
	
	public static void info(String msg) {
		LOGGER.info(msg);
	}
	
	public static void error(String msg) {
		LOGGER.warning(msg);
	}
}
