package it.ifonz.days;

import java.time.Instant;

public class EveryDay {

	public static void main(String[] args) throws Exception {
		var begin = Instant.now().toEpochMilli();
		System.out.println("D1");
		Day01.main(args);
		System.out.println("D2");
		Day02.main(args);
		System.out.println("D3");
		Day03.main(args);
		System.out.println("D4");
		Day04.main(args);
		System.out.println("D5");
		Day05.main(args);
		System.out.println("D6");
		Day06.main(args);
		System.out.println("D7");
		Day07.main(args);
		System.out.println("total: "+(Instant.now().toEpochMilli()-begin));
	}

}
