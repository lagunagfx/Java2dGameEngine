package eu.lagunalabs.myapp;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;

import eu.lagunalabs.myapp.Screen;

public class GameEngine extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	private static final int FRAMERATE = 60;
	private static final int MILLISECONDS = 1000;
	private static final int NANOSECONDS = 1000000000;

	public static String title = "My App";

	// Graphical Settings
	public static int width = 480;
	public static int height = width / 16 * 9;
	public static int scale = 2;

	// Setting up my process
	private Thread myThread;
	private Screen myScreen;
	private JFrame frame;
	private boolean running = false;

	public GameEngine() {
		Dimension size = new Dimension( width*scale , height*scale );
		setPreferredSize(size);
		myScreen = new Screen(width, height);
		frame = new JFrame();
	}
	
	// Buffered Image
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public synchronized void start() {
		running = true;
		myThread = new Thread(this, "Display");
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

		/*
			If you want to change the precision used you can change
			between "System.nanoTime()" and "System.currentTimeMillis()"

			Don't forget to adjust UNIT used (MILLISECONDS or NANOSECONDS)
		*/

		int TIME_UNIT = NANOSECONDS;
		final long singleFrame = (long) ( TIME_UNIT / FRAMERATE ); // 1/60th of a second = 1e9/60 nanoseconds

		long lastTime = System.nanoTime();
		long lastSecond = lastTime;

		int updates = 0;
		int frames = 0;

		while (running) {

			if ( System.nanoTime() - lastTime >= singleFrame ) {
				lastTime = System.nanoTime();
				update();
				updates++;
			}
			
			if ( System.nanoTime() - lastSecond >= TIME_UNIT ) {
				lastSecond = System.nanoTime();
				frame.setTitle( title + " f: " + frames + " u: " + updates );
				updates = 0;
				frames = 0;
			}	
			
			render();
			frames++;						
		}	
	}

	int x = 0, y = 0;

	public void update() {
		x++;
		y++;
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if ( bs == null ) {
			createBufferStrategy(3);
			return;
		}

		myScreen.clear();
		myScreen.render(x,y);

		for ( int i = 0 ; i < pixels.length ; i++ ) {
			pixels[i] = myScreen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null );
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		GameEngine myGame = new GameEngine();
		myGame.frame.setResizable(false);
		myGame.frame.setTitle(title);
		myGame.frame.add(myGame);
		myGame.frame.pack();
		myGame.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myGame.frame.setLocationRelativeTo(null);
		myGame.frame.setVisible(true);

		myGame.start();
	}
}
