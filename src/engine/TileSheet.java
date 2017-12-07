package engine;

import org.joml.Vector4f;

public class TileSheet extends Texture{

	private float fw;
	private float fh;
	
	public TileSheet(String path, int framesWide, int framesTall) {
		super(path);
		fw = 1f/framesWide;
		fh = 1f/framesTall;
	}
	
	public Vector4f getFrame(int x, int y) {
		return new Vector4f(fw, fh, x*fw, y*fh);
	}
}
