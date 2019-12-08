package it.ifonz.days;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.stream.IntStream;

import it.ifonz.common.FileReader;

public class Day08 {

	public static void main(String[] args) throws URISyntaxException, IOException {
		var input = FileReader.readLine("/d08.txt");
		var width = 25;
		var height = 6;
		var layers = new ArrayList<String>();
		for (int i = 0; i < input.length()/(width*height); i++) {
			layers.add(input.substring(i*width*height, (i+1)*width*height));
		}
		part1(layers);
		part2(layers, width, height);
	}

	public static void part1(ArrayList<String> layers) {
		var minLayer = layers.stream().min((l1, l2) -> (int)(l1.chars().filter(c -> c == '0').count() - l2.chars().filter(c -> c == '0').count())).get();
		System.out.println(minLayer.chars().filter(c -> c == '1').count() * minLayer.chars().filter(c -> c == '2').count());
	}
	
	public static void part2(ArrayList<String> layers, int width, int height) {
		var sb = new StringBuilder();
		IntStream.range(0, width*height).forEach(i ->{
			sb.append(layers.stream().filter(l -> l.charAt(i) != '2').findFirst().get().charAt(i));
		});
		var s = sb.toString().replace('0', ' ');
		for (int i = 0; i < height; i++) {
			System.out.println(s.substring(i*width, (i+1)*width));
		}
	}
}
