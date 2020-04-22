package com.afrikanov.tests.country;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CountriesRequest {

	private static RequestSpecification requestSpec = new RequestSpecBuilder()
			.setBaseUri("https://restcountries.eu")
			.setBasePath("/rest/v2/name/{country}")
			.setAccept(ContentType.JSON)
			.log(LogDetail.METHOD)
			.log(LogDetail.URI)
			.log(LogDetail.HEADERS)
			.build();

	public ValidatableResponse send(String name) {
		return given()
				.pathParam("country", name)
				.spec(requestSpec)
				.when()
				.get()
				.then()
				.log().status()
				.log().body();
	}

}
