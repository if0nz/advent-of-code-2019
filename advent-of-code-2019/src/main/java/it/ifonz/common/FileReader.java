package it.ifonz.common;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

public class FileReader {
	
	public static List<Long> readNumericLines(String fileName) throws URISyntaxException, IOException {
		var file = new File(FileReader.class.getResource(fileName).toURI());
		var readLines = FileUtils.readLines(file, Charset.defaultCharset());
		return readLines.parallelStream().map(Long::parseLong).collect(Collectors.toList());
	}
}
