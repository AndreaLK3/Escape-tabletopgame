package it.escape.utils;

import java.util.Arrays;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogHelper {
	
	private static Handler defaultHandler = new ConsoleHandler();
	
	private static Formatter defaultFormatter = new SimpleFormatter();
	
	public static void setDefaultOptions(Logger logger) {
		List<Handler> handlers = Arrays.asList(logger.getHandlers());
		
		logger.setUseParentHandlers(false);
		if (!handlers.contains(defaultHandler)) { // add handler only if missing
			logger.addHandler(defaultHandler);  // LOGGER all messages to standard error
		}
		logger.setLevel(Level.ALL);  // write every message to LOGGER
		defaultHandler.setLevel(Level.ALL);  // write every LOGGER message to console
		if (!defaultHandler.getFormatter().equals(defaultFormatter)) {  // add formatter only if missing
			defaultHandler.setFormatter(defaultFormatter);
		}
	}
	
	public static void setDefaultHandler(Handler DefaultHandler) {
		defaultHandler = DefaultHandler;
	}
	
	public static Formatter getDefaultFormatter() {
		return defaultFormatter;
	}
}
