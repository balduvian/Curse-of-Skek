package game;

import java.util.ArrayList;

import org.joml.Vector3f;

import engine.AniRect;
import engine.Rect;
import engine.TexRect;

public class Entity{
	
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	
	protected Rect rect;
	private float height;
	private float width;
	protected Vector3f position;
	
	private int tag;
	private int[] subTag;
	
	private boolean moving;
	private double xVel;
	private double yVel;
	
	private float targetX;
	private float targetY;
	
	private boolean complete;
	
	private boolean invisible;
	
	ArrayList<Integer> stack;
	
	public Entity(Rect t) {
		rect = t;
		width = 1;
		height = 1;
		tag = 0;
		position = new Vector3f();
		stack = new ArrayList<Integer>();
		moving = false;
		xVel = 0;
		yVel = 0;
		targetX = 0;
		targetY = 0;
		invisible = false;
	}

	public void queueMove(int dir){
		stack.add(dir);
	}
	
	public void update() {
		complete = false;
		if(!moving) {
			if(stack.size()>0) {
				int dir = stack.get(0);
				switch (dir) {
					case UP :
						xVel = 0;
						yVel = -2;
						targetX = position.x;
						targetY = position.y-1;
						break;
					case RIGHT :
						xVel = 2;
						yVel = 0;
						targetX = position.x+1;
						targetY = position.y;
						break;
					case DOWN :
						xVel = 0;
						yVel = 2;
						targetX = position.x;
						targetY = position.y+1;
						break;
					case LEFT :
						xVel = -2;
						yVel = 0;
						targetX = position.x-1;
						targetY = position.y;
						break;
				}
				moving = true;
				stack.remove(0);
			}
		}else {
			if( (Math.pow(targetX-position.x,2) + Math.pow(targetY-position.y,2) ) <= Math.pow((xVel+yVel)*Game.time,2) ) {
				position.x = targetX;
				position.y = targetY;
				moving = false;
				if(stack.size() == 0) {
					complete = true;
				}
			}else {
				position.x += xVel*Game.time;
				position.y += yVel*Game.time;
			}
		}
	}
	
	public void setLoc(float x, float y) {
		position.x = x;
		position.y = y;
	}
	
	public void setZ(float z){
		position.z = z;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setDims(float w, float h) {
		width = w;
		height = h;
	}
	
	public int getTag() {
		return tag;
	}
	
	public void setTag(int v) {
		tag = v;
	}
	
	public void initSub(int[] tags) {
		subTag = tags;
	}
	
	public void setSub(int i, int v) {
		subTag[i] = v;
	}
	
	public int getSubTag(int i) {
		return subTag[i];
	}
	
	public void Render() {
		if(!invisible) {
			rect.setDims(width, height);
			rect.setPosition(position);
			if(rect instanceof AniRect) {
				((AniRect)rect).render();
			}else {
				((TexRect)rect).render();
			}
		}
	}
	
	public Entity kekClone() {
		Entity temp = new Entity(rect);
		temp.setLoc(position.x, position.y);
		return temp;
	}
	
	public boolean moveComplete() {
		return complete;
	}
	
	public void setVisible(boolean v) {
		invisible = !v;
	}
}