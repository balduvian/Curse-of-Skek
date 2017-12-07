package scenes;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.joml.Vector3f;

import engine.AniRect;
import engine.Camera;
import engine.ColRect;
import engine.GniRect;
import engine.GuiRect;
import engine.TexRect;
import engine.TilRect;
import game.Enemy;
import game.Entity;
import game.EntityList;
import game.GUI;
import game.Game;
import game.Scene;
import game.World;
import game.levels.Level1;
import game.levels.Level2;
import game.levels.Level3;

public class GameScene extends Scene{
	
	private Camera camera;
	
	private EntityList entityList;
	
	private ColRect colRect;
	private AniRect playerRect;
	private TilRect stoneTiles;
	private GuiRect cameraGui;
	private GuiRect statsGui;
	private GuiRect inventoryGui;
	private GuiRect barsGui;
	private GuiRect indicatorGui;
	private GuiRect phaseGui;
	private TexRect selectorRect;
	private AniRect enemy0Rect;
	private AniRect enemy1Rect;
	private AniRect enemy2Rect;
	private TexRect endRect;
	private GuiRect loadingGui;
	private GniRect loadAnimGui;
	private GuiRect statsScreenGui;
	private GuiRect buyGui;
	private GuiRect heartGui;
	
	private Level1 level1;
	private Level2 level2;
	private Level3 level3;
	private World current;
	
	private GUI loadScreen;
	private GUI cameraButton;
	private GUI statsButton;
	private GUI inventoryButton;
	private GUI bars;
	private GUI indicator;
	private GUI phaseButton;
	private GUI loadAnim;
	private GUI statsScreen;
	private GUI buyHP;
	private GUI buyATK;
	private GUI buySPE;
	
	private int screenWidth;
	private int screenHeight;
	
	private int upRender;
	private int rightRender;
	private int downRender;
	private int leftRender;
	
	private Entity levelEnd;
	private Entity player;
	private Entity selector;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Enemy> activeEnemies;
	private int enemyMoves;
	private boolean waitForEnemy;
	
	private float viewBuffer;
	
	private int phase;
	
	public static final int UPKEEP_PHASE = 0;
	public static final int MOVEMENT_PHASE = 1;
	public static final int COMBAT_PHASE = 2;
	public static final int ENEMY_PHASE = 3;
	
	public static final int NUM_PHASES = 4;
	
	private Enemy[][] enemySpaces;
	private boolean[][] spaces;
	private boolean spaceDeployed;
	private float spaceR;
	private float spaceG;
	private float spaceB;
	
	private boolean moved;
	private int movesLeft;
	
	public static final int BASE_HEALTH = 10;
	public static final int BASE_ATTACK = 2;
	public static final int BASE_SPEED = 3;
	private int health;
	private int attack;
	private int speed;
	private int hp;
	
	private boolean following;
	private Entity eFollowing;
	
	private boolean mLock;
	
	private boolean loading;
	private int loadFrames;
	
	private int xp;
	
	public static final int NO_MENU = 0;
	public static final int STATS_MENU = 1;
	public static final int INVENTORY_MENU = 2;
	private int menu;
	
	private int range;
	private boolean attacked;
	
