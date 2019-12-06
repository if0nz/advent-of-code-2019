package it.ifonz.days;

import java.io.IOException;
import java.net.URISyntaxException;

import it.ifonz.common.FileReader;

public class Day04 {

	public static void main(String args[]) throws URISyntaxException, IOException {
		var range = FileReader.readLine("/d04.txt").split("-");
		int[] numericRange = { Integer.parseInt(range[0]), Integer.parseInt(range[1]) };
		part1(numericRange);
		part2(numericRange);
	}

	public static void part1(int[] range) {
		int valid = 0;
		for (int i = range[0]; i<=range[1]; i++) {
			boolean sameDigit = false;
			int n = i;
			int d1,d2;
			do  {
				d1 = n % 10;
				n /= 10;
				d2 = n % 10;
				if (d1 < d2)
					break;
				sameDigit = sameDigit || (d1 == d2);
			}while (n != 0);
			valid+= d1>=d2 && sameDigit ?1:0;
		}
		System.out.println(valid);
	}

	public static void part2(int[] range) {
		
		int valid = 0;
		for (int i = range[0]; i<=range[1]; i++) {
			int n = i;
			int sameDigitNumber = 0;
			boolean sameDigitPair = false;
			int d1,d2;
			do {
				d1 = n % 10;
				n /= 10;
				d2 = n % 10;
				if (d1 < d2)
					break;
				if (d1 == d2) {
					sameDigitNumber = sameDigitNumber * 10 + d1;
				} else if (sameDigitNumber != 0) {
					sameDigitPair = sameDigitPair || sameDigitNumber * 10 + d1 < 100;
					sameDigitNumber = 0;
				}
			} while (n != 0);
			valid += d1>=d2 && sameDigitPair ? 1 : 0;
		}
		System.out.println(valid);
	}


}
