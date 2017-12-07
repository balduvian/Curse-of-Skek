package engine;

import org.joml.Vector4f;

import game.Game;

public class Animation extends Texture{
	
	private float fw;
	private int frames;
	private double timer;
	private double loopTime;
	
	public Animation(String path, int f, double lt) {
		super(path);
		fw = 1f/f;
		frames = f;
		loopTime = lt;
	}
	
	public Vector4f getFrame() {
		timer += Game.time;
		timer = timer%loopTime;
		int current = (int)Math.round((timer/loopTime) * frames);
		return new Vector4f(fw, 1, current*fw, 1);
	}
}