	public GameScene() {
		super();
		screenWidth = 16;
		screenHeight = 9;
		
		camera = new Camera(screenWidth, screenHeight);
		
		colRect = new ColRect(camera);
		playerRect = new AniRect(camera, "res/textures/player.png", 2, 2);
		stoneTiles = new TilRect(camera, "res/textures/tiles_stone.png", 5, 3);
		selectorRect = new TexRect(camera, "res/textures/selector.png");
		enemy0Rect = new AniRect(camera, "res/textures/enemy0.png", 4, 4);
		enemy1Rect = new AniRect(camera, "res/textures/enemy1.png", 2, 1.5);
		enemy2Rect = new AniRect(camera, "res/textures/enemy2.png", 2, 2.5);
		endRect = new TexRect(camera, "res/textures/end.png");

		loadAnimGui = new GniRect(camera, "res/textures/running.png", 4, 1);
		loadingGui = new GuiRect(camera, "res/textures/loading.png",1,1);
		cameraGui = new GuiRect(camera, "res/textures/cameraButton.png", 4, 1);
		statsGui = new GuiRect(camera, "res/textures/statsButton.png", 4, 1);
		inventoryGui = new GuiRect(camera, "res/textures/inventoryButton.png", 4, 1);
		barsGui = new GuiRect(camera, "res/textures/bars.png", 1, 1);
		indicatorGui = new GuiRect(camera, "res/textures/indicator.png", 4, 1);
		phaseGui = new GuiRect(camera, "res/textures/phaseButton.png", 4, 1);
		statsScreenGui = new GuiRect(camera, "res/textures/statsScreen.png", 1, 1);
		buyGui = new GuiRect(camera, "res/textures/buyButton.png", 4, 1);
		heartGui = new GuiRect(camera, "res/textures/heart.png", 2, 1);
		
		loadAnim = new GUI(loadAnimGui, 6.5f , 3.5f, 2, 2, false);
		loadScreen = new GUI(loadingGui, 0, 0, 16, 9, false);
		statsButton = new GUI(statsGui, 0, 7, 2, 2, true);
		inventoryButton = new GUI(inventoryGui, 2, 7, 2, 2, true);
		bars = new GUI(barsGui, 4, 7, 3, 2, false);
		cameraButton = new GUI(cameraGui, 7, 8, 1, 1, true);
		indicator = new GUI(indicatorGui, 14, 8, 1, 1, false);
		phaseButton = new GUI(phaseGui, 15, 8, 1, 1, true);
		phaseButton.setKey(GLFW_KEY_I);
		statsScreen = new GUI(statsScreenGui, 0, 2, 6, 5, false);
		buyHP = new GUI(buyGui, 3, 3, 1, 1, true);
		buyATK = new GUI(buyGui, 3, 4, 1, 1, true);
		buySPE = new GUI(buyGui, 3, 5, 1, 1, true);
		
		guis.add(statsButton);
		guis.add(inventoryButton);
		guis.add(bars);
		guis.add(cameraButton);
		guis.add(indicator);
		guis.add(phaseButton);
		guis.add(statsScreen);
		guis.add(buyHP);
		guis.add(buyATK);
		guis.add(buySPE);
		//
		guis.add(loadScreen);
		guis.add(loadAnim);
		
		selector = new Entity(selectorRect);

		entityList = new EntityList();
		Entity playerEntity = new Entity(playerRect);
		Enemy enemy0 = new Enemy(enemy0Rect, 0);
		Enemy enemy1 = new Enemy(enemy1Rect, 1);
		Enemy enemy2 = new Enemy(enemy2Rect, 2);
		Entity end = new Entity(endRect);
		entityList.add("player", playerEntity);
		entityList.add("enemy0", enemy0);
		entityList.add("enemy1", enemy1);
		entityList.add("enemy2", enemy2);
		entityList.add("end", end);
		
		level1 = new Level1(entityList);
		level2 = new Level2(entityList);
		level3 = new Level3(entityList);
		level1.setnext(level2);
		level2.setnext(level3);
		
		viewBuffer = 1.5f;
		mLock = false;
		loading = false;
		loadFrames = 0;
	}
	
	private void changeLevel(World w) {//this is when the level starts
		loading = true;
		loadFrames = 100;
		resetPhase();//this is where we reset the phase when the level starts
		current = w;
		setEntities(current.getKeys());
		current.begin(this);
		resetCamera();
		menu = NO_MENU;
	}
	
	protected void begin() {//when this scene comes in
		changeLevel(level1);
		
		loadScreen.setVisible(true);
		statsButton.setVisible(false);
		inventoryButton.setVisible(false);
		bars.setVisible(false);
		cameraButton.setVisible(true);
		indicator.setVisible(true);
		phaseButton.setVisible(true);
		
		entities.add(selector);
		
		speed = BASE_SPEED;
		health = BASE_HEALTH;
		attack = BASE_ATTACK;
		hp = health;
		
		range = 2;
	}
	
