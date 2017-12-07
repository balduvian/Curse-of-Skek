package engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class TilRect extends Rect{
	
	private Til2DShader shader = Shader.TIL2DSHADER;
	private TileSheet texture;
	
	public TilRect(Camera c, String path, int w, int h) {
		super(c);
		texture = new TileSheet(path, w, h);
	}
	
	public void render(int x, int y) {
		texture.bind();
		shader.enable(texture.getFrame(x, y));
		Matrix4f transform = new Matrix4f().translate(position.add(width/2,height/2,0,new Vector3f())).rotateZ(rotation).scale(width,height,1).translate(-1f,-1f,0);
		shader.setMvp(camera.getProjview().mul(transform, new Matrix4f()));
		vao.render();
		shader.disable();
		texture.unbind();
	}

}
