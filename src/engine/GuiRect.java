package engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class GuiRect extends Rect{
	
	private Til2DShader shader = Shader.TIL2DSHADER;
	private TileSheet texture;
	
	public GuiRect(Camera c, String path, int w, int h) {
		super(c);
		texture = new TileSheet(path, w, h);
	}
	
	public void render(int x, int y) {
		texture.bind();
		shader.enable(texture.getFrame(x, y));
		shader.setMvp(camera.getProjection().translate(position.add(width/2,height/2,0,new Vector3f())).rotateZ(rotation).scale(width,height,1).translate(-0.5f,-0.5f,0));
		vao.render();
		shader.disable();
		texture.unbind();
	}

}
