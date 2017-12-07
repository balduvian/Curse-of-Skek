package engine;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class TexRect extends Rect{

	private Tex2DShader shader = Shader.TEX2DSHADER;
	private Texture texture;
	
	public TexRect(Camera c, String path) {
		super(c);
		texture = new Texture(path);
	}
	
	public void render() {
		texture.bind();
		shader.enable();
		Matrix4f transform = new Matrix4f().translate(position.add(width/2,height/2,0,new Vector3f())).rotateZ(rotation).scale(width,height,1).translate(-1f,-1f,0);
		shader.setMvp(camera.getProjview().mul(transform, new Matrix4f()));
		vao.render();
		shader.disable();
		texture.unbind();
	}

}
