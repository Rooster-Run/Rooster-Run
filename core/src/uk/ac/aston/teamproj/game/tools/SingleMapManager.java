package uk.ac.aston.teamproj.game.tools;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;

public class SingleMapManager {
	
	private static List<Map> maps = initialiseMaps();
	
	public static Map getMapByPath(String path) {
		for (Map map: maps) {
			if (map.getPath().equals(path)) {
				return map;
			}
		}
		return null;
	}
	
	public static Map getMapByIndex(int index) {
		return maps.get(index);
	}
	
	public static List<Map> initialiseMaps() {
		List<Map> allMaps = new ArrayList<>();
		
		Map beginner1 = new Map(new Texture("maps/previews/beginner1.png"),	"maps/map_b1_single",  300);
		Map beginner2 = new Map(new Texture("maps/previews/beginner2.png"), "maps/map_b2_single",  380);
		Map medium1 = 	new Map(new Texture("maps/previews/medium1.png"), 	"maps/map_m1_single",  380);
		Map medium2 = 	new Map(new Texture("maps/previews/medium2.png"), 	"maps/map_m2_single",  380);
		Map hard1 = 	new Map(new Texture("maps/previews/hard1.png"),		"maps/map_h1_single",  300);
		Map hard2 = 	new Map(new Texture("maps/previews/hard2.png"), 	"maps/map_h2_single",  380);
		
		allMaps.add(beginner1);
		allMaps.add(beginner2);
		allMaps.add(medium1);
		allMaps.add(medium2);
		allMaps.add(hard1);
		allMaps.add(hard2);
		
		return allMaps;
	}

	public static int getTotalMaps() {
		return maps.size();
	}
}
