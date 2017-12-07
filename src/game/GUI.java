package game;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector3f;

import engine.Camera;
import engine.GniRect;
import engine.GuiRect;
import engine.Rect;
import engine.Window;

public class GUI {
	
	public static final int STATE_NORMAL = 0;
	public static final int STATE_HOVER = 1;
	public static final int STATE_PRESSED = 2;
	public static final int STATE_DISABLED = 3;
	
	private Vector3f position;
	private Rect rect;
	private boolean visible;
	private float width, height;
	
	private double moveTime;
	private float moveX, moveY;
	private boolean moving;
	private double xVel;
	private double yVel;
	
	private int state;
	
	private boolean clicker;
	
	private boolean disabled;
	
	private boolean ready;
	private boolean hover;
	private boolean lock;
	
	private boolean activate;
	
	private int keyCode;
	private boolean keyLock;
	private boolean keyControl;
	
	public GUI(Rect r, float x, float y, float w, float h, boolean c) {
		rect = r;
		visible = false;
		position = new Vector3f(x,y,0);
		width = w;
		height = h;
		moveX = 0;
		moveY = 0;
		moving = false;
		state = STATE_NORMAL;
		disabled = false;
		clicker = c;
		keyCode = 0;
		keyControl = false;
	}
	
	public void setKey(int k) {
		keyControl = true;
		keyCode = k;
	}
	
	public void moveTo(float x, float y, double time) {
		moving = true;
		moveX = x;
		moveY = y;
		xVel = (moveX-position.x)/time;
		yVel = (moveY-position.y)/time;
	}
	
	public void update(Window window, Camera camera) {
		Vector3f mousePos = window.getMouseCoords(camera);
		boolean pressed = window.mousePressed(GLFW_MOUSE_BUTTON_1);
		activate = false;
		if(moving) {
			if(Math.abs(moveX-position.x) <= xVel*Game.time && Math.abs(moveY-position.y) <= yVel*Game.time) {
				moving = false;
				position.x = moveX;
				position.y = moveY;
			}else {
				position.x += xVel*Game.time;
				position.y += yVel*Game.time;
			}
		}
		
		if(clicker) {
			if(!disabled) {
				if(visible) {
					if(mousePos.x >= position.x && mousePos.x <= position.x+width && mousePos.y >= position.y && mousePos.y <= position.y+height) {
						if(state != STATE_HOVER) {
							if(pressed == true) {
								ready = false;
							}else {
								ready = true;
							}
						}
						if(!ready && pressed == false) {
							ready = true;
						}
						hover = true;
						state = STATE_HOVER;
					}else {
						ready = false;
						hover = false;
						state = STATE_NORMAL;
					}
					if( (ready && pressed)) {
						ready = false;
						activate = true;
						lock = true;
						state = STATE_PRESSED;
					}else if( lock && hover) {
						state = STATE_PRESSED;
						if(!pressed) {
							lock = false;
						}
					}else {
						lock = false;
					}
					
					if(keyControl) {
						boolean keyPressed = window.keyPressed(keyCode);
						if(!keyLock && keyPressed) {
							activate = true;
							keyLock = true;
						}
						if(!keyPressed) {
							keyLock = false;
						}
					}
				}
			}else {
				state = STATE_DISABLED;
			}
		}
	}
	
	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}
	
	public void setVisible(boolean v) {
		visible = v;
	}
	
	public void render() {
		if(visible) {
			rect.setDims(width, height);
			rect.setPosition(position);
			if(rect instanceof GuiRect) {
				((GuiRect)rect).render(state, 0);
			}else if(rect instanceof GniRect) {
				((GniRect)rect).render();
			}
		}
	}
	
	public float getheight() {
		return width;
	}
	
	public float getWidth() {
		return height;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public boolean click() {
		return activate;
	}
	
	public void setEnabled(boolean e) {
		disabled = !e;
	}
	
	public boolean getEnabled() {
		return !disabled;
	}
	
	public void setState(int s) {
		state = s;
	}
	
}
