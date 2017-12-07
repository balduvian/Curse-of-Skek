package engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class GniRect extends Rect{
	
	private Til2DShader shader = Shader.TIL2DSHADER;
	private Animation texture;
	
	public GniRect(Camera c, String path, int f, double lt) {
		super(c);
		texture = new Animation(path, f, lt);
	}
	
	public void render() {
		texture.bind();
		shader.enable(texture.getFrame());
		shader.setMvp(camera.getProjection().translate(position.add(width/2,height/2,0,new Vector3f())).rotateZ(rotation).scale(width,height,1).translate(-0.5f,-0.5f,0));
		vao.render();
		shader.disable();
		texture.unbind();
	}

}
