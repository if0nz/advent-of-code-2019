package it.ifonz.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;

public class EveryDay {

	public static void main(String[] args) throws URISyntaxException, IOException {
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
		System.out.println("total: "+(Instant.now().toEpochMilli()-begin));
	}

}
