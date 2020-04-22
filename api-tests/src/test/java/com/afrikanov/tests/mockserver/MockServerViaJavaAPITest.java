package com.afrikanov.tests.mockserver;

import com.afrikanov.tests.user.User;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.verify.VerificationTimes;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.Parameter.param;

public class MockServerViaJavaAPITest {

	private static ClientAndServer mockServer;

	@BeforeAll
	public static void startServer() {
		mockServer = startClientAndServer(1080);
		mockServer
				.when(
						request()
								.withMethod("GET")
								.withPath("/user")
								.withQueryStringParameters(
										param("id", "1")
								)
				)
				.respond(
						response()
								.withBody(
										new Gson().toJson(
												new User()
														.setId(1)
														.setName("Andrey")
														.setLastName("Afrikanov")
														.setAge(34)
										)

								)
				);
	}

	@AfterAll
	static void tearDownMockServer() {
		mockServer.stop();
	}

	@Test
	void checkUseExpectationAnswer() {

		RestAssured.defaultParser = Parser.JSON;

		given()
				.baseUri("http://localhost:1080")
				.basePath("/user")
				.param("id", 1)
				.when()
				.get()
				.then()
				.statusCode(200)
				.body("id", equalTo(1))
				.body("name", equalTo("Andrey"))
				.body("lastName", equalTo("Afrikanov"))
				.body("age", equalTo(34));

		mockServer.verify(
				request()
						.withMethod("GET")
						.withPath("/user")
						.withQueryStringParameters(
								param("id", "1")
						),
				VerificationTimes.exactly(1)
		);

	}

	@Test
	void checkRequestWithoutExpectation(){
		given()
				.baseUri("http://localhost:1080")
				.basePath("/user")
				.param("id", 2)
				.when()
				.get()
				.then()
				.statusCode(404);
	}
}
