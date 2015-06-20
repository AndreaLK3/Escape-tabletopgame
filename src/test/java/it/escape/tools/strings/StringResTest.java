package it.escape.tools.strings;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringResTest {

	@Test
	public void test() {
		assertEquals("This is a test", StringRes.getString("tests.getMe.one"));
		assertEquals(StringRes.noKey, StringRes.getString("I have no idea"));
	}

}
