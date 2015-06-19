package it.escape.core.server.swinglogviewer;

import java.io.OutputStream;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class ResponsiveStreamHandler extends StreamHandler {

	public ResponsiveStreamHandler() {
		super();
	}

	public ResponsiveStreamHandler(OutputStream out, Formatter formatter) {
		super(out, formatter);
	}

	@Override
	public synchronized void publish(LogRecord record) {
		super.publish(record);
		flush();
	}
	
}
