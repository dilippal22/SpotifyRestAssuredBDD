package com.spotify.oauth2.util;

import java.util.Properties;

public class ConfigReader {

	private final Properties prop;
	private static ConfigReader configReader;

	private ConfigReader() {
		prop = PropertyUtils.propertyLoader("C:\\dilip-dev-automation\\SpotifyRestAssuredBDD\\src\\test\\resource\\Config.properties");
	}

	public static ConfigReader getInstance() {
		if (configReader == null) {
			configReader = new ConfigReader();
		}
		return configReader;
	}

	public String getClientId() {
		String clientIdPropValue = prop.getProperty("client_id");
		if (clientIdPropValue != null)
			return clientIdPropValue;
		else
			throw new RuntimeException("Property client_id is not specified in Config.properties file");
	}

	public String getClientSecret() {
		String clientSecretPropValue = prop.getProperty("client_secret");
		if (clientSecretPropValue != null)
			return clientSecretPropValue;
		else
			throw new RuntimeException("Property client_secret is not specified in Config.properties file");
	}

	public String getGrantType() {
		String grantTypePropValue = prop.getProperty("grant_type");
		if (grantTypePropValue != null)
			return grantTypePropValue;
		else
			throw new RuntimeException("Property grant_type is not specified in Config.properties file");
	}

	public String getRefreshToken() {
		String refreshTokenPropValue = prop.getProperty("refresh_token");
		if (refreshTokenPropValue != null)
			return refreshTokenPropValue;
		else
			throw new RuntimeException("Property refresh_token is not specified in Config.properties file");
	}

}
