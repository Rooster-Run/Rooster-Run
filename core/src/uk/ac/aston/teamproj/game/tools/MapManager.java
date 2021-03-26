package uk.ac.aston.teamproj.game.tools;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;

public class MapManager {
	
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
		
		Map beginner1 = new Map(new Texture("maps/easymap.png"),"maps/map_beginner_fix_ice", 300);
		Map beginner2 = new Map(new Texture("maps/b2.png"), 	"maps/map_beginner2_fix_new",380);
		Map medium1 = 	new Map(new Texture("maps/m2.png"), 	"maps/map_median_fix_new", 	 380);
		Map medium2 = 	new Map(new Texture("maps/m2.png"), 	"maps/map_median2_fix_new",  380);
		Map hard1 = 	new Map(new Texture("maps/hardmap.png"),"maps/map_hard", 			 300);
		Map hard2 = 	new Map(new Texture("maps/h2.png"), 	"maps/map_hard2_fix_new", 	 380);
		Map demo = 		new Map(new Texture("maps/demo.png"), 	"maps/map_demo", 			 180);
		
		allMaps.add(beginner1);
		allMaps.add(beginner2);
		allMaps.add(medium1);
		allMaps.add(medium2);
		allMaps.add(hard1);
		allMaps.add(hard2);
		allMaps.add(demo);
		
		return allMaps;
	}

	public static int getTotalMaps() {
		return maps.size();
	}
}
