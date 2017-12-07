package engine;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;

public class Col2DShader extends Shader {

	private int colorLoc;
	
	protected Col2DShader() {
		super("res/shaders/col2d.vs", "res/shaders/col2d.fs");
		
	}
	
	protected void getUniforms() {
		colorLoc = glGetUniformLocation(program, "color");
	}
	
	public void enable(float r, float g, float b, float a) {
		super.enable();
		glUniform4f(colorLoc, r, g, b, a);
	}
}
