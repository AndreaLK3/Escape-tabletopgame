package it.escape.utils.synchrolaunch;

import java.awt.EventQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Reusable code for launching runnables in the EDT.
 * It uses EventQueue.invokeLater() to launch your runnable
 * (as you'd do for staring up a new jframe or something),
 * then it waits for said runnable to have completed its job
 * (i.e. the gui is up and ready to receive data).
 * Then it returns, and the program flow may continue.
 * @author michele
 *
 */
public class SwingSynchroLauncher {
	
	private static final int QUANTUM = 200;
	
	public static void synchronousLaunch(final SynchroLaunchInterface toRun) {
		final AtomicBoolean ready = new AtomicBoolean(false);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				toRun.run();
				ready.set(true);
			}
		});
		Thread sync = new Thread(new Runnable() {
			public void run() {
				while (!ready.get()) {
					try {
						Thread.sleep(QUANTUM);
					} catch (InterruptedException e) {
					}
				}
				
			}
		});
		sync.start();
		try {
			sync.join();
		} catch (InterruptedException e) {
		}
	}
}
