package engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class AniRect extends Rect{
	
	private Til2DShader shader = Shader.TIL2DSHADER;
	private Animation texture;
	
	public AniRect(Camera c, String path, int f, double lt) {
		super(c);
		texture = new Animation(path, f, lt);
	}
	
	public void render() {
		texture.bind();
		shader.enable(texture.getFrame());
		Matrix4f transform = new Matrix4f().translate(position.add(width/2,height/2,0,new Vector3f())).rotateZ(rotation).scale(width,height,1).translate(-1f,-1f,0);
		shader.setMvp(camera.getProjview().mul(transform, new Matrix4f()));
		vao.render();
		shader.disable();
		texture.unbind();
	}

}
