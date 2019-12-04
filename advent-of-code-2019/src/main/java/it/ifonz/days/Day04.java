package it.ifonz.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.stream.IntStream;

import it.ifonz.common.FileReader;

public class Day04 {
	
	public static void main(String args[]) throws URISyntaxException, IOException {
		var range = FileReader.readLines("/d04.txt").get(0).split("-");
		int[] numericRange = {Integer.parseInt(range[0]),Integer.parseInt(range[1])};
		part1(numericRange);
		part2(numericRange);
	}
	
	public static void part1(int[] range) {
		System.out.println(IntStream.rangeClosed(range[0], range[1]).parallel().filter(n -> {
			boolean sameDigit = false;
			boolean decrease = false;
			while (n != 0) {
				int d1 = n%10;
				n/=10;
				int d2 = n%10;
				sameDigit = sameDigit || (d1 == d2);
				decrease = decrease || (d1 < d2);
			}
			return sameDigit && !decrease;
		}).count());
	}

	public static void part2(int[] range) {
		
		System.out.println(IntStream.rangeClosed(range[0], range[1]).parallel().filter(n -> {
			boolean sameDigitStreak;
			String sameDigitGroup = "";
			var sameDigitGroups = new ArrayList<String>();
			boolean decrease = false;
			while (n != 0) {
				int d1 = n%10;
				n/=10;
				int d2 = n%10;
				sameDigitStreak = (d1 == d2);
				if (n!=0)
					decrease = decrease || (d1 < d2);
				if (sameDigitStreak) sameDigitGroup+=d1;
				else if (!sameDigitGroup.isEmpty()) {
					sameDigitGroups.add(""+sameDigitGroup+d1);
					sameDigitGroup = "";
				}
			}
			return !sameDigitGroups.isEmpty() && 
					!decrease && 
					sameDigitGroups.stream().filter(s -> s.length() == 2).count() > 0;
		}).count());
		
	}

}
