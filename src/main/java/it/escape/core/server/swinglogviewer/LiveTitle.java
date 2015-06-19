package it.escape.core.server.swinglogviewer;

import java.util.Date;

import javax.swing.JLabel;

public class LiveTitle implements Runnable {
	
	private static final int SCANTIME = 1000;
	
	private boolean sock;
	
	private boolean rmi;
	
	private JLabel target;
	
	private Date start;
	
	private Date now;
	
	private boolean running;
	
	public LiveTitle(boolean sock, boolean rmi, JLabel target) {
		this.sock = sock;
		this.rmi = rmi;
		this.target = target;
	}

	@Override
	public void run() {
		running = true;
		start = new Date();
		while (running) {
			now = new Date();
			target.setText(buildTitle());
			try {
				Thread.sleep(SCANTIME);
			} catch (InterruptedException e) {
			}
		}
	}
	
	/**
	 * It's better tu use a StringBUilder, as this function will
	 * run repeatedly for a long time
	 * @return
	 */
	private String buildTitle() {
		StringBuilder title = new StringBuilder();
		title.append("EFTAIOS ");
		if (rmi && sock) {
			title.append("socket + RMI");
		} else if (rmi) {
			title.append("RMI");
		} else {
			title.append("socket");
		}
		title.append(" server, uptime: ");
		title.append(printDeltaTime());
		return title.toString();
	}
	
	private String printDeltaTime() {
		long rawdiff = now.getTime() - start.getTime();
		long diffSeconds = rawdiff / 1000 % 60;
		long diffMinutes = rawdiff / (60 * 1000) % 60;
		long diffHours = rawdiff / (60 * 60 * 1000) % 24;
		long diffDays = rawdiff / (24 * 60 * 60 * 1000);
		return diffDays + " days, " + diffHours + " hours, "
				+ diffMinutes + " minutes, " + diffSeconds + " seconds";
	}

}
