package com.spotify.oauth2.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.testng.annotations.Test;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationapi.PlaylistAPI;
import com.spotify.oauth2.pojo.ErrorRoot;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.util.DataReader;
import com.spotify.oauth2.util.FakerUtils;
import com.spotify.oauth2.util.RestUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@Feature("Playlist API")
@Story("Playlist CRUD Operation")
public class PlaylistAPITests extends BaseTest {

	/*
	 * Endpoint: /playlists/{playlist_id} Get the playlist detail of the single
	 * playlist Sending request body is option is GET request. Sending the
	 * request body for the playlist Id which is expected in response
	 */
	@Link("https://api.spotify.com/v1/playlists/4NHI4v5Ykj1fwSOpplmoxo")
	@Link(name = "Get Playlist by Playlist ID", type = "GET")
	@Issue("DE763846")
	@TmsLink("TMS479372")
	@Description("User should get a single playlist detail using Playlist ID")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 0, description = "Verify Playlist detail using Playlist ID")
	public void getSinglePlaylist() {
		Playlist requestPlaylist = playlistBuilder("Dilip Talk Playlist", "Dilip recently created Talk Playlist",
				false);

		Response response = PlaylistAPI.getPlaylist(DataReader.getInstance().getPlaylistId());
		RestUtil.assertStatusCode(response.getStatusCode(), StatusCode.CODE_200.getStatusCode());

		Playlist responsePlaylist = response.as(Playlist.class);
		assertPlaylistEqual(responsePlaylist, requestPlaylist);
	}

	/*
	 * Endpoint: /users/{user_id}/playlists Create single playlist for a user
	 */
	@Link("https://api.spotify.com/v1/users/31rmk3j2lxqk3jbuztxtyrh3ng5a/playlists")
	@Link(name = "Create Playlist by User ID", type = "POST")
	@Issue("DE878932")
	@TmsLink("TMS893242")
	@Description("User should be able to create a Playlist using User ID")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 1, description = "Verify create Playlist using User ID")
	public void createPlaylist() {
		Playlist requestPlaylist = playlistBuilder(FakerUtils.generateName(), FakerUtils.generateDescription(), false);

		Response response = PlaylistAPI.post(requestPlaylist);
		RestUtil.assertStatusCode(response.getStatusCode(), StatusCode.CODE_201.getStatusCode());

		Playlist responsePlaylist = response.as(Playlist.class);
		assertPlaylistEqual(responsePlaylist, requestPlaylist);
	}

	/*
	 * Endpoint: /playlists/{playlist_id} Update single playlist detail
	 */
	@Link("https://api.spotify.com/v1/playlists/0X8Iyrxgy7cFrGBsRBxsRL")
	@Link(name = "Update Playlist by Playlist ID", type = "PUT")
	@Issue("DE983023")
	@TmsLink("TMS345454")
	@Description("User should be able to update Plylist detail using Playlist ID")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 2, description = "Verify update Playlist detail using Playlist ID")
	public void updateSinglePlaylistDetail() {
		Playlist updatePlaylist = playlistBuilder(FakerUtils.generateName(), FakerUtils.generateDescription(), false);

		Response response = PlaylistAPI.put(DataReader.getInstance().getUpdatePlaylistId(), updatePlaylist);
		RestUtil.assertStatusCode(response.getStatusCode(), StatusCode.CODE_200.getStatusCode());
	}

	/*
	 * Endpoint: /users/{user_id}/playlists Get all the playlist detail of the
	 * single user
	 */
	@Link("https://api.spotify.com/v1/users/31rmk3j2lxqk3jbuztxtyrh3ng5a/playlists")
	@Link(name = "Get all Playlist by Playlist ID", type = "GET")
	@Issue("DE342356")
	@TmsLink("TMS949373")
	@Description("User should be able to get all the Playlist for a particular User using User ID")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3, description = "Verify all Playlist for particular User using User ID")
	public void getSingleUserAllPlaylist() {
		Response response = PlaylistAPI.getAllPlaylist();
		RestUtil.assertStatusCode(response.getStatusCode(), StatusCode.CODE_200.getStatusCode());

		String responseString = response.asString();
		List<String> responsePlaylistName = JsonPath.from(responseString).getList("items.name");
		System.out.println("responsePlaylistName Name is: " + responsePlaylistName);
		int responseTotalPlaylist = JsonPath.from(responseString).getInt("items.size()");

		assertThat(responsePlaylistName,
				containsInAnyOrder("Dilip Walk Playlist", "Dilip Walk Playlist", "Dilip Walk Playlist",
						"Dilip Walk Playlist", "Dilip Walk Playlist", "Dilip Walk Playlist", "Dilip Walk Playlist",
						"Dilip Walk Playlist", "Dilip Walk Playlist", "Dilip Walk Playlist", "Dilip Walk Playlist",
						"Dilip Walk Playlist", "Dilip Walk Playlist", "Dilip Walk Playlist", "Dilip Walk Playlist",
						"Dilip Cardio Playlist", "Dilip Gym Playlist", "Dilip Talk Playlist", "Dilip Empty Playlist",
						"Dilip Learning Playlist", "Dilip Timepass Playlist", "Dilip Trace Playlist"));
		RestUtil.assertSizeEqualTo(responseTotalPlaylist, 22);
	}

	/*
	 * Endpoint: /users/{user_id}/playlists User should not be able to create
	 * playlist with empty name
	 */
	@Link("https://api.spotify.com/v1/users/31rmk3j2lxqk3jbuztxtyrh3ng5a/playlists")
	@Link(name = "Create Playlist with empty Playlist Name by User ID", type = "POST")
	@Issue("DE038364")
	@TmsLink("TMS810173")
	@Description("User should not be able to create Playlist with empty name")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 4, description = "Verify create playlist with empty name")
	public void notAbleToCreatePlaylistWithEmptyName() {
		Playlist requestPayloadWithEmptyName = playlistBuilder("", FakerUtils.generateDescription(), false);

		Response response = PlaylistAPI.post(requestPayloadWithEmptyName);
		RestUtil.assertStatusCode(response.getStatusCode(), StatusCode.CODE_400.getStatusCode());

		ErrorRoot responseError = response.as(ErrorRoot.class);
		RestUtil.assertError(responseError, StatusCode.CODE_400.getStatusCode(), StatusCode.CODE_400.getMessage());
	}

	/*
	 * Endpoint: /users/{user_id}/playlists User should not be able to create
	 * playlist with invalid access token
	 */
	@Link("https://api.spotify.com/v1/users/31rmk3j2lxqk3jbuztxtyrh3ng5a/playlists")
	@Link(name = "Create Playlists with Invalid Access Token by User ID", type = "POST")
	@Issue("DE797984")
	@TmsLink("TMS203848")
	@Description("User should not be able to create Playlist using invalid Access Token")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 5, description = "Verify create playlist using invalid access token")
	public void notAbleToCreatePlaylistUsingInvalidAccessToken() {
		String invalidToken = "BQCz0XmfN5jG_XaaHTFxqffx8UwkUPtugUvwHVeAjxQ4lZycwmrcaOHOWlYwZBSovymzgh_VfqnF6CFE-flR3YQpFv6jWRUz7f3b1jN4-jqWpJAKxnnkNiP7Wmg4Chzy9vtT_89fHr_8YT5aJNv2PNMn_mEcbuBegbyeAz444o2uTd4V8fjsT11rRbmlGSpWe92ymN0kghHsI4X05OGC37qKypSrZ-FICvtJxAQFezMPxZn8aU3oxlTqbp5Jims8-ADvTDndle_qdE4";

		Playlist requestPayloadWithInvalidToken = playlistBuilder(FakerUtils.generateName(),
				FakerUtils.generateDescription(), false);

		Response response = PlaylistAPI.post(requestPayloadWithInvalidToken, invalidToken);
		RestUtil.assertStatusCode(response.getStatusCode(), StatusCode.CODE_401_INVALID_TOKEN.getStatusCode());

		ErrorRoot responseError = response.as(ErrorRoot.class);
		RestUtil.assertError(responseError, StatusCode.CODE_401_INVALID_TOKEN.getStatusCode(),
				StatusCode.CODE_401_INVALID_TOKEN.getMessage());
	}

	/*
	 * Endpoint: /users/{user_id}/playlists User should not be able to create
	 * playlist with expired access token
	 */
	@Link("https://api.spotify.com/v1/users/31rmk3j2lxqk3jbuztxtyrh3ng5a/playlists")
	@Link(name = "Create Playlists with Expired Access Token by User ID", type = "POST")
	@Issue("DE348595")
	@TmsLink("TMS989544")
	@Description("Test Description: User should not be able to create Playlist using expired Access Token")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 6, description = "Verify create playlist using expired access token")
	public void notAbleToCreatePlaylistUsingExpiredAccessToken() {
		String expiredToken = "BQCz0XmfN5jG_XaaHTFxqffx8UwkUPtugUvwHVeAjxQ4lZycwmrcaOHOWlYwZBSovymzgh_VfqnF6CFE-flR3YQpFv6jWRUz7f3b1jN4-jqWpJAKxnnkNiP7Wmg4Chzy9vtT_89fHr_8YT5aJNv2PNMn_mEcbuBegbyeAz444o2uTd4V8fjsT11rRbmlGSpWe92ymN0kghHsI4X05OGC37qKypSrZ-FICvtJxAQFezMPxZn8aU3oxlTqbp5Jims8-ADvTDndle_qdE4e";

		Playlist requestPayloadWithInvalidToken = playlistBuilder(FakerUtils.generateName(),
				FakerUtils.generateDescription(), false);

		Response response = PlaylistAPI.post(requestPayloadWithInvalidToken, expiredToken);
		RestUtil.assertStatusCode(response.getStatusCode(), StatusCode.CODE_401_EXPIRED_TOKEN.getStatusCode());

		ErrorRoot responseError = response.as(ErrorRoot.class);
		RestUtil.assertError(responseError, StatusCode.CODE_401_EXPIRED_TOKEN.getStatusCode(),
				StatusCode.CODE_401_EXPIRED_TOKEN.getMessage());
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
