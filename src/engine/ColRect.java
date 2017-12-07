package engine;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class ColRect extends Rect{

	private Col2DShader shader = Shader.COL2DSHADER;
	
	public ColRect(Camera c) {
		super(c);
	}
	
	public void render(float r, float g, float b, float a) {
		shader.enable(r, g, b, a);
		Matrix4f transform = new Matrix4f().translate(position.add(width/2,height/2,0,new Vector3f())).rotateZ(rotation).scale(width,height,1).translate(-1f,-1f,0);
		shader.setMvp(camera.getProjview().mul(transform, new Matrix4f()));
		vao.render();
		shader.disable();
	}

}
