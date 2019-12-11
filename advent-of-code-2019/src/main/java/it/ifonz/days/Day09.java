package it.ifonz.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import it.ifonz.common.FileReader;

public class Day09 {

	public static void main(String[] args) throws URISyntaxException, IOException {
		var input = FileReader.readLine("/d09.txt").split(",");
		var a = Arrays.stream(input).map(Long::parseLong).collect(Collectors.toList());
		part1(new ArrayList<Long>(a));
//		part2(a.clone());
	}

	public static void part1(ArrayList<Long> memory) {
		var ip = new AtomicInteger(0);
		var relativeBase = new AtomicInteger(0);
		var output = 0L;
		int size = memory.size();
		for (int i = 0; i < size*4; i++)
			memory.add(0L);
		do {
			try {
				output = execIntcode(memory, new int[] { 1 }, ip, relativeBase);
				System.out.println(output);
			}  catch (HaltException e) {
				break;
			}
		} while (output != 99L);

	}

	public static void part2(Integer[] input) {

	}

	@SuppressWarnings("preview")
	private static Long execIntcode(ArrayList<Long> memory, int[] input, AtomicInteger i,
			AtomicInteger relativeBase) throws HaltException {
		int instruction;
		int opcode;
		int paramMode1;
		int paramMode2;
		Long val1;
		Long val2;
		var output = 0L;
		while (memory.get(i.get()).compareTo(Long.valueOf(99l)) != 0) {
			instruction = memory.get(i.get()).intValue();
			opcode = instruction % 10;
			instruction /= 100;
			paramMode1 = instruction % 10;
			instruction /= 10;
			paramMode2 = instruction % 10;
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
			switch (opcode) {
			case 1:
				memory.set(memory.get(i.get() + 3).intValue(), val1+val2);
				i.getAndAdd(4);
				break;
			case 2:
				memory.set(memory.get(i.get() + 3).intValue(), val1*val2);
				i.getAndAdd(4);
				break;
			case 3:
				if (paramMode1 == 1)
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
				memory.set(memory.get(i.get() + 3).intValue(), Long.valueOf(val1.compareTo(val2) < 0 ? 1 : 0));
				i.getAndAdd(4);
				break;
			case 8:
				memory.set(memory.get(i.get() + 3).intValue(), Long.valueOf(val1 == val2 ? 1 : 0));
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
