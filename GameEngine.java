//package eu.lagunalabs.gameengine;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class GameEngine extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	// Graphical Settings

	public static int width = 480;
	public static int height = width / 16 * 9;
	public static int scale = 1;

	// Setting up my process

	public GameEngine() {
		Dimension size = new Dimension( width*scale , height*scale );
		setPreferredSize(size);
		frame = new JFrame();
	}
	
	private Thread myThread;
	private JFrame frame;
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
			//System.out.print("Running...\r");
			update();
			render();
		}	
	}

	public void update() {
		//ToDo!
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if ( bs == null ) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		// The actual drawing stuff begins here ...
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth()/2, getHeight()/2);
		// ... And ends here
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		GameEngine myGame = new GameEngine();
		myGame.frame.setResizable(false);
		myGame.frame.setTitle("Just a working title");
		myGame.frame.add(myGame);
		myGame.frame.pack();
		myGame.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myGame.frame.setLocationRelativeTo(null);
		myGame.frame.setVisible(true);

		myGame.start();
	}
}
