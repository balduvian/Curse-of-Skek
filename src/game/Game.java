package game;

import static org.lwjgl.glfw.GLFW.*;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import engine.Main;
import engine.Shader;
import engine.Window;
import scenes.GameScene;
import scenes.TitleScene;

import static org.lwjgl.glfw.GLFW.*;

public class Game extends Main{
	
	private Scene current;
	private GameScene gameScene;
	private TitleScene titleScene;
	
	private double aspect;
	
	private boolean fullscreen;
	
	private boolean f11lock;
	
	public static void main(String args[]) {
		new Game();
	}
	
	protected void init() {
		window = new Window(1600,900,"Curse of Skek",true,true);
		
		aspect = 1.7777777777777777;
		
		Shader.init();
		Scene.init(window);
		
		fullscreen = false;
		window.fullscreen(fullscreen);
		
		gameScene = new GameScene();
		titleScene = new TitleScene();
		changeScene(titleScene);
	}
	
	private void changeScene(Scene s) {
		current = s;
		current.begin();
	}
	
	protected void update() {
		window.update();
		
		if(window.keyPressed(GLFW_KEY_F11)) {
			if(!f11lock) {
				f11lock = true;
				fullscreen = !fullscreen;
				window.fullscreen(fullscreen);
			}
		}else {
			f11lock = false;
		}
		
		if(window.keyPressed(GLFW_KEY_ESCAPE)) {
			window.close();
		}
		
		if(window.resized()) {
			window.setSize((int)Math.round(window.getHeight()*aspect), window.getHeight());
		}
		
		current.update();
		
		if(current.requested()) {
			if(current instanceof TitleScene) {
				changeScene(gameScene);
			}else if(current instanceof GameScene) {
				changeScene(titleScene);
			}
		}
	}

	protected void render() {
		window.clear();

		current.render();
		
		window.swap();
	}
}
