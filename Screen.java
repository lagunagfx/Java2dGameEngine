package eu.lagunalabs.myapp;

import java.util.Random;

public class Screen {

	private int width, height;
	public int[] pixels;
	private static final int TILE_SIZE = 16;
	private static final int MAP_SIZE_X = 8;
	private static final int MAP_SIZE_Y = 8;
	private static final int MAP_SIZE_X_MASK = MAP_SIZE_X - 1;
	private static final int MAP_SIZE_Y_MASK = MAP_SIZE_Y - 1; 
	private int[] tiles;;
	//private Random random;


	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[ width * height ];
		tiles = new int[ MAP_SIZE_X * MAP_SIZE_Y ];
		//random = new Random();

		for ( int i = 0 ; i < MAP_SIZE_X * MAP_SIZE_Y ; i++ ) {
			//tiles[i] = random.nextInt(0xffffff);
			tiles[i] = ( 0xffffff / 64 ) * i;
		}
	}	

	public void clear() {
		for ( int i = 0 ; i < pixels.length ; i++ ) {
			pixels[i] = 0;
		}
	}

	public void render(int xOffset, int yOffset) {

		for ( int y = 0 ; y < height ; y++ ) {
			int yo = y + yOffset;

			for ( int x = 0 ; x < width ; x++) {

				int xo = x + xOffset;

				int tileIndex = ((xo >> 3) & (MAP_SIZE_X_MASK)) + ((yo >> 3) & (MAP_SIZE_Y_MASK)) * MAP_SIZE_Y;
				pixels[x + y * width] = tiles[tileIndex];

			} // Colums
		} // Rows

	}
}
