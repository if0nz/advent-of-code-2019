package it.ifonz.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Collectors;

import it.ifonz.common.FileReader;

public class Day05 {

	public static void main(String[] args) throws URISyntaxException, IOException {
		var input = FileReader.readLine("/d05.txt").split(",");
		var a = new Integer[input.length];
		a = Arrays.stream(input).map(Integer::parseInt).collect(Collectors.toList()).toArray(a);
		part1(a.clone());
		part2(a.clone());
	}

	public static void part1(Integer[] a) {
		execIntcode(a, 1);
	}

	public static void part2(Integer[] a) {
		execIntcode(a, 5);
	}

	@SuppressWarnings("preview")
	private static void execIntcode(Integer[] a, int input) {
		int i = 0;
		int instruction;
		int opcode;
		int paramMode1;
		int paramMode2;
		int val1;
		int val2;
		while (a[i] != 99) {
			instruction = a[i].intValue();
			opcode = instruction % 10;
			instruction /= 100;
			paramMode1 = instruction % 10;
			instruction /= 10;
			paramMode2 = instruction % 10;
			val1 = switch (paramMode1) {
			case 0 -> a[a[i + 1]];
			case 1 -> a[i + 1];
			default -> 0;
			};
			val2 = opcode != 4 ? 
					switch (paramMode2) {
						case 0 -> a[a[i + 2]];
						case 1 -> a[i + 2];
						default -> 0;
					} 
					: 0;
			i = switch (opcode) {
			case 1:
				a[a[i + 3]] = val1 + val2;
				yield i + 4;
			case 2:
				a[a[i + 3]] = val1 * val2;
				yield i + 4;
			case 3:
				a[a[i + 1]] = input;
				yield i + 2;
			case 4:
				System.out.println(val1);
				yield i + 2;
			case 5:
				val1 = val1 != 0 ? 1 : 0;
				yield val1 == 1 ? val2 : i + 3;
			case 6:
				val1 = val1 == 0 ? 1 : 0;
				yield val1 == 1 ? val2 : i + 3;
			case 7:
				a[a[i + 3]] = val1 < val2 ? 1 : 0;
				yield i + 4;
			case 8:
				a[a[i + 3]] = val1 == val2 ? 1 : 0;
				yield i + 4;
			default:
				yield 0;
			};
		}
	}

}