	private void genMovementSpaces() {
		spaceDeployed = true;
		int px = Math.round(player.getPosition().x);
		int py = Math.round(player.getPosition().y);
		spaces = new boolean[3][3];
		spaceR = 1;
		spaceG = 1;
		spaceB = 1;
		
		if(current.access(px,py-1) == World.FLOOR || ( px == levelEnd.getPosition().x && py-1 == levelEnd.getPosition().y)) {
			spaces[1][0] = true;	
		}
		if(current.access(px+1,py) == World.FLOOR || ( px+1 == levelEnd.getPosition().x && py == levelEnd.getPosition().y)) {
			spaces[2][1] = true;	
		}
		if(current.access(px,py+1) == World.FLOOR || ( px == levelEnd.getPosition().x && py+1 == levelEnd.getPosition().y)) {
			spaces[1][2] = true;	
		}
		if(current.access(px-1,py) == World.FLOOR || ( px-1 == levelEnd.getPosition().x && py == levelEnd.getPosition().y)) {
			spaces[0][1] = true;	
		}
	}
	
	private void genAttackSpaces() {
		spaceDeployed = true;
		int px = Math.round(player.getPosition().x);
		int py = Math.round(player.getPosition().y);
		spaces = new boolean[range*2+1][range*2+1];
		enemySpaces = new Enemy[range*2+1][range*2+1];
		spaceR = 1;
		spaceG = 0;
		spaceB = 0;
		
		getActiveEnemies();
		
		for (int i = -range; i <= range; i++) {
			for (int j = -range; j <= range; j++) {
				int nx = (int)player.getPosition().x+i;
				int ny = (int)player.getPosition().y+j;
				for(Enemy e : activeEnemies) {
					if(e.getPosition().x == nx && e.getPosition().y == ny) {
						spaces[i+range][j+range] = true;
						enemySpaces[i+range][j+range] = e;
					}
				}
			}
		}
	}
	
	private void phaseActions() {
		mLock = true;
		switch(phase) {
			case UPKEEP_PHASE :
				selector.setVisible(true);
				
				statsButton.setEnabled(true);
				inventoryButton.setEnabled(true);
				phaseButton.setEnabled(true);
				cameraButton.setEnabled(true);
				phaseButton.setEnabled(true);
				break;
				
			case MOVEMENT_PHASE :
				
				movesLeft = speed;
				
				moved = false;
				genMovementSpaces();
				
				statsButton.setEnabled(false);
				inventoryButton.setEnabled(false);
				phaseButton.setEnabled(true);
				cameraButton.setEnabled(true);
				phaseButton.setEnabled(false);
				break;
				
			case COMBAT_PHASE :
				attacked = false;
				genAttackSpaces();
				
				statsButton.setEnabled(false);
				inventoryButton.setEnabled(false);
				phaseButton.setEnabled(false);
				cameraButton.setEnabled(true);
				phaseButton.setEnabled(true);
				break;
				
			case ENEMY_PHASE :
				
				spaceDeployed = false;
				spaces = null;
				
				selector.setVisible(false);
				setFollow(null);
				
				getActiveEnemies();
				
				statsButton.setEnabled(false);
				inventoryButton.setEnabled(false);
				phaseButton.setEnabled(false);
				cameraButton.setEnabled(false);
				phaseButton.setEnabled(false);
				break;
		}
	}
	
	private void getActiveEnemies() {
		activeEnemies = new ArrayList<Enemy>();
		for(Enemy e : enemies) {
			int ex = (int)e.getPosition().x;
			int ey = (int)e.getPosition().y;
			if(ex >= leftRender && ex <= rightRender && ey >= upRender && ey <= downRender) {
				activeEnemies.add(e);
			}
		}
	}
	
