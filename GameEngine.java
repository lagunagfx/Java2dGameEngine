package eu.lagunalabs.gameengine;

public class GameEngine implements Runnable {

	// Graphical Settings

	public static int width = 480;
	public static int height = width / 16 * 9;
	public static int scale = 2;

	// Setting up my process

	private Thread myThread;
	private boolean running = false;

	public synchronized void start() {
		running = true;
		myThread = new Thread(this, "Game");
		myThread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			myThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (running) {
			
		}	
	}

}
