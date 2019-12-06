package it.ifonz.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.stream.IntStream;

import it.ifonz.common.FileReader;

public class Day04 {

	public static void main(String args[]) throws URISyntaxException, IOException {
		var range = FileReader.readLine("/d04.txt").split("-");
		int[] numericRange = { Integer.parseInt(range[0]), Integer.parseInt(range[1]) };
		var begin = Instant.now().toEpochMilli();
		part1(numericRange);
		part2(numericRange);
		System.out.println(Instant.now().toEpochMilli() - begin);
	}

	public static void part1(int[] range) {
		System.out.println(IntStream.rangeClosed(range[0], range[1]).parallel().filter(n -> {
			boolean sameDigit = false;
			while (n != 0) {
				int d1 = n % 10;
				n /= 10;
				int d2 = n % 10;
				if (d1 < d2)
					return false; // if decrease
				sameDigit = sameDigit || (d1 == d2);
			}
			return sameDigit;
		}).count());
	}

	public static void part2(int[] range) {

		System.out.println(IntStream.rangeClosed(range[0], range[1]).parallel().filter(n -> {
			int sameDigitNumber = 0;
			var sameDigitNumbers = new ArrayList<Integer>();
			do {
				int d1 = n % 10;
				n /= 10;
				int d2 = n % 10;
				if (d1 < d2)
					return false;
				if (d1 == d2) {
					sameDigitNumber = sameDigitNumber * 10 + d1;
				} else if (sameDigitNumber != 0) {
					sameDigitNumbers.add(sameDigitNumber * 10 + d1);
					sameDigitNumber = 0;
				}
			} while (n != 0);
			return sameDigitNumbers.stream().filter(d -> d < 100).count() > 0;
		}).count());

	}


}
