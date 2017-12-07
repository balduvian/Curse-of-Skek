package engine;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.joml.Matrix4f;
import org.joml.Vector4f;

abstract public class Shader {
	
	public static Tex2DShader TEX2DSHADER;
	public static Col2DShader COL2DSHADER;
	public static Til2DShader TIL2DSHADER;
	
	protected int program;
	
	protected int mvpLoc;
	
	public static void init() {
		TEX2DSHADER = new Tex2DShader();
		COL2DSHADER = new Col2DShader();
		TIL2DSHADER = new Til2DShader();
	}
	
	protected Shader(String vertPath, String fragPath) {
		program = glCreateProgram();
		int vert = loadShader(vertPath, GL_VERTEX_SHADER);
		int frag = loadShader(fragPath, GL_FRAGMENT_SHADER);
		glAttachShader(program, vert);
		glAttachShader(program, frag);
		glLinkProgram(program);
		glDetachShader(program, vert);
		glDetachShader(program, frag);
		glDeleteShader(vert);
		glDeleteShader(frag);
		
		mvpLoc = glGetUniformLocation(program, "mvp");
		
		getUniforms();
	}
	
	private int loadShader(String path, int type) {
		StringBuilder build = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
			String line;
			while((line = br.readLine()) != null) {
				build.append(line);
				build.append('\n');
			}
			br.close();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		String src = build.toString();
		int shader = glCreateShader(type);
		glShaderSource(shader, src);
		
		glCompileShader(shader);
		if(glGetShaderi(shader,GL_COMPILE_STATUS) != 1) {
			throw new RuntimeException("Failed to compile shader p uan | "+path+" | "+type+" | "+glGetShaderInfoLog(shader));
		}
		return shader;
	}
	
	protected abstract void getUniforms();
	
	public void setMvp(Matrix4f m) {
		glUniformMatrix4fv(mvpLoc, false, m.get(new float[16]));
	}
	
	public void enable() {
		glUseProgram(program);
	}
	
	public void disable() {
		glUseProgram(0);
	}
	
	public void destroy() {
		glDeleteProgram(program);
	}
}