	protected void update() {
		changeRequest = false;
		boolean press = window.mousePressed(GLFW_MOUSE_BUTTON_1);
				
		if(!press) {
			mLock = false;
		}
		for(GUI g : guis) {
			g.update(window, camera);
		}
		if(cameraButton.click()) {
			resetCamera();
		}
		if(phaseButton.click()) {
			incrementPhase();
		}
		if(statsButton.click()) {
			if(menu != STATS_MENU) {
				menu = STATS_MENU;
				statsScreen.setVisible(true);
				buyHP.setVisible(true);
				buyATK.setVisible(true);
				buySPE.setVisible(true);
			}else if(menu == STATS_MENU){
				menu = NO_MENU;
				statsScreen.setVisible(false);
				buyHP.setVisible(false);
				buyATK.setVisible(false);
				buySPE.setVisible(false);
			}
		}
		if(!statsButton.getEnabled()) {
			menu = NO_MENU;
			statsScreen.setVisible(false);
			buyHP.setVisible(false);
			buyATK.setVisible(false);
			buySPE.setVisible(false);
		}

		indicator.setState(phase);
		
		Vector3f mp = window.getMouseCoords(camera);
		int tarX = Math.round(mp.x+camera.getX());
		int tarY = Math.round(mp.y+camera.getY());
		selector.setLoc(tarX, tarY);
		
		if(phase == MOVEMENT_PHASE) {
			
			if(!moved && press && onSpace(tarX, tarY) && !mLock) {
				moved = true;
				int vNed = (int) (tarY-player.getPosition().y);
				
				int hNed = (int) (tarX-player.getPosition().x);
				
				for (int i = 0; i < hNed; ++i) {
					player.queueMove(Entity.RIGHT);
				}
				for (int i = 0; i > hNed; --i) {
					player.queueMove(Entity.LEFT);
				}
				
				for (int i = 0; i < vNed; ++i) {
					player.queueMove(Entity.DOWN);
				}
				for (int i = 0; i > vNed; --i) {
					player.queueMove(Entity.UP);
				}
				
				spaceDeployed = false;
				spaces = null;
				setFollow(player);
			}
			
			if(following) {
				phaseButton.setEnabled(false);
				selector.setVisible(false);
			}else {
				selector.setVisible(true);
			}
			
			if(following && eFollowing.moveComplete()) {
				--movesLeft;
				setFollow(null);
				if(movesLeft == 0) {
					phaseButton.setEnabled(true);
				}else {
					moved = false;
					genMovementSpaces();
				}
			}
		}else if(phase == COMBAT_PHASE){
			
			if(!attacked && !mLock && press && onSpace(tarX, tarY)) {
				
				Enemy target = enemySpaces[(int)(tarX-player.getPosition().x+range)][(int)(tarY-player.getPosition().y+range)];
				if(target.hurt(attack)) {
					enemies.remove(target);
					entities.remove(target);
				}
				
				spaces = null;
				enemySpaces = null;
				attacked = true;
				spaceDeployed = false;
			}
			
		}else if(phase == ENEMY_PHASE) {
			if(!following) {
				if(activeEnemies.size()>0) {
					waitForEnemy = false;
					setFollow(activeEnemies.get(0));
					activeEnemies.remove(0);
				}else {
					incrementPhase();
				}
			}else {
				if(eFollowing.moveComplete()) {
					waitForEnemy = false;
					--enemyMoves;
					if(enemyMoves==0) {
						if(Math.abs(eFollowing.getPosition().x-player.getPosition().x) <= 2 && Math.abs(eFollowing.getPosition().y-player.getPosition().y) <= 2 ) {
							hp -= ((Enemy)eFollowing).getAttack();
						}
						setFollow(null);
					}
					System.out.println("enemy moves : "+enemyMoves);
				}else {
					if(!waitForEnemy) {
						if(enemyMoves == 0) {
							enemyMoves = ((Enemy)eFollowing).getSpeed();
						}
						ArrayList<Integer> possible = new ArrayList<Integer>();
						int px = (int)player.getPosition().x;
						int py = (int)player.getPosition().y;
						int ex = (int)eFollowing.getPosition().x;
						int ey = (int)eFollowing.getPosition().y;
						if(current.access(ex, ey-1) == World.FLOOR) {
							possible.add(0);
							if(py<ey) {
								possible.add(0);
								possible.add(0);
							}
						}
						if(current.access(ex+1, ey) == World.FLOOR) {
							possible.add(1);
							if(px>ex) {
								possible.add(1);
								possible.add(1);
							}
						}
						if(current.access(ex, ey+1) == World.FLOOR) {
							possible.add(2);
							if(py>ey) {
								possible.add(2);
								possible.add(2);
							}
						}
						if(current.access(ex-1, ey) == World.FLOOR) {
							possible.add(3);
							if(px<ex) {
								possible.add(3);
								possible.add(3);
							}
						}
						eFollowing.queueMove(possible.get((int)(Math.random()*possible.size())));
						waitForEnemy = true;
					}
				}
			}
		}
		
		if(!following) {
			if(window.keyPressed(GLFW_KEY_W)) {
				camera.move(0, -(float)(4*Game.time), 0);
			}
			if(window.keyPressed(GLFW_KEY_A)) {
				camera.move(-(float)(4*Game.time), 0, 0);
			}
			if(window.keyPressed(GLFW_KEY_S)) {
				camera.move(0, (float)(4*Game.time), 0);
			}
			if(window.keyPressed(GLFW_KEY_D)) {
				camera.move((float)(4*Game.time), 0, 0);
			}
			
			if(camera.getX() > player.getPosition().x + viewBuffer -screenWidth/2f) {
				camera.setX(player.getPosition().x + viewBuffer -screenWidth/2f);
			}else if(camera.getX() < player.getPosition().x - viewBuffer -screenWidth/2f) {
				camera.setX(player.getPosition().x - viewBuffer -screenWidth/2f);
			}
			if(camera.getY() > player.getPosition().y + viewBuffer -screenHeight/2f) {
				camera.setY(player.getPosition().y + viewBuffer -screenHeight/2f);
			}else if(camera.getY() < player.getPosition().y - viewBuffer -screenHeight/2f) {
				camera.setY(player.getPosition().y - viewBuffer -screenHeight/2f);
			}
		}else {
			camera.setCenter(eFollowing.getPosition());
		}
		
		float cx = camera.getX();
		float cy = camera.getY();
		
		upRender = (int)Math.round(cy);
		leftRender = (int)Math.round(cx);
		downRender = (int)Math.round(cy+screenHeight);
		rightRender = (int)Math.round(cx+screenWidth);
		
		for(Entity e : entities) {
			e.update();
		}
		//now do some litty entity interactions
		if(levelEnd != null && player.getPosition().x == levelEnd.getPosition().x && player.getPosition().y == levelEnd.getPosition().y ) {
			changeLevel(current.getNext());
		}
		if(hp<1) {
			changeRequest=true;
		}
		
		if(loadFrames>0) {
			loadFrames--;
		}else {
			loading = false;
		}
		if(loading) {
			loadScreen.setVisible(true);
			loadAnim.setVisible(true);
			resetCamera();
		}else {
			loadScreen.setVisible(false);
			loadAnim.setVisible(false);
		}
		
		current.update(this);
		
		camera.update();
	}
	
