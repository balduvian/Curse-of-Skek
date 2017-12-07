package game;

import java.util.HashMap;

public class EntityList {
	
	private HashMap<String, Entity> map;
	
	public EntityList() {
		map = new HashMap<String, Entity>();
	}
	
	public void add(String name, Entity e) {
		map.put(name, e);
	}
	
	public Entity get(String name) {
		return map.get(name).kekClone();
	}
}