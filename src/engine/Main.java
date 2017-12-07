package engine;

abstract public class Main {
	
	private final int FPS = 60;
	
	public static int fps;
	public static double time;
	
	protected Window window;
	protected Camera camera;
	protected Camera hudCamera;
	
	public Main() {
		game();
	}
	
	private void game() {
		
		init();
		
		long usingFPS = 1000000000 / FPS;
		
		long last = System.nanoTime();
		long lastSec = last;
		int frames = 0;
		while(!window.shouldClose()) {
			long now = System.nanoTime();
			if(now-last > usingFPS) {
				time = (now-last)/1000000000d;
				update();
				render();
				last = now; 
				++frames;
			}
			if(now-lastSec > 1000000000) {
				fps = frames; 
				frames = 0;
				lastSec = now;
				System.out.println(fps);
			}
		}
	}
	
	abstract protected void init();

	abstract protected void update();
	
	abstract protected void render();
	
}
