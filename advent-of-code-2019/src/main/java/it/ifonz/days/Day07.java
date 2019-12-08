package it.ifonz.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import it.ifonz.common.FileReader;

public class Day07 {

	public static void main(String[] args) throws URISyntaxException, IOException {
		var input = FileReader.readLine("/d07.txt").split(",");
		var a = new Integer[input.length];
		a = Arrays.stream(input).map(Integer::parseInt).collect(Collectors.toList()).toArray(a);
		part1(a.clone());
		part2(a.clone());
	}

	public static void part1(Integer[] input) {
		var signal = new ArrayList<Integer>();
		for (int a = 0; a < 5; a++) {
			for (int b = 0; b < 5; b++) {
				if (a != b)
					for (int c = 0; c < 5; c++) {
						if (b != c && a != c)
							for (int d = 0; d < 5; d++) {
								if (c != d && b != d && a != d)
									for (int e = 0; e < 5; e++) {
										if (d != e && c != e && b != e && a != e) {
											int amplified = 0;
											amplified = execIntcode(input.clone(), new int[] { a, amplified }, new AtomicInteger());
											amplified = execIntcode(input.clone(), new int[] { b, amplified }, new AtomicInteger());
											amplified = execIntcode(input.clone(), new int[] { c, amplified }, new AtomicInteger());
											amplified = execIntcode(input.clone(), new int[] { d, amplified }, new AtomicInteger());
											amplified = execIntcode(input.clone(), new int[] { e, amplified }, new AtomicInteger());
											signal.add(amplified);
										}
									}
							}
					}
			}
		}
		System.out.println(signal.stream().max(Integer::compare).get());
	}

	public static void part2(Integer[] input) {
		int lastAmplified = 0;
		var signal = new ArrayList<Integer>();
		int amplified = 0;
		for (int a = 5; a < 10; a++) {
			for (int b = 5; b < 10; b++) {
				if (a != b)
					for (int c = 5; c < 10; c++) {
						if (b != c && a != c)
							for (int d = 5; d < 10; d++) {
								if (c != d && b != d && a != d)
									for (int e = 5; e < 10; e++) {
										if (d != e && c != e && b != e && a != e) {
											Integer[] ampA = input.clone();
											Integer[] ampB = input.clone();
											Integer[] ampC = input.clone();
											Integer[] ampD = input.clone();
											Integer[] ampE = input.clone();
											AtomicInteger ipA = new AtomicInteger(0);
											AtomicInteger ipB = new AtomicInteger(0);
											AtomicInteger ipC = new AtomicInteger(0);
											AtomicInteger ipD = new AtomicInteger(0);
											AtomicInteger ipE = new AtomicInteger(0);
											do {
												lastAmplified = amplified;

												amplified = execIntcode(ampA, new int[] { a, amplified }, ipA);
												amplified = execIntcode(ampB, new int[] { b, amplified }, ipB);
												amplified = execIntcode(ampC, new int[] { c, amplified }, ipC);
												amplified = execIntcode(ampD, new int[] { d, amplified }, ipD);
												amplified = execIntcode(ampE, new int[] { e, amplified }, ipE);
												signal.add(amplified);
											} while (lastAmplified < amplified);
										}
									}
							}
					}
			}
		}
		System.out.println(signal.stream().max(Integer::compare).get());
	}

	@SuppressWarnings("preview")
	private static int execIntcode(Integer[] a, int[] input, AtomicInteger i) {
		int instruction;
		int opcode;
		int paramMode1;
		int paramMode2;
		int val1;
		int val2;
		int output = 0;
		while (a[i.get()] != 99) {
			instruction = a[i.get()].intValue();
			opcode = instruction % 10;
			instruction /= 100;
			paramMode1 = instruction % 10;
			instruction /= 10;
			paramMode2 = instruction % 10;
			val1 = switch (paramMode1) {
			case 0 -> a[a[i.get() + 1]];
			case 1 -> a[i.get() + 1];
			default -> 0;
			};
			val2 = opcode != 4 && opcode != 3 ? switch (paramMode2) {
			case 0 -> a[a[i.get() + 2]];
			case 1 -> a[i.get() + 2];
			default -> 0;
			} : 0;
			switch (opcode) {
			case 1:
				a[a[i.get() + 3]] = val1 + val2;
				i.getAndAdd(4);
				break;
			case 2:
				a[a[i.get() + 3]] = val1 * val2;
				i.getAndAdd(4);
				break;
			case 3:
				a[a[i.get() + 1]] = input[i.get() == 0 ? 0 : 1];
				i.getAndAdd(2);
				break;
			case 4:
				i.getAndAdd(2);
				return val1;
			case 5:
				val1 = val1 != 0 ? 1 : 0;
				if (val1 == 1)
					i.set(val2);
				else
					i.getAndAdd(3);
				break;
			case 6:
				val1 = val1 == 0 ? 1 : 0;
				if(val1 == 1) 
					i.set(val2);
				else {
					i.getAndAdd(3);
				}
				break;
			case 7:
				a[a[i.get() + 3]] = val1 < val2 ? 1 : 0;
				i.getAndAdd(4);
				break;
			case 8:
				a[a[i.get() + 3]] = val1 == val2 ? 1 : 0;
				i.getAndAdd(4);
				break;
			default:
				break;
			}
			;
		}
		return output;
	}
}
