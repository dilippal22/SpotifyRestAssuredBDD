package com.spotify.oauth2.util;

import com.github.javafaker.Faker;

public class FakerUtils {

	static Faker fake = new Faker();

	public static String generateName() {
		return "Playlist" + fake.regexify("[A-Za-z0-9 , _-]{20}");
	}

	public static String generateDescription() {
		return "Description" + fake.regexify("[A-Za-z0-9_@./#&+-]{30}");
	}

}
