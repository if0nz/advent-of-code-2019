package it.ifonz.days;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import it.ifonz.bean.Orbit;
import it.ifonz.common.FileReader;

public class Day06 {

	public static void main(String[] args) throws Exception {
		var input = FileReader.readLines("/d06.txt");
		var orbits = input.parallelStream().map(o -> new Orbit(o.split("\\)"))).collect(Collectors.toList());
		part1(orbits);
		part2(orbits);
	}

	public static void part1(List<Orbit> orbits) {
		HashMap<String, Integer> distances = new HashMap<>();
		var planets = orbits.stream().map(s -> s.orbiting).distinct().collect(Collectors.toList());
		System.out.println(planets.stream().mapToInt(s -> distanceFromCenterPlanet(s, distances, orbits)).sum());
	}

	public static void part2(List<Orbit> orbits) {
		var sanPathToCom = new ArrayDeque<String>();
		sanPathToCom.add("SAN");
		while (!sanPathToCom.getLast().equals("COM")) {
			sanPathToCom.add(orbits.stream().filter(o -> o.orbiting.equals(sanPathToCom.getLast())).findFirst()
					.get().center);
		}
		var youPathToCom = new ArrayDeque<String>();
		youPathToCom.add("YOU");
		while (!youPathToCom.getLast().equals("COM")) {
			youPathToCom.add(orbits.stream().filter(o -> o.orbiting.equals(youPathToCom.getLast())).findFirst()
					.get().center);
		}
		String cross = sanPathToCom.stream().flatMap(s -> youPathToCom.stream().filter(p -> p.equals(s))).findFirst()
				.get();
		var p1 = new ArrayList<>(sanPathToCom).indexOf(cross);
		var p2 = new ArrayList<>(youPathToCom).indexOf(cross);
		// -2 removes the last hop from both the paths (puzzle requires to just reach
		// the orbit, you don't have to reach the final planet)
		System.out.println(p1 + p2 - 2);
	}

	private static int distanceFromCenterPlanet(String planet, HashMap<String, Integer> distances, List<Orbit> orbits) {
		try {
			if (distances.containsKey(planet))
				return 1 + distances.get(planet);
			Orbit orbit = orbits.stream().filter(o -> o.orbiting.equals(planet)).findAny().get();
			if (orbit.center.equals("COM"))
				return 1;
			var d = distanceFromCenterPlanet(orbit.center, distances, orbits);
			distances.put(planet, d);
			return 1 + d;
		} catch (Exception e) {
			return 0;
		}
	}
	
}
