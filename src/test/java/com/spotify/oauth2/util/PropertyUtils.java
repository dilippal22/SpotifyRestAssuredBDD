package com.spotify.oauth2.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {

	public static Properties propertyLoader(String filePath) {
		Properties prop = new Properties();
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader(filePath));
			try {
				prop.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Filed to load Properties File " + filePath);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Properties File does not exist at " + filePath);
		}
		return prop;
	}
}
