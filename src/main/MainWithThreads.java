package main;

import java.io.IOException;
import java.sql.SQLException;

import reading.FolderVerification;

public class MainWithThreads implements Runnable {
	
	Thread[] threads = new Thread[3];
	private String source;
	private FolderVerification fv;

	public static void main(String[] args) {
		new MainWithThreads().init();
	}

	private void init() {
		setSource("./logArchives/"); // setting archives directory
		fv = new FolderVerification(getSource());
		
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(this);
			threads[i].start();
		}
	}

	
	@Override
	public void run() {
		Thread currentThread = Thread.currentThread();
		if (Thread.currentThread() == threads[0]) {
			while (this.threads[0] == currentThread) {
				try {
					fv.verify(1);
					Thread.sleep(1000 * 3);
				} catch (IOException | SQLException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (Thread.currentThread() == threads[1]) {
			while (this.threads[1] == currentThread) {
				try {
					fv.verify(2);
					Thread.sleep(1000 * 3);
				} catch (IOException | SQLException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (Thread.currentThread() == threads[2]) {
			while (this.threads[2] == currentThread) {
				try {
					fv.verify(3);
					Thread.sleep(1000 * 3);
				} catch (IOException | SQLException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getSource() {
		return source;
	}

	public void setSource(String dir) {
		this.source = dir;
	}
}