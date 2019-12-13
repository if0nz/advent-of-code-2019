package it.ifonz.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import it.ifonz.bean.Coord;
import it.ifonz.common.FileReader;

public class Day10 {

	// FIXME add a lot of comments to explain what I did and why. and refactor this
	// s**t.
	public static void main(String[] args) throws URISyntaxException, IOException {
		var input = FileReader.readLines("/d10.txt");
		char grid[][] = new char[input.get(0).length()][input.size()];
		for (int i = 0; i < input.size(); i++) {
			grid[i] = input.get(i).toCharArray();
		}
		part1(grid);
		part2(grid);
	}

	public static void part1(char grid[][]) {
		var asteroids = new HashMap<Coord, ArrayList<Coord>>();
		for (int station_x = 0; station_x < grid.length; station_x++) {
			for (int station_y = 0; station_y < grid.length; station_y++) {
				var station = new Coord(station_x, station_y);
				ArrayList<Coord> asteroidsDetected = new ArrayList<>();
				asteroids.put(station, asteroidsDetected);
				if ('#' == grid[station_y][station_x]) {
					for (int asteroid_x = 0; asteroid_x < grid.length; asteroid_x++) {
						for (int asteroid_y = 0; asteroid_y < grid.length; asteroid_y++) {
							// same coords
							if (Math.abs(asteroid_y - station_y) + Math.abs(asteroid_x - station_x) == 0)
								continue;
							// no asteoroid in the testing coods
							if ('#' != grid[asteroid_y][asteroid_x])
								continue;
							// 1st asteroid ever seen
							if (asteroidsDetected.isEmpty()) {
								asteroidsDetected.add(new Coord(asteroid_x, asteroid_y));
							}
							// check if this asteroid is obsured by other asteroids already seen
							else if (test(asteroidsDetected, asteroid_x, asteroid_y, station) == null) {
								asteroidsDetected.add(new Coord(asteroid_x, asteroid_y));
							}
						}
					}
				}
			}
		}

		System.out.println(asteroids.values().stream().max((a1, a2) -> a1.size() - a2.size()).get().size());
	}

	public static void part2(char grid[][]) {
		var asteroids = new HashMap<Coord, ArrayList<Coord>>();
		for (int station_x = 0; station_x < grid.length; station_x++) {
			for (int station_y = 0; station_y < grid.length; station_y++) {
				var station = new Coord(station_x, station_y);
				ArrayList<Coord> asteroidsDetected = new ArrayList<>();
				asteroids.put(station, asteroidsDetected);
				if ('#' == grid[station_y][station_x]) {
					for (int asteroid_x = 0; asteroid_x < grid.length; asteroid_x++) {
						for (int asteroid_y = 0; asteroid_y < grid.length; asteroid_y++) {
							// same coords
							var currentAsteroid = new Coord(asteroid_x, asteroid_y);
							
							if (station.distance(currentAsteroid) == 0) {
							}
							// no asteoroid in the testing coods
							else if ('#' != grid[asteroid_y][asteroid_x]) {
							}
							// 1st asteroid ever seen
							else if (asteroidsDetected.isEmpty()) {
								asteroidsDetected.add(new Coord(asteroid_x, asteroid_y));
							}
							// check if this asteroid is obsured by other asteroids already seen
							else {
								// if it's not insert it into the list
								Coord existingAsteroid = test(asteroidsDetected, asteroid_x, asteroid_y, station);
								if (existingAsteroid == null) 
									asteroidsDetected.add(currentAsteroid);
								// else test its distance with the one laying in the same line. if the asteroid is nearer to the station than the existing one, then replace the latter with the former
								else {
									if (currentAsteroid.distance(station) < existingAsteroid.distance(station)) {
										asteroidsDetected.remove(existingAsteroid);
										asteroidsDetected.add(currentAsteroid);
									}
								}
							}
						}
					}
				}
			}
		}
		var station = asteroids.entrySet().stream().max((e1, e2) -> e1.getValue().size() - e2.getValue().size()).get();
		var stationCoords = station.getKey();
		var destroyingAsteroids = station.getValue();
		destroyingAsteroids.sort((c1, c2) -> {
			return extracted(stationCoords, c1, c2);
		});

		Coord asteroid200 = destroyingAsteroids.get(200);
//		for (Coord c : destroyingAsteroids) {
//			if (c.equals(a1)) break;
//			System.out.println(extracted(stationCoords, a1, c));
//		}
		System.out.println(extracted(stationCoords, new Coord(10,16), new Coord(10,9)));
		System.out.println(asteroid200.x * 100 + asteroid200.y);

	}

	private static int extracted(Coord stationCoords, Coord c1, Coord c2) {
		var angle1 = Math.atan2(c1.y - stationCoords.y, c1.x - stationCoords.x);
		var angle2 = Math.atan2(c2.y - stationCoords.y, c2.x - stationCoords.x);
		angle1 = angle1 < -Math.PI/2 ? angle1 + Math.PI : angle1;
		angle2 = angle2 < -Math.PI/2 ? angle2 + Math.PI : angle2;
		return Double.compare(angle1, angle2);
	}

	// 0,4 2,0
	private static Coord test(ArrayList<Coord> asteroidsDetected, int asteroid_x, int asteroid_y, Coord station) {
		var c = asteroidsDetected.stream().filter(asteroid -> {
			return testSingle(asteroid, station, asteroid_x, asteroid_y);
		}).findFirst().orElse(null);
		return c;
	}

	private static boolean testSingle(Coord asteroid, Coord station, int asteroid_x, int asteroid_y) {
		/*
		 * if 2 points on a plane are aligned with an origin point, then they have the
		 * same angle with the origin. to test this, one can check if tan(origin,a) ==
		 * tan(origin,b). to avoid division by zeros, one can apply arithemtic
		 * properties to transorm equality between fractions in equality between
		 * products.
		 */
		if (((asteroid.x - station.x) * (asteroid_y - station.y)) == ((asteroid_x - station.x)
				* (asteroid.y - station.y))
				&& Math.signum(asteroid.x - station.x) == Math.signum(asteroid_x - station.x)
				&& Math.signum(asteroid.y - station.y) == Math.signum(asteroid_y - station.y)) {
			return true;
		}
		return false;
	}
}
