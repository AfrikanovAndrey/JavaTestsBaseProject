package com.afrikanov.tests.country;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static com.google.common.truth.Truth.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class CountriesValidator {

	private ValidatableResponse validatableResponse;

	public CountriesValidator(ValidatableResponse validatableResponse) {
		this.validatableResponse = validatableResponse;
	}

	public ValidatableResponse validate() {
		return validatableResponse;
	}

	@Step("Check status code: {0}")
	public CountriesValidator checkStatusCode(int expectedCode) {
		// Truth
		assertThat(validatableResponse.extract().statusCode()).isEqualTo(expectedCode);

		// Inner Hamcrest matcher
		validatableResponse.statusCode(expectedCode);
		return this;
	}

	@Step("Check countries count")
	public CountriesValidator checkCountriesCount(int count) {
		// Truth
		assertThat((int) validatableResponse.extract().jsonPath().get("size()")).isEqualTo(count);

		// Inner Hamcrest matcher
		validatableResponse.body("size()", equalTo(count));
		return this;
	}

	@Step("Check country [{0}] name: {1}")
	public CountriesValidator checkCountryName(int index, String expectedName) {
		validatableResponse.body("name[0]", equalTo(expectedName));
		return this;
	}

	@Step("Check country [{0}] capital's name: {1}")
	public CountriesValidator checkCountryCapitalName(int index, String expectedCapitalName) {
		validatableResponse.body("capital[0]", equalTo(expectedCapitalName));
		return this;
	}

	@Step("Check countries names")
	public CountriesValidator checkCountriesNames(String... expectedNames) {

		// Truth
		assertThat(
				validatableResponse.extract().jsonPath().getList("collect {it.name}")
		)
				.containsExactly(expectedNames);

		// Inner Hamcrest matcher
		assertThat(
				"Unexpected countries",
				validatableResponse.extract().response().path("collect {it.name}"),
				contains(expectedNames)
		);
		return this;
	}

	public Response getResponse() {
		return validatableResponse.extract().response();
	}


}
