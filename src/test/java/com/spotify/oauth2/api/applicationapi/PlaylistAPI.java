package com.spotify.oauth2.api.applicationapi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.api.Route;
import com.spotify.oauth2.api.TokenManager;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.util.DataReader;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class PlaylistAPI {

	@Step
	public static Response getPlaylist(String playlistId) {
		return RestResource.get(Route.PLAYLISTS + "/" + playlistId, TokenManager.getAccessToken());
	}

	@Step
	public static Response getAllPlaylist() {
		return RestResource.get(Route.USERS + DataReader.getInstance().getUserId() + Route.PLAYLISTS,
				TokenManager.getAccessToken());
	}

	@Step
	public static Response post(Playlist requestPlaylist) {
		return RestResource.post(Route.USERS + DataReader.getInstance().getUserId() + Route.PLAYLISTS, requestPlaylist,
				TokenManager.getAccessToken());
	}

	@Step
	public static Response post(Playlist requestPlaylist, String token) {
		return RestResource.post(Route.USERS + DataReader.getInstance().getUserId() + Route.PLAYLISTS, requestPlaylist,
				token);
	}

	@Step
	public static Response put(String playlistId, Playlist requestPlaylist) {
		return RestResource.put(Route.PLAYLISTS + "/" + playlistId, requestPlaylist, TokenManager.getAccessToken());
	}

}
