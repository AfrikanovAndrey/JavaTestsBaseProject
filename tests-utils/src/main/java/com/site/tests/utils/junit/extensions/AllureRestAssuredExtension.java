package com.site.tests.utils.junit.extensions;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AllureRestAssuredExtension implements BeforeEachCallback {
	@Override
	public void beforeEach(ExtensionContext extensionContext) throws Exception {
		if (RestAssured.requestSpecification == null) {
			RestAssured.requestSpecification = new RequestSpecBuilder()
					.addFilter(new AllureRestAssured())
					.build();
		}
	}
}
