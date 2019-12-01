package it.ifonz.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import it.ifonz.common.FileReader;

public class Day01 {

	public static void main(String[] args) throws URISyntaxException, IOException {
		var modules = FileReader.readNumericLines("/d01.txt");
		part1(modules);
		part2(modules);
	}
	
	public static void part1(List<Long> modules) throws URISyntaxException, IOException {
		var s = modules.parallelStream().mapToLong(m -> ((long)m/3)-2).sum();
		System.out.println(s);
	}
	
	public static void part2(List<Long> modules) throws URISyntaxException, IOException {
		var s = modules.parallelStream().mapToLong(m -> recursiveFuel(m)).sum();
		System.out.println(s);
	}
	
	public static long recursiveFuel(long mass) {
		return mass < 9 ? 0 : ((long)mass/3)-2+recursiveFuel(((long)mass/3-2));
	}
}
