package it.escape.utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogHelper {
	
	private static Handler consoleOutput = new ConsoleHandler();
	
	private static Formatter simpleFormatter = new SimpleFormatter();
	
	public static void setDefaultOptions(Logger logger) {
		logger.setUseParentHandlers(false);
		logger.addHandler(consoleOutput);  // log all messages to standard error
		logger.setLevel(Level.ALL);  // write every message to log
		consoleOutput.setLevel(Level.ALL);  // write every log message to console
		
		if (!consoleOutput.getFormatter().equals(simpleFormatter)) {
			consoleOutput.setFormatter(simpleFormatter);
		}
	}
	
}
