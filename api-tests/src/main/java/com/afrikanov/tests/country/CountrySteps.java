package com.afrikanov.tests.country;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class CountrySteps {

	private static RequestSpecification requestSpec = new RequestSpecBuilder()
			.setBasePath("/rest/v2/name/{country}")
			.setAccept(ContentType.JSON)
			.setContentType(ContentType.JSON)
			.log(LogDetail.METHOD)
			.log(LogDetail.URI)
			.build();

	@Step("Get countries by name: {0}")
	public static CountriesValidator getCountriesByName(String name) {
		return new CountriesValidator(
				new CountriesRequest().send(name)
		);
	}
}
