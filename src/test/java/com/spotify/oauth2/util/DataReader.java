package com.spotify.oauth2.util;

import java.util.Properties;

public class DataReader {

	private final Properties prop;
	private static DataReader dataReader;

	private DataReader() {
		prop = PropertyUtils.propertyLoader(
				"C:\\dilip-dev-automation\\SpotifyRestAssuredBDD\\src\\test\\resource\\Data.properties");
	}

	public static DataReader getInstance() {
		if (dataReader == null) {
			dataReader = new DataReader();
		}
		return dataReader;
	}

	public String getPlaylistId() {
		String playlistIdPropValue = prop.getProperty("playlist_id");
		if (playlistIdPropValue != null)
			return playlistIdPropValue;
		else
			throw new RuntimeException("Property playlist_id is not specified in Data.properties file");
	}

	public String getUserId() {
		String userIdPropValue = prop.getProperty("user_id");
		if (userIdPropValue != null)
			return userIdPropValue;
		else
			throw new RuntimeException("Property user_id is not specified in Data.properties file");
	}

	public String getUpdatePlaylistId() {
		String updatePlaylistIdPropValue = prop.getProperty("update_playlist");
		if (updatePlaylistIdPropValue != null)
			return updatePlaylistIdPropValue;
		else
			throw new RuntimeException("Property update_playlist is not specified in Data.properties file");
	}
}