	protected void render() {
		
			stoneTiles.setDims(1, 1);
			for (int i = leftRender; i <= rightRender; i++) {
				for (int j = upRender; j <= downRender; j++) {
					int block = current.access(i, j);
					
					stoneTiles.setPosition(i,j,0);
					
					int[] around = new int[] {current.access(i, j-1), current.access(i+1, j), current.access(i, j+1), current.access(i-1, j), current.access(i-1, j-1), current.access(i+1, j-1), current.access(i+1, j+1), current.access(i-1, j+1)};
					
					if(block == World.FLOOR){
						stoneTiles.render(3,2);
					}else if(block == World.TERRAIN) {
						
						boolean up    = around[0] == World.TERRAIN;
						boolean right = around[1] == World.TERRAIN;
						boolean down  = around[2] == World.TERRAIN;
						boolean left  = around[3] == World.TERRAIN;
						boolean ul    = around[4] == World.TERRAIN;
						boolean ur    = around[5] == World.TERRAIN;
						boolean dr    = around[6] == World.TERRAIN;
						boolean dl    = around[7] == World.TERRAIN;
						
						if(up && right && down && left && ul && ur && dr && dl) {
							stoneTiles.render(1,1);
						}else if(up && right && down && left && ul && ur && dr && !dl) {
							stoneTiles.render(4,0);
						}else if(up && right && down && left && ul && ur && !dr && dl) {
							stoneTiles.render(3,0);
						}else if(up && right && down && left && ul && !ur && dr && dl) {
							stoneTiles.render(3,1);
						}else if(up && right && down && left && !ul && ur && dr && dl) {
							stoneTiles.render(4,1);
						}else if(up && right && down && !left && ur && dr) {
							stoneTiles.render(0,1);
						}else if(up && right && !down && left && ul && ur) {
							stoneTiles.render(1,2);
						}else if(up && !right && down && left && ul && dl) {
							stoneTiles.render(2,1);
						}else if(!up && right && down && left && dr && dl) {
							stoneTiles.render(1,0);
						}else if(up && right && !down && !left && ur && !dl) {
							stoneTiles.render(0,2);
						}else if(up && !right && !down && left && ul && !dr) {
							stoneTiles.render(2,2);
						}else if(!up && !right && down && left && !ur && dl) {
							stoneTiles.render(2,0);
						}else if(!up && right && down && !left && !ul && dr) {
							stoneTiles.render(0,0);
						}
						
					}else if(block == World.BLOCK) {
						stoneTiles.render(4, 2);
					}
				}
			}
			
			for(Entity e : entities) {
				e.Render();
			}
			
			if(spaceDeployed) {
				int half = spaces.length/2;
				for(int i = -half ; i <= half; ++i) {
					for (int j = -half; j <= half; j++) {
						if(spaces[i+half][j+half]) {
							colRect.setPosition(player.getPosition().x+i, player.getPosition().y+j);
							colRect.render(spaceR, spaceG, spaceB, 0.2f);
						}
					}
				}
			}
			
			int hpc = hp;
			heartGui.setDims(0.5f, 0.5f);
			for (int i = 0; i < health; i++) {
				heartGui.setPosition(i/2f, 0);
				if(hpc>0) {
					heartGui.render(0,0);
				}else {
					heartGui.render(1,0);
				}
				hpc--;
			}
			for(GUI g : guis) {
				g.render();
			}
	}
	
	private void resetCamera() {
		camera.setCenter(player.getPosition());
	}
	
	private boolean onSpace(int x, int y) {
		try {
			int rx = Math.round(x-player.getPosition().x+spaces.length/2);
			int ry = Math.round(y-player.getPosition().y+spaces.length/2);
			return spaces[rx][ry];
		}catch(ArrayIndexOutOfBoundsException ex) {
			return false;
		}
	}
	
	public void setPlayer(Entity e) {
		player = e;
	}
	
	public void setEnd(Entity e) {
		levelEnd = e;
	}
	
	public void setEnemies(ArrayList<Enemy> e) {
		enemies = new ArrayList<Enemy>(e);
	}
	
	private void resetPhase() {
		phase = 0;
		phaseActions();
	}
	
	private void incrementPhase() {
		++phase;
		phase = phase % NUM_PHASES;
		phaseActions();
	}
	
	private void setFollow(Entity e) {
		if(e == null) {
			following = false;
		}else {
			following = true;
			eFollowing = e;
		}
	}
	
}