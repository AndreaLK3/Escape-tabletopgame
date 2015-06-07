package it.escape.strings;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class FormatToPatternTest {

	@Test
	public void testMotd() {
		String core = "this is a test\nhello!\n";
		Pattern conv;
		String format = String.format(
				StringRes.getString("messaging.motd"),
				core);
		conv = new FormatToPattern(StringRes.getString("messaging.motd")).convert();
		Matcher match = conv.matcher(format);
		assertEquals(true, match.matches());
		assertEquals(core, match.group(1));
		
	}

}
