package game;

import engine.Rect;

public class Enemy extends Entity{
	
	private int hp;
	
	private int health;
	private int attack;
	private int speed;
	
	private int type;
	
	public static final int HEALTH0 = 3;
	public static final int ATTACK0 = 1;
	public static final int SPEED0 = 2;
	
	public static final int HEALTH1 = 4;
	public static final int ATTACK1 = 2;
	public static final int SPEED1 = 3;
	
	public static final int HEALTH2 = 5;
	public static final int ATTACK2 = 3;
	public static final int SPEED2 = 4;
	
	public Enemy(Rect r, int t) {
		super(r);
		type = t;
		switch(type) {
			case 0 : 
				health = HEALTH0;
				attack = ATTACK0;
				speed = SPEED0;
				break;
			case 1 :
				health = HEALTH1;
				attack = ATTACK1;
				speed = SPEED1;
				break;
			case 2 :
				health = HEALTH2;
				attack = ATTACK2;
				speed = SPEED2;
				break;
		}
		hp = health;
	}
	
	public Enemy kekClone() {
		Enemy temp = new Enemy(rect, type);
		temp.setLoc(position.x, position.y);
		return temp;
	}
	
	public int getAttack() {
		return attack;
	}
	
	public int getHp() {
		return hp;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public boolean hurt(int damage) {
		hp -= damage;
		return hp<1;
	}
	
}
