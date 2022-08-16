package com.spotify.oauth2.api;

import static io.restassured.RestAssured.given;

import java.util.Map;

import com.spotify.oauth2.pojo.Playlist;

import io.restassured.response.Response;

public class RestResource {
	
	public static Response get(String path, String accessToken){
		return given(SpecBuilder.requestSpecification()).
					header("authorization", "Bearer " + accessToken).
				when().
					get(path).
				then().spec(SpecBuilder.responseSpecification()).
					extract().
					response();
	}
	
	public static Response post(String path, Playlist requestPlaylist, String accessToken){
		return given(SpecBuilder.requestSpecification()).
					header("authorization", "Bearer " + accessToken).
					body(requestPlaylist).
				when().
					post(path).
				then().spec(SpecBuilder.responseSpecification()).
					extract().
					response();
	}
	
	public static Response put(String path, Playlist requestPlaylist, String accessToken){
		return given(SpecBuilder.requestSpecification()).
					header("authorization", "Bearer " + accessToken).
					body(requestPlaylist).
				when().
					put(path).
				then().spec(SpecBuilder.responseSpecification()).
					extract().
					response();
	}
	
	public static Response postAccount(Map<String, String> accessTokenMap){
		return given(SpecBuilder.accountRequestSpecification()).
					formParams(accessTokenMap).
				when().
					post(Route.API + Route.TOKEN).
					then().spec(SpecBuilder.responseSpecification()).
					extract().
					response();
	}
}
