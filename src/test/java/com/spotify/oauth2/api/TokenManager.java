package com.spotify.oauth2.api;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import com.spotify.oauth2.util.ConfigReader;

import io.restassured.response.Response;

public class TokenManager {
	
	private static String accessToken;
	private static Instant expiryTime;
	
	public synchronized static String getAccessToken(){
		try{
			if(accessToken == null || Instant.now().isAfter(expiryTime)){
				System.out.println("Renewing Token...");
				Response response = renewAccessToken();
				accessToken = response.path("access_token");
				
				int expiryDurationInSeconds = response.path("expires_in");
				expiryTime = Instant.now().plusSeconds(expiryDurationInSeconds);
			}else{
				System.out.println("Token is Good To Use");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("Failed To Get Token!");
		}
		return accessToken;
	}
	
	private static Response renewAccessToken(){
		Map<String, String> accessTokenMap = new HashMap<String, String>();
		accessTokenMap.put("client_id", ConfigReader.getInstance().getClientId());
		accessTokenMap.put("client_secret", ConfigReader.getInstance().getClientSecret());
		accessTokenMap.put("refresh_token", ConfigReader.getInstance().getRefreshToken());
		accessTokenMap.put("grant_type", ConfigReader.getInstance().getGrantType());
		
		Response response = RestResource.postAccount(accessTokenMap);
		
		if(response.statusCode() != 200){
			throw new RuntimeException("Renew Token Failed!");
		}
		return response;
	}
}
