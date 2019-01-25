package com.mapr.streams.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileUtil {

	public static String mkString(final String fileName) throws IOException {
		return FileUtils.readFileToString(new File(fileName));
	}
	
	public static void main(String[] args) throws IOException {
		String fileLocation = "/Users/sa356713/Documents/SAIWS/SAWFE/MapR/json.data";
		System.out.println(mkString(fileLocation));
	}
}

