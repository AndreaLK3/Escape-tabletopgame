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
	
	public static byte[] getResourceAsByteArray(String filename) throws IOException {
		InputStream inStream = getResourceFile(filename);
		// Get the size of the file
		long streamLength = inStream.available();
		
		if (streamLength > Integer.MAX_VALUE) {
			throw new IOException();
		}
		
		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) streamLength];
		
		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length &&
				(numRead = inStream.read(bytes,offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		
		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file ");
		}
		
		// Close the input stream and return bytes
		inStream.close();
		return bytes;
	}
	
	public static void saveToFile(String filename, String data) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(filename);
		out.print(data);
		out.close();
	}
}
