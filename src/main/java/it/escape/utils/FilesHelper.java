package it.escape.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;

public class FilesHelper {
	
	private final static int BEFORE_FIRSTLINE = -1;
	
	public static String streamToString(InputStream ingresso) throws IOException {
		try {
			LineNumberReader reader = new LineNumberReader(new InputStreamReader(ingresso));
			StringBuilder ret = new StringBuilder();
			String line;
			int prev = BEFORE_FIRSTLINE;
			
			while ((line = reader.readLine()) != null) {
				if (prev != reader.getLineNumber()) {
		        	if (prev!=BEFORE_FIRSTLINE) {
		        		ret.append("\n");
		        	}
		        	prev = reader.getLineNumber();
		        }
		        ret.append(line);
		    }
			// TODO: save newlines
			
			return ret.toString();
		} catch (NullPointerException e) {
			throw new IOException();
		}
	}
	
	public static InputStream getResourceFile(String filename) throws IOException {
		try {
			return Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
		} catch(NullPointerException e) {
			throw new IOException();
		}
		
	}
	
	public static void saveToFile(String filename, String data) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(filename);
		out.print(data);
		out.close();
	}
}
