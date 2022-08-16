package com.spotify.oauth2.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.testng.annotations.Test;

import com.spotify.oauth2.api.applicationapi.PlaylistAPI;
import com.spotify.oauth2.pojo.ErrorRoot;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.util.DataReader;
import com.spotify.oauth2.util.RestUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@Feature("Playlist API")
@Story("Playlist CRUD Operation")
public class PlaylistAPITests {

	/*
	 * Endpoint: /playlists/{playlist_id} Get the playlist detail of the single
	 * playlist Sending request body is option is GET request. Sending the
	 * request body for the playlist Id which is expected in response
	 */
	@Description("User should get a single playlist detail using Playlist ID")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 0, description = "Verify Playlist detail using Playlist ID")
	public void getSinglePlaylist() {
		Playlist requestPlaylist = playlistBuilder("Dilip Talk Playlist", "Dilip recently created Talk Playlist",
				false);

		Response response = PlaylistAPI.getPlaylist(DataReader.getInstance().getPlaylistId());
		RestUtil.assertStatusCode(response.getStatusCode(), 200);

		Playlist responsePlaylist = response.as(Playlist.class);
		assertPlaylistEqual(responsePlaylist, requestPlaylist);
	}

	/*
	 * Endpoint: /users/{user_id}/playlists Create single playlist for a user
	 */
	@Description("User should be able to create a Playlist using User ID")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 1, description = "Verify create Playlist using User ID")
	public void createPlaylist() {
		Playlist requestPlaylist = playlistBuilder("Dilip Walk Playlist", "Dilip recently created Walk Playlist",
				false);

		Response response = PlaylistAPI.post(requestPlaylist);
		RestUtil.assertStatusCode(response.getStatusCode(), 201);

		Playlist responsePlaylist = response.as(Playlist.class);
		assertPlaylistEqual(responsePlaylist, requestPlaylist);
	}

	/*
	 * Endpoint: /playlists/{playlist_id} Update single playlist detail
	 */
	@Description("User should be able to update Plylist detail using Playlist ID")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 2, description = "Verify update Playlist detail using Playlist ID")
	public void updateSinglePlaylistDetail() {
		Playlist updatePlaylist = playlistBuilder("Dilip Talk Playlist", "Dilip recently created Talk Playlist", false);

		Response response = PlaylistAPI.put(DataReader.getInstance().getUpdatePlaylistId(), updatePlaylist);
		RestUtil.assertStatusCode(response.getStatusCode(), 200);
	}

	/*
	 * Endpoint: /users/{user_id}/playlists Get all the playlist detail of the
	 * single user
	 */
	@Description("User should be able to get all the Playlist for a particular User using User ID")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3, description = "Verify all Playlist for particular User using User ID")
	public void getSingleUserAllPlaylist() {
		Response response = PlaylistAPI.getAllPlaylist();
		RestUtil.assertStatusCode(response.getStatusCode(), 200);

		String responseString = response.asString();
		List<String> responsePlaylistName = JsonPath.from(responseString).getList("items.name");
		int responseTotalPlaylist = JsonPath.from(responseString).getInt("items.size()");

		assertThat(responsePlaylistName,
				containsInAnyOrder("Dilip Walk Playlist", "Dilip Cardio Playlist", "Dilip Gym Playlist",
						"Dilip Empty Playlist", "Dilip Empty Playlist", "Dilip Learning Playlist",
						"Dilip Timepass Playlist", "Dilip Trace Playlist", "Dilip Travel Playlist", "dp"));
		RestUtil.assertSizeEqualTo(responseTotalPlaylist, 10);
	}

	/*
	 * Endpoint: /users/{user_id}/playlists User should not be able to create
	 * playlist with empty name
	 */
	@Description("User should not be able to create Playlist with empty name")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 4, description = "Verify create playlist with empty name")
	public void notAbleToCreatePlaylistWithEmptyName() {
		Playlist requestPayloadWithEmptyName = playlistBuilder("", "Dilip recently created Empty Playlist", false);

		Response response = PlaylistAPI.post(requestPayloadWithEmptyName);
		RestUtil.assertStatusCode(response.getStatusCode(), 400);

		ErrorRoot responseError = response.as(ErrorRoot.class);
		RestUtil.assertError(responseError, 400, "Missing required field: name");
	}

	/*
	 * Endpoint: /users/{user_id}/playlists User should not be able to create
	 * playlist with invalid access token
	 */
	@Description("User should not be able to create Playlist using invalid Access Token")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 5, description = "Verify create playlist using invalid access token")
	public void notAbleToCreatePlaylistUsingInvalidAccessToken() {
		String invalidToken = "BQCz0XmfN5jG_XaaHTFxqffx8UwkUPtugUvwHVeAjxQ4lZycwmrcaOHOWlYwZBSovymzgh_VfqnF6CFE-flR3YQpFv6jWRUz7f3b1jN4-jqWpJAKxnnkNiP7Wmg4Chzy9vtT_89fHr_8YT5aJNv2PNMn_mEcbuBegbyeAz444o2uTd4V8fjsT11rRbmlGSpWe92ymN0kghHsI4X05OGC37qKypSrZ-FICvtJxAQFezMPxZn8aU3oxlTqbp5Jims8-ADvTDndle_qdE4";

		Playlist requestPayloadWithInvalidToken = playlistBuilder("Dilip Empty Playlist", "Dilip Empty Playlist",
				false);

		Response response = PlaylistAPI.post(requestPayloadWithInvalidToken, invalidToken);
		RestUtil.assertStatusCode(response.getStatusCode(), 401);

		ErrorRoot responseError = response.as(ErrorRoot.class);
		RestUtil.assertError(responseError, 401, "Invalid access token");
	}

	/*
	 * Endpoint: /users/{user_id}/playlists User should not be able to create
	 * playlist with invalid access token
	 */
	@Description("Test Description: User should not be able to create Playlist using expired Access Token")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 6, description = "Verify create playlist using expired access token")
	public void notAbleToCreatePlaylistUsingExpiredAccessToken() {
		String expiredToken = "BQCz0XmfN5jG_XaaHTFxqffx8UwkUPtugUvwHVeAjxQ4lZycwmrcaOHOWlYwZBSovymzgh_VfqnF6CFE-flR3YQpFv6jWRUz7f3b1jN4-jqWpJAKxnnkNiP7Wmg4Chzy9vtT_89fHr_8YT5aJNv2PNMn_mEcbuBegbyeAz444o2uTd4V8fjsT11rRbmlGSpWe92ymN0kghHsI4X05OGC37qKypSrZ-FICvtJxAQFezMPxZn8aU3oxlTqbp5Jims8-ADvTDndle_qdE4e";

		Playlist requestPayloadWithInvalidToken = playlistBuilder("Dilip Empty Playlist",
				"Dilip recently created Empty Playlist", false);

		Response response = PlaylistAPI.post(requestPayloadWithInvalidToken, expiredToken);
		RestUtil.assertStatusCode(response.getStatusCode(), 401);

		ErrorRoot responseError = response.as(ErrorRoot.class);
		RestUtil.assertError(responseError, 401, "The access token expired");
	}

	@Step
	private Playlist playlistBuilder(String name, String description, boolean _public) {
		return Playlist.builder().name(name).description(description)._public(_public).build();
	}

	@Step
	private void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist) {
		assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
		assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
		assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
	}

}
