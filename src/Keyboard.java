package eu.lagunalabs.myapp.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	private boolean[] keys = new boolean[120];
	public boolean up,down,left,right;

	public void keyPressed( KeyEvent e ) {
		keys[e.getKeyCode()] = true;	
	}

	public void keyReleased( KeyEvent e ) {
		keys[e.getKeyCode()] = false;	
	}
	
	public void keyTyped( KeyEvent e ) {
		// ToDo!	
	}
}
