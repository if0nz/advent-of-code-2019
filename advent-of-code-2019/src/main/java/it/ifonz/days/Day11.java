package it.ifonz.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import it.ifonz.bean.Coord;
import it.ifonz.common.FileReader;

public class Day11 {

	public static void main(String[] args) throws URISyntaxException, IOException {
		var input = FileReader.readLine("/d11.txt").split(",");
		var a = Arrays.stream(input).map(Long::parseLong).collect(Collectors.toList());
		part1(new ArrayList<Long>(a));
	}

	@SuppressWarnings("preview")
	public static void part1(ArrayList<Long> memory) {
		char direction = 'U';
		var ip = new AtomicInteger(0);
		var relativeBase = new AtomicInteger(0);
		var output = 0L;
		var current = new Coord(0, 0);
		var paintItBlack = new HashMap<Coord, Character>();
		paintItBlack.put(current, '.');
		// I'll xor it with false to switch it after every intcode cycle
		// true => outputs panel's color
		// false => outputs right/left turn
		boolean outputSwitch = false;
		do {
			try {
				output = execIntcode(memory, new int[] { (int)output }, ip, relativeBase);
				outputSwitch ^= true;
				if (outputSwitch) {
					// paint it
					paintItBlack.put(new Coord(current.x, current.y), output == 0 ? '.' : '#');
				} else {
					// move
					direction = switch (direction) {
					case 'U' -> output == 0 ? 'L' : 'R';
					case 'L' -> output == 0 ? 'D' : 'U';
					case 'D' -> output == 0 ? 'R' : 'L';
					case 'R' -> output == 0 ? 'U' : 'D';
					default -> ' ';
					};
					current.x += switch (direction) {
					case 'L' -> -1;
					case 'R' -> 1;
					default -> 0;
					};
					current.y += switch (direction) {
					case 'U' -> 1;
					case 'D' -> -1;
					default -> 0;
					};
				}
			} catch (IndexOutOfBoundsException e) {
				int size = memory.size();
				for (int i = 0; i < size; i++)
					memory.add(0L);
			} catch (HaltException e) {
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (true);
		System.out.println(paintItBlack.entrySet().size());

	}

	public static void part2(ArrayList<Long> a) {

	}

	@SuppressWarnings("preview")
	private static Long execIntcode(ArrayList<Long> memory, int[] input, AtomicInteger i, AtomicInteger relativeBase)
			throws HaltException {
		int instruction;
		int opcode;
		int paramMode1;
		int paramMode2;
		int paramMode3;
		Long val1;
		Long val2;
		int val3; // it's an address, so it will be either 0 or 2
		var output = 0L;
		while (memory.get(i.get()).compareTo(Long.valueOf(99l)) != 0) {
			instruction = memory.get(i.get()).intValue();
			opcode = instruction % 10;
			instruction /= 100;
			paramMode1 = instruction % 10;
			instruction /= 10;
			paramMode2 = instruction % 10;
			instruction /= 10;
			paramMode3 = instruction % 10;
			val1 = switch (paramMode1) {
			case 0 -> memory.get(memory.get(i.get() + 1).intValue());
			case 1 -> memory.get(i.get() + 1);
			case 2 -> memory.get(memory.get(i.get() + 1).intValue() + relativeBase.get());
			default -> 0L;
			};
			val2 = opcode != 4 && opcode != 3 && opcode != 9 ? switch (paramMode2) {
			case 0 -> memory.get(memory.get(i.get() + 2).intValue());
			case 1 -> memory.get(i.get() + 2);
			case 2 -> memory.get(memory.get(i.get() + 2).intValue() + relativeBase.get());
			default -> 0L;
			} : 0L;
			val3 = opcode != 4 && opcode != 3 && opcode != 9 ? switch (paramMode3) {
			case 0 -> memory.get(i.get() + 3).intValue();
			case 2 -> memory.get(i.get() + 3).intValue() + relativeBase.get();
			default -> 0;
			} : 0;
			switch (opcode) {
			case 1:
				memory.set(val3, val1 + val2);
				i.getAndAdd(4);
				break;
			case 2:
				memory.set(val3, val1 * val2);
				i.getAndAdd(4);
				break;
			case 3:
				if (paramMode1 == 0)
					memory.set(memory.get(i.get() + 1).intValue(), Long.valueOf(input[0]));
				else
					memory.set(memory.get(i.get() + 1).intValue() + relativeBase.get(), Long.valueOf(input[0]));
				i.getAndAdd(2);
				break;
			case 4:
				i.getAndAdd(2);
				return val1;
			case 5:
				val1 = val1.compareTo(0L) != 0 ? 1L : 0L;
				if (val1.compareTo(1L) == 0)
					i.set(val2.intValue());
				else
					i.getAndAdd(3);
				break;
			case 6:
				val1 = val1.compareTo(0L) == 0 ? 1L : 0L;
				if (val1.compareTo(1L) == 0)
					i.set(val2.intValue());
				else {
					i.getAndAdd(3);
				}
				break;
			case 7:
				memory.set(val3, Long.valueOf(val1.compareTo(val2) < 0 ? 1 : 0));
				i.getAndAdd(4);
				break;
			case 8:
				memory.set(val3, Long.valueOf(val1 == val2 ? 1 : 0));
				i.getAndAdd(4);
				break;
			case 9:
				relativeBase.getAndAdd(val1.intValue());
				i.getAndAdd(2);
				break;
			default:
				break;
			}
			;
		}
		if (memory.get(i.get()).compareTo(Long.valueOf(99)) == 0)
			throw new HaltException();
		return output;
	}

	private static class HaltException extends Exception {

		/**
		 * 
		 */
		/**
		 * 
		 */
		private static final long serialVersionUID = -1918954010019975400L;

	}
}
