package it.escape.tools.utils.synchrolaunch;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SwingSynchroLauncherTest {

	/** accessory class used to fill up an ordered list*/
	private class Container {
		private List<String> stuff;
		
		public Container() {
			stuff = new ArrayList<String>();
		}
		
		public void append(String coso) {
			stuff.add(coso);
		}

		public List<String> getStuff() {
			return stuff;
		}
	}
	
	@Test
	public void test() {
		List<String> testSequence = Arrays.asList(
			"alfa",
			"bravo",
			"charlie brown",
			"delta",
			"are you still there?",
			"critical error",
			"why?",
			"this is the end"
		);
		
		final Container roba = new Container();
		
		// if everything goes well, the strings will be enqueued in sequence,
		// because the swing threads will run in sequence
		for (String s : testSequence) {
			final String s1 = s;
			SwingSynchroLauncher.synchronousLaunch(new SynchroLaunchInterface() {
				public void run() {
					roba.append(s1);
				}
			});
		}
		
		List<String> result = roba.getStuff();
		int index = 0;
		for (String c : result) {
			assertEquals(testSequence.get(index), c);
			index++;
		}
	}

}
