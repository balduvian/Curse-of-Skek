package engine;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;

import org.joml.Vector4f;

public class Til2DShader extends Shader {
	
	private int texLoc;
	
	public void enable(Vector4f frame) {
		super.enable();
		glUniform4f(texLoc, frame.x, frame.y, frame.z, frame.w);
	}
	
	protected Til2DShader() {
		super("res/shaders/til2d.vs", "res/shaders/til2d.fs");
		
	}
	
	protected void getUniforms() {
		texLoc = glGetUniformLocation(program, "frame");
	}
	
}
