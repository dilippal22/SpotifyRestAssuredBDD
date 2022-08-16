
package com.spotify.oauth2.pojo;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
public class ExternalUrls {

	@JsonProperty("spotify")
	private String spotify;

	@JsonProperty("spotify")
	public String getSpotify() {
		return spotify;
	}

	@JsonProperty("spotify")
	public void setSpotify(String spotify) {
		this.spotify = spotify;
	}

}
