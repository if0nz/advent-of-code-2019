package it.ifonz.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Collectors;

import it.ifonz.common.FileReader;

public class Day05 {

	public static void main(String[] args) throws URISyntaxException, IOException {
		var input = FileReader.readLines("/d05.txt").get(0).split(",");
		var a = new Integer[input.length];
		a = Arrays.stream(input).map(Integer::parseInt).collect(Collectors.toList()).toArray(a);
		part1(a.clone());
		part2(a.clone());
	}

	@SuppressWarnings("preview")
	public static void part1(Integer[] a) {
		var i = 0;
		int input = 1;
		while (a[i] != 99) {
			int opcode = a[i].intValue();
			int paramMode1;
			int paramMode2;
			int val1;
			int val2;
			i += switch (opcode % 10) {
			case 1 -> {
				opcode /= 100;
				paramMode1 = opcode % 10;
				opcode /= 10;
				paramMode2 = opcode % 10;
				val1 = switch (paramMode1) {
				case 0 -> a[a[i + 1]];
				case 1 -> a[i + 1];
				default -> 0;
				};
				val2 = switch (paramMode2) {
				case 0 -> a[a[i + 2]];
				case 1 -> a[i + 2];
				default -> 0;
				};
				a[a[i + 3]] = val1 + val2;
				yield 4;
			}
			case 2 -> {
				opcode /= 100;
				paramMode1 = opcode % 10;
				opcode /= 10;
				paramMode2 = opcode % 10;
				val1 = switch (paramMode1) {
				case 0 -> a[a[i + 1]];
				case 1 -> a[i + 1];
				default -> 0;
				};
				val2 = switch (paramMode2) {
				case 0 -> a[a[i + 2]];
				case 1 -> a[i + 2];
				default -> 0;
				};
				a[a[i + 3]] = val1 * val2;
				yield 4;
			}
			case 3 -> {
				a[a[i + 1]] = input;
				yield 2;
			}
			case 4 -> {
				int out = switch (opcode / 100) {
				case 0 -> a[a[i + 1]];
				case 1 -> a[i + 1];
				default -> 0;
				};
				System.out.println(out);
				yield 2;
			}
			default -> 0;
			};
		}
	}

	@SuppressWarnings("preview")
	public static void part2(Integer[] a) {
		var i = 0;
		int input = 5;
		while (a[i] != 99) {
			int opcode = a[i].intValue();
			int paramMode1;
			int paramMode2;
			int val1;
			int val2;
			i = switch (opcode % 10) {
			case 1:
				opcode /= 100;
				paramMode1 = opcode % 10;
				opcode /= 10;
				paramMode2 = opcode % 10;
				val1 = switch (paramMode1) {
				case 0 -> a[a[i + 1]];
				case 1 -> a[i + 1];
				default -> 0;
				};
				val2 = switch (paramMode2) {
				case 0 -> a[a[i + 2]];
				case 1 -> a[i + 2];
				default -> 0;
				};
				a[a[i + 3]] = val1 + val2;
				yield i + 4;
			case 2:
				opcode /= 100;
				paramMode1 = opcode % 10;
				opcode /= 10;
				paramMode2 = opcode % 10;
				val1 = switch (paramMode1) {
				case 0 -> a[a[i + 1]];
				case 1 -> a[i + 1];
				default -> 0;
				};
				val2 = switch (paramMode2) {
				case 0 -> a[a[i + 2]];
				case 1 -> a[i + 2];
				default -> 0;
				};
				a[a[i + 3]] = val1 * val2;
				yield i + 4;
			case 3:
				a[a[i + 1]] = input;
				yield i + 2;
			case 4:
				int out = switch (opcode / 100) {
				case 0 -> a[a[i + 1]];
				case 1 -> a[i + 1];
				default -> 0;
				};
				System.out.println(out);
				yield i + 2;
			case 5:
				opcode /= 100;
				paramMode1 = opcode % 10;
				opcode /= 10;
				paramMode2 = opcode % 10;
				val1 = switch (paramMode1) {
				case 0 -> a[a[i + 1]] != 0 ? 1 : 0;
				case 1 -> a[i + 1] != 0 ? 1 : 0;
				default -> 0;
				};
				val2 = switch (paramMode2) {
				case 0 -> a[a[i + 2]];
				case 1 -> a[i + 2];
				default -> 0;
				};
				yield val1 == 1 ? val2 : i + 3;
			case 6:
				opcode /= 100;
				paramMode1 = opcode % 10;
				opcode /= 10;
				paramMode2 = opcode % 10;
				val1 = switch (paramMode1) {
				case 0 -> a[a[i + 1]] == 0 ? 1 : 0;
				case 1 -> a[i + 1] == 0 ? 1 : 0;
				default -> 0;
				};
				val2 = switch (paramMode2) {
				case 0 -> a[a[i + 2]];
				case 1 -> a[i + 2];
				default -> 0;
				};
				yield val1 == 1 ? val2 : i + 3;
			case 7:
				opcode /= 100;
				paramMode1 = opcode % 10;
				opcode /= 10;
				paramMode2 = opcode % 10;
				val1 = switch (paramMode1) {
				case 0 -> a[a[i + 1]];
				case 1 -> a[i + 1];
				default -> 0;
				};
				val2 = switch (paramMode2) {
				case 0 -> a[a[i + 2]];
				case 1 -> a[i + 2];
				default -> 0;
				};
				a[a[i + 3]] = val1 < val2 ? 1 : 0;
				yield i + 4;
			case 8:
				opcode /= 100;
				paramMode1 = opcode % 10;
				opcode /= 10;
				paramMode2 = opcode % 10;
				val1 = switch (paramMode1) {
				case 0 -> a[a[i + 1]];
				case 1 -> a[i + 1];
				default -> 0;
				};
				val2 = switch (paramMode2) {
				case 0 -> a[a[i + 2]];
				case 1 -> a[i + 2];
				default -> 0;
				};
				a[a[i + 3]] = val1 == val2 ? 1 : 0;
				yield i + 4;
			default:
				yield 0;
			};
		}
	}

}
