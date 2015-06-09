package it.escape.strings;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class FormatToPatternTest {
	
	@Test
	public void testPlayObj() {
		Pattern conv;
		String format = StringRes.getString("messaging.askPlayObjectCard");
		conv = new FormatToPattern(StringRes.getString("messaging.askPlayObjectCard")).convert();
		Matcher match = conv.matcher(format);
		assertEquals(true, match.matches());
		
	}

}
