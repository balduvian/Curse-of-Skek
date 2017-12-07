package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import engine.Camera;
import engine.Window;

abstract public class Scene {
	
	protected boolean changeRequest;
	
	protected static Window window;
	public static void init(Window w) {
		window = w;
	}
	
	protected ArrayList<Entity> entities;
	protected ArrayList<GUI> guis;
	
	public Scene() {
		entities = new ArrayList<Entity>();
		guis = new ArrayList<GUI>();
	}
	
	protected void setEntities(ArrayList<Entity> e) {
		entities = e;
	}
	
	public boolean requested() {
		return changeRequest;
	}
	
	protected abstract void begin();
	
	protected abstract void update();
	
	protected abstract void render();
}
