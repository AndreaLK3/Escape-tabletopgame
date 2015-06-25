package it.escape.tools.utils;


public class SingleShutdownHook {
	
	private static Thread hook = null;
	
	public static void setHook(Thread newHook) {
		if (hook == null && newHook != null) {
			hook = newHook;
			Runtime.getRuntime().addShutdownHook(hook);
		}
	}
}
