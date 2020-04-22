package com.afrikanov.tests.countries;

import com.afrikanov.tests.country.CountriesRequest;
import com.site.tests.utils.junit.extensions.AllureRestAssuredExtension;
import io.restassured.response.Response;
import org.hamcrest.core.IsCollectionContaining;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.afrikanov.tests.country.CountrySteps.getCountriesByName;
import static com.google.common.truth.Truth.assertThat;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.CoreMatchers.equalTo;

/*
 *  REST Countries API : https://restcountries.eu/rest/v2
 */

@ExtendWith(AllureRestAssuredExtension.class)
public class RestAssuredCountriesTest extends BaseRestCountriesTest {

	static Stream<Arguments> countriesDataProvider() {
		return Stream.of(
				Arguments.of(
						// Country name
						"russia",
						// Official name
						"Russian Federation",
						// Capital
						"Moscow"
				),
				Arguments.of(
						// Country name
						"canada",
						// Official name
						"Canada",
						// Capital
						"Ottawa"
				)
		);
	}

	@ParameterizedTest(name = "Getting info about country: {0}")
	@MethodSource("countriesDataProvider")
	void checkGetCountryInfo(
			String country,
			String expectedOfficialName,
			String expectedCapitalName
	) {
		new CountriesRequest().send(country)
				.statusCode(200)
				.body("size()", equalTo(1))
				.body("name[0]", equalTo(expectedOfficialName))
				.body("capital[0]", equalTo(expectedCapitalName));
	}

	@Test
	@DisplayName("Getting several countries by partially name")
	void checkGetMultipleCountryInfo() {
		Response response = getCountriesByName("uk")
				.validate()
				.statusCode(200)
				.body("size()", equalTo(4))
				.body("collect {it.name}", IsCollectionContaining.hasItems(
						"Ukraine",
						"Cook Islands",
						"Korea (Democratic People's Republic of)",
						"United Kingdom of Great Britain and Northern Ireland"
						)
				)
				.extract().response();

		assertThat((List<String>) response.path("collect {it.name}"))
				.containsExactly(
						"Ukraine",
						"Cook Islands",
						"Korea (Democratic People's Republic of)",
						"United Kingdom of Great Britain and Northern Ireland"
				);
	}

	@Test
	@DisplayName("Getting nonexistent country")
	void checkGettingNonexistentCountry() {
		given()
				.param("id", "zzz")
				.basePath("http://yandex.ru/{id}")
				.get()
				.then()
				.statusCode(SC_NOT_FOUND);
	}


}
