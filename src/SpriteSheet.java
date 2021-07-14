package eu.lagunalabs.myapp.graphics;

public class SpriteSheet {
	private String path;
	private final int SIZE;
	public int[] pixels;

	public SpriteSheet(String path, int size) {
		this.path = path;
		this.SIZE = size;
		pixels = new int[SIZE*SIZE];
	}
}
