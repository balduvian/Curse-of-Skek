package game.levels;

import game.Entity;
import game.EntityList;
import game.World;
import scenes.GameScene;

import java.util.ArrayList;

import game.Enemy;

public class Level3 extends World{
	
	Entity player;
	Entity end;
	
	ArrayList<Enemy> enemies;
	
	public Level3(EntityList e) {
		super("res/levels/level3.png", e, 0);
		
		player = entityList.get("player");
		player.setLoc(25, 25);
		
		end = entityList.get("end");
		end.setLoc(17, 1);
		
		enemies = new ArrayList<Enemy>();
		
		Enemy enemy0 = (Enemy)entityList.get("enemy0");
		enemy0.setLoc(3, 10);
		enemies.add(enemy0.kekClone());
		enemy0.setLoc(10, 18);
		enemies.add(enemy0.kekClone());
		enemy0.setLoc(18, 9);
		enemies.add(enemy0.kekClone());
		
		ledger.add(end);
		ledger.add(player);
		ledger.addAll(enemies);
		
		setKeys(ledger);
	}

	public void begin(GameScene gs) {
		gs.setPlayer(player);
		gs.setEnd(end);
		gs.setEnemies(enemies);
	}

	public void update(GameScene gs) {

	}

}