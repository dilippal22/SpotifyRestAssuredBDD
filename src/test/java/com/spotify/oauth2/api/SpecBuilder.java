package com.spotify.oauth2.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {
	
	public static RequestSpecification requestSpecification(){
		return new RequestSpecBuilder().
				setBaseUri(System.getProperty("BASE_URI")).
				//setBaseUri("https://api.spotify.com").
				setBasePath(Route.BASE_PATH).
				setContentType(ContentType.JSON).
				log(LogDetail.ALL).
				build();
	}
	
	public static ResponseSpecification responseSpecification(){
		return new ResponseSpecBuilder().
				expectContentType(ContentType.JSON).
				log(LogDetail.ALL).
				build();
	}
	
	public static RequestSpecification accountRequestSpecification(){
		return new RequestSpecBuilder().
				setBaseUri(System.getProperty("ACCOUNTS_BASE_URI")).
				//setBaseUri("https://accounts.spotify.com").
				setContentType(ContentType.URLENC).
				log(LogDetail.ALL).
				build();
	}
}
