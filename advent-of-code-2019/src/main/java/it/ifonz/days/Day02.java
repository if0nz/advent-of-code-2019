package it.ifonz.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.ifonz.common.FileReader;

public class Day02 {

	public static void main(String args[]) throws URISyntaxException, IOException {
		var input = FileReader.readLine("/d02.txt").split(",");
		var a = new Integer[input.length];
		a = Arrays.stream(input).map(Integer::parseInt).collect(Collectors.toList()).toArray(a);
		part1(a.clone());
		part2(a.clone());
	}

	public static void part1(Integer[] input) {
		System.out.println(execIntcode(input, 12, 2));
	}


	public static void part2(Integer[] input) {
		int[] nounverb = {0,0};
		IntStream.range(0,99).forEach(noun -> {
			IntStream.range(0, 99).forEach(verb -> {
				if (execIntcode(input.clone(), noun, verb) == 19690720) {
					nounverb[0] = noun;
					nounverb[1] = verb;
					return;
				}
			});
		});
		System.out.println(100 * nounverb[0] + nounverb[1]);
	}
	
	@SuppressWarnings("preview")
	private static int execIntcode(Integer[] a, int noun, int verb) {
		a[1] = noun;
		a[2] = verb;
		var i = 0;
		while (a[i] != 99) {
			a[a[i + 3]] = switch (a[i].intValue()) {
				case 1 -> a[a[i + 1]] + a[a[i + 2]];
				case 2 -> a[a[i + 1]] * a[a[i + 2]];
				default -> 0;
			}
			;
			i += 4;
		}
		return a[0];
	}
}
	