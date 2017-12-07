package scenes;

import static org.lwjgl.glfw.GLFW.*;

import engine.Camera;
import engine.GuiRect;
import game.GUI;
import game.Scene;

public class TitleScene extends Scene{

	private Camera camera;
	private GuiRect backgroundRect;
	private GuiRect titleRect;
	private GuiRect playRect;
	
	private GUI background;
	private GUI title;
	private GUI play;
	
	private int screenWidth;
	private int screenHeight;
	
	public TitleScene() {
		super();
		screenWidth = 16;
		screenHeight = 9;
		camera = new Camera(screenWidth, screenHeight);
		
		backgroundRect = new GuiRect(camera,"res/textures/titleBackground.png", 1, 1 );
		titleRect = new GuiRect(camera, "res/textures/titleCard.png", 1, 1 );
		playRect = new GuiRect(camera, "res/textures/playButton.png", 4, 1 );
		
		background = new GUI(backgroundRect, 0, 0, 16, 9, false);
		title = new GUI(titleRect, 1, 2, 14, 2, false);
		play = new GUI(playRect, 6, 6, 4, 1, true);
		play.setKey(GLFW_KEY_ENTER);
		
		guis.add(background);
		guis.add(title);
		guis.add(play);
	}
	
	protected void begin() {
		background.setVisible(true);
		title.setVisible(true);
		play.setVisible(true);
	}

	protected void update() {
		changeRequest = false;
		for(GUI g : guis) {
			g.update(window, camera);
		}
		if(play.click()) {
			changeRequest = true;
		}
	}

	protected void render() {
		for(GUI g : guis) {
			g.render();
		}
	}

}
