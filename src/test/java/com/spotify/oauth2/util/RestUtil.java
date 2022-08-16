package com.spotify.oauth2.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.spotify.oauth2.pojo.ErrorRoot;

import io.qameta.allure.Step;

public class RestUtil {

	@Step
	public static void assertStatusCode(int actualStatusCode, int expectedStatusCode) {
		assertThat(actualStatusCode, equalTo(expectedStatusCode));
	}

	@Step
	public static void assertSizeEqualTo(int actualSize, int expectedSize) {
		assertThat(actualSize, equalTo(expectedSize));
	}

	@Step
	public static void assertError(ErrorRoot responseError, int expectedStatucCode, String expectedMessage) {
		assertThat(responseError.getError().getStatus(), equalTo(expectedStatucCode));
		assertThat(responseError.getError().getMessage(), equalTo(expectedMessage));
	}
}
