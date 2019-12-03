package it.ifonz.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import it.ifonz.bean.Coord;
import it.ifonz.common.FileReader;

public class Day03 {

	public static void main(String[] args) throws URISyntaxException, IOException {
		var crosses = crossTheWires();
		part1(crosses);
		part2(crosses);
	}

	public static void part1(HashMap<Coord,Integer> crosses) {
		var min = crosses.keySet()
				.parallelStream()
				.min((c1,c2) -> 
				Integer.compare(Math.abs(c1.x)+Math.abs(c1.y), (Math.abs(c2.x)+Math.abs(c2.y)))).get();
		
		System.out.println(Math.abs(min.x)+Math.abs(min.y));
	}
	
	public static void part2(HashMap<Coord,Integer> crosses) {
		var min = crosses.values()
				.parallelStream().min(Integer::compareTo).get();
		System.out.println(min);
	}

	private static HashMap<Coord,Integer> crossTheWires() throws URISyntaxException, IOException {
		var lines = FileReader.readLines("/d03.txt");
		var wire1 = lines.get(0).split(",");
		var wire2 = lines.get(1).split(",");
		var map1 = new HashMap<Coord, Integer>();
		var crosses = new HashMap<Coord, Integer>();
		int x=0;
		int y=0;
		int steps=0;
		map1.put(new Coord(0,0), 0);
		for (String direction : wire1) {
			switch (direction.charAt(0)) {
			case 'U':
				for (int i = 0; i < Integer.parseInt(direction.substring(1)); i++) {
					map1.put(new Coord(x, ++y),++steps);
				}
				break;
			case 'D':
				for (int i = 0; i < Integer.parseInt(direction.substring(1)); i++) {
					map1.put(new Coord(x, --y),++steps);
				}
				break;
			case 'R':
				for (int i = 0; i < Integer.parseInt(direction.substring(1)); i++) {
					map1.put(new Coord(++x, y),++steps);
				}
				break;
			case 'L':
				for (int i = 0; i < Integer.parseInt(direction.substring(1)); i++) {
					map1.put(new Coord(--x, y),++steps);
				}
				break;
			}
		}
		x=0;
		y=0;
		steps = 0;
		for (String direction : wire2) {
			switch (direction.charAt(0)) {
			case 'U':
				for (int i = 0; i < Integer.parseInt(direction.substring(1)); i++) {
					++steps;
					Coord e = new Coord(x, ++y);
					if (map1.containsKey(e)) crosses.put(e,map1.get(e)+steps);
				}
				break;
			case 'D':
				for (int i = 0; i < Integer.parseInt(direction.substring(1)); i++) {
					++steps;
					Coord e = new Coord(x, --y);
					if (map1.containsKey(e)) crosses.put(e,map1.get(e)+steps);
				}
				break;
			case 'R':
				for (int i = 0; i < Integer.parseInt(direction.substring(1)); i++) {
					++steps;
					Coord e = new Coord(++x, y);
					if (map1.containsKey(e)) crosses.put(e,map1.get(e)+steps);
				}
				break;
			case 'L':
				for (int i = 0; i < Integer.parseInt(direction.substring(1)); i++) {
					++steps;
					Coord e = new Coord(--x, y);
					if (map1.containsKey(e))  crosses.put(e,map1.get(e)+steps);
				}
				break;
			}
		}
		return crosses;
	}
		
}
