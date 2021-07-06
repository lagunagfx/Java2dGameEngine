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

	// Graphical Settings

	public static int width = 480;
	public static int height = width / 16 * 9;
	public static int scale = 1;

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

		// 1 second = 1e9 nanoseconds
		// 1/60th of a second = 1e9/60 nanoseconds
		long lastTime = System.nanoTime();
		final double minimumDelta = ( 1e9 / 60.0 );
		double delta = 0;

		int frames = 0;
		int updates = 0;
		long timer = System.currentTimeMillis();

		while (running) {
			// Will NOT execute this one more than 60 times per second

			//long delta = System.nanoTime() - lastTime;
			long now = System.nanoTime();
			delta += ( now - lastTime ) / minimumDelta;
			lastTime = now;			
			
			//if ( delta >= minimumDelta ) {
			while ( delta >= 1 ) {
				update();
				System.out.println("delta: " + delta);
				//delta = 0;
				delta--;
				//lastTime = System.nanoTime();
				updates++;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				//timer = System.currentTimeMillis();
				timer += 1000;
				//System.out.println("frames: " + frames + "\tupdates: " + updates);
				frame.setTitle("frames: " + frames + "\tupdates: " + updates);
				updates = 0;
				frames = 0;
			}	

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

		myScreen.render();
		for ( int i = 0 ; i < pixels.length ; i++ ) {
			pixels[i] = myScreen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();

		// The actual drawing stuff begins here ...
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null );
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
