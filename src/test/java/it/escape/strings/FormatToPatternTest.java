package it.escape.strings;

import static org.junit.Assert.*;
import it.escape.tools.strings.FormatToPattern;
import it.escape.tools.strings.StringRes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class FormatToPatternTest {
	
	/**
	 * convert a format string into a regexp, then use
	 * said regexp to match the original strings, should
	 * always return true
	 */
	@Test
	public void testPlayObj() {
		Pattern conv;
		String format = StringRes.getString("messaging.askPlayObjectCard");
		conv = new FormatToPattern(StringRes.getString("messaging.askPlayObjectCard")).convert();
		Matcher match = conv.matcher(format);
		assertEquals(true, match.matches());
		
	}

}
