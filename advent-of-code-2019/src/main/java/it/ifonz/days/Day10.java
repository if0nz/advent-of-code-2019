package it.ifonz.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import it.ifonz.bean.Coord;
import it.ifonz.common.FileReader;

public class Day10 {

	public static void main(String[] args) throws URISyntaxException, IOException {
		var input = FileReader.readLines("/d10.txt");
		char grid[][] = new char[input.get(0).length()][input.size()];
		for (int i = 0; i < input.size(); i++) {
			grid[i] = input.get(i).toCharArray();
		}
		part1(grid);
	}

	public static void part1(char grid[][]) {
		var asteroids = new HashMap<Coord, ArrayList<Coord>>();
		for (int i = 0; i < grid[0].length; i++) {
			for (int j = 0; j < grid.length; j++) {
				var station = new Coord(i, j);
				ArrayList<Coord> asteroidsDetected = new ArrayList<>();
				asteroids.put(station, asteroidsDetected);
				if (grid[i][j] == '#') {
					for (int ii = 0; ii < grid[0].length; ii++) {
						for (int jj = 0; jj < grid.length; jj++) {
							// same coords
							if (Math.abs(ii - i) + Math.abs(jj - j) == 0)
								continue;
							// no asteoroid in the testing coods
							if (grid[ii][jj] != '#')
								continue;
							// 1st asteroid ever seen
							if (asteroidsDetected.isEmpty()) {
								asteroidsDetected.add(new Coord(ii, jj));
								continue;
							}
							// check if this asteroid is obsured by other asteroids already seen
							if (test(asteroidsDetected, ii, jj, station)) {
								asteroidsDetected.add(new Coord(ii, jj));
							}

						}
					}
				}
			}
		}

		System.out.println(asteroids.values().stream().max((a1, a2) -> a1.size() - a2.size()).get().size());
	}

	public static void part2() {

	}

	// 0,4 2,0
	private static boolean test(ArrayList<Coord> asteroidsDetected, int ii, int jj, Coord station) {
		var c = asteroidsDetected.stream().filter(asteroid -> {
			if (asteroid.y != station.y && jj != station.y
					? Math.abs((asteroid.x - station.x) / (asteroid.y - station.y)) == Math
							.abs((ii - station.x) / (jj - station.y))
					: asteroid.x != ii && asteroid.y == jj) {
				System.out.println(asteroid.x + " " + asteroid.y);
				return true;
			}
			return false;
		}).count();
		return c == 0;
	}
}
