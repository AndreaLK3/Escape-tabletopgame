package it.escape.strings;

import java.util.regex.Pattern;

/**
 * This class lets you convert a format string to a pattern,
 * allowing you to parse all the strings created using that
 * format string.
 * 
 * Basically takes a format string with "%s" (string) type
 * parameters, replaces it with a `matchall` (a regexp that
 * matches any string), and then return it as a pattern.
 * @author michele
 *
 */
public class FormatToPattern {
	
	private static String matchall = "(.*)";
	
	private String temp;

	public FormatToPattern(String temp) {
		this.temp = temp;
	}
	
	public Pattern convert() {
		temp = temp.replaceAll("\\(", "\\\\(");  // escape all parenthesis
		temp = temp.replaceAll("\\)", "\\\\)");
		temp = temp.replaceAll("\\.", "\\\\.");
		temp = temp.replaceAll("\\*", "\\\\*");
		temp = temp.replaceAll("\\?", "\\\\?");
		temp = temp.replaceAll("\\^", "\\\\^");
		temp = temp.replaceAll("%s", matchall);  // match strings
		temp = temp.replaceAll("%d", matchall);  // match integer numbers 
		
		return Pattern.compile(temp, Pattern.DOTALL);
	}
	
}
