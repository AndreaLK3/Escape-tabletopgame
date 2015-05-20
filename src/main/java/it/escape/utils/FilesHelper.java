package it.escape.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FilesHelper {
	
	public static String streamToString(InputStream ingresso) throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(ingresso));
			StringBuilder ret = new StringBuilder();
			String line;
			
			while ((line = reader.readLine()) != null) {
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
	
	public static void saveToFile(String filename, String data) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(filename);
		out.print(data);
		out.close();
	}
}
